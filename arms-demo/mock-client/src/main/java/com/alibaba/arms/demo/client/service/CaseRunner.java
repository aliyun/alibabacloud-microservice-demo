package com.alibaba.arms.demo.client.service;

import com.alibaba.arms.demo.client.domain.NamedThreadFactory;
import com.alibaba.arms.demo.client.domain.TestCase;
import com.alibaba.arms.mock.api.IComponentAPI;
import com.alibaba.arms.mock.api.Invocation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.shaded.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
@Slf4j
public class CaseRunner {


    private Map<String, IComponentAPI> cache = new ConcurrentHashMap<>();
    private ExecutorService executors = Executors.newFixedThreadPool(500, new NamedThreadFactory("case-runner"));
    private Map<String, List<CaseFuture>> futures = new HashMap<>();
    private Map<String, List<AtomicBoolean>> flagMap = new ConcurrentHashMap<>();
    private List<TestCase> testCases = new ArrayList<>();
    @Autowired
    private ServiceRoute serviceRoute;

    public String run(String name, String serverUrl, Integer parallel, Integer durationSeconds, Invocation invocation, Double qps) {
        return run(name, serverUrl, parallel, durationSeconds, invocation, qps, false);
    }

    public String run(String name, String serverUrl, Integer parallel, Integer durationSeconds, Invocation invocation, Double qps, boolean onlyOnce) {
        TestCase testCase = new TestCase(name);
        testCase.setServerUrl(serverUrl);
        testCase.setParallel(parallel);
        testCase.setDurationSeconds(durationSeconds);
        testCase.setInvocation(invocation);
        testCase.setQps(qps);
        testCase.setOnlyOnce(onlyOnce);
        return doRun(testCase);
    }

    String doRun(TestCase testCase) {

        String service = testCase.getInvocation().getService();
        IComponentAPI componentAPI = serviceRoute.getServiceAPI(service);
        double sleepMs = 1000.0f / testCase.getQps();
        List<CaseFuture> tasks = new ArrayList<>();
        for (int i = 0; i < testCase.getParallel(); i++) {
            long endTime = testCase.getDurationSeconds() * 1000L + System.currentTimeMillis();
            AtomicBoolean runningFlag = new AtomicBoolean(true);
            if (!testCase.isOnlyOnce()) {
                Future<Void> task = executors.submit(new StoppableCase(testCase, runningFlag, componentAPI));
                tasks.add(new CaseFuture(task, runningFlag));
                if (flagMap.containsKey(testCase.getName())) {
                    flagMap.get(testCase.getName()).add(runningFlag);
                } else {
                    List<AtomicBoolean> flagList = new ArrayList<>();
                    flagList.add(runningFlag);
                    flagMap.put(testCase.getName(), flagList);
                }
            } else {
                executors.submit(new OnlyOnceCase(testCase, componentAPI));
            }
        }
        if (!testCase.isOnlyOnce()) {
            List<CaseFuture> cases = futures.getOrDefault(testCase.getName(), new ArrayList<>());
            cases.addAll(tasks);
            futures.put(testCase.getName(), cases);
        }
        return "success";
    }

    public String stop(String caseName) {
        log.error("stop case {}", caseName);
        if (flagMap.containsKey(caseName)) {
            flagMap.get(caseName).forEach(e -> {
                e.set(false);
            });
        }
        List<CaseFuture> tasks = futures.remove(caseName);
        if (null == tasks || tasks.isEmpty()) {
            log.error("stop case fail {}", caseName);
            return "未找到指定用例";
        }
        tasks.forEach(e -> {
            e.getRunningFlag().set(false);
        });
        log.error("stop case success {}", caseName);
        return "用例停止成功";
    }

    public List<String> list() {
        return new ArrayList<>(futures.keySet());
    }

    @Data
    private static class CaseFuture {

        private final Future<Void> future;
        private final AtomicBoolean runningFlag;
    }

