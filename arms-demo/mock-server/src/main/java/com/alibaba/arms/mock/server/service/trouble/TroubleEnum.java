package com.alibaba.arms.mock.server.service.trouble;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Slf4j
public enum TroubleEnum {
    DISK("disk"),
    JVM_CPU("jvmCPU"),
    GC("gc"),
    ERROR("error"),
    SLOW("slow"),
    NONE("none");

    TroubleEnum(String code) {
        this.code = code;
    }

    public static TroubleEnum getByCode(String trouble) {
        for (TroubleEnum each : TroubleEnum.values()) {
            if (each.getCode().equalsIgnoreCase(trouble)) {
                return each;
            }
        }
        return NONE;
    }

    @Getter
    private final String code;
}
