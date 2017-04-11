package com.szchangliang.htmlfivewebview.ui.activity;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.szchangliang.htmlfivewebview.common.LoadingHelper;
import com.szchangliang.htmlfivewebview.databinding.ActivitySecBasewebviewBinding;

/**
 * Created by 麦显达 on 2017/4/10.
 */

public class CustomerWebViewActivity extends BaseWebViewAcrivity implements View.OnClickListener{
    private ActivitySecBasewebviewBinding binding;
    private boolean isShowLoadingView = false;//是否显示默认的正在加载对话框,false不显示

    //region 生命周期
    @Override
    public void beforeInitView() {
        super.beforeInitView();
        super.beforeInitView();
        binding = DataBindingUtil.setContentView(this, getResourceId());
    }

    @Override
    public void initView() {
        super.initView();
        binding.idMenuBtn.setOnClickListener(this);

        if (isShowLoadingView ==false)
        {
            idWebLoadLayout.setVisibility(View.GONE);
        }

        //javascript = getIntent().getStringExtra(JAVASCRIPT_KEY);
    }

    @Override
    public void initListener() {

    }
    //endregion 生命周期

    //region 页面加载控制
    @Override
    public void startLoading() {
        binding.idVdh.setVisibility(View.GONE);
        LoadingHelper.showMaterLoading(this, "加载中");
        binding.idMenuBtn.setVisibility(View.GONE);
    }

    @Override
    public void loadSuccess() {
        binding.idVdh.setVisibility(View.VISIBLE);
        LoadingHelper.hideMaterLoading();
    }

    @Override
    public void loadFailure() {
        LoadingHelper.hideMaterLoading();
        binding.idVdh.setVisibility(View.VISIBLE);
        binding.idMenuBtn.setVisibility(View.GONE);
    }
    //endregion 页面加载控制

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if(idWebWebView.canGoBack())
        {
            idWebWebView.goBack();
        }
        else
        {
            finish();
        }
    }

}
