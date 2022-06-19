package com.ddgotxdy.service.impl;

import com.ddgotxdy.entity.Comment;
import com.ddgotxdy.mapper.CommentMapper;
import com.ddgotxdy.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
