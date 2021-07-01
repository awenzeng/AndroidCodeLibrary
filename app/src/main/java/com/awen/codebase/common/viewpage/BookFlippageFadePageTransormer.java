package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

public class BookFlippageFadePageTransormer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onTransform(View page, float position) {
        if (position <= -1) {
            /*页面已经在屏幕左侧且不可视*/
            page.setAlpha(position);
        } else if (position <= 0) {
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setAlpha(1 + position);
            page.setPivotY(page.getHeight() / 2);
            page.setPivotX(0);
            page.setCameraDistance(60000);/*调整摄像机的位置，避免出现糊脸的感觉*/
            page.setRotationY((position * 180));
            page.setTranslationX(position * -page.getWidth());
        } else if (position <= 1) {
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setTranslationX(position * -page.getWidth());
        } else if (position > 1) {
            /*页面已经在屏幕右侧且不可视*/
            page.setTranslationX(position * -page.getWidth());
        }
    }
}