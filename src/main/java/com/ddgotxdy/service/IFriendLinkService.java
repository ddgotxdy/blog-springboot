package com.ddgotxdy.service;

import com.ddgotxdy.dto.FriendLinkDTO;
import com.ddgotxdy.entity.FriendLink;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author ddgo
 * @description: 友链服务接口
 */
public interface IFriendLinkService extends IService<FriendLink> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendLinkDTO> listFriendLinks();
}
