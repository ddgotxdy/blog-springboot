package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddgotxdy.dto.ArticleHomeDTO;
import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.entity.Article;
import com.ddgotxdy.entity.ArticleTag;
import com.ddgotxdy.entity.Tag;
import com.ddgotxdy.mapper.ArticleMapper;
import com.ddgotxdy.mapper.ArticleTagMapper;
import com.ddgotxdy.mapper.CategoryMapper;
import com.ddgotxdy.mapper.TagMapper;
import com.ddgotxdy.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ddgo
 * @description: 文章服务实现类
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private TagMapper tagMapper;


    @Override
    public List<ArticleHomeDTO> listArticles() {
        // 获取分页的文章
        Page<Article> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        Page<Article> articlePage = articleMapper.selectPage(page, null);
        List<Article> articleList = articlePage.getRecords();
        // 转换为DTO对象
        List<ArticleHomeDTO> articleHomeDTOList = new ArrayList<>(16);
        articleList.forEach(article -> {
            ArticleHomeDTO articleHomeDTO = new ArticleHomeDTO();
            BeanUtils.copyProperties(article, articleHomeDTO);
            // 根据分类id查询分类名称
            articleHomeDTO.setCategoryName(
                    categoryMapper.
                            selectById(article.getCategoryId())
                                        .getCategoryName());
            // 设置所有的标签列表
            // 获取当前文章的所有标签id
            LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<ArticleTag>();
            queryWrapper.eq(ArticleTag::getArticleId, article.getId());
            List<ArticleTag> articleTagList = articleTagMapper.selectList(queryWrapper);
            // 根据id获取标签名称
            List<TagDTO> tagDTOList = new ArrayList<>(16);
            articleTagList.forEach(articleTag -> {
                Tag tag = tagMapper.selectById(articleTag.getTagId());
                TagDTO tagDTO = new TagDTO();
                BeanUtils.copyProperties(tag, tagDTO);
                tagDTOList.add(tagDTO);
            });
            articleHomeDTO.setTagDTOList(tagDTOList);
            articleHomeDTOList.add(articleHomeDTO);
        });
        return articleHomeDTOList;
    }
}
