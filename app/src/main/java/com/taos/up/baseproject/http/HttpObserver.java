package com.taos.up.baseproject.http;

import android.support.annotation.CallSuper;

import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <pre>
 *     author: PrinceOfAndroid
 *     date  : 2017/7/5 0005 11:30
 *     desc  : 网络请求结果封装(可处理Toast或dialog,但注意内存泄露问题)
 * </pre>
 */

public abstract class HttpObserver<T> implements Observer<HttpResponse<T>> {
    private final int RESPONSE_CODE_OK = 200;       //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_CODE_FAILED = -1;  //返回数据失败,严重的错误
    private int errorCode;
    private String errorMsg = "未知的错误！";
    private static Gson gson = new Gson();


    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(HttpResponse<T> value) {
        //请求成功 code为服务器规定的成功code
        if (value.getCode() == RESPONSE_CODE_OK) {
            if (value.getObject() != null) {
                //直接返回所需obj
                onSuccess(value.getObject());
            }
            //返回带code 与 msg 的对象
            onSuccess(value);
        } else {
            //处理服务器错误code
            onFailure(value.getCode(), null, value.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();  //打印网络请求异常
        //这里可处理各种网络异常
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            getErrorMsg(httpException);
        } else if (e instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "连接服务器失败！请检查您的网络状况";
        } else if (e instanceof UnknownHostException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "哎呀，网络连接有问题~";
        }

        //统一处理
        onFailure(errorCode, e, errorMsg);
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


    /**
     * 请求失败（服务器返回数据  服务器无法返回数据）
     *
     * @param code     服务器返回的code
     * @param e        请求中异常
     * @param errorMsg 服务器返回的消息
     */
    @CallSuper
    public void onFailure(int code, Throwable e, String errorMsg) {
        if (code == RESPONSE_CODE_FAILED) {
            //返回数据失败,严重的错误
//            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        } else {
            //服务器规定的错误code
            disposeEorCode(errorMsg, code);
        }
    }


    /**
     * 对服务器规定的错误code统一拦截处理
     *
     * @param code
     */
    private final void disposeEorCode(String message, int code) {
        switch (code) {
            case 101:

                break;
            case 401:

                break;
        }
    }


    /**
     * 获取详细的错误的信息 errorCode,errorMsg ,   这里和Api 结构有关，这里和Api 结构有关 ！
     * 以登录的时候的Grant_type 故意写错为例子,http 应该是直接的返回401=httpException.code()
     * 但是是怎么导致401的？我们的服务器会在respose.errorBody 中的content 中说明
     */
    private final void getErrorMsg(HttpException httpException) {
        String errorBodyStr = "";
        try {
            HttpResponse errorResponse = gson.fromJson(errorBodyStr, HttpResponse.class);
            if (null != errorResponse) {
                errorCode = errorResponse.getCode();
                errorMsg = errorResponse.getMessage();
            } else {
                errorCode = RESPONSE_CODE_FAILED;
                errorMsg = "ErrorResponse is null";
            }
        } catch (Exception jsonException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "http请求错误Json 信息异常";
            jsonException.printStackTrace();
        }
    }
}
