package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.entity.ArticleTag;
import com.ddgotxdy.entity.Tag;
import com.ddgotxdy.mapper.ArticleTagMapper;
import com.ddgotxdy.mapper.TagMapper;
import com.ddgotxdy.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddgotxdy.util.BeanCopyUtil;
import com.ddgotxdy.util.PageUtil;
import com.ddgotxdy.vo.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ddgo
 * @description: 标签服务的实现类
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<TagDTO> getTagDTOListByArticleId(Integer articleId) {
        // 获取当前文章的所有标签id
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, articleId);
        List<ArticleTag> articleTagList = articleTagMapper.selectList(queryWrapper);
        // 根据id获取标签名称
        List<TagDTO> tagDTOList = new ArrayList<>(16);
        articleTagList.forEach(articleTag -> {
            Tag tag = tagMapper.selectById(articleTag.getTagId());
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            tagDTOList.add(tagDTO);
        });
        return tagDTOList;
    }

    @Override
    public PageResult<TagDTO> listTags() {
        // 封装page
        Page<Tag> page = new Page<>(PageUtil.getCurrent(), PageUtil.getSize());
        // 获得page信息
        Page<Tag> tagPage = tagMapper.selectPage(page, null);
        List<Tag> tagList = tagPage.getRecords();
        // 转换为DTO
        List<TagDTO> tagDTOList = BeanCopyUtil.copyList(tagList, TagDTO.class);
        return new PageResult<>(tagDTOList, tagPage.getTotal());
    }
}
