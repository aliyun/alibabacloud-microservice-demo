//package com.alibaba.arms.demo.client.cmd;
//
//import com.alibaba.arms.demo.client.testcase.ICase;
//import com.alibaba.arms.demo.client.testcase.MuyiCase;
//import com.alibaba.arms.mock.api.IComponentAPI;
//import com.google.common.base.Strings;
//import okhttp3.OkHttpClient;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author aliyun
// * @date 2020/06/17
// */
//public class MockClient {
//
//
//    private static final int NUM_OF_THREAD = 10;
//
//    private static MockClient CLIENT;
//
//    private IComponentAPI api;
//    private String defaultServer = "http://127.0.0.1:9190/";
//
//
//    public static void main(String[] args) throws IOException, InterruptedException {
//        CLIENT = new MockClient();
//        CLIENT.init("http://172.16.156.177/");
//        ICase test = new MuyiCase();
//        test.init();
//        Thread[] workers = new Thread[NUM_OF_THREAD];
//        for (int i = 0; i < NUM_OF_THREAD; i++) {
//            workers[i] = new Thread() {
//                @Override
//                public void run() {
//                    while (true) {
//                        Response<String> response = null;
//                        try {
//                            response = CLIENT.api.invokeChildren("local", "success", test.nextOne(), 0).execute();
//                            if (!response.isSuccessful()) {
//                                try {
//                                    System.out.println(response.errorBody().string());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            };
//
//            workers[i].setDaemon(false);
//        }
//        for (int i = 0; i < NUM_OF_THREAD; i++) {
//            workers[i].start();
//        }
//    }
//
//
//    public void init(String server) {
//        server = Strings.isNullOrEmpty(server) ? defaultServer : server;
//        System.out.println("init with server " + server);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(15, TimeUnit.MINUTES)
//                .connectTimeout(15, TimeUnit.MINUTES)
//                .writeTimeout(15, TimeUnit.MINUTES)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(server)
//                .client(okHttpClient)
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        this.api = retrofit.create(IComponentAPI.class);
//    }
//
//
//}
