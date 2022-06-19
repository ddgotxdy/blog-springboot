package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ddgo
 * @description: admin前端菜单
 */
@Getter
@Setter
@TableName("tb_menu")
@ApiModel(value = "Menu对象", description = "admin前端菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    private Integer id;

    @ApiModelProperty("菜单名")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("菜单icon")
    private String icon;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("排序")
    private Boolean orderNum;

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty("是否隐藏  0否1是")
    private Boolean isHidden;


}
