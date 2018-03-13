package com.awen.codebase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.awen.codebase.activity.AnimationActivity;
import com.awen.codebase.activity.BannerActivity;
import com.awen.codebase.activity.CreditRoundActivity;
import com.awen.codebase.activity.DrawAnimActivity;
import com.awen.codebase.activity.FloatCycleViewActivity;
import com.awen.codebase.activity.FragmentsActivity;
import com.awen.codebase.activity.GroupsActivity;
import com.awen.codebase.activity.InfiniteViewActivity;
import com.awen.codebase.activity.KeybordActivity;
import com.awen.codebase.activity.MaterialDesignActivity;
import com.awen.codebase.activity.ProgressBarsActivity;
import com.awen.codebase.activity.SwipeCardActivity;
import com.awen.codebase.activity.SwitchButtonActivity;
import com.awen.codebase.activity.VerticalViewPagerActivity;
import com.awen.codebase.activity.XRecyleviewActivity;

/**
 * Created by AwenZeng on 2017/3/6.
 */

public class MainAdapter extends BaseAdapter {
    private String[] iStrings;
    private Context mContext;

    public MainAdapter(Context context, String[] iStrings) {
        super();
        this.iStrings = iStrings;
        mContext = context;
    }

    @Override
    public int getCount() {
        return iStrings.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button = new Button(mContext);
        button.setText(iStrings[position]);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                int pos = (Integer) v.getTag();
                switch (iStrings[pos]){
                    case "FloatCycleViewActivity":
                        intent.setClass(mContext, FloatCycleViewActivity.class);
                        break;
                    case "GroupsActivity":
                        intent.setClass(mContext, GroupsActivity.class);
                        break;
                    case "FragmentsActivity":
                        intent.setClass(mContext, FragmentsActivity.class);
                        break;
                    case "AnimationActivity":
                        intent.setClass(mContext, AnimationActivity.class);
                        break;
                    case "ProgressBarsActivity":
                        intent.setClass(mContext, ProgressBarsActivity.class);
                        break;
                    case "SwitchButtoonActivity":
                        intent.setClass(mContext, SwitchButtonActivity.class);
                        break;
                    case "CreditRoundActivity":
                        intent.setClass(mContext, CreditRoundActivity.class);
                        break;
                    case "SwipeCardActivity":
                        intent.setClass(mContext, SwipeCardActivity.class);
                        break;
                    case "KeybordActivity":
                        intent.setClass(mContext, KeybordActivity.class);
                        break;
                    case "XRecycleView":
                        intent.setClass(mContext, XRecyleviewActivity.class);
                        break;
                    case "VerticalViewPagerActivity":
                        intent.setClass(mContext, VerticalViewPagerActivity.class);
                        break;
                    case "InfiniteViewActivity":
                        intent.setClass(mContext, InfiniteViewActivity.class);
                        break;
                    case "BannerActivity":
                        intent.setClass(mContext, BannerActivity.class);
                        break;
                    case "DrawAnimActivity":
                        intent.setClass(mContext, DrawAnimActivity.class);
                        break;
                    case "MaterialDesignActivity":
                        intent.setClass(mContext, MaterialDesignActivity.class);
                        break;
                    default:
                        break;
                }
                mContext.startActivity(intent);
            }
        });
        return button;
    }

}