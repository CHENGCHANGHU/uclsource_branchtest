package com.example.uclsourceproject;

import android.os.Message;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    public static String BASEURL_LOGIN_SIGN_PRODUCE = "http://223.3.79.211:8000";
    public static String BASEURL_SELL = "http://223.3.79.119:8000";

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public static void sendOKHttp3RequestPOST(final String url, final String jsonPara, final okhttp3.Callback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody body = RequestBody.create(JSON, jsonPara);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }

    public static void sendOKHttp3RequestGET(final String url, final okhttp3.Callback callback, final Object... args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
//                RequestBody body = RequestBody.create(JSON, jsonstr);
                StringBuilder sb = new StringBuilder(url + "?");
                for (int i = 0; i < args.length; i += 2) {
                    sb.append("\"")
                            .append(args[i])
                            .append("\"")
                            .append(":")
                            .append("\"")
                            .append(args[i + 1])
                            .append("\"")
                            .append(",");
                }
                sb.setLength(sb.length() - 1);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(sb.toString())
                        .build();
                client.newCall(request).enqueue(callback);
            }
        }).start();
    }
}

//    public static void uploadFile(final String url) {
//        //1.创建对应的MediaType
//        private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//        private final OkHttpClient client = new OkHttpClient();
//
//        public void uploadImage (String userName, File file) throws NetworkException {
//
//            //2.创建RequestBody
//            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
//
//            //3.构建MultipartBody
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .setType(MultipartBody.FORM)
//                    .addFormDataPart("file", "testImage.png", fileBody)
//                    .addFormDataPart("userName", userName)
//                    .build();
//
//            //4.构建请求
//            Request request = new Request.Builder()
//                    .url("http://xxxxx")
//                    .post(requestBody)
//                    .build();
//
//            //5.发送请求
//            Response response = client.newCall(request).execute();
//        }
//
//
//    }
