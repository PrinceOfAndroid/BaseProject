package com.taos.up.baseproject.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 */

public class RetrofitFactory {
    private static BaseHttpService httpService = null;

    public static BaseHttpService getInstance() {
        if (httpService == null) {
            synchronized (RetrofitFactory.class) {
                if (httpService == null) {
                    return createHttpService();
                }
            }
        }
        return httpService;
    }

    private static BaseHttpService createHttpService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())  //全局拦截器
                .connectTimeout(8L, TimeUnit.SECONDS)  //连接超时
                .readTimeout(8L, TimeUnit.SECONDS)     //读取超时
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)                                             //设置okHttp
                .baseUrl(ApiUrl.BASE_URL)                                   //基础地址
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //引入rxJava2
                .addConverterFactory(GsonConverterFactory.create())         //json 转换
                .build();

        httpService = retrofit.create(BaseHttpService.class);
        return httpService;
    }
}
