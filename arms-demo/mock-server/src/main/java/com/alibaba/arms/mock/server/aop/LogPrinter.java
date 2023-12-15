package com.alibaba.arms.mock.server.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author aliyun
 * @date 2021/07/19
 */

@Aspect
@Component
@Slf4j
public class LogPrinter {


    /**
     * 进入方法时间戳
     */
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    public LogPrinter() {
    }


    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(public * com.alibaba.arms.mock.server..*.*(..))")
    public void logPointcut() {
    }

    /**
     * 前置通知：
     * 1. 在执行目标方法之前执行，比如请求接口之前的登录验证;
     * 2. 在前置通知中设置请求日志信息，如开始时间，请求参数，注解内容等
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("logPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Class sigClass = joinPoint.getSignature().getDeclaringType();
        if (null != sigClass.getAnnotation(IgnoreLog.class)) {
            return;
        }
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        if (method.contains("HealthController")) {
            return;
        }
        //打印请求的内容
        startTime.set(System.currentTimeMillis());
        log.info("begin method={}, params={}",
                method,
                Arrays.toString(joinPoint.getArgs()));

    }

    /**
     * 返回通知：
     * 1. 在目标方法正常结束之后执行
     * 1. 在返回通知中补充请求日志信息，如返回时间，方法耗时，返回值，并且保存日志信息
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "logPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        if (null == startTime.get()) {
            return;
        }
        long endTime = System.currentTimeMillis();
        log.info("end cost time={}ms", (endTime - startTime.get()));
        startTime.remove();
    }

    /**
     * 异常通知：
     * 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     * 1. 在异常通知中设置异常信息，并将其保存
     *
     * @param throwable
     */
    @AfterThrowing(value = "logPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        // 保存异常日志记录
        log.error("throw exception", throwable);
    }


}
