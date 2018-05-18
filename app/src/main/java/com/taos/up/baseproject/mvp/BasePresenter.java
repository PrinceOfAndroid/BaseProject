package com.taos.up.baseproject.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by PrinceOfAndroid on 2018/4/9 0009.
 * presenter 基类(持有view)
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T mView;
    private CompositeDisposable mCompositeDisposable;

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
        unSubscribe();
        this.mView = null;
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);

    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 生命周期
     */
    public abstract void onCreate();

    /**
     * 销毁view的引用
     */
    public void onDestroy() {
        if (this.mView != null) {
            detachView();
        }
    }

}
