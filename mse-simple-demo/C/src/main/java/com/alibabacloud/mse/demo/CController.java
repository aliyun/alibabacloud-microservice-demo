package com.alibabacloud.mse.demo;

import com.alibabacloud.mse.demo.sql.User;
import com.alibabacloud.mse.demo.sql.UserDao;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
class CController {

    @Autowired
    InetUtils inetUtils;

    @Autowired
    String serviceTag;

    @Value("${throwException:false}")
    boolean throwException;

    private String currentZone;

    private static final Random RANDOM = new Random();

    @Autowired
    private UserDao userDao;

    private AutoTask task = null;

    @PostConstruct
    private void init() {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(1000)
                    .setConnectTimeout(1000)
                    .setSocketTimeout(1000)
                    .build();
            HttpGet req = new HttpGet("http://100.100.100.200/latest/meta-data/zone-id");
            req.setConfig(requestConfig);
            HttpResponse response = client.execute(req);
            currentZone = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            currentZone = e.getMessage();
        }

        if (task != null) {
            return;
        }
        task = new AutoTask();
        task.start();
    }

    private class AutoTask extends Thread {

        private boolean stopped = false;

        public AutoTask() {
            setName("ahas-mybatis-auto-task");
        }

        @Override
        public void run() {
            while (true) {
                if (stopped) {
                    return;
                }
                try {
                    autoDo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(30, 50));
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }

        public void stopTask() {
            stopped = true;
        }
    }

    @Transactional
    public void autoDo() {
        String username = "foo_test_$$" + ThreadLocalRandom.current().nextInt(0, 100000);
        int rand = ThreadLocalRandom.current().nextInt(10000000);
        userDao.addUser(username, "abc" + rand + "@ss.com");
        User user = userDao.findUserByName(username);
        if (user != null) {
            if (rand % 3 == 0) {
                userDao.updateUsername(user.getId(), username + rand);
            }
            userDao.deleteUser(user.getId());
        }

//        try{
//            Class.forName("com.mysql.jdbc.Driver");
//            //2. 获得数据库连接
//            Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "", "");
//            //3.操作数据库，实现增删改查
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT username FROM user_test where username=" + username);
//            //如果有数据，rs.next()返回true
//            while(rs.next()){
//                System.out.println(rs.getString("username"));
//            }
//
//        }catch (Throwable e){
//
//        }

    }

    @GetMapping("/c")
    public String c(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }

    @GetMapping("/c-zone")
    public String cZone(HttpServletRequest request) {
        if (throwException) {
            throw new RuntimeException();
        }
        return "C" + serviceTag + "[" + currentZone + "]";
    }

    @GetMapping("/spring_boot")
    public String spring_boot(HttpServletRequest request) {
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]";
    }


    @GetMapping("/flow")
    public String flow(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime;
    }

    @GetMapping("/params/{hot}")
    public String params(HttpServletRequest request,@PathVariable("hot") String hot) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime+":"+hot;
    }

    @GetMapping("/isolate")
    public String isolate(HttpServletRequest request) throws ExecutionException, InterruptedException {
        long sleepTime = 5 + RANDOM.nextInt(5);
        silentSleep(sleepTime);
        return "C" + serviceTag + "[" + inetUtils.findFirstNonLoopbackAddress().getHostAddress() + "]" + " -> " + sleepTime;
    }

    private void silentSleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
