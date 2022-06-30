package com.ddgotxdy.service;

import com.ddgotxdy.dto.PhotoDTO;
import com.ddgotxdy.entity.Photo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ddgo
 * @description: 照片服务类接口
 */
public interface IPhotoService extends IService<Photo> {

    /**
     * 根据相册id查看照片列表
     *
     * @param albumId 相册id
     * @return {@link List <PhotoDTO>} 照片列表
     */
    PhotoDTO listPhotosByAlbumId(Integer albumId);
}
