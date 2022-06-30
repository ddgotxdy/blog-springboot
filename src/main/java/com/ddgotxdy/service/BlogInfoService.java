package com.ddgotxdy.service;

import com.ddgotxdy.dto.BlogHomeInfoDTO;

/**
 * @author: ddgo
 * @description: 博客信息服务类接口
 */
public interface BlogInfoService {
    /**
     * 获取首页数据
     *
     * @return 博客首页信息
     */
    BlogHomeInfoDTO getBlogHomeInfo();

}
