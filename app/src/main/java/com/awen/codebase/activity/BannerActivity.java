package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.awen.codebase.R;
import com.awen.codebase.widget.banner.BannerLayout;
import com.awen.codebase.widget.banner.RecyclerViewBannerBase;
import com.awen.codebase.widget.banner.RecyclerViewBannerNormal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerActivity extends Activity  implements BannerLayout.OnBannerItemClickListener,RecyclerViewBannerBase.OnBannerItemClickListener{

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
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        List<String> list = new ArrayList<>();
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/69427561.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/23738150.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/30127126.jpg");
        list.add("http://oo6pz0u05.bkt.clouddn.com/17-12-13/36125492.jpg");
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
