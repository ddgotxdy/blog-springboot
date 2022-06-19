package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Getter
@Setter
@TableName("tb_menu")
@ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
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
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("排序")
    private Boolean orderNum;

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty("是否隐藏  0否1是")
    private Boolean isHidden;


}
