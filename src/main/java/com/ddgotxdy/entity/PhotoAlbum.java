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
 * @description: 相册
 */
@Getter
@Setter
@TableName("tb_photo_album")
@ApiModel(value = "PhotoAlbum对象", description = "相册")
public class PhotoAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    private Integer id;

    @ApiModelProperty("相册名")
    private String albumName;

    @ApiModelProperty("相册描述")
    private String albumDesc;

    @ApiModelProperty("相册封面")
    private String albumCover;

    @ApiModelProperty("是否删除")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty("状态值 1公开 2私密")
    private Boolean status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;


}
