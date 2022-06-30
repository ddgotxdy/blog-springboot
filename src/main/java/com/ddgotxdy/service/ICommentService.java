package com.ddgotxdy.service;

import com.ddgotxdy.dto.CommentDTO;
import com.ddgotxdy.dto.ReplyDTO;
import com.ddgotxdy.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ddgotxdy.vo.CommentVO;
import com.ddgotxdy.vo.PageResult;

import java.util.List;

/**
 * @author ddgo
 * @description: 评论服务类
 */
public interface ICommentService extends IService<Comment> {
    /**
     * 查看评论
     *
     * @param commentVO 评论信息
     * @return 评论列表
     */
    PageResult<CommentDTO> listComments(CommentVO commentVO);

    /**
     * 查看评论下的回复
     *
     * @param commentId 评论id
     * @return 回复列表
     */
    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    /**
     * 添加评论
     *
     * @param commentVO 评论对象
     */
    void saveComment(CommentVO commentVO);

    /**
     * 点赞评论
     *
     * @param commentId 评论id
     */
    void saveCommentLike(Integer commentId);
}