    private static class OCallBack implements Callback<String> {

        public static OCallBack INSTANCE = new OCallBack();
        private static AtomicLong success = new AtomicLong(0);
        private static AtomicLong failed = new AtomicLong(0);

        @Override
        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
            if (!response.isSuccessful()) {
                try {
                    log.error("{}", response.errorBody().string());
                } catch (IOException e) {
                    log.error("error", e);
                }
                failed.incrementAndGet();
            } else {
                success.incrementAndGet();
            }
            if ((failed.get() != 0 && failed.get() % 1000 == 0) || success.get() % 1000 == 0) {
                log.info("success={},failed={}", success.get(), failed.get());
            }
        }

        @Override
        public void onFailure(retrofit2.Call<String> call, Throwable t) {
            failed.incrementAndGet();
            log.error("error", t);
            if (failed.get() != 0 && failed.get() % 1000 == 0) {
                log.info("success={},failed={}", success.get(), failed.get());
            }
        }
    }

    @Data
    private static class OnlyOnceCase implements Callable<Void> {
        private final TestCase testCase;
        private final IComponentAPI componentAPI;

        @Override
        public Void call() throws Exception {
            try {
                Response<String> resp = componentAPI.startCase(testCase.getName(), testCase.getInvocation(), 0).execute();
                int testNum = RandomUtils.nextInt(1, 10);
                Response<String> resp2 = componentAPI.testCase(testCase.getName(), testCase.getInvocation(), 0, "method"+testNum).execute();
                OCallBack.INSTANCE.onResponse(null, resp);
            } catch (IOException e) {
                log.error("caseName:{} call error", testCase.getName());
                OCallBack.INSTANCE.onFailure(null, e);
            }
            return null;
        }
    }

    @Data
    private static class StoppableCase implements Callable<Void> {

        private final TestCase testCase;
        private final AtomicBoolean runningFlag;
        private final IComponentAPI componentAPI;

        @Override
        public Void call() throws Exception {
            double sleepMs = 1000.0f / testCase.getQps();
            long endTime = testCase.getDurationSeconds() * 1000L + System.currentTimeMillis();
            while (runningFlag.get()) {
                int testNum = RandomUtils.nextInt(1, 10);
                try {
                    if (0 != testCase.getDurationSeconds() && System.currentTimeMillis() > endTime) {
                        log.warn("we have run {} seconds,exit now", testCase.getDurationSeconds());
                        break;
                    }
                    if (0 == testCase.getQps()) {
                        try {
                            Response<String> resp = componentAPI.startCase(testCase.getName(), testCase.getInvocation(), 0)
                                    .execute();
//                            Response<String> resp2 = componentAPI.testCase(testCase.getName(), testCase.getInvocation(), 0, "method"+testNum).execute();
                            OCallBack.INSTANCE.onResponse(null, resp);
                        } catch (IOException e) {
                            log.error("caseName:{} call error", testCase.getName());
                            OCallBack.INSTANCE.onFailure(null, e);
                        }
                    } else {
                        long startTime = System.currentTimeMillis();
                        try {
                            Response<String> resp = componentAPI.startCase(testCase.getName(), testCase.getInvocation(), 0).execute();
//                            Response<String> resp2 = componentAPI.testCase(testCase.getName(), testCase.getInvocation(), 0, "method"+testNum).execute();
                            OCallBack.INSTANCE.onResponse(null, resp);
                        } catch (IOException e) {
                            log.error("caseName:{} call error", testCase.getName());
                            OCallBack.INSTANCE.onFailure(null, e);
                        }
                        long _endTime = System.currentTimeMillis();
                        try {
                            double remain = sleepMs - (_endTime - startTime);
                            if (remain > 0) {
                                Thread.sleep((long) remain);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    log.error("start case failed", th);
                }
            }
            log.info("somebody stop me");
            return null;
        }
    }
}
