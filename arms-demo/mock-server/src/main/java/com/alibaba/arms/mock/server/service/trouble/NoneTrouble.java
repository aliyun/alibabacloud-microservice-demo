package com.alibaba.arms.mock.server.service.trouble;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
public class NoneTrouble extends AbstractTrouble {
    @Override
    public void doCancel() {

    }

    @Override
    public void doMake(Map<String, String> params) {
    }

    @Override
    public TroubleEnum getTroubleType() {
        return TroubleEnum.NONE;
    }

    @Override
    public List<String> getAffectedResource() {
        return Collections.emptyList();
    }
}
