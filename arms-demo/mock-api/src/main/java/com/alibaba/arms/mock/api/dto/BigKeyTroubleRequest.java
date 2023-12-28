package com.alibaba.arms.mock.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("大key故障请求")
@NoArgsConstructor
public class BigKeyTroubleRequest extends BaseTroubleRequest{
    @ApiModelProperty("大key大小")
    private String size;
    // key过多 或者 value值过大
    @ApiModelProperty("大key错误类型")
    private String type;

    public BigKeyTroubleRequest(String componentName) {
        super(componentName);
    }
}
