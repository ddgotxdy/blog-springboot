package com.ddgotxdy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ddgo
 * @description: 角色资源权限
 */
@Getter
@Setter
@TableName("tb_role_resource")
@ApiModel(value = "RoleResource对象", description = "角色资源权限")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("权限id")
    private Integer resourceId;


}
