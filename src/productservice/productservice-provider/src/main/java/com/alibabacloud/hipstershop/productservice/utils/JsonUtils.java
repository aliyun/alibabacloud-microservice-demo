package com.alibabacloud.hipstershop.productservice.utils;

import com.alibabacloud.hipstershop.productserviceapi.domain.ProductItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ProductItem> getObjectList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<ProductItem>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}