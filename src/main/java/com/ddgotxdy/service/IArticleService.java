package com.ddgotxdy.service;

import com.ddgotxdy.dto.ArchiveDTO;
import com.ddgotxdy.dto.ArticleDTO;
import com.ddgotxdy.dto.ArticleHomeDTO;
import com.ddgotxdy.dto.ArticlePreviewListDTO;
import com.ddgotxdy.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.ConditionVO;
import com.ddgotxdy.vo.PageResult;

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

    /**
     * 查询文章归档
     *
     * @return 文章归档
     */
    PageResult<ArchiveDTO> listArchives();

    /**
     * 根据条件查询文章列表
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition);

    /**
     * 根据id查看文章
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);
}
