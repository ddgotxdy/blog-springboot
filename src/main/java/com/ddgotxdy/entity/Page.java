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
 * 页面
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Getter
@Setter
@TableName("tb_page")
@ApiModel(value = "Page对象", description = "页面")
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("页面id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("页面名")
    private String pageName;

    @ApiModelProperty("页面标签")
    private String pageLabel;

    @ApiModelProperty("页面封面")
    private String pageCover;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}
