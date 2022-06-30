package com.ddgotxdy.service;

import com.ddgotxdy.dto.BlogHomeInfoDTO;
import com.ddgotxdy.vo.WebsiteConfigVO;

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

    /**
     * 获取关于我内容
     *
     * @return 关于我内容
     */
    String getAbout();

    /**
     * 上传访客信息
     */
    void report();

    /**
     * 获取网站配置
     *
     * @return {@link WebsiteConfigVO} 网站配置
     */
    WebsiteConfigVO getWebsiteConfig();

}
