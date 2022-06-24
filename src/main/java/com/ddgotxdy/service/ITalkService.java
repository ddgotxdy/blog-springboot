package com.ddgotxdy.service;

import com.ddgotxdy.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.PageResult;
import com.ddgotxdy.dto.TalkDTO;

import java.util.List;

/**
 * @author ddgo
 * @description: 说说服务接口
 */
public interface ITalkService extends IService<Talk> {

    /**
     * 获取首页说说列表
     * @return {@link List<String>} 说说列表
     */
    List<String> listHomeTalks();

    /**
     * 获取说说列表
     *
     * @return {@link PageResult<TalkDTO>} 说说列表
     */
    PageResult<TalkDTO> listTalks();

    /**
     * 根据id查看说说
     *
     * @param talkId 说说id
     * @return {@link TalkDTO} 说说信息
     */
    TalkDTO getTalkById(Integer talkId);



}
