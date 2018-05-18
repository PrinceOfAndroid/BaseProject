package com.taos.up.baseproject.demo.presenter;

import com.taos.up.baseproject.demo.beans.User;
import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.demo.model.LoginModel;
import com.taos.up.baseproject.http.HttpObserver;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.http.RxUtils;
import com.taos.up.baseproject.mvp.BasePresenter;

import io.reactivex.disposables.Disposable;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * 只专注于数据层与视图层的交互部分，通过接口方式通知view
 */

public class LoginPresenter extends BasePresenter<LoginContract.IView> implements LoginContract.IPresenter {
    private LoginContract.IModel iModel;

    /**
     * 进行初始化操作（初始化model）
     */
    @Override
    public void onCreate() {
        iModel = new LoginModel();
    }

    /**
     * 登录
     *
     * @param userId
     * @param userPwd
     */
    @Override
    public void login(String userId, String userPwd) {
        User user = new User();
        user.setMobile(userId);
        user.setPassword(userPwd);
        user.setSms_code("");

        addSubscribe(iModel.login(user)
                .compose(RxUtils.<HttpResponse<String>>applyFSchedulers())
                .compose(RxUtils.<String>handleResult())
                .subscribeWith(new HttpObserver<String>() {
                    @Override
                    public void onSuccess(String s) {

                    }
                }));

    }
}
