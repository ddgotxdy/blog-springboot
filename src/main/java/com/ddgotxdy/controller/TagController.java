package com.ddgotxdy.controller;

import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.service.ITagService;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ddgo
 * @description: 标签控制器
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private ITagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link Result<TagDTO>} 标签列表
     */
    @ApiOperation(value = "查询标签列表")
    @GetMapping
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }
}
