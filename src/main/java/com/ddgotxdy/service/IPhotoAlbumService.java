package com.ddgotxdy.service;

import com.ddgotxdy.dto.PhotoAlbumDTO;
import com.ddgotxdy.entity.PhotoAlbum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ddgo
 * @description: 相册服务类接口
 */
public interface IPhotoAlbumService extends IService<PhotoAlbum> {

    /**
     * 获取相册列表
     *
     * @return {@link List <PhotoAlbumDTO>}相册列表
     */
    List<PhotoAlbumDTO> listPhotoAlbums();

}
