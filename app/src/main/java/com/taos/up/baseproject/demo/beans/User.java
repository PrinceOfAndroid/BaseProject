package com.taos.up.baseproject.demo.beans;

/**
 * Created by mayn on 2017/11/1.
 */

public class User {
    private String mobile;
    private String sms_code;
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSms_code() {
        return sms_code;
    }

    public void setSms_code(String sms_code) {
        this.sms_code = sms_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "mobile='" + mobile + '\'' +
                ", sms_code='" + sms_code + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
