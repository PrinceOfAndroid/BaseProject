package com.taos.up.baseproject.mvp;

import android.content.Context;

/**
 * Created by PrinceOfAndroid on 2018/4/25 0025.
 */

public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements BaseView {
    protected T mPresenter;

    /**
     * 初始化已完成
     */
    @Override
    protected void onCreateFinish() {
        super.onCreateFinish();
        mPresenter = buildPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.onCreate();
        }
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDetach();
    }

    public abstract T buildPresenter();


}
