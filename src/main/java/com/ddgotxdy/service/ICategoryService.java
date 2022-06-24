package com.ddgotxdy.service;

import com.ddgotxdy.dto.CategoryDTO;
import com.ddgotxdy.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.PageResult;

/**
 * @author ddgo
 * @description: 分类服务类
 */
public interface ICategoryService extends IService<Category> {

    /**
     * 查询分类列表
     *
     * @return 分类列表
     */
    PageResult<CategoryDTO> listCategories();
}
