package com.awen.codebase.activity.webview;

/**
 * @ClassName: IAppletPushListener
 * @Author: AwenZeng
 * @CreateDate: 2020/10/22 16:22
 * @Description:
 */
public interface IAppletJsPushListener {

    /**
     * 推送消息
     * @param event
     * @param data
     */
    void onReceiveMessage(String event, String data);

}
