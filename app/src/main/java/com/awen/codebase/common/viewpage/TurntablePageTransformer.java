package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: TurntablePageTransformer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 16:28
 * @Description:层叠缩放
 */
public class TurntablePageTransformer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onTransform(View page, float position) {
        if (position < -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧且不可视*/
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight());
            page.setRotation(90*position);
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setPivotX(page.getWidth() / 2);
            page.setPivotY(page.getHeight());
            page.setRotation(90*position);
        } else if (position > 1) {
            /*页面已经在屏幕右侧且不可视*/
        }
    }
}