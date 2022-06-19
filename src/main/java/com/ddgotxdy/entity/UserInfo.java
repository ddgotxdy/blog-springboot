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
 * @description: 用户基本信息
 */
@Getter
@Setter
@TableName("tb_user_info")
@ApiModel(value = "UserInfo对象", description = "用户基本信息")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableId
    private Integer id;

    @ApiModelProperty("邮箱号")
    private String email;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户简介")
    private String intro;

    @ApiModelProperty("个人网站")
    private String webSite;

    @ApiModelProperty("是否禁用")
    private Boolean isDisable;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
