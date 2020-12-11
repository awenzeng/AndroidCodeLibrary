package com.awen.codebase.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.ui.swipecard.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AwenZeng on 2017/3/6.
 */

public class SwipeCardActivity extends BaseActivity {
    @BindView(R.id.frame)
    SwipeFlingAdapterView flingContainer;
    @BindView(R.id.left)
    Button left;
    @BindView(R.id.right)
    Button right;

    private ArrayList<String> dataList;
    private ArrayAdapter<String> arrayAdapter;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_swipe_card);
        ButterKnife.bind(this);

        dataList = new ArrayList<>();
        dataList.add("php");
        dataList.add("c");
        dataList.add("python");
        dataList.add("java");
        dataList.add("html");
        dataList.add("c++");
        dataList.add("css");
        dataList.add("javascript");

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_swipe_card, R.id.helloText, dataList );


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                dataList.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(SwipeCardActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(SwipeCardActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                dataList.add("XML ".concat(String.valueOf(index)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                index++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(SwipeCardActivity.this, "Clicked!");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.right)
    public void right() {
        /**
         * Trigger the right event manually.
         */
        flingContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.left)
    public void left() {
        flingContainer.getTopCardListener().selectLeft();
    }
}
