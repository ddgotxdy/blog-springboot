package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.Category;
import com.ddgotxdy.mapper.CategoryMapper;
import com.ddgotxdy.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
