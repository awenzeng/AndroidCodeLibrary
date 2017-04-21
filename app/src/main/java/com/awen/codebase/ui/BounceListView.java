package com.awen.codebase.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.Scroller;

public class BounceListView extends ListView
{
	private static final int MAX_Y_OVERSCROLL_DISTANCE = 250;
    private static final float SCROLL_RATIO = 0.5f;// 阻尼系数  
	private Context mContext;
	private int mMaxYOverscrollDistance;

	public BounceListView(Context context)
	{
		super(context);
		mContext = context;
		initBounceListView();
	}

	public BounceListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		initBounceListView();
	}

	public BounceListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mContext = context;
		initBounceListView();
	}
	private void initBounceListView()
	{

		final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		final float density = metrics.density;

		mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		//模拟点击操作，解决会
		this.performClick();
		return super.onTouchEvent(ev);
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
	{
		Log.i("good", "deltaX:"+deltaX+",deltaY:"+deltaY+",scrollX："+scrollX+",scrollY:"+scrollY+",scrollRangeX:"+scrollRangeX+",scrollRangeY:"+scrollRangeY+",maxOverScrollX:"+maxOverScrollX+",maxOverScrollY:"+maxOverScrollY+"   "+isTouchEvent);
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
	}
	
}