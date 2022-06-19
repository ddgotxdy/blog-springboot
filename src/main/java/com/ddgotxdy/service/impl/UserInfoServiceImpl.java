package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.UserInfo;
import com.ddgotxdy.mapper.UserInfoMapper;
import com.ddgotxdy.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
