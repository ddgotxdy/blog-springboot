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
 * @description: 照片
 */
@Getter
@Setter
@TableName("tb_photo")
@ApiModel(value = "Photo对象", description = "照片")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    private Integer id;

    @ApiModelProperty("相册id")
    private Integer albumId;

    @ApiModelProperty("照片名")
    private String photoName;

    @ApiModelProperty("照片描述")
    private String photoDesc;

    @ApiModelProperty("照片地址")
    private String photoSrc;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
