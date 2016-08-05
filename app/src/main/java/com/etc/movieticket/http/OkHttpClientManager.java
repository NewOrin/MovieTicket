package com.etc.movieticket.http;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private static OkHttpClient mOkHttpClient;

    private static final String TAG = "OkHttpClientManager";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * Post请求
     *
     * @param url          请求的url
     * @param requestParam 请求参数字符串
     * @return 返回json字符串
     * @throws IOException
     */
    public String doHttpPost(String url, String requestParam) {
        RequestBody body = new FormBody.Builder().add("data", requestParam).build();
        return doHttp(body, url);
    }

    /**
     * Get请求
     *
     * @param url 请求的url
     * @return 返回json字符串
     */
    public String doHttpGet(String url) {
        return doHttp(null, url);
    }

    /**
     * 网络访问
     *
     * @param body
     * @param url
     * @return
     */
    public String doHttp(RequestBody body, String url) {
        String result;
        Request request;
        if (body == null) {
            request = new Request.Builder().url(url).build();
        } else {
            request = new Request.Builder().url(url).post(body).build();
        }
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
                Log.d(TAG, "返回数据:" + result);
                return result;
            } else {
                result = "网络请求错误";
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        result = "网络请求错误";
        return result;
    }

    public String uploadFile() {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("userid", "userid")
                .addFormDataPart("image", "avatar.jpg", RequestBody.create(MEDIA_TYPE_PNG, new File(""))).build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        return "";
    }
}
