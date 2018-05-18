package com.taos.up.baseproject.http;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * <pre>
 *     author: PrinceOfAndroid
 *     date  : 2017/7/5 0005 11:30
 *     desc  : 网络请求结果封装(可处理Toast或dialog,但注意内存泄露问题)
 * </pre>
 */

public abstract class HttpObserver<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();  //打印网络请求异常
//        if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
//            mView.showErrorMsg(mErrorMsg);
//        } else if (e instanceof ApiException) {
//            mView.showErrorMsg(e.toString());
//        } else if (e instanceof HttpException) {
//            mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
//        } else {
//            mView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
//            LogUtil.d(e.toString());
//        }
    }

    /**
     * 完成
     */
    @Override
    public void onComplete() {

    }

    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！Error 是选择重写，but 必须Super ！
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    public void onSuccess(HttpResponse<T> value) {

    }

}
