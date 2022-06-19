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
 * 相册
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Getter
@Setter
@TableName("tb_photo_album")
@ApiModel(value = "PhotoAlbum对象", description = "相册")
public class PhotoAlbum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("相册名")
    private String albumName;

    @ApiModelProperty("相册描述")
    private String albumDesc;

    @ApiModelProperty("相册封面")
    private String albumCover;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("状态值 1公开 2私密")
    private Boolean status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
