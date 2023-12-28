//package com.alibaba.arms.demo.client.testcase;
//
//import com.alibaba.arms.demo.client.DynamicParamsCall;
//import com.alibaba.arms.demo.client.RandomVal;
//import com.alibaba.arms.demo.client.domain.PeriodParam;
//import com.alibaba.arms.mock.api.Invocation;
//
//import java.util.*;
//
///**
// * 三个请求分支
// * <p>
// * 两个正常，一个异常
// *
// *
// * dev-0:local->success
// *  dev-1:redis->success
// *      dev-2:mysql-success
// *
// * dev-1:redis->success
// *  dev-2:mysql-slow
// *
// * dev-0:redis->success
// *  dev-1:local->success
// *     dev-2:mysql->stupid
// *      xxx: local->slow
// *
// * 根因为: local->slow和mysql-slow 两个
// *
// * @author aliyun
// * @date 2021/07/09
// */
//public class MixedCase implements ICase {
//
//    private static final Random random = new Random();
//    private List<DynamicParamsCall> dynamicParamsCalls;
//    private PeriodParam[] params;
//    private long periodStartTime = System.currentTimeMillis();
//    private int periodIndex = 0;
//    private int callIndex = 0;
//    private PeriodParam choosed;
//    private int periodDurationSeconds;
//
//    public static DynamicParamsCall normalCall() {
//        Invocation mysqlOkAtDev2 = new Invocation("arms-mock-dev-2", "mysql", "success", null);
//        Invocation redisOkAtDev1 = new Invocation("arms-mock-dev-1", "redis", "success", Arrays.asList(mysqlOkAtDev2));
//        Invocation localOkAtDev0 = new Invocation("arms-mock-dev-0", "local", "success", Arrays.asList(redisOkAtDev1));
//        return new DynamicParamsCall() {
//            @Override
//            public Invocation nextInvocation(Map<String, String> nextParams) {
//                return localOkAtDev0;
//            }
//        };
//    }
//
//    public static DynamicParamsCall stupidCall() {
//        Invocation mysqlStupidAtDev2 = new Invocation("arms-mock-dev-2", "mysql", "stupid", null);
//        Invocation localOkAtDev1 = new Invocation("arms-mock-dev-1", "local", "success", Arrays.asList(mysqlStupidAtDev2));
//        Invocation redisOkAtDev0 = new Invocation("arms-mock-dev-0", "redis", "success", Arrays.asList(localOkAtDev1));
//        return new DynamicParamsCall() {
//            @Override
//            public Invocation nextInvocation(Map<String, String> nextParams) {
//                return redisOkAtDev0;
//            }
//        };
//    }
//
//    public static DynamicParamsCall slowCall() {
//        Invocation mysqlSlowAtDev2 = new Invocation("arms-mock-dev-2", "mysql", "slow", , null);
//        Invocation redisOkAtDev1 = new Invocation("arms-mock-dev-1", "redis", "success", null, Arrays.asList(mysqlSlowAtDev2));
//
//        return new DynamicParamsCall() {
//            @Override
//            public Invocation nextInvocation(Map<String, String> nextParams) {
//                mysqlSlowAtDev2.setParams(nextParams);
//                return redisOkAtDev1;
//            }
//        };
//    }
//
//    @Override
//    public Invocation nextOne() {
//        Invocation choosedBranch = this.dynamicParamsCalls.get(callIndex++ % dynamicParamsCalls.size())
//                .nextInvocation(choosed.getParams());
//
//        if (System.currentTimeMillis() - periodStartTime > periodDurationSeconds * 1000) {
//            periodIndex++;
//            choosed = params[periodIndex % params.length];
//            periodDurationSeconds = choosed.getDurationSeconds().getNextVal();
//            periodStartTime = System.currentTimeMillis();
//            System.out.println("duration=" + periodDurationSeconds);
//        }
//        return choosedBranch;
//    }
//
//    @Override
//    public void init() {
//        Map<String, RandomVal<String>> normal = new HashMap<>();
//        normal.put("durationInSeconds", new RandomVal() {
//            @Override
//            public String getNextVal() {
//                return String.valueOf(random.nextInt(3));
//            }
//        });
//
//
//        Map<String, RandomVal<String>> slow = new HashMap<>();
//        slow.put("durationInSeconds", new RandomVal() {
//            @Override
//            public String getNextVal() {
//                return String.valueOf(random.nextInt(3) + 10);
//            }
//        });
//
//
//        this.dynamicParamsCalls = Arrays.asList(normalCall(), stupidCall(), slowCall());
//        this.params = new PeriodParam[]{
//                /**
//                 * 正常访问持续时长
//                */
//                new PeriodParam(normal, new RandomVal<Integer>() {
//                    @Override
//                    public Integer getNextVal() {
//                        return 5 * 60 + random.nextInt(300);
//                    }
//                }),
//
//                /**
//                 * 异常访问持续时长
//                */
//                new PeriodParam(slow, new RandomVal<Integer>() {
//                    @Override
//                    public Integer getNextVal() {
//                        return 1 * 60 + random.nextInt(120);
//                    }
//                })};
//
//        this.periodStartTime = System.currentTimeMillis();
//        this.periodIndex = 0;
//        this.callIndex = 0;
//        this.choosed = params[periodIndex % params.length];
//        this.periodDurationSeconds = choosed.getDurationSeconds().getNextVal();
//    }
//
//}
