package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.widget.banner.BannerLayout;
import com.awen.codebase.common.widget.banner.RecyclerViewBannerBase;
import com.awen.codebase.common.widget.banner.RecyclerViewBannerNormal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ActivityRouter.AROUTER_BannerActivity)
public class BannerActivity extends BaseActivity implements BannerLayout.OnBannerItemClickListener,RecyclerViewBannerBase.OnBannerItemClickListener{

    @BindView(R.id.banner)
    BannerLayout banner;
    @BindView(R.id.banner1)
    BannerLayout banner1;
    @BindView(R.id.bannerNormal)
    RecyclerViewBannerNormal bannerNormal;
    @BindView(R.id.bannerNormal2)
    RecyclerViewBannerNormal bannerNormal2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        List<String> list = new ArrayList<>();
        list.add("https://imglf3.lf127.net/img/MUgydEdvOEdHeHZ0NWpjMU5mNVFEVGx4TlJDU1dvMzlzY0hIM2Y2Q2NWekFLTk0xWHdGVWhRPT0.jpg?imageView&thumbnail=1680x0&quality=96&stripmeta=0&type=jpg");
        list.add("https://imglf5.lf127.net/img/MUgydEdvOEdHeHZSdlBLSGtzT3VMSWIrUmxuQksrSWVNN0RsRjM4VEp5RURlUmZhUnJSU1hnPT0.jpg?imageView&thumbnail=1680x0&quality=96&stripmeta=0&type=jpg");
        list.add("https://imglf5.lf127.net/img/MUgydEdvOEdHeHR3U2UzYThFaGZHZEdMSmlyLzlpd2JNajgrOWUyWkVUQzUzVFBSaUpLeWpnPT0.jpg?imageView&thumbnail=1680x0&quality=96&stripmeta=0&type=jpg");
        list.add("https://imglf6.lf127.net/img/MUgydEdvOEdHeHVBaldEZThUbzNxMmh0VjFTRXh2bUExdTU1OFJFYXh2UTVXR3hybHRIWmJBPT0.jpg?imageView&thumbnail=1680x0&quality=96&stripmeta=0&type=jpg");
        banner.initBannerImageView(list);
        banner1.initBannerImageView(list);
        banner.setOnBannerItemClickListener(this);
        banner1.setOnBannerItemClickListener(this);
        bannerNormal.initBannerImageView(list, this);
        bannerNormal2.initBannerImageView(list, this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(BannerActivity.this, "clicked:" + position, Toast.LENGTH_SHORT).show();
    }



}
