package com.awen.codebase.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awen.codebase.R;
import com.awen.codebase.adapter.MiLaucherAdapter;
import com.awen.codebase.data.Configure;
import com.awen.codebase.ui.MiLaucherDragGrid;
import com.awen.codebase.ui.ScrollLayout;

public class MiLaucherActivity extends Activity{

	/** GridView. */
	private LinearLayout linear;private RelativeLayout relate;
	private MiLaucherDragGrid gridView;
	private ScrollLayout lst_views;
	private TextView tv_page;// int oldPage=1;
	private ImageView runImage, delImage;
	private LinearLayout.LayoutParams param;

	private TranslateAnimation left, right;
	private Animation up, down;

	public static final int PAGE_SIZE = 8;
	private ArrayList<MiLaucherDragGrid> gridviews = new ArrayList<MiLaucherDragGrid>();
	
	private ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();//全部数据的集合集lists.size()==countpage;
	private ArrayList<String> lstDate = new ArrayList<String>();//每一页的数据
	
	private SensorManager sm;
	private SensorEventListener lsn;
	private boolean isClean =false;
	private Vibrator vibrator;
	private int rockCount=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_milaucher);
	
		initView();
		initData();
		runAnimation();

		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);   
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		lsn = new SensorEventListener() {
			public void onSensorChanged(SensorEvent e) {
				if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					if(!isClean&&rockCount>=10){
						isClean=true;rockCount=0;
						vibrator.vibrate(100);
						CleanItems();
						return;
					}
					float newX = e.values[SensorManager.DATA_X];
					float newY = e.values[SensorManager.DATA_Y];
					float newZ = e.values[SensorManager.DATA_Z];
					if((newX >= 18 || newY >= 20||newZ >= 20 ) && rockCount%2==0){
						rockCount++;
						return;
					}if((newX <= -18 || newY <= -20||newZ <= -20 ) && rockCount%2==1){
						rockCount++;
						return;
					}
					
				}
			}
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};
		sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);
	}

	public void initView() {
		relate = (RelativeLayout) findViewById(R.id.relate);
		lst_views = (ScrollLayout) findViewById(R.id.views);
		tv_page = (TextView) findViewById(R.id.tv_page);
		runImage = (ImageView) findViewById(R.id.run_image);
		delImage = (ImageView) findViewById(R.id.dels);
		tv_page.setText("1");
		Configure.init(MiLaucherActivity.this);
		param = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		param.rightMargin = 100;
		param.leftMargin = 20;
		if (gridView != null) {
			lst_views.removeAllViews();
		}
		relate.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("LongClick");
				return false;
			}
		});
	}
	public void initData(){
		for (int i = 0; i < 22; i++) {
			lstDate.add("" + i);
		}
		
		Configure.countPages = (int) Math.ceil(lstDate.size()
				/ (float) PAGE_SIZE);

		lists = new ArrayList<ArrayList<String>>();
		for(int i=0;i<Configure.countPages;i++){
			lists.add(new ArrayList<String>());
			for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > lstDate
				.size() ? lstDate.size() : PAGE_SIZE * (i + 1)); j++) 
				lists.get(i).add(lstDate.get(j));
		}
		boolean isLast=true;
		for(int i=lists.get(Configure.countPages-1).size();i<PAGE_SIZE;i++){
			if(isLast){lists.get(Configure.countPages-1).add(null);isLast=false;}
			else lists.get(Configure.countPages-1).add("none");
		}
		
		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
		}
		
		lst_views.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {
				setCurPage(page);
			}
		});
	}
	public void CleanItems(){
		lstDate=new ArrayList<String>();
		for(int i=0;i<lists.size();i++){
			for(int j=0;j<lists.get(i).size();j++){
				if(lists.get(i).get(j)!=null&&!lists.get(i).get(j).equals("none")){
					lstDate.add(lists.get(i).get(j).toString());System.out.println("-->"+lists.get(i).get(j).toString());
				}
			}
		}
		System.out.println(lstDate.size());
		initData();
		lst_views.removeAllViews();
		gridviews = new ArrayList<MiLaucherDragGrid>();
		for (int i = 0; i < Configure.countPages; i++) {
			lst_views.addView(addGridView(i));
		}
		isClean=false;
		lst_views.snapToScreen(0);
	}
	public int getFristNonePosition(ArrayList<String> array){
		for(int i=0;i<array.size();i++){
			if(array.get(i)!=null &&
					array.get(i).toString().equals("none")){
				return i;
			}
		}
		return -1;
	}
	public int getFristNullPosition(ArrayList<String> array){
		for(int i=0;i<array.size();i++){
			if(array.get(i)==null){
				return i;
			}
		}
		return -1;
	}
	
	public LinearLayout addGridView(int i) {
		
		linear = new LinearLayout(MiLaucherActivity.this);
		gridView = new MiLaucherDragGrid(MiLaucherActivity.this);
		gridView.setAdapter(new MiLaucherAdapter(MiLaucherActivity.this, lists.get(i)));
		gridView.setNumColumns(2);
		gridView.setHorizontalSpacing(0);
		gridView.setVerticalSpacing(0);
		final int index = i;
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (lists.get(index).get(arg2) == null) {
					new AlertDialog.Builder(MiLaucherActivity.this)
							.setTitle("添加")
							.setItems(R.array.items,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											final String[] arrayAddItems = getResources().getStringArray(R.array.items); // array
											lists.get(index).add(arg2,arrayAddItems[which]);
											lists.get(index).remove(arg2 + 1);
											
											if (getFristNonePosition(lists.get(index)) >0 && getFristNullPosition(lists.get(index)) <0) {
												lists.get(index).set(getFristNonePosition(lists.get(index)), null);
											}
											if(getFristNonePosition(lists.get(index)) <0  && getFristNullPosition(lists.get(index)) <0){
												System.out.println("===");
												if(index==Configure.countPages-1 || (getFristNullPosition(lists.get(lists.size()-1)) <0 && getFristNonePosition(lists.get(lists.size()-1)) <0)){
													lists.add(new ArrayList<String>());
													lists.get(lists.size()-1).add(null);
													for(int i=1;i<PAGE_SIZE;i++)
														lists.get(lists.size()-1).add("none");
													
													lst_views.addView(addGridView(Configure.countPages));Configure.countPages++;
												}else if(getFristNonePosition(lists.get(lists.size()-1)) >0  && getFristNullPosition(lists.get(lists.size()-1)) <0){
													lists.get(lists.size()-1).set(getFristNonePosition(lists.get(lists.size()-1)), null);
													((MiLaucherAdapter) ((gridviews.get(lists.size()-1)).getAdapter())).notifyDataSetChanged();
												}
											}
										/*	
											if(lists.get(Configure.countPages-1)!=null &&lists.get(Configure.countPages-1).size()>0
													&& lists.get(Configure.countPages-1).get(lists.get(Configure.countPages-1).size()-1)!=null){
												lists.get(Configure.countPages-1).add(null);
												((DateAdapter) ((gridviews.get(Configure.countPages-1)).getAdapter())).notifyDataSetChanged();
											}*/
											((MiLaucherAdapter) ((gridviews.get(index)).getAdapter())).notifyDataSetChanged();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									}).show();
				}
				
			}
		});
		gridView.setSelector(R.anim.grid_light);
		gridView.setPageListener(new MiLaucherDragGrid.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:// 滑动页面
					lst_views.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Configure.isChangingPage = false;
						}
					}, 800);
					break;
				case 1:// 删除按钮上来
					delImage.setBackgroundResource(R.drawable.del);
					delImage.setVisibility(0);
					delImage.startAnimation(up);
					break;
				case 2:// 删除按钮变深
					delImage.setBackgroundResource(R.drawable.del_check);
					Configure.isDelDark = true;
					break;
				case 3:// 删除按钮变淡
					delImage.setBackgroundResource(R.drawable.del);
					Configure.isDelDark = false;
					break;
				case 4:// 删除按钮下去
					delImage.startAnimation(down);
					break;
				case 5:// 松手动作
					delImage.startAnimation(down);
					// Configure.isDelRunning = false;
					lists.get(Configure.curentPage).add(Configure.removeItem,
							null);
					lists.get(Configure.curentPage).remove(
							Configure.removeItem + 1);
					((MiLaucherAdapter) ((gridviews.get(Configure.curentPage))
							.getAdapter())).notifyDataSetChanged();
					break;
				}
			}
		});
		gridView.setOnItemChangeListener(new MiLaucherDragGrid.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				String toString = (String) lists.get(
						Configure.curentPage - count).get(from);

				lists.get(Configure.curentPage - count).add(from,
						(String) lists.get(Configure.curentPage).get(to));
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, toString);
				lists.get(Configure.curentPage).remove(to + 1);

				((MiLaucherAdapter) ((gridviews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
				((MiLaucherAdapter) ((gridviews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
			}
		});
		gridviews.add(gridView);
		linear.addView(gridviews.get(i), param);
		return linear;
	}
	public void runAnimation() {
		down = AnimationUtils.loadAnimation(MiLaucherActivity.this,
				R.anim.del_down);
		up = AnimationUtils
				.loadAnimation(MiLaucherActivity.this, R.anim.del_up);
		down.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				delImage.setVisibility(8);
			}
		});

		right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
				Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f);
		left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
				Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
				0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(25000);
		left.setDuration(25000);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				runImage.startAnimation(right);
			}
		});
		runImage.startAnimation(right);
	}

	public void setCurPage(final int page) {
		Animation a = AnimationUtils.loadAnimation(MiLaucherActivity.this,
				R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				tv_page.setText((page + 1) + "");
				tv_page.startAnimation(AnimationUtils.loadAnimation(
						MiLaucherActivity.this, R.anim.scale_out));
			}
		});
		tv_page.startAnimation(a);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sm.unregisterListener(lsn);
	}
	
}













