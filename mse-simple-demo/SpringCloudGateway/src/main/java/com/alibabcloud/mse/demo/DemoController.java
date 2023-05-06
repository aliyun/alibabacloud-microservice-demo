package com.alibabcloud.mse.demo;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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

    @Value("${demo.qps:20}")
    private int qps;

    @Value("${enable.rpc.invoke:true}")
    private boolean enableRpcInvoke;

    @Value("${background.color:white}")
    private String backgroundColor;

    @Value("${enable.sql:false}")
    private boolean enableSql;

    @Value("${enable.auto:true}")
    private boolean enableAuto;

    @Value("${enable.sentinel.demo.flow:true}")
    private boolean enableSentinelFlow;

    @Value("${enable.gray:false}")
    private boolean enableGray;

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
                        HttpGet httpGet = new HttpGet("http://localhost:20000/B/b");
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
                        if (enableGray) {
                            httpGet.addHeader("x-mse-tag", "gray");
                        }
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
                            if (enableGray) {
                                httpGet.addHeader("x-mse-tag", "gray");
                            }
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
                            if (enableGray) {
                                httpGet.addHeader("x-mse-tag", "gray");
                            }
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
                            if (enableGray) {
                                httpGet.addHeader("x-mse-tag", "gray");
                            }
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

                // region 熔断规则
                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/circuit-breaker-rt");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/circuit-breaker-exception");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-circuit-breaker-rt");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo-circuit-breaker-exception");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);
                // endregion 热点限流
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
                            if (enableGray) {
                                httpGet.addHeader("x-mse-tag", "gray");
                            }
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo2");
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 1000000 / qps, TimeUnit.MICROSECONDS);


                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo2?name=xiaoming");
                            httpClient.execute(httpGet);
                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

                FLOW_EXECUTOR.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                            HttpGet httpGet = new HttpGet("http://localhost:20000/A/dubbo2");
                            if (enableGray) {
                                httpGet.addHeader("x-mse-tag", "gray");
                            }
                            httpClient.execute(httpGet);

                        } catch (Exception ignore) {
                        }
                    }
                }, 100, 10 * 1000000 / qps, TimeUnit.MICROSECONDS);

            }
        }
    }
}
