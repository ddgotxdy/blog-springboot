package com.ddgotxdy.controller;

import com.ddgotxdy.dto.ArchiveDTO;
import com.ddgotxdy.dto.ArticleHomeDTO;
import com.ddgotxdy.service.IArticleService;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/articles/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }

}
