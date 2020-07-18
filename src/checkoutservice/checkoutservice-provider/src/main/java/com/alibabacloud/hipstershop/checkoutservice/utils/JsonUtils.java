package com.alibabacloud.hipstershop.checkoutservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
public class JsonUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
