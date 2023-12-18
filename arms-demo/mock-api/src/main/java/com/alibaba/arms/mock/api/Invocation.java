package com.alibaba.arms.mock.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author aliyun
 * @date 2021/06/10
 */
@Data
@NoArgsConstructor
@ApiModel
public class Invocation {

    @ApiModelProperty(value = "服务名称", required = true)
    //服务
    private String service;
    //组件
    @ApiModelProperty(value = "组件名称", required = true)
    private String component;
    //方法
    @ApiModelProperty(value = "组件方法", required = true)
    private String method;

    @ApiModelProperty(value = "下游调用", required = false)
    private List<Invocation> children;

    public Invocation(String service, String component, String method, List<Invocation> children) {
        this.service = service;
        this.component = component;
        this.method = method;
        this.children = children;
    }
}
