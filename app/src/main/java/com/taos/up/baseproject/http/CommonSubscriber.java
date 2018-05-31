package com.taos.up.baseproject.http;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * <pre>
 *     author: PrinceOfAndroid
 *     date  : 2017/7/5 0005 11:30
 *     desc  : 网络请求结果封装(可处理Toast或dialog,但注意内存泄露问题)
 * </pre>
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 通过Throwable处理对应异常
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
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


}
