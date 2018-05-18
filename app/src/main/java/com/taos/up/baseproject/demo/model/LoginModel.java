package com.taos.up.baseproject.demo.model;

import com.taos.up.baseproject.demo.beans.User;
import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.http.HttpObserver;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.http.RetrofitFactory;
import com.taos.up.baseproject.http.RxUtils;

import io.reactivex.Flowable;
import io.reactivex.Observable;


/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * 模型层，负责数据的处理
 */

public class LoginModel implements LoginContract.IModel {
    /**
     * @param user
     */
    @Override
    public Flowable<HttpResponse<String>> login(User user) {
        return RetrofitFactory.getInstance()
                .login(user);
    }
}
