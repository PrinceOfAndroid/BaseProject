package com.taos.up.baseproject.mvp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author   : PrinceOfAndroid
 *     created  : 2017/7/5 0005 11:56
 *     desc     : activity 基础类
 * </pre>
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity
        implements View.OnClickListener, BaseView {
    protected T mPresenter;
    /**
     * 是否全屏
     */
    private boolean isFullScreen = false;
    /**
     * 是否沉浸状态栏
     */
    private boolean isSteepStatusBar = false;
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;
    protected BaseActivity mActivity;
    public static List<Activity> activities;
    private final String TAG = "MPermissions";
    private int REQUEST_CODE_PERMISSION = 0x00099;


    public BaseActivity() {
        //构造方法创建装Activity的list
        if (activities == null) {
            activities = new ArrayList<Activity>();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        initStatusBar();

        contentView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(contentView);
        activities.add(this);

        initView(savedInstanceState, contentView);
        initListener();

        //初始化 presenter 执行onCreate() 生命周期 绑定view(activity)
        mPresenter = buildPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.onCreate();
        }
    }

    /**
     * 初始化状态栏
     */
    private void initStatusBar() {
        if (isFullScreen) {
            this.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSteepStatusBar) {
            // 经测试在代码里直接声明透明状态栏更有效（设置状态栏透明）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }
    }


    /**
     * 初始化数据
     *
     * @param bundle 从上个Activity传递过来的bundle
     */
    protected abstract void initData(final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局Id
     */
    protected abstract int bindLayout();

    /**
     * 初始化view
     */
    protected abstract void initView(final Bundle savedInstanceState, final View view);

    /**
     * 初始化事件
     */
    protected abstract void initListener();

    /**
     * 抽象生成presenter 交给子类去实现
     *
     * @return
     */
    protected abstract T buildPresenter();

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    protected abstract void onWidgetClick(final View view);

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    protected boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

    /**
     * 设置是否全屏
     *
     * @param isFullScreen 是否全屏
     */
    protected void setFullScreen(final boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
    }

    /**
     * 设置是否沉浸状态栏
     *
     * @param isSteepStatusBar 是否沉浸状态栏
     */
    protected void setSteepStatusBar(final boolean isSteepStatusBar) {
        this.isSteepStatusBar = isSteepStatusBar;
    }

    /**
     * 退出程序
     */
    protected void exitSystem() {
        for (Activity activity : activities) {
            activity.finish();
        }
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * 关闭所有界面
     */
    protected void finishActivities() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

    /**
     * 关闭外层界面
     */
    protected void finishOutActivities() {
        for (int i = 0; i < activities.size(); i++) {
            if (i != 0) {
                activities.get(i).finish();
            }
        }
    }

    /**
     * 实现baseView获取上下文方法 返回this
     *
     * @return
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * onDestroy 事件 释放资源
     */
    @Override
    protected void onDestroy() {
        activities.remove(this);
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        super.onDestroy();
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     * @param requestCode 请求权限的请求码
     */
    protected void requestPermission(String[] permissions, int requestCode) {
        this.REQUEST_CODE_PERMISSION = requestCode;
        if (checkPermissions(permissions)) {
            permissionSuccess(REQUEST_CODE_PERMISSION);
        } else {
            List<String> needPermissions = getDeniedPermissions(permissions);
            ActivityCompat.requestPermissions(this, needPermissions.toArray(new String[needPermissions.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     *
     * @param permissions
     * @return
     */
    private boolean checkPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


    /**
     * 系统请求权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(grantResults)) {
                permissionSuccess(REQUEST_CODE_PERMISSION);
            } else {
                permissionFail(REQUEST_CODE_PERMISSION);
                showTipsDialog();
            }
        }
    }

    /**
     * 确认所有的权限是否都已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    private void showTipsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    public void permissionSuccess(int requestCode) {
        Log.d(TAG, "获取权限成功=" + requestCode);

    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    public void permissionFail(int requestCode) {
        Log.d(TAG, "获取权限失败=" + requestCode);
    }
}
