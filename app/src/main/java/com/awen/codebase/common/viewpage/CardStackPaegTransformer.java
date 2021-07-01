package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: CardStackPaegTransformer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 16:32
 * @Description:
 */
public class CardStackPaegTransformer extends ABaseTransformer {

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onTransform(View page, float position) {
        if (position <= -1) {
            /*页面已经在屏幕左侧且不可视*/
        } else if (position <= 0) {
            /*页面从左侧进入或者向左侧滑出的状态*/
        } else if (position < 1) {
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setAlpha((float) (1 - position * 0.1));
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX((float) Math.pow(0.9f, position));    /*0.9f为缩放系数*/
            page.setScaleY((float) Math.pow(0.9f, position));
            page.setTranslationX(position * -page.getWidth());
            page.setTranslationY(-position * 70);/*70每层card的Y轴间隔*/
        } else if (position >= 1) {
            /*页面已经在屏幕右侧且不可视*/
            page.setAlpha((float) (1 - position * 0.1));
            page.setPivotX(page.getWidth() / 2f);
            page.setPivotY(page.getHeight() / 2f);
            page.setScaleX((float) Math.pow(0.9f, position));
            page.setScaleY((float) Math.pow(0.9f, position));
            page.setTranslationX(position * -page.getWidth());
            page.setTranslationY(-position * 70);
        }
    }
}