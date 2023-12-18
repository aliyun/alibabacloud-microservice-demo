package com.alibaba.arms.mock.server.service.trouble;

import com.alibaba.arms.mock.server.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
@Slf4j
public class DiskTrouble extends AbstractTrouble {

    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2,
            new NamedThreadFactory("disk-killer"));

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.DISK;
    }

    @Override
    public List<String> getAffectedResource() {
        return this.working.get() ? Arrays.asList("disk") : Collections.emptyList();
    }

    @Override
    public void doCancel() {

    }

    private static final byte[] random = new byte[1024 * 1024 * 10];

    @Override
    public void doMake(Map<String, String> params) {

        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        File tmpDirectory = new File("/tmp");
                        File tmpFile = File.createTempFile("arms_mock", "bigfile", tmpDirectory);
                        tmpFile.deleteOnExit();
                        log.info("generate tmpfile={}", tmpFile);
                        FileOutputStream fos = new FileOutputStream(tmpFile);
                        while (!working.get()) {
                            fos.write(random, 0, random.length);
                        }
                        fos.close();
                        tmpFile.delete();
                    } catch (Exception e) {
                        log.error("error", e);
                    }
                }
            });
        }
    }
}
