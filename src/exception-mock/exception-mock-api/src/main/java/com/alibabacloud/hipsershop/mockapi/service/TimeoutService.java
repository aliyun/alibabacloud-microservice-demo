package com.alibabacloud.hipsershop.mockapi.service;

public interface TimeoutService {

    String timeout1000(String returnString);

    String timeout(int timeout, String returnString);

}