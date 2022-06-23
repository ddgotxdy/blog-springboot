package com.ddgotxdy.controller;

import com.ddgotxdy.dto.TalkDTO;
import com.ddgotxdy.service.ITalkService;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ddgo
 * @description: 说说控制器
 */
@RestController
@RequestMapping("/talks")
public class TalkController {

    @Autowired
    ITalkService talkService;

    /**
     * 查看首页说说
     * @return {@link Result<String>}
     */
    @ApiOperation(value = "查看首页说说")
    @GetMapping("/home")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    @ApiOperation(value = "查看说说列表")
    @GetMapping
    public Result<PageResult<TalkDTO>> listTalks() {
        return Result.ok(talkService.listTalks());
    }

}
