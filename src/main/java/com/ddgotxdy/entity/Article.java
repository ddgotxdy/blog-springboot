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
 * @description: 文章实体类对象
 */
@Getter
@Setter
@TableName("tb_article")
@ApiModel(value = "Article对象", description = "文章实体类对象")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @ApiModelProperty("作者id")
    private Integer userId;

    @ApiModelProperty("文章分类id")
    private Integer categoryId;

    @ApiModelProperty("文章缩略图")
    private String articleCover;

    @ApiModelProperty("文章标题")
    private String articleTitle;

    @ApiModelProperty("文章内容")
    private String articleContent;

    @ApiModelProperty("文章类型 1原创 2转载 3翻译")
    private Integer type;

    @ApiModelProperty("原文链接")
    private String originalUrl;

    @ApiModelProperty("是否置顶 0否 1是")
    private Integer isTop;

    @ApiModelProperty("是否删除  0否 1是")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty("状态值 1公开 2私密 3评论可见")
    private Integer status;

    @ApiModelProperty("发表时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
