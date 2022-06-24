package com.ddgotxdy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.entity.ArticleTag;
import com.ddgotxdy.entity.Tag;
import com.ddgotxdy.mapper.ArticleTagMapper;
import com.ddgotxdy.mapper.TagMapper;
import com.ddgotxdy.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
