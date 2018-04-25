package com.taos.up.baseproject.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/4/19 0019.
 * 非mvp基类Fragment
 */

public abstract class SimpleFragment extends Fragment  {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;

    private boolean isFirstVisible;

    private Unbinder mUnBinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化状态
        initVariable();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    /**
     * 创建fragment中的View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        contentView = inflater.inflate(getLayoutId(), container, false);
        return contentView;
    }

    /**
     * 视图完成加载 在onCreateView后执行
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        if (view != null) {
            Bundle bundle = getArguments();
            //初始化数据 如list  intent传值
            initData(bundle);
            //实例化view
            initView(savedInstanceState, contentView);
            //数据与视图初始化完成
            onCreateFinish();
            initEventLoadData();

            if (getUserVisibleHint() && isFirstVisible) {
                lazyLoad();
                isFirstVisible = false;
            }

            onFragmentVisibleChange(getUserVisibleHint());
        }

    }

    protected void onCreateFinish() {

    }

    /**
     * 初始化数据
     * @param bundle
     */
    protected abstract void initData(Bundle bundle);

    protected abstract int getLayoutId();

    //实例化操作
    protected abstract void initView(Bundle savedInstanceState, View view);

    /**
     * 获取数据，事件监听
     */
    protected abstract void initEventLoadData();


    private void initVariable() {
        contentView = null;
        isFirstVisible = true;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/
    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */

    public void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 第一次可见 可选择重写（懒加载模式）
     */
    public void lazyLoad() {

    }


    @Override
    public void onDestroyView() {
        if (contentView != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

}
