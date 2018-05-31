package com.taos.up.baseproject.http;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * <pre>
 *     author   : PrinceOfAndroid
 *     created  : 2017/7/5 0005 11:47
 *     desc     : 网络请求拦截器
 * </pre>
 */

public class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.e(TAG, "\n");
        Log.e(TAG, "----------Start----------------");
        Log.e(TAG, "| " + request.toString());
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.e(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        Log.e(TAG, "| Response:" + content);
        Log.e(TAG, "----------End:" + duration + "毫秒----------");

        Gson gson = new Gson();
        HttpResponse httpResponse = gson.fromJson(content, HttpResponse.class);
        if (httpResponse.getCode() != 0) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("code", httpResponse.getCode());
                jsonObject.put("msg",httpResponse.getMsg());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, jsonObject.toString()))
                    .build();
        } else {
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
