package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.dto.CommentDTO;
import com.ddgotxdy.dto.ReplyCountDTO;
import com.ddgotxdy.dto.ReplyDTO;
import com.ddgotxdy.entity.Comment;
import com.ddgotxdy.entity.UserInfo;
import com.ddgotxdy.mapper.CommentMapper;
import com.ddgotxdy.mapper.UserInfoMapper;
import com.ddgotxdy.service.BlogInfoService;
import com.ddgotxdy.service.ICommentService;
import com.ddgotxdy.util.BeanCopyUtil;
import com.ddgotxdy.util.HTMLUtil;
import com.ddgotxdy.util.PageUtil;
import com.ddgotxdy.util.RedisUtil;
import com.ddgotxdy.vo.CommentVO;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.vo.WebsiteConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ddgotxdy.constant.CommonConst.FALSE;
import static com.ddgotxdy.constant.CommonConst.TRUE;
import static com.ddgotxdy.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.ddgotxdy.constant.RedisPrefixConst.COMMENT_USER_LIKE;

/**
 * @author ddgo
 * @description: 评论服务实现类
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BlogInfoService blogInfoService;


    @Override
    public PageResult<CommentDTO> listComments(CommentVO commentVO) {
        // 分页查询评论数据
        Page<Comment> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<Comment> commentPage = commentMapper.selectPage(page, new LambdaQueryWrapper<Comment>()
                .select(Comment::getId, Comment::getUserId, Comment::getCommentContent, Comment::getCreateTime)
                // 文章id
                .eq(commentVO.getTopicId() != null, Comment::getTopicId, commentVO.getTopicId())
                // 评论的类型
                .eq(Comment::getType, commentVO.getType())
                // 是否审核
                .eq(Comment::getIsReview, 1)
                // 一级评论
                .isNull(Comment::getParentId)
                // 按照id（时间）降序排序
                .orderByDesc(Comment::getId));
        List<Comment> commentList = commentPage.getRecords();
        if (CollectionUtils.isEmpty(commentList)) {
            return new PageResult<>();
        }
        List<CommentDTO> commentDTOList = new ArrayList<>(10);
        // 转换为CommentDTO对象
        commentList.forEach(comment -> {
            CommentDTO commentDTO = BeanCopyUtil.copyObject(comment, CommentDTO.class);
            UserInfo userInfo = userInfoMapper.selectById(comment.getUserId());
            commentDTO.setAvatar(userInfo.getAvatar());
            commentDTO.setNickname(userInfo.getNickname());
            commentDTO.setWebSite(userInfo.getWebSite());
            commentDTOList.add(commentDTO);
        });

        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisUtil.hGetAll(COMMENT_LIKE_COUNT);
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream()
                .map(CommentDTO::getId)
                .collect(Collectors.toList());
        // 根据评论id集合查询回复数据
        List<ReplyDTO> replyDTOList = commentMapper.listReplies(commentIdList);

        // 封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replyMap = replyDTOList.stream()
                .collect(Collectors.groupingBy(ReplyDTO::getParentId));

        // 根据评论id查询回复量
        Map<Integer, Integer> replyCountMap = commentMapper.listReplyCountByCommentId(commentIdList)
                .stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replyMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentDTOList, commentPage.getTotal());
    }

    @Override
    public List<ReplyDTO> listRepliesByCommentId(Integer commentId) {
        return null;
    }

    @Override
    public void saveComment(CommentVO commentVO) {
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtil.filter(commentVO.getCommentContent()));
        Comment comment = new Comment();
        // TODO 更换用户
        comment.setUserId(1);
        comment.setReplyUserId(commentVO.getReplyUserId());
        comment.setTopicId(commentVO.getTopicId());
        comment.setCommentContent(commentVO.getCommentContent());
        comment.setParentId(commentVO.getParentId());
        comment.setType(commentVO.getType());
        comment.setIsReview(isReview == TRUE ? FALSE : TRUE);
        commentMapper.insert(comment);

        // 判断是否开启邮箱通知,通知用户 TODO
//        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
//            CompletableFuture.runAsync(() -> notice(comment));
//        }
    }

    @Override
    public void saveCommentLike(Integer commentId) {
        // 判断是否点赞 TODO 换成用户id
        String commentLikeKey = COMMENT_USER_LIKE + 1;
        if (redisUtil.sIsMember(commentLikeKey, commentId)) {
            // 点过赞则删除评论id
            redisUtil.sRemove(commentLikeKey, commentId);
            // 评论点赞量-1
            redisUtil.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 未点赞则增加评论id
            redisUtil.sAdd(commentLikeKey, commentId);
            // 评论点赞量+1
            redisUtil.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }
}
