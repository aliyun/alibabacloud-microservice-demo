package com.alibabacloud.hipstershop.cartserviceprovider.domain;

import java.util.ArrayList;

/**
 * @author xiaofeng.gxf
 * @date 2020/7/7
 */
public class RedisKey {
    private ArrayList<String> keys;

    public String getKey() {
        StringBuffer sb = new StringBuffer();
        keys.forEach(key -> sb.append(key).append(":"));
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public RedisKey(String k1) {
        this.keys = new ArrayList<>();
        this.keys.add(k1);
    }

    public RedisKey(String k1, String k2) {
        this.keys = new ArrayList<>();
        this.keys.add(k1);
        this.keys.add(k2);
    }

    public RedisKey(String k1, String k2, String k3) {
        this.keys = new ArrayList<>();
        this.keys.add(k1);
        this.keys.add(k2);
        this.keys.add(k3);
    }

    public RedisKey(String k1, String k2, String k3, String k4) {
        this.keys = new ArrayList<>();
        this.keys.add(k1);
        this.keys.add(k2);
        this.keys.add(k3);
        this.keys.add(k4);
    }
}
