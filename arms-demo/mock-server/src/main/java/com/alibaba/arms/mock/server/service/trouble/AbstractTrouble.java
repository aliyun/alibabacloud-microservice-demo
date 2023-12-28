package com.alibaba.arms.mock.server.service.trouble;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alibaba.arms.mock.server.constant.Constants.*;

@Slf4j
public abstract class AbstractTrouble implements ITrouble {

    protected AtomicBoolean working = new AtomicBoolean(false);

    @Override
    public String make(Map<String, String> params) {
        if (working.compareAndSet(false, true)) {
            doMake(params);
            return TROUBLE_MAKE_SUCCESS;
        }
        return TROUBLE_IS_MAKING;
    }

    @Override
    public String cancel(Map<String, String> params) {
        if (working.compareAndSet(true, false)) {
            doCancel();
            return CANCEL_SUCCESS;
        }
        return ALREADY_CANCELED;
    }

    protected abstract void doCancel();

    protected abstract void doMake(Map<String, String> params);

}
