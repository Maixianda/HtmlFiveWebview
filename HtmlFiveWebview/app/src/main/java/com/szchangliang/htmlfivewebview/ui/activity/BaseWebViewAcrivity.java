package com.szchangliang.htmlfivewebview.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.szchangliang.htmlfivewebview.R;
import com.szchangliang.htmlfivewebview.ui.base.BaseActivity;
import com.szchangliang.htmlfivewebview.ui.view.LoadingLayout;
import com.szchangliang.htmlfivewebview.util.PhotoUtil;

/**
 * webView的基类
 * 支持js调用原生
 * Created by maixianda on 2017/4/10.
 */
public class BaseWebViewAcrivity extends BaseActivity{

    //region final static变量
    /**
     * 判断5.0以下手机的标志
     */
    public final static int FILECHOOSER_RESULTCODE = 1;
    /**
     * 判断5.0手机的标志
     */
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    //endregion final static变量

    //region 成员变量
    /**
     * 用于异步提供值的回调接口,用来提供5.0以下的手机的图片的值
     */
    public ValueCallback<Uri> mUploadMessage;
    /**
     * 用于异步提供值的回调接口,用来提供5.0的手机的图片的值
     */
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    /**
     * 要加载的url,通过intent传进来
     */
    protected String url;

    /**
     * 要加载的url的intent的key
     */
    public static final String WEB_URL = "url";

    /**
     * webview
     */
    protected WebView idWebWebView;

    /**
     * 加载网页对话框
     */
    protected LoadingLayout idWebLoadLayout;

    /**
     * 是否正在加载
     */
    private boolean isLoading;
    //endregion 成员变量

    //region 生命周期
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 用于获取传进来的intent
     */
    @Override
    public void beforeInitView() {
        url = getIntent().getStringExtra(WEB_URL);
    }

    /**
     * 用于findeviewbyid
     */
    @Override
    public void initView() {
        idWebWebView = (WebView) findViewById(R.id.id_webview);
        idWebLoadLayout = (LoadingLayout) findViewById(R.id.id_loadview);
    }

    @Override
    public void initListener() {

    }

    /**
     * 判断完url后，初始化webview
     *
     */
    @Override
    @SuppressLint("JavascriptInterface")
    public void initData() {
        //region 判断url是否正确
        if (null == url || "".equals(url.trim())) {
            Toast.makeText(this, "请输入正确地址", Toast.LENGTH_SHORT).show();
            return;
        }
        //endregion 判断url是否正确

        idWebWebView.setVerticalScrollBarEnabled(false); //设置垂直显示网页时不显示webview滚动条

        //region WebSettings设置
        WebSettings settings = idWebWebView.getSettings();
        settings.setJavaScriptEnabled(true);//设置启用js
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js能打开新窗口,如window.open()
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存：
        //endregion WebSettings设置

        idWebWebView.setWebViewClient(new WebViewClient() {
            boolean isSuccess;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            /**
             * 加载成功
             *
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                //super.onPageFinished(view, url);
                Log.e("ceshi-:", "ceshi" + url);
                isLoading = false;

                if (null != idWebLoadLayout && isSuccess) {
//                    WeLog.d("!!---------onPageFinished");
                    idWebLoadLayout.setLoadSuccess(idWebWebView);
                    loadSuccess();
                }
//                idWebLoadLayout.setLoadStop(true, null, null);
            }

            /**
             * 开始加载
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isLoading = true;
//                idWebLoadLayout.setLoadStart();
                isSuccess = true;
                startLoading();
                if (null != idWebLoadLayout)
                    idWebLoadLayout.setLoadStart();
            }

            /**
             * 加载失败
             */
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                isLoading = false;
                super.onReceivedError(view, errorCode, description, failingUrl);
//                idWebLoadLayout.setLoadStop(false, null, "访问失败!");
                isSuccess = false;
                if (null != idWebLoadLayout)
                    idWebLoadLayout.setLoadFailure("访问失败!");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                isSuccess = false;
                loadFailure();
                if (null != idWebLoadLayout)
                    idWebLoadLayout.setLoadFailure("访问失败!");
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) { // 重写此方法可以让webview处理https请求
                // handler.proceed();
                super.onReceivedSslError(view, handler, error);
                isSuccess = false;
                loadFailure();
                if (null != idWebLoadLayout)
                    idWebLoadLayout.setLoadFailure("访问失败!");
            }
        });

        idWebWebView.setWebChromeClient(new WebChromeClient(){


            //扩展浏览器上传文件
            //3.0++版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            //3.0--版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 5.0
            public boolean onShowFileChooser (WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }

            private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                //region 文件选取的
//                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                i.addCategory(Intent.CATEGORY_OPENABLE);
//                i.setType("image/*");
//                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                //endregion 文件选取的
                PhotoUtil.takePhoto(BaseWebViewAcrivity.this,FILECHOOSER_RESULTCODE);
            }

            private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
                mUploadMessageForAndroid5 = uploadMsg;
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
            }

        });

        idWebLoadLayout.setBtnRetry(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idWebWebView.loadUrl(url);
            }
        });

        Log.d("maixianda", "initData: "+url);
        idWebWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    //endregion 生命周期

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null: data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDROID_5){
            if (null == mUploadMessageForAndroid5)
                return;
            Uri result = (data == null || resultCode != RESULT_OK) ? null: data.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }

    public void startLoading() {
    }

    public void loadSuccess() {
    }

    public void loadFailure() {
    }

    protected int getResourceId() {
        return R.layout.activity_sec_basewebview;
    }
}
