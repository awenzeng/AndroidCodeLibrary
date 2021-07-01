package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: DepthCardTransformer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 16:29
 * @Description:滑动缩放
 */
public class DepthCardTransformer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onTransform(View page, float position) {
        if (position < -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧第一个*/
            page.setCameraDistance(10000);
            page.setPivotX(page.getWidth()/2);
            page.setPivotY(page.getWidth());
            page.setRotationY(20);
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setCameraDistance(10000);
            page.setPivotX(page.getWidth()/2);
            page.setPivotY(page.getWidth());
            page.setRotationY(-20+(1-position)*20);
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setCameraDistance(10000);
            page.setPivotX(page.getWidth()/2);
            page.setPivotY(page.getWidth());
            page.setRotationY(-20+(1-position)*20);
        } else if (position<=2) {
            /*页面已经在屏幕右侧第一个*/
            page.setCameraDistance(10000);
            page.setPivotX(page.getWidth()/2);
            page.setPivotY(page.getWidth());
            page.setRotationY(-20);
        }
    }
}