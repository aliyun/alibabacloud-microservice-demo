package com.alibabacloud.hipstershop.productservice.domain.bo;

import com.alibabacloud.hipstershop.productservice.utils.Constant;

import java.util.*;

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

    public FaultInfo(String dataId, String group, String content){
        this.dataId = dataId;
        this.group = group;
        if(!Constant.CLEAR.equals(content)){
            String[] temp = content.split("@");
            ips = new HashSet<>();
            String[] ipArray = temp[0].split("=");
            if(ipArray.length > 1) {
                Collections.addAll(ips, ipArray[1].split(","));
            }
            rate = Integer.valueOf(temp[1].split("=")[1]);
            type = temp[2].split("=")[1];
            value = Integer.valueOf(temp[3].split("=")[1]);
        }
    }

    public String getContent(){
        return "exception.ips=" + String.join(",", ips) +
                "\n" + "exception.rate=" + rate + "\n" +
                "exception.type=" + type + "\n" +
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
