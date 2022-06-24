package com.ddgotxdy.service;

import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ddgo
 * @description: 标签服务类
 */
public interface ITagService extends IService<Tag> {

    /**
     * 通过文章id获取标签列表
     * @param articleId 文章id
     * @return 标签列表
     */
    List<TagDTO> getTagDTOListByArticleId(Integer articleId);

}
