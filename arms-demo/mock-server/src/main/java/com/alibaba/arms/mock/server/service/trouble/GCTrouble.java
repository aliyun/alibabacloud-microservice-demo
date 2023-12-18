package com.alibaba.arms.mock.server.service.trouble;

import com.alibaba.arms.mock.server.NamedThreadFactory;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
public class GCTrouble extends AbstractTrouble {
    private ExecutorService executorService = Executors.newSingleThreadExecutor(new NamedThreadFactory("full-mem"));

    private static class BigObject {
        private final byte[] data;

        public BigObject(int byteSize) {
            data = new byte[byteSize];
        }
    }


    @Override
    protected void doCancel() {

    }

    @Override
    protected void doMake(Map<String, String> params) {
        int arrSize = null == params || Strings.isNullOrEmpty(params.get("byteSize")) ?
                1024 * 1024
                : Integer.parseInt(params.get("byteSize"));
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                List<BigObject> bigObjects = new ArrayList<>();
                while (!working.get()) {
                    bigObjects.add(new BigObject(arrSize));
                }
            }
        });

    }

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.GC;
    }

    @Override
    public List<String> getAffectedResource() {
        return this.working.get() ? Arrays.asList("gc") : Collections.emptyList();
    }
}
