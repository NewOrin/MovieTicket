package com.etc.movieticket.http;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
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
     * @param url  请求的url
     * @param json 封装的json字符串
     * @return 返回json字符串
     * @throws IOException
     */
    public String doHttpPost(String url, String json) {
        String result;
        RequestBody body = new FormBody.Builder().add("data", json).build();
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = null;
        try {
            response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
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
}
