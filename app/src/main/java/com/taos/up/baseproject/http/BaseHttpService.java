package com.taos.up.baseproject.http;

import java.util.List;
import java.util.Map;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 */

public interface BaseHttpService {

    /**
     * post 请求，参数以map的形式传入(也可直接传值)
     *
     * @param map
     * @return
     */
    @POST(ApiUrl.ARTICLE_LIST)
    Observable<HttpResponse<String>> login(@QueryMap Map<String, String> map);

    /**
     * get 请求，参数以传值的形式传入（也可以传map）
     *
     * @param userId
     * @param userPwd
     * @return
     */
    @GET(ApiUrl.ARTICLE_LIST)
    Observable<HttpResponse<String>> login(@Query("userId") String userId, @Query("userPwd") String userPwd);


    /**
     * get 请求，参数以map的形式传入
     *
     * @param map
     * @return
     */
    @GET(ApiUrl.ARTICLE_LIST)
    Flowable<HttpResponse<List<String>>> getList(@QueryMap Map<String, String> map);


    /**
     * 单文件及参数上传
     *
     * @param file   //文件
     * @param userId //参数
     * @param remark
     * @param lat
     * @param lng
     * @return 返回 HttpResponse<String>
     */
    @POST(ApiUrl.ARTICLE_LIST)
    @Multipart
    Observable<HttpResponse<String>> addFile(@Part MultipartBody.Part file,
                                             @Part("userId") RequestBody userId,
                                             @Part("remark") RequestBody remark,
                                             @Part("lat") RequestBody lat,
                                             @Part("lng") RequestBody lng);


    /**
     * 多文件以及参数上传
     *
     * @param file
     * @param menuFile
     * @param userId
     * @param remark
     * @param lat
     * @param lng
     * @param saleId
     * @param deliveryAreaId
     * @return
     */
    @POST(ApiUrl.ARTICLE_LIST)
    @Multipart
    Observable<HttpResponse<String>> addMultiFile(@Part MultipartBody.Part[] file, //多文件
                                                  @Part MultipartBody.Part[] menuFile,//多文件
                                                  @Part("userId") RequestBody userId,
                                                  @Part("remark") RequestBody remark,
                                                  @Part("lat") RequestBody lat,
                                                  @Part("lng") RequestBody lng,
                                                  @Part("saleId") RequestBody saleId,
                                                  @Part("deliveryAreaId") RequestBody deliveryAreaId);
}
