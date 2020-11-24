package com.alibabacloud.hipsershop.mockprovider.service;

import com.alibabacloud.hipsershop.mockapi.service.TimeoutService;
import org.springframework.stereotype.Service;

@Service
@org.apache.dubbo.config.annotation.Service(version = "1.0.0", group = "mock")
public class TimeoutServiceImpl implements TimeoutService {

    @Override
    public String timeout1000(String returnString) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    @Override
    public String timeout(int timeout, String returnString) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
