package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: CubesPageTransformer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 16:27
 * @Description:转盘旋转
 */
public class CubesPageTransformer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onTransform(View page, float position) {
        if (position <= -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧且不可视*/
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setCameraDistance(100000);
            page.setPivotX(page.getMeasuredWidth());
            page.setPivotY(page.getMeasuredHeight() * 0.5f);
            page.setRotationY(90 * position);
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setCameraDistance(100000);
            page.setPivotX(0);
            page.setPivotY(page.getWidth() * (0.5f));
            page.setRotationY(90 * position);
        } else if (position > 1) {
            /*页面已经在屏幕右侧且不可视*/
        }
    }
}