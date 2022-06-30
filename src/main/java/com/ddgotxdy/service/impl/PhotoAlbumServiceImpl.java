package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddgotxdy.dto.PhotoAlbumDTO;
import com.ddgotxdy.entity.PhotoAlbum;
import com.ddgotxdy.mapper.PhotoAlbumMapper;
import com.ddgotxdy.service.IPhotoAlbumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.ddgotxdy.enums.TalkStatusEnum.PUBLIC;

/**
 * @author ddgo
 * @description: 相册 服务实现类
 */
@Service
public class PhotoAlbumServiceImpl extends ServiceImpl<PhotoAlbumMapper, PhotoAlbum> implements IPhotoAlbumService {

    @Resource
    private PhotoAlbumMapper photoAlbumMapper;

    @Override
    public List<PhotoAlbumDTO> listPhotoAlbums() {
        // 查询相册列表
        List<PhotoAlbum> photoAlbumList = photoAlbumMapper.selectList(new LambdaQueryWrapper<PhotoAlbum>()
                .eq(PhotoAlbum::getStatus, PUBLIC.getStatus())
                .orderByDesc(PhotoAlbum::getId));
        return BeanCopyUtil.copyList(photoAlbumList, PhotoAlbumDTO.class);
    }
}
