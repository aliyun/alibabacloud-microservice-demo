package com.alibaba.arms.demo.client.domain;

import com.alibaba.arms.mock.api.Invocation;
import lombok.Data;

/**
 * @author aliyun
 * @date 2021/07/30
 */
@Data
public class TestCase {
    private final String name;
    private String serverUrl;
    // 只执行一次
    private boolean onlyOnce;
    private int parallel;

    public int getDurationSeconds() {
        if (null == durationSeconds) {
            return Integer.MAX_VALUE;
        } else {
            return durationSeconds;
        }
    }

    private Integer durationSeconds;
    private Invocation invocation;
    private Double qps;
}
