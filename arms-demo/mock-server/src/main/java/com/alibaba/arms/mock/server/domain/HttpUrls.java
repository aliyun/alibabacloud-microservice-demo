package com.alibaba.arms.mock.server.domain;

import lombok.Data;

@Data
public class HttpUrls {
    private String slowUrl;
    private String successUrl;
    private String errorUrl;
}
