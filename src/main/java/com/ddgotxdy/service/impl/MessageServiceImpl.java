package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.Message;
import com.ddgotxdy.mapper.MessageMapper;
import com.ddgotxdy.service.IMessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
