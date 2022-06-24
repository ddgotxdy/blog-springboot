package com.ddgotxdy.controller;

import com.ddgotxdy.dto.ArchiveDTO;
import com.ddgotxdy.dto.ArticleDTO;
import com.ddgotxdy.dto.ArticleHomeDTO;
import com.ddgotxdy.dto.ArticlePreviewListDTO;
import com.ddgotxdy.service.IArticleService;
import com.ddgotxdy.vo.ConditionVO;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ddgo
 * @description: 文章控制器
 */
@Api(tags = "文章模块")
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    /**
     * 查看首页文章
     *
     * @return {@link Result<ArticleHomeDTO>} 首页文章列表
     */
    @ApiOperation(value = "查看首页文章")
    @GetMapping
    public Result<List<ArticleHomeDTO>> listArticles() {
        return Result.ok(articleService.listArticles());
    }

    /**
     * 查看文章归档
     *
     * @return {@link Result<ArchiveDTO>} 文章归档列表
     */
    @ApiOperation(value = "查看文章归档")
    @GetMapping("/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }

    /**
     * 根据条件查询文章
     *
     * @param condition 条件
     * @return {@link Result<ArticlePreviewListDTO>} 文章列表
     */
    @ApiOperation(value = "根据条件查询文章")
    @GetMapping("/condition")
    public Result<ArticlePreviewListDTO> listArticlesByCondition(ConditionVO condition) {
        return Result.ok(articleService.listArticlesByCondition(condition));
    }

    /**
     * 根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link Result<ArticleDTO>} 文章信息
     */
    @ApiOperation(value = "根据id查看文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/{articleId}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }

}
