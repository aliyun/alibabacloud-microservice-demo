package com.alibaba.arms.demo.client.domain;

import com.alibaba.arms.demo.client.RandomVal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Data
@AllArgsConstructor
public class PeriodParam {

    //参数
    private Map<String, RandomVal<String>> params;

    //持续时长
    private RandomVal<Integer> durationSeconds;

    public Map<String, String> getParams() {

        if (null == params) {
            return null;
        }
        Map<String, String> finalParams = new HashMap<>();
        params.forEach(new BiConsumer<String, RandomVal<String>>() {
            @Override
            public void accept(String s, RandomVal<String> randomVal) {
                finalParams.put(s, randomVal.getNextVal());
            }
        });
        return finalParams;
    }
}
