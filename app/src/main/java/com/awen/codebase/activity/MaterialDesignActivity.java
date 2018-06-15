package com.awen.codebase.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.awen.codebase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created by AwenZeng on 2018/3/13.
 */

public class MaterialDesignActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_material_design);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome Material Design!!!");
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);

        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Snackbar comes out", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(
                                        MaterialDesignActivity.this,
                                        "Toast comes out",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        setupViewPager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        List<String> titles = new ArrayList<>();
        titles.add("Page One");
        titles.add("Page Two");
        titles.add("Page Three");
        titles.add("Page Three");
        titles.add("Page Four");
        titles.add("Page Five");
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        List<View> fragments = new ArrayList<>();
        TextView textView = new TextView(this);
        textView.setText("Welcome!!!!");
        fragments.add(textView);
        fragments.add(textView);
        fragments.add(textView);
//        mViewPager.setAdapter(null);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabsFromPagerAdapter(null);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
