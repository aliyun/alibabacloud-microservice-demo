package com.alibabacloud.hipstershop.productservice.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/23
 */
public class CommonUtil {
    public static String getLocalIp() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            if (inetAddress != null) {
                //获得本机Ip;
                return inetAddress.getHostAddress();
            }
        } catch (UnknownHostException e) {
        }
        return null;
    }
}
