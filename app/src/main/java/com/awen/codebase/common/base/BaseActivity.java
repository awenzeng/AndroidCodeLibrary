package com.awen.codebase.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.awen.codebase.R;
import com.awen.codebase.common.ui.statusbar.ActivityTitleBar;
import com.awen.codebase.common.ui.statusbar.StatusBarCompat;

public class BaseActivity extends FragmentActivity {
    private ActivityTitleBar mActTitileBar;
    private FrameLayout mContentView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.main_color));
        setContentView(R.layout.act_base);
        mActTitileBar = findViewById(R.id.titleBar);
        mContentView = findViewById(R.id.contentView);
        mActTitileBar.setTitle(getClass().getSimpleName());
        mActTitileBar.setBackgroundColor(getResources().getColor(R.color.main_color));

        mActTitileBar.setLeftLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActTitileBar.hideRightLayout();
    }

    public void setActivityContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID,null);
        mContentView.addView(view);
    }

    /**
     * 设置title
     * @param titile
     */
    public void setBarTitle(String titile){
        mActTitileBar.setTitle(titile);
    }

    /**
     * 隐藏actionBar
     */
    public void hideActTitileBar(){
        mActTitileBar.setVisibility(View.GONE);
    }

    /**
     * 隐藏左边布局
     */
    public void hideLeftLayout(){
        mActTitileBar.hideLeftLayout();
    }

    /**
     * 隐藏右边布局
     */
    public void hideRightLayout(){
        mActTitileBar.hideLeftLayout();
    }


    /**
     * 隐藏左右布局
     */
    public void hideLeftRightLayout(){
        mActTitileBar.hideLeftLayout();
        mActTitileBar.getRightLayout();
    }

}
