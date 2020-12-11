package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;

public class LottieActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_lottie);
    }

}
