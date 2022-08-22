package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.SwipeAdapter;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.widget.SwipeDividerDecoration;
import com.awen.codebase.common.widget.SwipeRecycleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AwenZeng on 2017/6/19.
 */
@Route(path = ActivityRouter.AROUTER_XRecyleviewActivity)
public class XRecyleviewActivity extends BaseActivity {
    @BindView(R.id.mRecycleView)
    SwipeRecycleView mRecycleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_xrecyleview);
        ButterKnife.bind(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.addItemDecoration(new SwipeDividerDecoration());
        SwipeAdapter swipeAdapter = new SwipeAdapter(this);
        mRecycleView.setAdapter(swipeAdapter);
    }
}
