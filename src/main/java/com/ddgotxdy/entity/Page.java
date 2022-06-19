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
 * @description: 页面修改
 */
@Getter
@Setter
@TableName("tb_page")
@ApiModel(value = "Page对象", description = "页面修改")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("页面id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("页面名")
    private String pageName;

    @ApiModelProperty("页面标签")
    private String pageLabel;

    @ApiModelProperty("页面封面")
    private String pageCover;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
