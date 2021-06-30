package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.awen.codebase.R;
import com.awen.codebase.common.viewpage.AccordionTransformer;
import com.awen.codebase.common.viewpage.BackgroundToForegroundTransformer;
import com.awen.codebase.common.viewpage.CubeInTransformer;
import com.awen.codebase.common.viewpage.CubeOutTransformer;
import com.awen.codebase.common.viewpage.DepthPageTransformer;
import com.awen.codebase.common.viewpage.FlipHorizontalTransformer;
import com.awen.codebase.common.viewpage.FlipVerticalTransformer;
import com.awen.codebase.common.viewpage.ForegroundToBackgroundTransformer;
import com.awen.codebase.common.viewpage.RotateDownTransformer;
import com.awen.codebase.common.viewpage.ScaleInOutTransformer;
import com.awen.codebase.common.viewpage.StackTransformer;
import com.awen.codebase.common.viewpage.TabletTransformer;
import com.awen.codebase.common.viewpage.ZoomInTransformer;
import com.awen.codebase.common.viewpage.ZoomOutSlideTransformer;
import com.awen.codebase.common.viewpage.ZoomOutTranformer;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAnimActivity extends Activity {

	private View view1, view2, view3;
	private ViewPager viewPager;

	private PagerTabStrip pagerTabStrip;
	private List<View> viewList;
	private List<String> titleList;
	private Button changeButton;
    private String[] animStrs= {"0.左右黏贴平移","1.左右黏合滑动", "2.快速消失切入","3.3D向前飞出屏幕", "4.3D箱子旋转","5.平移", "6.卡片左右翻页", "7.卡片上下翻页",
			"8.等比放大缩小", "9.左右带角度平移", "10.左右带角度平移", "11.好像没有写","12.遮盖翻页", "13.内旋3D翻页", "14.不翻页中心缩小",
			"15.左右半透明平移", "16.改变透明度变换"};

	private int index = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_view_pager);
		initView();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setDrawFullUnderline(false);

		pagerTabStrip.setTextSpacing(50);

		LayoutInflater lf = getLayoutInflater().from(this);
		view1 = lf.inflate(R.layout.layout1, null);
		view2 = lf.inflate(R.layout.layout2, null);
		view3 = lf.inflate(R.layout.layout3, null);

		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		titleList = new ArrayList<String>();
		titleList.add("1");
		titleList.add("2");
		titleList.add("3");
		changeButton = (Button) findViewById(R.id.button1);
		changeButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				index++;
				if (index == 17) {
					index = 0;
				}
               setPageAnim(index);
			}
		});

		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {

				return arg0 == arg1;
			}

			@Override
			public int getCount() {

				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
                                    Object object) {
				container.removeView(viewList.get(position));

			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public CharSequence getPageTitle(int position) {

				return titleList.get(position);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}

		};

		setPageAnim(0);
		viewPager.setAdapter(pagerAdapter);
	}

	private void setPageAnim(int index){
		switch (index) {
			case 1:
				viewPager.setPageTransformer(true,
						new AccordionTransformer());
				break;
			case 2:
				viewPager.setPageTransformer(true,
						new BackgroundToForegroundTransformer());

				break;
			case 3:
				viewPager.setPageTransformer(true,
						new CubeInTransformer());
				break;
			case 4:
				viewPager.setPageTransformer(true,
						new CubeOutTransformer());
				break;
			case 5:
				viewPager.setPageTransformer(true,
						new DepthPageTransformer());
				break;
			case 6:
				viewPager.setPageTransformer(true,
						new FlipHorizontalTransformer());
				break;
			case 7:
				viewPager.setPageTransformer(true,
						new FlipVerticalTransformer());
				break;
			case 8:
				viewPager.setPageTransformer(true,
						new ForegroundToBackgroundTransformer());
				break;
			case 9:
				viewPager.setPageTransformer(true,
						new RotateDownTransformer());
				break;
			case 11:
				viewPager.setPageTransformer(true,
						new ScaleInOutTransformer());
				break;
			case 12:
				viewPager.setPageTransformer(true,
						new StackTransformer());
				break;
			case 13:
				viewPager.setPageTransformer(true,
						new TabletTransformer());
				break;
			case 14:
				viewPager.setPageTransformer(true,
						new ZoomInTransformer());
				break;
			case 15:
				viewPager.setPageTransformer(true,
						new ZoomOutSlideTransformer());
				break;
			case 16:
				viewPager.setPageTransformer(true,
						new ZoomOutTranformer());
				break;
			default:
				break;
		}
		changeButton.setText("当前效果："+animStrs[index]+"  共16种效果");
	}

}
