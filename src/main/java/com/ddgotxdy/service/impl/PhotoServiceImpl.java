package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddgotxdy.dto.PhotoDTO;
import com.ddgotxdy.entity.Photo;
import com.ddgotxdy.entity.PhotoAlbum;
import com.ddgotxdy.exception.ServerException;
import com.ddgotxdy.mapper.PhotoAlbumMapper;
import com.ddgotxdy.mapper.PhotoMapper;
import com.ddgotxdy.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author ddgo
 * @description: 照片服务实现类
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

    @Resource
    private PhotoAlbumMapper photoAlbumMapper;
    @Resource
    private PhotoMapper photoMapper;

    @Override
    public PhotoDTO listPhotosByAlbumId(Integer albumId) {
        // 查询相册信息
        PhotoAlbum photoAlbum = photoAlbumMapper.selectOne(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getId, albumId)
                .eq(PhotoAlbum::getStatus, PUBLIC.getStatus()));
        if (Objects.isNull(photoAlbum)) {
            throw new ServerException("相册不存在");
        }
        // 查询照片列表
        Page<Photo> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        List<String> photoList = photoMapper.selectPage(page, new LambdaQueryWrapper<Photo>()
                        .select(Photo::getPhotoSrc)
                        .eq(Photo::getAlbumId, albumId)
                        .orderByDesc(Photo::getId))
                .getRecords()
                .stream()
                .map(Photo::getPhotoSrc)
                .collect(Collectors.toList());
        return PhotoDTO.builder()
                .photoAlbumCover(photoAlbum.getAlbumCover())
                .photoAlbumName(photoAlbum.getAlbumName())
                .photoList(photoList)
                .build();
    }
}
