package com.alibabacloud.hipstershop.domain.bo.fault;

import com.alibabacloud.hipstershop.utils.Constant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *@author xiaofeng.gxf
 *@date 2020/8/25
 */
public class FaultInfo {
    private String dataId;
    private String group;
    private Set<String> ips;
    private Integer rate;
    private String type;
    private Integer value;

    public FaultInfo(String dataId, String group, Set<String> ips, Integer rate, String type, Integer value) {
        this.dataId = dataId;
        this.group = group;
        this.ips = ips;
        this.rate = rate;
        this.type = type;
        this.value = value;
    }

    public String getContent(){
        return "exception.ips=" + String.join(",", ips) +
                "@" + "exception.rate=" + rate + "@" +
                "exception.type=" + type + "@" +
                "exception.value=" + value;
    }

    public boolean isClear(){
        return Objects.isNull(ips) && Objects.isNull(rate) && Objects.isNull(type) && Objects.isNull(value);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Set<String> getIps() {
        return ips;
    }

    public void setIps(Set<String> ips) {
        this.ips = ips;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
