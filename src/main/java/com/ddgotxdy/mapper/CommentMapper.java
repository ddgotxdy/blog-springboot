package com.ddgotxdy.mapper;

import com.ddgotxdy.dto.ReplyCountDTO;
import com.ddgotxdy.dto.ReplyDTO;
import com.ddgotxdy.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

/**
 * @author ddgo
 * @description: 评论Mapper 接口
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查看评论id集合下的回复
     *
     * @param commentIdList 评论id集合
     * @return 回复集合
     */
    List<ReplyDTO> listReplies(@Param("commentIdList") List<Integer> commentIdList);

    /**
     * 根据评论id查询回复总量
     *
     * @param commentIdList 评论id集合
     * @return 回复数量
     */
    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);
}
