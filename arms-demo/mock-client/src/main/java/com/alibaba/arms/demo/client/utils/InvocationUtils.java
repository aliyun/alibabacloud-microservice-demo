package com.alibaba.arms.demo.client.utils;

import com.alibaba.arms.mock.api.Invocation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;

public class InvocationUtils {
    private static final String SERVICE_0 = "insights-server-0";

    private static final String SERVICE_1 = "insights-server-1";

    private static final String SERVICE_2 = "insights-server-2";

    private static final String SERVICE_3 = "insights-server-3";
    
    private static final String REDIS_SERVICE = "insights-redis-server";

    private static final String MYSQL_SERVICE = "insights-mysql-server";

    private static final String MYSQL_SERVICE_CONNECTION = "insights-mysql-connection";

    private static final String MEMORY_SERVICE = "insights-memory-case";

    private static final String INFRA_SERVICE = "insights-infra-case";

    private static final String LOCAL_COMPONENT = "local";
    
    private static final String DUBBO_COMPONENT = "dubbo";

    private static final String MYSQL_COMPONENT = "mysql";

    private static final String HTTP_COMPONENT = "http";

    private static final String REDIS_COMPONENT = "redis";

    private static final String SUCCESS_METHOD = "success";

    private static final String CPU_METHOD = "cpu";

    private static final String CANCEL_CPU_METHOD = "stopCpu";

    private static final String MEMORY_METHOD = "memory";

    private static final String CANCEL_MEMORY_METHOD = "stopMemory";



    public static Invocation genHttpInvocation() {
        Invocation root = new Invocation(SERVICE_0, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation dubboChild1 = new Invocation(SERVICE_1, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation dubboChild2 = new Invocation(SERVICE_2, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation dubboChild3 = new Invocation(SERVICE_3, HTTP_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        root.getChildren().add(dubboChild1);
        root.getChildren().add(dubboChild2);
        dubboChild2.getChildren().add(dubboChild3);
        dubboChild1.getChildren().add(dubboChild3);
        return root;
    }

    public static Invocation genHttpInvocation2() {
        Invocation root = new Invocation(SERVICE_0, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation dubboChild2 = new Invocation(SERVICE_2, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        root.getChildren().add(dubboChild2);
        return root;
    }

    public static Invocation genHttpInvocation3() {
        Invocation root = new Invocation(SERVICE_0, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation dubboChild1 = new Invocation(SERVICE_1, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        root.getChildren().add(dubboChild1);
        return root;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(genHttpInvocation(), SerializerFeature.DisableCircularReferenceDetect));
    }
    
    public static Invocation genCPUInvocation() {
        Invocation root = new Invocation(MEMORY_SERVICE, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation child = new Invocation(INFRA_SERVICE, LOCAL_COMPONENT, CPU_METHOD, new ArrayList<>());
        root.getChildren().add(child);
        return root;
    }

    public static Invocation genStopCPUInvocation() {
        Invocation root = new Invocation(MEMORY_SERVICE, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation child = new Invocation(INFRA_SERVICE, LOCAL_COMPONENT, CANCEL_CPU_METHOD, new ArrayList<>());
        root.getChildren().add(child);
        return root;
    }
    
    public static Invocation genMemoryInvocation() {
        Invocation root = new Invocation(INFRA_SERVICE, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation child = new Invocation(MEMORY_SERVICE, LOCAL_COMPONENT, MEMORY_METHOD, new ArrayList<>());
        root.getChildren().add(child);
        return root;
    }

    public static Invocation genMemoryStopInvocation() {
        Invocation root = new Invocation(INFRA_SERVICE, LOCAL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        Invocation child = new Invocation(MEMORY_SERVICE, LOCAL_COMPONENT, CANCEL_MEMORY_METHOD, new ArrayList<>());
        root.getChildren().add(child);
        return root;
    }
    
    public static Invocation genRedisInvocation() {
        Invocation root = new Invocation(REDIS_SERVICE, REDIS_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        return root;
    }
    
    public static Invocation genMysqlInvocation() {
        Invocation root = new Invocation(MYSQL_SERVICE, MYSQL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        return root;
    }

    public static Invocation genMysqlConnectInvocation() {
        Invocation root = new Invocation(MYSQL_SERVICE_CONNECTION, MYSQL_COMPONENT, SUCCESS_METHOD, new ArrayList<>());
        return root;
    }
}
