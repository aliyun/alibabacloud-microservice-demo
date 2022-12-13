package com.karl.pre.utils;

import okhttp3.*;

import java.io.IOException;

public class HttpHelper {
    public static final OkHttpClient client = new OkHttpClient();

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

//        request = request.newBuilder().url("http://localhost:8381/world").build();

//        HttpUrl httpUrl = request.url().newBuilder().host("127.0.0.1").port(8111).build();

//        HttpUrl httpUrl = request.url().newBuilder().host("127.0.0.1").build();
//
//        request = request.newBuilder().url(httpUrl).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

//    //public static final MediaType JSON  = MediaType.get("application/json; charset=utf-8");
//    public static final MediaType mediaType = MediaType.get("application/octet-stream; charset=utf-8");
//
//    public static String post(String url, String param) throws IOException {
//        RequestBody body = RequestBody.create(mediaType, param);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
