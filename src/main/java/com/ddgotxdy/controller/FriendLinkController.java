package com.ddgotxdy.controller;

import com.ddgotxdy.dto.FriendLinkDTO;
import com.ddgotxdy.service.IFriendLinkService;
import com.ddgotxdy.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ddgo
 * @description: 友链控制器
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/links")
public class FriendLinkController {

    @Autowired
    private IFriendLinkService friendLinkService;
    /**
     * 查看友链列表
     *
     * @return {@link Result<FriendLinkDTO>} 友链列表
     */
    @ApiOperation(value = "查看友链列表")
    @GetMapping
    public Result<List<FriendLinkDTO>> listFriendLinks() {
        return Result.ok(friendLinkService.listFriendLinks());
    }
}
