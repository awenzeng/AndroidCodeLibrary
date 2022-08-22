package com.awen.codebase.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.ui.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created by AwenZeng on 2019/3/26
 */

@Route(path = ActivityRouter.AROUTER_MarqueeTextActivity)
public class MarqueeTextActivity extends BaseActivity {

    private MarqueeView marqueeView;
    private MarqueeView marqueeView1;
    private MarqueeView marqueeView2;
    private MarqueeView marqueeView3;
    private MarqueeView marqueeView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_marquee_text);
        marqueeView = findViewById(R.id.marqueeView);
        marqueeView1 = findViewById(R.id.marqueeView1);
        marqueeView2 = findViewById(R.id.marqueeView2);
        marqueeView3 = findViewById(R.id.marqueeView3);
        marqueeView4 = findViewById(R.id.marqueeView4);

        List<CharSequence> list = new ArrayList<>();
        SpannableString ss1 = new SpannableString("1、感悟生命的意义");
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss1);
        SpannableString ss2 = new SpannableString("2、体验人生的美好");
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 4, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss2);
        SpannableString ss3 = new SpannableString("3、创造回忆");
        list.add(ss3);
        list.add("回忆人生");

        marqueeView.startWithList(list);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(MarqueeTextActivity.this, textView.getText() + "", Toast.LENGTH_SHORT).show();
            }
        });

        marqueeView1.startWithText("感悟生命的意义，体验人生的美好！创造回忆，回忆人生。", R.anim.anim_top_in, R.anim.anim_bottom_out);
        marqueeView1.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(MarqueeTextActivity.this, String.valueOf(marqueeView1.getPosition()) + ". " + textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        marqueeView2.startWithText("创造回忆，回忆人生。");

        marqueeView3.startWithText("感悟生命的意义，体验人生的美好！创造回忆，回忆人生。");

        marqueeView4.startWithText("感悟生命的意义，体验人生的美好！创造回忆，回忆人生。");

    }
}
