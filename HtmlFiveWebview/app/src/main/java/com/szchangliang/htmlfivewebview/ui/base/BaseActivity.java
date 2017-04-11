package com.szchangliang.htmlfivewebview.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by 麦显达 on 2017/4/10.
 * 所有Activity的基类
 * 调用顺序为
 * 1.super.OnCreate
 * 2.beforeInitView
 * 3.initView
 * 4.initListener
 * 5.initData
 */

public abstract class BaseActivity extends Activity {
    /**
     * 一切工作的预备工作
     */
    protected abstract void beforeInitView();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化视图
     */
    protected abstract void initView();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView();
        initView();
        initListener();
        initData();
    }
}
