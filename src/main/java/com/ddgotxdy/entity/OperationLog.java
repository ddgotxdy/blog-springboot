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
 * @description: 日志记录
 */
@Getter
@Setter
@TableName("tb_operation_log")
@ApiModel(value = "OperationLog对象", description = "日志记录")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    @TableId
    private Integer id;

    @ApiModelProperty("操作模块")
    private String optModule;

    @ApiModelProperty("操作类型")
    private String optType;

    @ApiModelProperty("操作url")
    private String optUrl;

    @ApiModelProperty("操作方法")
    private String optMethod;

    @ApiModelProperty("操作描述")
    private String optDesc;

    @ApiModelProperty("请求参数")
    private String requestParam;

    @ApiModelProperty("请求方式")
    private String requestMethod;

    @ApiModelProperty("返回数据")
    private String responseData;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("操作ip")
    private String ipAddress;

    @ApiModelProperty("操作地址")
    private String ipSource;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
