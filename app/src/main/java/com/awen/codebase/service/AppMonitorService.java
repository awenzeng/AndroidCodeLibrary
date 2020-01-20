package com.awen.codebase.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.awen.codebase.utils.LogUtil;

import java.util.List;

/**
 * @ClassName: AppMonitorService
 * @Author: AwenZeng
 * @CreateDate: 2020/1/19 17:51
 * @Description: 应用监听
 */
public class AppMonitorService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LogUtil.androidLog("onAccessibilityEvent:"+event.getEventType()+":"+event.toString());
        AccessibilityNodeInfo nodeInfo = findViewByText("下一个", true);
        if (nodeInfo != null) {
            performViewClick(nodeInfo);
        } else {
            LogUtil.androidLog("nodeInfo is null.");
        }

    }

    @Override
    public void onInterrupt() {
       LogUtil.androidLog("onInterrupt:");
    }

    

    private AccessibilityNodeInfo findViewByText(String text, boolean clickable) {
        AccessibilityNodeInfo accessibilityNodeInfo = getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            LogUtil.androidLog("accessibilityNodeInfo is null");
            return null;
        }

        //根据Text寻找按键
//        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByText(text);

        //根据ID寻找按键
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.awen.myapplication:id/next");
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (nodeInfo != null && (nodeInfo.isClickable() == clickable)) {
                    return nodeInfo;
                }
            }
        }
        return null;
    }

    private void performViewClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        LogUtil.androidLog("performViewClick start.");
        while (nodeInfo != null) {
            if (nodeInfo.isClickable()) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                LogUtil.androidLog("has clicked the view");
                nodeInfo.recycle(); //  当点击完后回收
                break;
            }
            nodeInfo = nodeInfo.getParent();
        }
    }
}
