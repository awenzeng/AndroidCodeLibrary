package com.awen.codebase.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.awen.codebase.R;
import com.awen.codebase.activity.fragment.DragRecyclerViewFragment;
import com.awen.codebase.activity.fragment.ExplosionFragment;
import com.awen.codebase.activity.fragment.MultiTouchScaleFragment;
import com.awen.codebase.activity.fragment.PopupWindowFragment;
import com.awen.codebase.activity.fragment.ProgressBarFragment;
import com.awen.codebase.common.base.BaseActivity;

public class FragmentsActivity extends BaseActivity implements OnClickListener {
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.fragment_activitygroups_main);
        layout = (FrameLayout) this.findViewById(R.id.layout_groups);
        RadioButton button = (RadioButton) this.findViewById(R.id.button01);
        button.setOnClickListener(this);
        button = (RadioButton) this.findViewById(R.id.button02);
        button.setOnClickListener(this);
        button = (RadioButton) this.findViewById(R.id.button03);
        button.setOnClickListener(this);
        button = (RadioButton) this.findViewById(R.id.button04);
        button.setOnClickListener(this);
        button = (RadioButton) this.findViewById(R.id.button05);
        button.setOnClickListener(this);
        Fragment fragment = null;
        //第一种为android版本低于3.0的用法。继承FragmentActivity
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //第二种为高于android版本3.0的用法。
//	FragmentTransaction ft = getFragmentManager().beginTransaction();
        layout.removeAllViews();
        fragment = new PopupWindowFragment();
        ft.add(R.id.layout_groups, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.button01:
                layout.removeAllViews();
                fragment = new PopupWindowFragment();
                ft.add(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            case R.id.button02:
                layout.removeAllViews();
                fragment = new ExplosionFragment();
                ft.replace(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            case R.id.button03:
                layout.removeAllViews();
                fragment = new MultiTouchScaleFragment();
                ft.replace(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            case R.id.button04:
                layout.removeAllViews();
                fragment = new ProgressBarFragment();
                ft.replace(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            case R.id.button05:
                layout.removeAllViews();
                fragment = new DragRecyclerViewFragment();
                ft.replace(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            default:
                break;
        }
    }

}
