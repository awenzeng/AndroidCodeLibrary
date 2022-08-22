package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;


@Route(path = ActivityRouter.AROUTER_ProxyActivity)
public class ProxyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_main);
    }

}
