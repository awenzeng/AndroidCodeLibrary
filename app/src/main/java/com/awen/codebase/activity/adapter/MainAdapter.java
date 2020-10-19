package com.awen.codebase.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.awen.codebase.activity.AnimationActivity;
import com.awen.codebase.activity.BannerActivity;
import com.awen.codebase.activity.CreditRoundActivity;
import com.awen.codebase.activity.DrawAnimActivity;
import com.awen.codebase.activity.FlexboxLayoutActivity;
import com.awen.codebase.activity.FloatCycleViewActivity;
import com.awen.codebase.activity.FragmentsActivity;
import com.awen.codebase.activity.GroupsActivity;
import com.awen.codebase.activity.InfiniteViewActivity;
import com.awen.codebase.activity.KeybordActivity;
import com.awen.codebase.activity.MarqueeTextActivity;
import com.awen.codebase.activity.MaterialDesignActivity;
import com.awen.codebase.activity.ProgressBarsActivity;
import com.awen.codebase.activity.SVGActivity;
import com.awen.codebase.activity.SwipeCardActivity;
import com.awen.codebase.activity.SwitchButtonActivity;
import com.awen.codebase.activity.VerticalViewPagerActivity;
import com.awen.codebase.activity.XRecyleviewActivity;
import com.awen.messagebus.MessageBus;

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
                Intent intent = null;
                int pos = (Integer) v.getTag();
                switch (iStrings[pos]){
                    case "FloatCycleViewActivity":
                        intent = new Intent(mContext,FloatCycleViewActivity.class);
                        break;
                    case "GroupsActivity":
                        intent = new Intent(mContext,GroupsActivity.class);
                        break;
                    case "FragmentsActivity":
                        intent = new Intent(mContext,FragmentsActivity.class);
                        break;
                    case "AnimationActivity":
                        intent = new Intent(mContext,AnimationActivity.class);
                        break;
                    case "ProgressBarsActivity":
                        intent = new Intent(mContext,ProgressBarsActivity.class);
                        break;
                    case "SwitchButtoonActivity":
                        intent = new Intent(mContext,SwitchButtonActivity.class);
                        break;
                    case "CreditRoundActivity":
                        intent = new Intent(mContext,CreditRoundActivity.class);
                        break;
                    case "SwipeCardActivity":
                        intent = new Intent(mContext,SwipeCardActivity.class);
                        break;
                    case "KeybordActivity":
                        intent = new Intent(mContext,KeybordActivity.class);
                        break;
                    case "XRecycleView":
                        intent = new Intent(mContext,XRecyleviewActivity.class);
                        break;
                    case "VerticalViewPagerActivity":
                        intent = new Intent(mContext,VerticalViewPagerActivity.class);
                        break;
                    case "InfiniteViewActivity":
                        intent = new Intent(mContext,InfiniteViewActivity.class);
                        break;
                    case "BannerActivity":
                        intent = new Intent(mContext,BannerActivity.class);
                        break;
                    case "DrawAnimActivity":
                        intent = new Intent(mContext,DrawAnimActivity.class);
                        break;
                    case "MaterialDesignActivity":
                        intent = new Intent(mContext,MaterialDesignActivity.class);
                        break;
                    case "MarqueeTextActivity":
                        intent = new Intent(mContext,MarqueeTextActivity.class);
                        break;
                    case "FlexboxLayoutActivity":
                        intent = new Intent(mContext, FlexboxLayoutActivity.class);
                        break;
                    case "SVGActivity":
                        intent = new Intent(mContext, SVGActivity.class);
                        break;
                    default:
                        break;
                }
                mContext.startActivity(intent);
                Message message = new Message();
                message.obj = iStrings[pos];
                MessageBus.getDefault().sendMessage(message);

                Message msg = new Message();
                msg.obj = "延迟消息："+iStrings[pos];
                MessageBus.getDefault().sendMessageDelayed(msg,1500);
            }
        });
        return button;
    }

}