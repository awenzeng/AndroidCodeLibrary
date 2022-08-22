package com.awen.codebase.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;


@Route(path = ActivityRouter.AROUTER_SVGActivity)
public class SVGActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_svg);
    }

    public void btnClick(View view) {
        ImageView imageView = (ImageView) view;
//        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) fab.getDrawable();
//        animatedVectorDrawable.start();

        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }

    private boolean isTwitterChecked = false;
    public void twitterClick(View view) {
        ImageView imageView = (ImageView) view;
        isTwitterChecked = !isTwitterChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
        imageView.setImageState(stateSet, true);
    }

}
