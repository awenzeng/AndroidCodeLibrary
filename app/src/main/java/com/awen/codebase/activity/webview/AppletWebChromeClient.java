package com.awen.codebase.activity.webview;

import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;


/**
 * @ClassName: AppletWebChromeClient
 * @Author: AwenZeng
 * @CreateDate: 2020/10/22 18:00
 * @Description:
 */
public class AppletWebChromeClient extends WebChromeClient {

    private ProgressBar mProgressBar;

    public AppletWebChromeClient(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
//            setTitle(title);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        super.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            if (mProgressBar.getVisibility() == View.GONE) {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            mProgressBar.setProgress(newProgress);
        }
        super.onProgressChanged(view, newProgress);
    }
}
