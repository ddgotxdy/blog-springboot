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
 * 
 * </p>
 *
 * @author ddgo
 * @since 2022-06-19
 */
@Getter
@Setter
@TableName("tb_resource")
@ApiModel(value = "Resource对象", description = "")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资源名")
    private String resourceName;

    @ApiModelProperty("权限路径")
    private String url;

    @ApiModelProperty("请求方式")
    private String requestMethod;

    @ApiModelProperty("父权限id")
    private Integer parentId;

    @ApiModelProperty("是否匿名访问 0否 1是")
    private Boolean isAnonymous;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;


}
