package com.alibaba.arms.mock.server.service.trouble;

import java.util.List;
import java.util.Map;

/**
 * 间接故障(通过其他线程向共享资源施压,间接影响方法执行)
 *
 * @author aliyun
 * @date 2021/07/20
 */
public interface ITrouble {

    /**
     * 注入故障
     *
     * @param params
     */
    public String make(Map<String, String> params);

    /**
     * 取消故障注入
     *
     * @param params
     */
    public String cancel(Map<String, String> params);

    public TroubleEnum getTroubleType();

    /**
     * 获取受影响的资源列表
     *
     * @return
     */
    public List<String> getAffectedResource();
}
