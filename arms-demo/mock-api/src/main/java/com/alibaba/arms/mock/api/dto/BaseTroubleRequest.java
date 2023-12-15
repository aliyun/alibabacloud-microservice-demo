package com.alibaba.arms.mock.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aliyun
 * @date 2022/02/08
 */
@Data
@NoArgsConstructor
public class BaseTroubleRequest {

    @ApiModelProperty("组件名称")
    private String componentName;

    private Map<String, String> params = new HashMap<>();

    public BaseTroubleRequest(String componentName) {
        this.componentName = componentName;
    }

    public void addParam(String key, String val) {
        this.params.put(key, val);
    }

    public String getParam(String key) {
        return params.get(key);
    }
}
