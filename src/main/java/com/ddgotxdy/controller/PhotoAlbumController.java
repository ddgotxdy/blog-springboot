package com.ddgotxdy.controller;

import com.ddgotxdy.dto.PhotoAlbumDTO;
import com.ddgotxdy.service.IPhotoAlbumService;
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
 * @description: 相册控制器
 */
@Api(tags = "相册模块")
@RestController
public class PhotoAlbumController {
    @Autowired
    private IPhotoAlbumService photoAlbumService;

    /**
     * 获取相册列表
     *
     * @return {@link Result<PhotoAlbumDTO>} 相册列表
     */
    @ApiOperation(value = "获取相册列表")
    @GetMapping("/photos/albums")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbums() {
        return Result.ok(photoAlbumService.listPhotoAlbums());
    }
}
