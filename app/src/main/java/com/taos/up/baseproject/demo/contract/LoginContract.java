package com.taos.up.baseproject.demo.contract;

import com.taos.up.baseproject.demo.beans.User;
import com.taos.up.baseproject.http.HttpObserver;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.mvp.BaseView;

import io.reactivex.Flowable;
import io.reactivex.Observable;


/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * mvp的管理者，方法都在这里进行定义
 */

public interface LoginContract {
    interface IModel {
        Flowable<HttpResponse<String>> login(User user);
    }

    interface IView extends BaseView {

        void showLoginSuccess(String s);

    }

    interface IPresenter {
        void login(String userId, String userPwd);
    }
}
