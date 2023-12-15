package com.alibaba.arms.mock.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author aliyun
 * @date 2022/02/08
 */
@Data
@ApiModel("错误故障请求对象")
@NoArgsConstructor
public class ErrorTroubleRequest extends BaseTroubleRequest{
    public ErrorTroubleRequest(String componentName) {
        super(componentName);
    }
}
