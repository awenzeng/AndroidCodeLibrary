package com.awen.codebase.activity.group;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

import com.awen.codebase.CodeBaseApp;
import com.awen.codebase.R;
import com.awen.codebase.common.utils.LogUtil;

public class TabDemoActivity extends Activity {
	private ViewPager mPager;//页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private TextView myTextView1 ;
	private SurfaceView mSurfaceView ;
	private SurfaceHolder surfaceHolder;
	private MediaPlayer mediaPlayer;
	private Button startButton;
	private Button pauseButtion;
	private Button resetButtion;
	private Button stopButton;
	
	private SlidingDrawer slidingDrawer;
	private GridView gridView;
	private ImageView imageView;
	private int[] itemIcons = new int[]
	{ R.drawable.alarm, R.drawable.calendar, R.drawable.camera, R.drawable.clock, R.drawable.music, R.drawable.tv };
	private String[] itemString =  new String[]{"Alarm","Calendar","camera","clock","music","tv"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_demo_main);
		InitImageView();
		InitTextView();
		InitViewPager();
	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		listViews.add(mInflater.inflate(R.layout.lay1, null));
		listViews.add(mInflater.inflate(R.layout.lay2, null));
		listViews.add(mInflater.inflate(R.layout.lay3, null));

		slidingDrawer = (SlidingDrawer)listViews.get(1).findViewById(R.id.drawer1);
		gridView = (GridView)listViews.get(1).findViewById(R.id.mycontent1);
		imageView = (ImageView)listViews.get(1).findViewById(R.id.myImage);
		/* 使用告定义的MyGridViewAdapter设置GridView里面的item内容 */
		GridAdapter ga = new GridAdapter(TabDemoActivity.this, itemString, itemIcons);
		gridView.setAdapter(ga);
		 /* 设定SlidingDrawer被打开的事件处理 */
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener()
		{

			public void onDrawerOpened()
			{
				imageView.setImageResource(R.drawable.close);

			}
		});
		 /* 设定SlidingDrawer被关闭的事件处理 */
		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener()
		{

			public void onDrawerClosed()
			{
				imageView.setImageResource(R.drawable.open);
			}
		});
    	myTextView1 = (TextView)listViews.get(2).findViewById(R.id.myTextView1);
    	mSurfaceView = (SurfaceView)listViews.get(2).findViewById(R.id.mSurfaceView1);//显示控件
    	surfaceHolder = mSurfaceView.getHolder();
    	surfaceHolder.setFixedSize(176, 144);	//设置视频大小
    	surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型 (推送)
    	
    	startButton = (Button)listViews.get(2).findViewById(R.id.startButton);
    	pauseButtion = (Button)listViews.get(2).findViewById(R.id.pauseButtion);
    	resetButtion = (Button)listViews.get(2).findViewById(R.id.reset);
    	stopButton = (Button)listViews.get(2).findViewById(R.id.stopButton);
    	
    	//开始播放
    	startButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				palyVideo("android.resource://"+getPackageName()+"/"+ R.raw.video_1);
			}
		});
    	
    	//暂停播放
    	pauseButtion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					myTextView1.setText("暂停");
				}else{
					mediaPlayer.start();
					myTextView1.setText("播放");
				}
			}
		});
    	
    	//重新播放
    	resetButtion.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.seekTo(0); //定位到开始位置(从头开始播放)
				//此处可以和SeekBar(拖动)控件结合使用，将当前刻度传递给seekTo
				mediaPlayer.start();
			}
		});
    	
    	//停止播放
    	stopButton.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
				if(mediaPlayer.isPlaying()){
					mediaPlayer.stop();
				}
				myTextView1.setText("停止播放");
			}
		});
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}
    private void palyVideo(String path){
    	try {
			mediaPlayer.reset() ;	//	重设
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// 设置Video影片以SurfaceHolder播放
			mediaPlayer.setDisplay(surfaceHolder);
			String url = CodeBaseApp.httpProxyCacheServer.getProxyUrl(path);
			LogUtil.androidLog("video url:"+url);
			url = path;
			mediaPlayer.setDataSource(this, Uri.parse(url));
			mediaPlayer.setLooping(true);
			mediaPlayer.prepare();
			mediaPlayer.start();
			myTextView1.setText("开始播放");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Override
    protected void onResume() {
    	super.onResume();
    	mediaPlayer = new MediaPlayer();
    	mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				myTextView1.setText("播放完毕");
			}
		});
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	if(mediaPlayer.isPlaying()){
    		mediaPlayer.stop();
    	}
    	mediaPlayer.release();
    }
	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	public class GridAdapter extends BaseAdapter
	{
		private Context _con;
		private String[] _itemString;
		private int[] _itemIcons;
		
		public GridAdapter(Context con,String[] itemString,int[] itemIcons){
			_con = con;
			_itemString = itemString;
			_itemIcons = itemIcons;
		}
		public int getCount()
		{
			// TODO Auto-generated method stub
			return _itemIcons.length;
		}

		public Object getItem(int position)
		{
			// TODO Auto-generated method stub
			return _itemString[position];
		}

		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = LayoutInflater.from(_con);
			 /* 使用item.xml为每几个item的Layout */
			View v = inflater.inflate(R.layout.slidingdrawer_item, null);
			 /* 取得View */
			ImageView iv = (ImageView) v.findViewById(R.id.item_grid);
			TextView tv = (TextView) v.findViewById(R.id.item_text);
			/* 设定显示的Image与文? */
			iv.setImageResource(_itemIcons[position]);
			tv.setText(_itemString[position]);
			return v;
		}

	}

}