package com.ddgotxdy.service;

import com.ddgotxdy.entity.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.PageVO;

import java.util.List;

/**
 * @author ddgo
 * @description: 页面服务
 */
public interface IPageService extends IService<Page> {

    /**
     * 获取页面列表
     *
     * @return {@link List<PageVO>} 页面列表
     */
    List<PageVO> listPages();
}
