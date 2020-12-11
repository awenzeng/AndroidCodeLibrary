package com.awen.codebase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.awen.codebase.R;
import com.awen.codebase.activity.webview.AppletJavascriptInterface;
import com.awen.codebase.activity.webview.AppletWebChromeClient;
import com.awen.codebase.activity.webview.AppletWebViewClinet;
import com.awen.codebase.activity.webview.IAppletJsPushListener;
import com.awen.codebase.common.base.BaseActivity;

/**
 * @ClassName: WebViewActivity
 * @Author: AwenZeng
 * @CreateDate: 2020/11/6 20:59
 * @Description:
 */
public class WebViewActivity extends BaseActivity implements IAppletJsPushListener {
    public static final String TAG = "WebView";
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private String mUrl = "";
    private String mData;
    public static final String URL = "applet-url";
    public static final String DATA = "applet-data";

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_webview);
        initData(getIntent());
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initData(intent);
        loadUrl();
    }

    private void initData(Intent intent) {
        if (intent != null) {
            mUrl = intent.getStringExtra(URL);
            mData = intent.getStringExtra(DATA);
            if (mUrl == null) {
//                mUrl = "file:///android_asset/demo.html";
                mUrl = "https://www.baidu.com/";
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void init() {

        mWebView = findViewById(R.id.mWebView);
        mProgressBar = findViewById(R.id.mProgressBar);

        //设置支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new AppletJavascriptInterface(), "coocaaAppletJS");
        // 设置支持本地存储
        mWebView.getSettings().setDatabaseEnabled(true);
        //取得缓存路径
        //设置路径
        mWebView.getSettings().setDatabasePath(getDir("cache", Context.MODE_PRIVATE).getPath());
        //设置支持DomStorage
        mWebView.getSettings().setDomStorageEnabled(true);
        //设置存储模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        //设置适应屏幕
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        //设置缓存
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.requestFocus();
        //下面三个各种监听
//        mWebView.setDownloadListener(dl);
        mWebView.setWebViewClient(new AppletWebViewClinet(this,mProgressBar));
        mWebView.setWebChromeClient(new AppletWebChromeClient(mProgressBar));
        loadUrl();
    }

    public void loadUrl() {
        //加载连接
        mWebView.loadUrl(mUrl);
        sendMessage();
    }

    private void sendMessage(){
        mWebView.postDelayed(new Runnable() {
            @Override
            public void run() {
                onReceiveMessage("EVENT: ","我要努力！");
                onReceiveMessage("EVENT: ","我要努力！做好自己！！");
                onReceiveMessage("EVENT: ","我要努力！做好自己！！致良知！！！");
            }
        },3000);
    }



    @Override
    public void onReceiveMessage(String event, String data) {
        mWebView.loadUrl("javascript:onReceiveMessage('" + event + "','" + data + "')");
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        mWebView.stopLoading();
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.removeAllViews();
        mWebView.destroy();
        super.onDestroy();
    }
}