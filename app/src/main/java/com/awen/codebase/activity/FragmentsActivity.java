package com.awen.codebase.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.awen.codebase.R;
import com.awen.codebase.fragment.AniGridFragment;
import com.awen.codebase.fragment.ExplosionFragment;
import com.awen.codebase.fragment.MultiTouchScaleFragment;
import com.awen.codebase.fragment.PopupWindowFragment;
import com.awen.codebase.fragment.ProgressBarFragment;

public class FragmentsActivity extends FragmentActivity implements OnClickListener {
    private FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activitygroups_main);
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
        fragment = new PopupWindowFragment(FragmentsActivity.this);
        ft.add(R.id.layout_groups, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.button01:
                layout.removeAllViews();
                fragment = new PopupWindowFragment(this);
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
                fragment = new AniGridFragment();
                ft.replace(R.id.layout_groups, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
            default:
                break;
        }
    }

}
