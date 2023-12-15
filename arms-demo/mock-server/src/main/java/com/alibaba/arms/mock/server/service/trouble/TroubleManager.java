package com.alibaba.arms.mock.server.service.trouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aliyun
 * @date 2021/07/20
 */
@Service
public class TroubleManager {

    @Autowired
    private List<ITrouble> troubles;

    private Map<TroubleEnum, ITrouble> indexByTroubleEnum;

    @PostConstruct
    public void init() {
        indexByTroubleEnum = new HashMap<>();
        troubles.forEach(e -> {
            indexByTroubleEnum.put(e.getTroubleType(), e);
        });
    }

    public ITrouble getTroubleMaker(String troubleCode) {
        return indexByTroubleEnum.get(TroubleEnum.getByCode(troubleCode));
    }
}
