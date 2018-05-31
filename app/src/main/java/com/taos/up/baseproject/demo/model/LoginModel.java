package com.taos.up.baseproject.demo.model;

import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.http.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;


/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * 模型层，负责数据的处理
 */

public class LoginModel implements LoginContract.IModel {
    /**
     * @param map
     */
    @Override
    public Flowable<HttpResponse<List<String>>> getList(Map<String,String> map) {
        return RetrofitFactory.getInstance()
                .getList(map);
    }
}
