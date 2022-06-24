package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddgotxdy.dto.CategoryDTO;
import com.ddgotxdy.entity.Article;
import com.ddgotxdy.entity.Category;
import com.ddgotxdy.mapper.ArticleMapper;
import com.ddgotxdy.mapper.CategoryMapper;
import com.ddgotxdy.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.PageUtil;
import com.ddgotxdy.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ddgo
 * @description: 分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageResult<CategoryDTO> listCategories() {
        // 分页查询类别
        Page<Category> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<Category> categoryPage = categoryMapper.selectPage(page, null);
        List<Category> categories = categoryPage.getRecords();
        List<CategoryDTO> categoryDTOS = new ArrayList<>(16);
        // 查询每一类的文章数
        categories.forEach(category -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(category, categoryDTO);
            Long count = articleMapper.selectCount(
                    new LambdaQueryWrapper<Article>()
                            .eq(Article::getCategoryId, category.getId()));
            categoryDTO.setArticleCount(count);
            categoryDTOS.add(categoryDTO);
        });

        return new PageResult<>(categoryDTOS, categoryPage.getTotal());
    }
}
