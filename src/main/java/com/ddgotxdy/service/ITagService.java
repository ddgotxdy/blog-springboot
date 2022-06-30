package com.ddgotxdy.service;

import com.ddgotxdy.dto.TagDTO;
import com.ddgotxdy.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.PageResult;

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

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    PageResult<TagDTO> listTags();
}
