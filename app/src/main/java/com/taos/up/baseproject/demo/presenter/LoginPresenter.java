package com.taos.up.baseproject.demo.presenter;

import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.demo.model.LoginModel;
import com.taos.up.baseproject.http.CommonSubscriber;
import com.taos.up.baseproject.http.HttpResponse;
import com.taos.up.baseproject.http.RxUtils;
import com.taos.up.baseproject.mvp.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, String> map = new HashMap<>();
        map.put("is_index", "true");
        map.put("rows", String.valueOf("3"));

        addSubscribe(iModel.getList(map)
                .compose(RxUtils.<HttpResponse<List<String>>>applyFSchedulers())
                .compose(RxUtils.<List<String>>handleResult())
                .subscribeWith(new CommonSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {

                    }
                }));

    }
}
