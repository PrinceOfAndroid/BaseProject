package com.taos.up.baseproject.mvp;

import android.app.Application;
import android.content.Context;

/**
 * Created by PrinceOfAndroid on 2018/4/10 0010.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static Context getInstance() {
        return myApplication;
    }
}
