package com.awen.codebase.common.viewpage;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * @ClassName: ZoominPagerTransFormer
 * @Author: AwenZeng
 * @CreateDate: 2021/7/1 11:32
 * @Description:
 */
public class ZoominPagerTransFormer extends ABaseTransformer {
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onTransform(View page, float position) {
        if (position < -1) { /* [-Infinity,-1)*/
            /*页面已经在屏幕左侧且不可视*/
            page.setScaleX((float) (1 + position * 0.1));
            page.setScaleY((float) (1 + position * 0.1));
        } else if (position <= 0) { /* [-1,0]*/
            /*页面从左侧进入或者向左侧滑出的状态*/
            page.setScaleX((float) (1 + position * 0.1));
            page.setScaleY((float) (1 + position * 0.1));
        } else if (position <= 1) {/* (0,1]*/
            /*页面从右侧进入或者向右侧滑出的状态*/
            page.setScaleX((float) (1-  position * 0.1));
            page.setScaleY((float) (1 - position * 0.1));
        } else if (position > 1) {
            /*页面已经在屏幕右侧且不可视*/
            page.setScaleX((float) (1-  position * 0.1));
            page.setScaleY((float) (1 - position * 0.1));
        }
    }
}