package com.awen.codebase.activity.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.awen.codebase.activity.WebViewActivity;
import com.awen.codebase.common.utils.LogUtil;


/**
 * @ClassName: AppletWebViewClinet
 * @Author: AwenZeng
 * @CreateDate: 2020/10/22 17:56
 * @Description:
 */
public class AppletWebViewClinet extends WebViewClient {
    private ProgressBar mProgressBar;
    private Context mContext;

    public AppletWebViewClinet(Context context, ProgressBar progressBar) {
        mContext = context;
        this.mProgressBar = progressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LogUtil.androidLog(WebViewActivity.TAG, "======== onLoadUrl   =======");
        LogUtil.androidLog(WebViewActivity.TAG, "Url=" + url);
        try {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
            } else {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                mContext.startActivity(intent);
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mProgressBar.setProgress(0);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.androidLog(WebViewActivity.TAG, "======== onPageFinished   =======");
        LogUtil.androidLog(WebViewActivity.TAG, "finish url->" + (TextUtils.isEmpty(url) ? "" : url));
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

}
