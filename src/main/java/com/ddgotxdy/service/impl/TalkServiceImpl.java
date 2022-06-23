package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddgotxdy.entity.Talk;
import com.ddgotxdy.mapper.TalkMapper;
import com.ddgotxdy.service.ITalkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.HTMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author ddgo
 * @description: 说说服务接口实现类
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements ITalkService {

    @Resource
    TalkMapper talkMapper;

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
}
