package com.example.wordlistapp.web;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//登陆注册的URL并不相同！！！！！！！！！
public class OkhttpUtil {

    public static void Login(String url1, String name, String passwd, Handler handler) {
        enqueueUser(url1, name, passwd, handler, "1");
    }

    public static void Register(String url2, String name, String passwd, Handler handler) {
        enqueueUser(url2, name, passwd, handler, "3");
    }

    public static void Add(String url2, String USR_ID, String englishWord, String chineseWord, String Instance, Handler handler) {
        enqueueUPDATE(url2, USR_ID, englishWord, chineseWord, Instance, handler, "1");
    }

    public static void Delete(String url2, String USR_ID, String englishWord, Handler handler) {
        enqueueUPDATE(url2, USR_ID, englishWord, "", "", handler, "2");
    }

    public static void Update(String url2, String USR_ID, String englishWord, String chineseWord, String Instance, Handler handler) {
        enqueueUPDATE(url2, USR_ID, englishWord, chineseWord, Instance, handler, "4");
    }

    public static void SelectOne(String url1, String USR_ID, String englishWord, Handler handler) {
        enqueueSelect(url1, USR_ID, englishWord, handler, "4");
    }

    public static void SelectMore(String url1, String USR_ID, Handler handler) {
        enqueueSelect(url1, USR_ID, "", handler, "3");
    }

    private static void enqueueUser(String url, String name, String passwd, final Handler handler, String reqID) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("passwd", passwd)
                .add("reqID", reqID)
                .build();
        //构建 Request.Builder 对象，方便中途插入配置及后续构建 Request
        Request request = new Request.Builder().post(body).url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            /**
             * 网络请求错误或失败的响应函数
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", "请求失败，请重试！");

                handler.sendEmptyMessage(HttpConfig.HTTP_FAILURE);
            }

            /**
             * 正常响应函数
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("onResponse", "请求成功，响应码：" + response.code());

                Message msg = handler.obtainMessage();
                msg.what = response.code();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    private static void enqueueUPDATE(String url, String USR_ID, String englishWord, String chineseWord, String Instance, final Handler handler, String reqID) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", USR_ID)
                .add("englishWord", englishWord)
                .add("chineseWord", chineseWord)
                .add("instance", Instance)
                .add("reqID", reqID)
                .build();
        //构建 Request.Builder 对象，方便中途插入配置及后续构建 Request
        Request request = new Request.Builder().post(body).url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            /**
             * 网络请求错误或失败的响应函数
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", "请求失败，请重试！");

                handler.sendEmptyMessage(HttpConfig.HTTP_FAILURE);
            }

            /**
             * 正常响应函数
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("onResponse", "请求成功，响应码：" + response.code());

                Message msg = handler.obtainMessage();
                msg.what = response.code();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    private static void enqueueSelect(String url, String USR_ID, String englishWord, final Handler handler, String reqID) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("userid", USR_ID)
                .add("englishWord", englishWord)
                .add("reqID", reqID)
                .build();
        //构建 Request.Builder 对象，方便中途插入配置及后续构建 Request
        Request request = new Request.Builder().post(body).url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            /**
             * 网络请求错误或失败的响应函数
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", "请求失败，请重试！");

                handler.sendEmptyMessage(HttpConfig.HTTP_FAILURE);
            }

            /**
             * 正常响应函数
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("onResponse", "请求成功，响应码：" + response.code());

                Message msg = handler.obtainMessage();
                msg.what = response.code();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

    public static void enqueueSyn(String url, String USR_ID, String words, final Handler handler, String reqID) {
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder()
                .add("userid", USR_ID)
                .add("reqID", reqID)
                .add("words", words)
                .build();
        //构建 Request.Builder 对象，方便中途插入配置及后续构建 Request
        Request request = new Request.Builder().post(body).url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            /**
             * 网络请求错误或失败的响应函数
             */
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("onFailure", "请求失败，请重试！");

                handler.sendEmptyMessage(HttpConfig.HTTP_FAILURE);
            }

            /**
             * 正常响应函数
             */
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i("onResponse", "请求成功，响应码：" + response.code());

                Message msg = handler.obtainMessage();
                msg.what = response.code();
                msg.obj = response.body().string();
                handler.sendMessage(msg);
            }
        });
    }

}
