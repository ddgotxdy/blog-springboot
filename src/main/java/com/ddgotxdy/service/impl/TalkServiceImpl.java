package com.ddgotxdy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.dto.CommentCountDTO;
import com.ddgotxdy.dto.TalkDTO;
import com.ddgotxdy.entity.Talk;
import com.ddgotxdy.entity.UserInfo;
import com.ddgotxdy.exception.ServerException;
import com.ddgotxdy.mapper.CommentMapper;
import com.ddgotxdy.mapper.TalkMapper;
import com.ddgotxdy.mapper.UserInfoMapper;
import com.ddgotxdy.service.ITalkService;
import com.ddgotxdy.util.CommonUtil;
import com.ddgotxdy.util.HTMLUtil;
import com.ddgotxdy.util.PageUtil;
import com.ddgotxdy.util.RedisUtil;
import com.ddgotxdy.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ddgotxdy.constant.RedisPrefixConst.TALK_COMMENT_COUNT;
import static com.ddgotxdy.constant.RedisPrefixConst.TALK_LIKE_COUNT;
import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author ddgo
 * @description: 说说服务接口实现类
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements ITalkService {

    @Resource
    TalkMapper talkMapper;
    @Resource
    UserInfoMapper userInfoMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<String> listHomeTalks() {
        // 查询最新10条说说
        LambdaQueryWrapper<Talk> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                    // 公开
                    .eq(Talk::getStatus, PUBLIC.getStatus())
                    // 按照是否置顶排序
                    .orderByDesc(Talk::getIsTop)
                    // 按照发布的顺序排序
                    .orderByDesc(Talk::getId)
                    // 限制10个
                    .last("limit 10");

        return talkMapper.selectList(queryWrapper)
                .stream()
                // 只允许200的大小,并且删除带html的标签
                .map(item -> item.getContent().length() > 200 ?
                        HTMLUtil.deleteHMTLTag(item.getContent().substring(0, 200))
                        : HTMLUtil.deleteHMTLTag(item.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<TalkDTO> listTalks() {
        // 查询语句
        LambdaQueryWrapper<Talk> queryWrapper = new LambdaQueryWrapper<>();
        // 分页对象
        Page<Talk> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        queryWrapper.eq(Talk::getStatus, PUBLIC.getStatus());
        IPage<Talk> iPage = talkMapper.selectPage(page, queryWrapper);
        List<Talk> talkList = iPage.getRecords();
        List<TalkDTO> talkDTOList = new ArrayList<>();
        talkList.forEach(item ->{
            talkDTOList.add(getTalkDTO(item));
        });
        return new PageResult<>(talkDTOList, iPage.getTotal());
    }

    @Override
    public TalkDTO getTalkById(Integer talkId) {
        Talk talk = talkMapper.selectById(talkId);
        if (Objects.isNull(talk)) {
            throw new ServerException("说说不存在");
        }
        return getTalkDTO(talk);
    }

    /**
     * talk 2 talkDTO
     * @param talk 说说对象
     * @return TalkDTO
     */
    private TalkDTO getTalkDTO(Talk talk) {
        TalkDTO talkDTO = new TalkDTO();
        BeanUtils.copyProperties(talk, talkDTO);
        // 查询发表说说的用户头像名称
        UserInfo userInfo = userInfoMapper.selectById(talk.getUserId());
        talkDTO.setAvatar(userInfo.getAvatar());
        talkDTO.setNickname(userInfo.getNickname());

        // 查询说说点赞量
        Object likeCount = redisUtil.hGet(TALK_COMMENT_COUNT, talk.getId().toString());
        if(Objects.nonNull(likeCount)) {
            talkDTO.setLikeCount((Integer) likeCount);
        } else {
            talkDTO.setLikeCount(0);
        }

        // 评论量处理
        Object commentCount = redisUtil.hGet(TALK_LIKE_COUNT, talk.getId().toString());
        if(Objects.nonNull(commentCount)) {
            talkDTO.setCommentCount((Integer) commentCount);
        } else {
            talkDTO.setCommentCount(0);
        }
        // 转换图片格式
        if (Objects.nonNull(talkDTO.getImages())) {
            talkDTO.setImgList(CommonUtil.castList(JSON.parseObject(talkDTO.getImages(), List.class), String.class));
        }
        return talkDTO;
    }
}
