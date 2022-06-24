package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author ddgo
 * @description: 说说
 */
@Getter
@Setter
@TableName("tb_talk")
@ApiModel(value = "Talk对象", description = "说说")
public class Talk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("说说id")
    @TableId
    private Integer id;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("说说内容")
    private String content;

    @ApiModelProperty("图片")
    private String images;

    @ApiModelProperty("是否置顶")
    private Integer isTop;

    @ApiModelProperty("状态 1.公开 2.私密")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
