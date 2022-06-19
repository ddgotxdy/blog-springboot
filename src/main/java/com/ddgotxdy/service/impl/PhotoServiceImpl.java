package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.Photo;
import com.ddgotxdy.mapper.PhotoMapper;
import com.ddgotxdy.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 照片 服务实现类
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {

}
