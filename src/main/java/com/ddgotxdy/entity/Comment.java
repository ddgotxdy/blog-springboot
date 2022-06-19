package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ddgo
 * @description: 评论
 */
@Getter
@Setter
@TableName("tb_comment")
@ApiModel(value = "Comment对象", description = "评论")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    private Integer id;

    @ApiModelProperty("评论用户Id")
    private Integer userId;

    @ApiModelProperty("评论主题id")
    private Integer topicId;

    @ApiModelProperty("评论内容")
    private String commentContent;

    @ApiModelProperty("回复用户id")
    private Integer replyUserId;

    @ApiModelProperty("父评论id")
    private Integer parentId;

    @ApiModelProperty("评论类型 1.文章 2.友链 3.说说")
    private Integer type;

    @ApiModelProperty("是否删除  0否 1是")
    @TableLogic
    private Integer isDelete;

    @ApiModelProperty("是否审核")
    private Boolean isReview;

    @ApiModelProperty("评论时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
