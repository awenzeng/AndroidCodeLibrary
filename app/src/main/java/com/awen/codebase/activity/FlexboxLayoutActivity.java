package com.awen.codebase.activity;

import android.app.Activity;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awen.codebase.R;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Random;

/**
 * @ClassName: FlexboxLayoutActivity
 * @Author: AwenZeng
 * @CreateDate: 2020/8/18 19:03
 * @Description:
 */
public class FlexboxLayoutActivity extends Activity {

    private RecyclerView mRecyclerView;
    private java.util.List<Integer> mDatas = new ArrayList<Integer>();

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexboxlayout);

        mDatas.add(R.drawable.bleach_1);
        mDatas.add(R.drawable.bleach_2);
        mDatas.add(R.drawable.bleach_3);
        mDatas.add(R.drawable.bleach_4);
        mDatas.add(R.drawable.bleach_5);
        mDatas.add(R.drawable.bleach_6);
        mDatas.add(R.drawable.bleach_7);
        mDatas.add(R.drawable.bleach_8);
        mDatas.add(R.drawable.bleach_9);
        mDatas.add(R.drawable.bleach_10);
        mDatas.add(R.drawable.bleach_11);
        mDatas.add(R.drawable.bleach_12);

        mDatas.add(R.drawable.bleach_9);
        mDatas.add(R.drawable.bleach_16);
        mDatas.add(R.drawable.bleach_15);
        mDatas.add(R.drawable.bleach_14);
        mDatas.add(R.drawable.bleach_15);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.test_recyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new CommonAdapter<Integer>(this, R.layout.flexbox_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                int pos = position % mDatas.size();
                android.graphics.drawable.Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(),
                        mDatas.get(pos), null);
                ImageView mImageView = (ImageView)holder.getView(R.id.imageview);
                mImageView.setImageDrawable(drawable);

                ViewGroup.LayoutParams lp = mImageView.getLayoutParams();
                if (lp instanceof FlexboxLayoutManager.LayoutParams) {
                    FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
                    Random random = new Random();
                    //随机宽度
                    int width  =random.nextInt(300)+200;
                    flexboxLp.width=width;
                    flexboxLp.setFlexGrow(1.0f);
                }
            }
        });
    }
}
