package com.alibabacloud.mse.demo;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Controller
public class DemoController {

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Value("${demo.qps:100}")
    private int qps;

    @Value("${enable.rpc.invoke:false}")
    private boolean enableRpcInvoke;

    @Value("${background.color:white}")
    private String backgroundColor;

    @Value("${enable.sql:false}")
    private boolean enableSql;

    @Value("${enable.auto:false}")
    private boolean enableAuto;

    @Value("${enable.sentinel.demo.flow:false}")
    private boolean enableSentinelFlow;

    private static Random random = new Random();

    private static final ScheduledExecutorService FLOW_EXECUTOR = Executors.newScheduledThreadPool(16,
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setDaemon(true);
                    thread.setName("dubbo.outlier.refresh-" + thread.getId());
                    return thread;
                }
            });

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("backgroundColor", backgroundColor);
        return "index";
    }

    @GetMapping("/spring_boot")
    @ResponseBody
    public String spring_boot(HttpServletRequest request) {
        String result = restTemplate.getForObject("http://sc-a:20001/spring_boot", String.class);
        return result;
    }

    @PostConstruct
    private void flow() {
        if (qps > 0 && enableAuto) {
            FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet("http://localhost:20000/A/a");
                        httpClient.execute(httpGet);

                    } catch (Exception ignore) {
                    }
                }
            }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);

            FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet("http://localhost:20000/A/aByFeign");
                        httpClient.execute(httpGet);

                    } catch (Exception ignore) {
                    }
                }
            }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

            FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet("http://localhost:20000/A/a2bc");
                        httpClient.execute(httpGet);

                    } catch (Exception ignore) {
                    }
                }
            }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);


            FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet("http://localhost:20000/A/a?name=xiaoming");
                        httpClient.execute(httpGet);

                    } catch (Exception ignore) {
                    }
                }
            }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);


            FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                        HttpGet httpGet = new HttpGet("http://localhost:20000/A/a");
                        httpGet.addHeader("x-mse-tag", "gray");
                        httpClient.execute(httpGet);

                    } catch (Exception ignore) {
                    }
                }
            }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

            // 限流降级的流量发起
            if (enableSentinelFlow) {
                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/flow");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/flow");
                            httpGet.addHeader("x-mse-tag", "gray");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-flow");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-flow");
                            httpGet.addHeader("x-mse-tag", "gray");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-isolate");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-isolate");
                            httpGet.addHeader("x-mse-tag", "gray");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);

                // region 热点限流
                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-params/hot");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/params/hot");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);
                // endregion 热点限流

                // region 隔离规则
                for (int i = 0; i < 8; i++) {
                    int finalI = i;
                    FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                                HttpGet httpGet = new HttpGet("http://localhost:20000/A/isolate?i_id=" + finalI);
                                httpClient.execute(httpGet);
                            } catch (Exception ignore) {
                            }

                            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                                HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-isolate?i_id=" + finalI);
                                httpClient.execute(httpGet);
                            } catch (Exception ignore) {
                            }
                        }
                    }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);
                }
                // endregion 隔离规则
            }

            if (enableSql) {
                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            int id = random.nextInt(5) + 1;
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/sql?command=query&id=" + id);
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                    }
                }, 5000000, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGetUpdate = new HttpGet("http://localhost:20000/A/sql?command=update&name=name1Update&email=emailUpdate@demo.com&age=1&id=1");
                            httpClient.execute(httpGetUpdate);
                            TimeUnit.MILLISECONDS.sleep(500);
                            HttpGet httpGetUpdate2 = new HttpGet("http://localhost:20000/A/sql?command=update&name=name1&email=email@demo.com&age=1&id=1");
                            httpClient.execute(httpGetUpdate2);
                        } catch (Exception ignore) {
                        }
                    }
                }, 5000000, 50 * 1000000 / qps, TimeUnit.MICROSECONDS);
            }

            if (enableRpcInvoke) {
                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);


                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo?name=xiaoming");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo");
                            httpGet.addHeader("x-mse-tag", "gray");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);
            }
        }
    }
}
