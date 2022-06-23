package com.ddgotxdy.service;

import com.ddgotxdy.entity.Talk;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
