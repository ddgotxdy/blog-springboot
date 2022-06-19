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
 * @description: 网站中个人的信息
 */
@Getter
@Setter
@TableName("tb_website_config")
@ApiModel(value = "WebsiteConfig对象", description = "网站中个人的信息")
public class WebsiteConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    @ApiModelProperty("配置信息")
    private String config;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
