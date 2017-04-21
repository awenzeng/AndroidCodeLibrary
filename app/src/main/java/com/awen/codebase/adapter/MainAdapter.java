package com.awen.codebase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.awen.codebase.activity.AnimationActivity;
import com.awen.codebase.activity.CreditRoundActivity;
import com.awen.codebase.activity.FloatCycleViewActivity;
import com.awen.codebase.activity.FragmentsActivity;
import com.awen.codebase.activity.GroupsActivity;
import com.awen.codebase.activity.KeybordActivity;
import com.awen.codebase.activity.MiLaucherActivity;
import com.awen.codebase.activity.ProgressBarsActivity;
import com.awen.codebase.activity.SwipeCardActivity;
import com.awen.codebase.activity.SwitchButtonActivity;

/**
 * Created by AwenZeng on 2017/3/6.
 */

public class MainAdapter extends BaseAdapter {
    private static final int FLOATCYCLEVIEW = 0;
    private static final int GROUPSACTIVITY = 1;
    private static final int FRAGMNETSACTIVITY = 2;
    private static final int MILAUCHERACTIVITY = 3;
    private static final int ANIMATIONACTIVITY = 4;
    private static final int PROGRESSBARSACTIVITY = 5;
    private static final int SWITCH_BUTTON_ACTIVITY = 6;
    private static final int CREDI_ROUND_ACTIVITY = 7;
    private static final int SWIPE_CARD_ACTIVITY = 8;
    private static final int KEYBORD_ACTIVITY = 9;
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
                switch (pos) {
                    case FLOATCYCLEVIEW:
                        intent.setClass(mContext, FloatCycleViewActivity.class);
                        break;
                    case GROUPSACTIVITY:
                        intent.setClass(mContext, GroupsActivity.class);
                        break;
                    case FRAGMNETSACTIVITY:
                        intent.setClass(mContext, FragmentsActivity.class);
                        break;
                    case MILAUCHERACTIVITY:
                        intent.setClass(mContext, MiLaucherActivity.class);
                        break;
                    case ANIMATIONACTIVITY:
                        intent.setClass(mContext, AnimationActivity.class);
                        break;
                    case PROGRESSBARSACTIVITY:
                        intent.setClass(mContext, ProgressBarsActivity.class);
                        break;
                    case SWITCH_BUTTON_ACTIVITY:
                        intent.setClass(mContext, SwitchButtonActivity.class);
                        break;
                    case CREDI_ROUND_ACTIVITY:
                        intent.setClass(mContext, CreditRoundActivity.class);
                        break;
                    case SWIPE_CARD_ACTIVITY:
                        intent.setClass(mContext, SwipeCardActivity.class);
                        break;
                    case KEYBORD_ACTIVITY:
                        intent.setClass(mContext, KeybordActivity.class);
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