package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.awen.codebase.R;

public class LottieActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lottie);
    }

}
