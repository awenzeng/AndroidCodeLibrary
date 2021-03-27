package com.awen.codebase.activity;

import android.os.Bundle;


import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.ui.LargeImageView;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageViewActivity extends BaseActivity {
    private LargeImageView mLargeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_large_image);

        mLargeImageView = (LargeImageView) findViewById(R.id.id_largetImageview);
        try {
            InputStream inputStream = getAssets().open("world.jpg");
            mLargeImageView.setInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}