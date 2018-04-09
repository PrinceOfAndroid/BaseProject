package com.taos.up.baseproject.demo.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taos.up.baseproject.R;
import com.taos.up.baseproject.demo.contract.LoginContract;
import com.taos.up.baseproject.demo.presenter.LoginPresenter;
import com.taos.up.baseproject.mvp.BaseActivity;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * 通过接口与presenter进行交互
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.IView {
    private EditText etId;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View view) {
        etId = (EditText) findViewById(R.id.et_id);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
    }

    /**
     * 初始化该View的presenter
     * @return
     */
    @Override
    protected LoginPresenter buildPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkData();
                break;
        }
    }

    /**
     * 我习惯在activity中处理界面数据 把数据整理好传给presenter
     */
    private void checkData() {
        String id = etId.getText().toString();
        String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(id)||TextUtils.isEmpty(pwd)){
            //Toast
            return;
        }
        mPresenter.login(id,pwd);
    }

    /**
     * 登录成功presenter的通知
     * @param s
     */
    @Override
    public void showLoginSuccess(String s) {
        Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show();
    }
}
