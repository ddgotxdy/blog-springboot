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
 * @description: 留言
 */
@Getter
@Setter
@TableName("tb_message")
@ApiModel(value = "Message对象", description = "留言")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("留言内容")
    private String messageContent;

    @ApiModelProperty("用户ip")
    private String ipAddress;

    @ApiModelProperty("用户地址")
    private String ipSource;

    @ApiModelProperty("弹幕速度")
    private Boolean time;

    @ApiModelProperty("是否审核")
    private Integer isReview;

    @ApiModelProperty("发布时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
