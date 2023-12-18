package com.alibaba.arms.demo.client.testcase;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * @author aliyun
 * @date 2021/07/16
 */
public class MuyiCase implements ICase {
    @Override
    public void init() {

    }

    @Override
    public Invocation nextOne() {
        Invocation invocation = new Invocation();
        invocation.setService("muyicar");
        invocation.setComponent("local");
        invocation.setMethod("success");

        Invocation redis = new Invocation();
        redis.setService("muyilocation");
        redis.setComponent("redis");
        redis.setMethod("success");

        Invocation mysql = new Invocation();
        mysql.setService("muyiorder");
        mysql.setComponent("mysql");
        mysql.setMethod("success");

        redis.setChildren(Arrays.asList(mysql));


        invocation.setChildren(Arrays.asList(redis));

        return invocation;
    }

    public static void main(String[] args) {

        MuyiCase c = new MuyiCase();
        System.out.println(JSON.toJSONString(c.nextOne()));
    }
}
