package com.taos.up.baseproject.mvp;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * presenter 基类(持有view)
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T mView;

    /**
     * 绑定 view
     *
     * @param view
     */
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * view 解绑
     */
    public void detachView() {
        this.mView = null;
    }


    /**
     * 生命周期
     */
    public abstract void onCreate();

    /**
     * 销毁view的引用
     */
    public void onDestory() {
        if (this.mView != null) {
            detachView();
        }
    }

}
