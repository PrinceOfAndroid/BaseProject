package com.taos.up.baseproject.demo.model;

import com.taos.up.baseproject.demo.beans.User;
import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.http.HttpObserver;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.http.RetrofitFactory;
import com.taos.up.baseproject.http.RxUtils;



/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * 模型层，负责数据的处理
 */

public class LoginModel implements LoginContract.IModel {
    /**
     * @param user
     * @param httpObserver 接口访问的回调
     */
    @Override
    public void login(User user, HttpObserver<String> httpObserver) {
        RetrofitFactory.getInstance()
                .login(user)
                .compose(RxUtils.<HttpResponse<String>>applySchedulers())
                .subscribe(httpObserver);
    }
}
