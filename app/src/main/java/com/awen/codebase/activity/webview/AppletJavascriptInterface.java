package com.awen.codebase.activity.webview;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.awen.codebase.CodeBaseApp;


/**
 * @ClassName: AppletJavascriptInterface
 * @Author: AwenZeng
 * @CreateDate: 2020/10/22 16:10
 * @Description:
 */
public class AppletJavascriptInterface {


    public AppletJavascriptInterface() {
    }
    /**
     *
     * @param data
     */
    @JavascriptInterface
    public void sendTVBroadcastMsg(String data) {
        Toast.makeText(CodeBaseApp.getAppContext(),"发送消息：" + data,Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param data
     */
    @JavascriptInterface
    public void sendBroadcastMsg(String data) {
        Toast.makeText(CodeBaseApp.getAppContext(),"发送消息：" + data,Toast.LENGTH_SHORT).show();
    }

}
