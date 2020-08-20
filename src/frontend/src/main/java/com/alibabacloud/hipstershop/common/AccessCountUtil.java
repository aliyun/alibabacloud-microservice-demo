package com.alibabacloud.hipstershop.common;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import static com.alibabacloud.hipstershop.common.CommonUtil.*;

/**
 * @author meisheng.lym
 */
public class AccessCountUtil {

    // 数组变量初始化
    public static void initial() {
        for (int i = 0; i < 6; i++) {
            PRODUCT_LOCK[i] = new Object();

            switch (i) {
                case 0:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE0;
                    break;
                case 1:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE1;
                    break;
                case 2:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE2;
                    break;
                case 3:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE3;
                    break;
                case 4:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE4;
                    break;
                case 5:
                    SPRING_CLOUD_AUTH_RESULT_QUEUE[i] = PRODUCT_QUEUE5;
                    break;
            }
        }
    }

    // 模拟对某uri持续的访问，queue中存放状态码
    public static void uriAccess(String uri, Object lock, Queue<String> queue) {
        long t1 = System.currentTimeMillis();

        int minutes = 1;

        while (AUTH_ENABLE.get()) {
            try {
                long t2 = System.currentTimeMillis();
                HttpUriRequest request = new HttpGet(uri);
                CloseableHttpResponse response = HttpClients.createDefault().execute(request);
                int code = response.getStatusLine().getStatusCode();
                String result = Integer.toString(code);
                synchronized (lock) {
                    queue.add(result);
                }

                // 超时或用户手动停止
                if (t2 - t1 > minutes * 60 * 1000 || !AUTH_ENABLE.get()) {
                    AUTH_BEGIN.compareAndSet(true, false);

                    synchronized (lock) {
                        queue.clear();
                    }
                    break;
                }
            } catch (Exception ignore) {
            }
        }
    }

    // 从queue中取出字符串，并放入map中计数
    public static void putResult(Object lock, List<ResultNode> list, Map<String, Integer> map, Queue<String> queue, boolean display) {

        synchronized (lock) {

            list.clear();
            map.clear();

            for (String str : queue) {
                if (null == map.get(str)) {
                    map.put(str, 1);
                } else {
                    int count = map.get(str);
                    count++;
                    map.put(str, count);
                }
            }

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                ResultNode resultNode = new ResultNode(entry.getKey(), entry.getValue(), display);
                if (!display && entry.getValue() == 10) {
                    resultNode.setDisplay(false);
                }
                list.add(resultNode);
            }

        }
    }

    public static class ResultNode {

        private String result;
        private int times;
        private boolean display; // 是否显示times

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public boolean getDisplay() {
            return display;
        }

        public void setDisplay(boolean display) {
            this.display = display;
        }

        public ResultNode(String result, int times, boolean display) {
            this.result = result;
            this.times = times;
            this.display = display;
        }
    }
}
