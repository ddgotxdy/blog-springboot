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
 * @description: 友链
 */
@Getter
@Setter
@TableName("tb_friend_link")
@ApiModel(value = "FriendLink对象", description = "友链")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @ApiModelProperty("链接名")
    private String linkName;

    @ApiModelProperty("链接头像")
    private String linkAvatar;

    @ApiModelProperty("链接地址")
    private String linkAddress;

    @ApiModelProperty("链接介绍")
    private String linkIntro;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
