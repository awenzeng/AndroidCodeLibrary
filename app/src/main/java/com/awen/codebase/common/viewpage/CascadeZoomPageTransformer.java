package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: CascadeZoomPageTransformer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 16:35
 * @Description: 折叠向上
 */
public class CascadeZoomPageTransformer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onTransform(View page, float position) {
        if (position < -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧且不可视*/
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setAlpha(1 + position);
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setTranslationX(page.getWidth() * -position);
            page.setScaleX(1-position*0.5f);
            page.setScaleY(1-position*0.5f);
            page.setAlpha(1 - position);
        }else if (position >1){
            /*页面已经在屏幕右侧且不可视*/
        }
    }
}