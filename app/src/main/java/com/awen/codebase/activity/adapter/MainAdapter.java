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
import com.awen.codebase.activity.WebViewActivity;
import com.awen.codebase.activity.XRecyleviewActivity;
import com.awen.messagebus.MessageBus;

/**
 * Created by AwenZeng on 2017/3/6.
 */

public class MainAdapter extends BaseAdapter {
    private String[] iStrings;
    private Context mContext;
    private ItemClickListener mOnClickListener;

    public MainAdapter(Context context, String[] iStrings) {
        super();
        this.iStrings = iStrings;
        mContext = context;
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.mOnClickListener = clickListener;
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
                int pos = (Integer) v.getTag();
                if(mOnClickListener!=null){
                    mOnClickListener.onClick(pos,iStrings[pos]);
                }
            }
        });
        return button;
    }

    public interface ItemClickListener{
        void onClick(int position,String data);
    }

}