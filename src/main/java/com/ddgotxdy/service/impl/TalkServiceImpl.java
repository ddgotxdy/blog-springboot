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
            TalkDTO talkDTO = new TalkDTO();
            BeanUtils.copyProperties(item, talkDTO);
            // 查询发表说说的用户头像名称
            LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userInfoLambdaQueryWrapper.eq(UserInfo::getId, item.getUserId());
            UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaQueryWrapper);
            talkDTO.setAvatar(userInfo.getAvatar());
            talkDTO.setNickname(userInfo.getNickname());

            talkDTOList.add(talkDTO);
        });

        // 查询说说评论量
        Map<String, Object> commentCountMap = redisUtil.hGetAll(TALK_COMMENT_COUNT);
        // 查询说说点赞量
        Map<String, Object> likeCountMap = redisUtil.hGetAll(TALK_LIKE_COUNT);

        talkDTOList.forEach(item -> {
            // 喜欢的数字处理
            Object likeCount = likeCountMap.get(item.getId().toString());
            if(Objects.nonNull(likeCount)) {
                item.setLikeCount((Integer) likeCount);
            } else {
                item.setLikeCount(0);
            }

            Object CommentCount = commentCountMap.get(item.getId().toString());
            if(Objects.nonNull(CommentCount)) {
                item.setCommentCount((Integer) CommentCount);
            } else {
                item.setCommentCount(0);
            }

            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtil.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });

        return new PageResult<>(talkDTOList, iPage.getTotal());
    }
}
