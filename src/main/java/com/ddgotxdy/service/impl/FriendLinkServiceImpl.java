package com.ddgotxdy.service.impl;

import com.ddgotxdy.dto.FriendLinkDTO;
import com.ddgotxdy.entity.FriendLink;
import com.ddgotxdy.mapper.FriendLinkMapper;
import com.ddgotxdy.service.IFriendLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ddgo
 * @description: 友链服务实现类
 */
@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {
    @Resource
    FriendLinkMapper friendLinkMapper;

    @Override
    public List<FriendLinkDTO> listFriendLinks() {
        List<FriendLink> friendLinkList = friendLinkMapper.selectList(null);
        List<FriendLinkDTO> friendLinkDTOList = new ArrayList<>(16);
        friendLinkList.forEach(friendLink -> {
            FriendLinkDTO friendLinkDTO = new FriendLinkDTO();
            BeanUtils.copyProperties(friendLink, friendLinkDTO);
            friendLinkDTOList.add(friendLinkDTO);
        });
        return friendLinkDTOList;
    }
}
