package com.ddgotxdy.controller;

import com.ddgotxdy.dto.BlogHomeInfoDTO;
import com.ddgotxdy.service.BlogInfoService;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ddgo
 * @description: 博客信息管理
 */
@Api(tags = "博客基本信息模块")
@RestController
public class BlogInfoController {

    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 查看博客信息
     *
     * @return {@link Result<BlogHomeInfoDTO>} 博客信息
     */
    @ApiOperation(value = "查看博客信息")
    @GetMapping
    public Result<BlogHomeInfoDTO> getBlogHomeInfo() {
        return Result.ok(blogInfoService.getBlogHomeInfo());
    }

}
