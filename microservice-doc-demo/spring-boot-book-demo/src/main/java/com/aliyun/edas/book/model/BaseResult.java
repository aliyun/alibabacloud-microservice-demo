package com.aliyun.edas.book.model;

import lombok.Data;

@Data
public class BaseResult<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> BaseResult<T> ok(T data) {
        return restResult(data, 200, "success");
    }

    public static <T> BaseResult<T> restResult(T data, int code, String msg) {
        BaseResult<T> apiResult = new BaseResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}