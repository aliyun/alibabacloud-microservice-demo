package com.alibaba.arms.mock.server.service.trouble;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.alibaba.arms.mock.server.constant.Constants.*;

/**
 * 直接故障(对执行栈造成直接的影响)
 *
 * @author aliyun
 * @date 2021/07/19
 */
@Slf4j
public abstract class DirectTrouble implements ITrouble {

    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static ThreadLocal<JoinPoint> CACHE = new ThreadLocal<JoinPoint>();
    private Map<Method, Map<String, String>> pointcuts = new ConcurrentHashMap<>();

    public DirectTrouble() {

    }

    @Override
    public String make(Map<String, String> params) {
        this.addPointCut(params.get("fullMethodName"), params);
        SCHEDULED_EXECUTOR_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                Method m = new Method(params.get("fullMethodName"));
                pointcuts.remove(m);
            }
        }, 30, TimeUnit.MINUTES);
        return TROUBLE_MAKE_SUCCESS;
    }

    @Override
    public String cancel(Map<String, String> params) {
        Method m = new Method(params.get("fullMethodName"));
        if (null == this.pointcuts.remove(m)) {
            return ALREADY_CANCELED;
        }
        return CANCEL_SUCCESS;
    }

    @Override
    public List<String> getAffectedResource() {
        return pointcuts.keySet().stream().map(e -> e.getFullMethodName()).collect(Collectors.toList());
    }

    /**
     * 添加需要注入故障的接口
     *
     * @param fullMethodName
     * @param params
     */
    private void addPointCut(String fullMethodName, Map<String, String> params) {
        Method m = new Method(fullMethodName);
        this.pointcuts.put(m, params);
    }

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(public * com.alibaba.arms.mock.server.service.*.*(..))")
    void troublePointcut() {
    }

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("troublePointcut()")
    void doBefore(JoinPoint joinPoint) {
        CACHE.set(joinPoint);
    }

    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 1. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "troublePointcut()")
    void doAfterReturning(Object ret) throws Throwable {
        JoinPoint jp = CACHE.get();
        Signature signature = jp.getSignature();
        String fullClassName = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        Method point = new Method(fullClassName + "#" + methodName);
        Map<String, String> params = pointcuts.get(point);
        if (null != params) {
            this.doMakeTrouble(params);
        }

    }

    protected abstract void doMakeTrouble(Map<String, String> params);

    @Data
    private static class Method {
        private final String fullMethodName;
    }

}
