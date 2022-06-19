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
 * @description: 用户认证表，存放密码登录地等信息
 */
@Getter
@Setter
@TableName("tb_user_auth")
@ApiModel(value = "UserAuth对象", description = "用户认证表")
public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @ApiModelProperty("用户信息id")
    private Integer userInfoId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("登录类型")
    private Boolean loginType;

    @ApiModelProperty("用户登录ip")
    private String ipAddress;

    @ApiModelProperty("ip来源")
    private String ipSource;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;


}
