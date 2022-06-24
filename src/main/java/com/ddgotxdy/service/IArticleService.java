package com.ddgotxdy.service;

import com.ddgotxdy.dto.ArticleHomeDTO;
import com.ddgotxdy.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ddgo
 * @description: 文章服务接口
 */
public interface IArticleService extends IService<Article> {

    /**
     * 查询首页文章
     *
     * @return 文章列表
     */
    List<ArticleHomeDTO> listArticles();
}
