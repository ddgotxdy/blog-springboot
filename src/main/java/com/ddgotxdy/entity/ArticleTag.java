package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author ddgo
 * @description: 文章标签一对多对象
 */
@Getter
@Setter
@TableName("tb_article_tag")
@ApiModel(value = "ArticleTag对象", description = "文章标签一对多对象")
public class ArticleTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @ApiModelProperty("文章id")
    private Integer articleId;

    @ApiModelProperty("标签id")
    private Integer tagId;


}
