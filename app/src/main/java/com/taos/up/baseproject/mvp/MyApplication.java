package com.taos.up.baseproject.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by PrinceOfAndroid on 2018/4/10 0010.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication = null;
    private Set<Activity> allActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }
}
