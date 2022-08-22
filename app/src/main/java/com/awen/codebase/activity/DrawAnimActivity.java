package com.awen.codebase.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.anim.BoomView;
import com.awen.codebase.common.anim.ChipsView;
import com.awen.codebase.common.anim.CoinsView;
import com.awen.codebase.common.anim.LoadingView;
import com.awen.codebase.common.anim.MergeView;
import com.awen.codebase.common.anim.MoreFrameView;
import com.awen.codebase.common.anim.ShowViewManager;
import com.awen.codebase.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by AwenZeng on 2018/2/26.
 */
@Route(path = ActivityRouter.AROUTER_DrawAnimActivity)
public class DrawAnimActivity extends BaseActivity {

    @BindView(R.id.layout01)
    RelativeLayout layout01;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    private ShowViewManager showViewManager;
    private AnimationSet animationSet;
    private AnimationSet animationSet1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_draw_anim);
        ButterKnife.bind(this);
        showViewManager = new ShowViewManager(this);
        stopTweenAnim();
    }

    private void startTweenAnim() {
        Bitmap img = ((BitmapDrawable) getResources().getDrawable(R.drawable.ball)).getBitmap();
        float x = imageView.getX();
        float y = imageView.getY();
        float x1 = imageView1.getX();
        float y1 = imageView1.getY();
        DisplayMetrics dm = this.getResources().getDisplayMetrics();

        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(0, dm.widthPixels - img.getWidth() - 200, 0, dm.heightPixels - img.getHeight() - 300);
        translateAnimation.setDuration(3000);
        translateAnimation.setRepeatCount(100);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        translateAnimation.setInterpolator(new LinearInterpolator());

        TranslateAnimation translateAnimation1 = new TranslateAnimation(0, dm.widthPixels - img.getWidth() - 200, 0, -(dm.heightPixels - img.getHeight() - 300));
        translateAnimation1.setDuration(3000);
        translateAnimation1.setRepeatCount(100);
        translateAnimation1.setRepeatMode(Animation.REVERSE);
        translateAnimation1.setInterpolator(new LinearInterpolator());

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, x + img.getWidth() / 2, y + img.getHeight() / 2);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatCount(100);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        //透明度
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatCount(100);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, x + img.getWidth() / 2, y + img.getHeight() / 2);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(100);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        RotateAnimation rotateAnimation1 = new RotateAnimation(0, 360, x1 + img.getWidth() / 2, y1 + img.getHeight() / 2);
        rotateAnimation1.setDuration(1000);
        rotateAnimation1.setRepeatCount(100);
        rotateAnimation1.setRepeatMode(Animation.RESTART);
        rotateAnimation1.setInterpolator(new LinearInterpolator());

        //合集动画
        animationSet = new AnimationSet(false);
        // animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);

        animationSet1 = new AnimationSet(false);
        animationSet1.addAnimation(rotateAnimation1);
        animationSet1.addAnimation(translateAnimation1);
        animationSet1.addAnimation(alphaAnimation);
        imageView.setAnimation(animationSet);
        animationSet.startNow();
        imageView1.setAnimation(animationSet1);
        animationSet1.startNow();
        animationSet1.cancel();
        imageView.setVisibility(View.VISIBLE);
        imageView1.setVisibility(View.VISIBLE);
    }

    private void stopTweenAnim() {
        if (animationSet != null) {
            animationSet.cancel();
        }
        if (animationSet1 != null) {
            animationSet1.cancel();
        }
        imageView.setVisibility(View.GONE);
        imageView1.setVisibility(View.GONE);
    }


    @OnClick({R.id.button01, R.id.button02, R.id.button03, R.id.button04, R.id.button05, R.id.button06, R.id.button07, R.id.button08, R.id.button09, R.id.button10})
    public void onViewClicked(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(getApplicationContext())) {
                //启动Activity让用户授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return;
            }
        }
        switch (view.getId()) {
            case R.id.button01:
                ChipsView heartView = new ChipsView(this);
                showViewManager.showView(heartView);
                stopTweenAnim();
                break;
            case R.id.button02:
                MoreFrameView winView = new MoreFrameView(this, null);
                showViewManager.showView(winView);
                stopTweenAnim();
                break;
            case R.id.button03:
                CoinsView coinsView = new CoinsView(this);
                showViewManager.showView(coinsView);
                stopTweenAnim();
                break;
            case R.id.button04:
                startTweenAnim();
                break;
            case R.id.button05:
                LoadingView loadingView = new LoadingView(this);
                loadingView.setText(R.string.loading);
                loadingView.setShowTextTips(true);
                loadingView.setTextSize(45);
                loadingView.setShowFlyCardItem(true);
                loadingView.setTextPosition(LoadingView.TEXT_SHOW_RIGHT);
                showViewManager.showView(loadingView);
                stopTweenAnim();
                break;
            case R.id.button06:
                int[] res = new int[]{
                        R.drawable.doubleline01, R.drawable.doubleline02, R.drawable.doubleline03, R.drawable.doubleline04,
                        R.drawable.doubleline05, R.drawable.doubleline06, R.drawable.doubleline07, R.drawable.doubleline08,
                        R.drawable.doubleline09, R.drawable.doubleline10
                };
                MoreFrameView moreFrameView = new MoreFrameView(this, res);
                showViewManager.showView(moreFrameView);
                stopTweenAnim();
                break;
            case R.id.button07:
                BoomView boomView = new BoomView(this);
                showViewManager.showView(boomView);
                break;
            case R.id.button08:
                MergeView mergeView = new MergeView(this);
                showViewManager.showView(mergeView);
                break;
            case R.id.button10:
                layout01.setVisibility(View.VISIBLE);
                break;

            default:
                view.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showViewManager.removeView();
    }
}
