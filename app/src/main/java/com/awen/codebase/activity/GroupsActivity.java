package com.awen.codebase.activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.awen.codebase.R;
import com.awen.codebase.utils.AnimatioinUtil;
import com.awen.codebase.utils.DimensionUtil;

public class GroupsActivity extends ActivityGroup implements OnClickListener{
    private FrameLayout layout;
    private TextView sideButton;
    private View view;
	private RelativeLayout rlHeader;
	private int itemWidth;
	private int startX;
	private LayoutParams params;
	private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.act_groups);
	layout = (FrameLayout)this.findViewById(R.id.layout_groups);
	button = (Button)this.findViewById(R.id.button01);
	button.setOnClickListener(this);
	button = (Button)this.findViewById(R.id.button02);
	button.setOnClickListener(this);
	button = (Button)this.findViewById(R.id.button03);
	button.setOnClickListener(this);
	button = (Button)this.findViewById(R.id.button04);
	button.setOnClickListener(this);
	button = (Button)this.findViewById(R.id.button05);
	button.setOnClickListener(this);
	
	
	sideButton = new TextView(this);
	sideButton.setTextColor(Color.WHITE);
	sideButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
	sideButton.setGravity(Gravity.CENTER);
	sideButton.setWidth((getScreenWidth() - DimensionUtil.dip2px(this, 20)) / 5);
	sideButton.setBackgroundResource(R.drawable.menu_home_down);
//	
 	RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
	param.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
	
	rlHeader = (RelativeLayout) findViewById(R.id.layout_title_bar);
	rlHeader.addView(sideButton, param);
	
	params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	view = getLocalActivityManager().startActivity("Module1",
			new Intent(GroupsActivity.this, TabDemoActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	layout.addView(view,params);
	
    }
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
    itemWidth = button.getWidth();
    
	switch (v.getId()) {
	case R.id.button01:
		AnimatioinUtil.SetImageSlide(sideButton, startX, 0, 0, 0);
		startX = 0;
	    view = getLocalActivityManager().startActivity("Module1",
	    	    new Intent(GroupsActivity.this, TabDemoActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	    break;
	case R.id.button02:
		AnimatioinUtil.SetImageSlide(sideButton, startX, itemWidth, 0, 0);
		startX = itemWidth;
	    view = getLocalActivityManager().startActivity("Module1",
	    	    new Intent(GroupsActivity.this, ShadeActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	    break;
	case R.id.button03:
		AnimatioinUtil.SetImageSlide(sideButton, startX, itemWidth * 2, 0, 0);
		startX = itemWidth * 2;
	    view = getLocalActivityManager().startActivity("Module1",
	    	    new Intent(GroupsActivity.this, SearchFlyActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	    break;
	case R.id.button04:
		AnimatioinUtil.SetImageSlide(sideButton, startX, itemWidth * 3, 0, 0);
		startX = itemWidth * 3;
	    view = getLocalActivityManager().startActivity("Module1",
	    	    new Intent(GroupsActivity.this, Image3DShowActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	    break;
	case R.id.button05:
		AnimatioinUtil.SetImageSlide(sideButton, startX, itemWidth * 4, 0, 0);
		startX = itemWidth * 4;
	    view = getLocalActivityManager().startActivity("Module1",
	    	    new Intent(GroupsActivity.this, RotateActivity.class) .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
	    break;
	default:
	    break;
	}
	layout.removeAllViews();
	layout.addView(view, params);
    }
    /**
     * 获取屏幕的宽度
     * @return
     */
    private int getScreenWidth(){
    	WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int screenWidth = display.getWidth();
		return screenWidth;
    }
}
