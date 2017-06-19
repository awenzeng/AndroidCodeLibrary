package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.awen.codebase.R;
import com.awen.codebase.adapter.SwipeAdapter;
import com.awen.codebase.widget.SwipeDividerDecoration;
import com.awen.codebase.widget.SwipeRecycleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AwenZeng on 2017/6/19.
 */

public class XRecyleviewActivity extends Activity {
    @BindView(R.id.mRecycleView)
    SwipeRecycleView mRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_xrecyleview);
        ButterKnife.bind(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.addItemDecoration(new SwipeDividerDecoration());
        SwipeAdapter swipeAdapter = new SwipeAdapter(this);
        mRecycleView.setAdapter(swipeAdapter);
    }
}
