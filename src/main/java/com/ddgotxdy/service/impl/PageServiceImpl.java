package com.ddgotxdy.service.impl;

import com.alibaba.fastjson.JSON;
import com.ddgotxdy.entity.Page;
import com.ddgotxdy.mapper.PageMapper;
import com.ddgotxdy.service.IPageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.BeanCopyUtil;
import com.ddgotxdy.util.RedisUtil;
import com.ddgotxdy.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.ddgotxdy.constant.RedisPrefixConst.PAGE_COVER;

/**
 * @author: ddgo
 * @description: 页面服务实现类
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements IPageService {
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private PageMapper pageMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        // 查找缓存信息，不存在则从mysql读取，更新缓存
        Object pageList = redisUtil.get(PAGE_COVER);
        if (Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            pageVOList = BeanCopyUtil.copyList(pageMapper.selectList(null), PageVO.class);
            redisUtil.set(PAGE_COVER, JSON.toJSONString(pageVOList));
        }
        return pageVOList;
    }
}
