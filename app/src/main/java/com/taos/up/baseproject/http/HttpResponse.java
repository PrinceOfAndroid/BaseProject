package com.taos.up.baseproject.http;

/**
 * <pre>
 *     author   : PrinceOfAndroid
 *     created  : 2017/7/5 0005 11:50
 *     desc     : 基本的接口数据返回类型
 * </pre>
 */
public class HttpResponse<T> {
    private int code;
    private String message;
    private T object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
