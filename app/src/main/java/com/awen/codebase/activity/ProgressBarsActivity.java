package com.awen.codebase.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.awen.codebase.R;

public class ProgressBarsActivity extends Activity{
	
    private ProgressBar mColor = null;
    private ProgressBar mRight = null;
    private ProgressBar mLeft = null;
    private int mCountColor = 0;
    private int mCountLeft = 0;
    private int mCountRight = 0;
	private SeekBar mSeekBar = null;
	private Context mContext;
	public static final int HANDLER_COLOR = 0;
    public static final int HANDLER_FROM_LEFT = 1;
    public static final int HANDLER_FROM_RIGHT = 2;
    public static final int HANDLER_NOSURE = 3;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
        	switch (msg.what) {
        	case HANDLER_COLOR:
				break;
			case HANDLER_FROM_LEFT:
				break;
			case HANDLER_FROM_RIGHT:
				
				break;
			case HANDLER_NOSURE:
	
				break;
			default:
				break;
			}
            super.handleMessage(msg);
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_progressbars);
		colorProgressBar();
		fromLeftProgressBar();
		fromRightProgressBar();
	}
	
	/**
	 * 颜色可变progressBar
	 */
   private void colorProgressBar()
	    {
	   		mCountColor = 0;
	        mColor = (ProgressBar) findViewById(R.id.progress_horizontal_color);
	        mColor.setMax(100);
	        mColor.setProgress(0);
	        mColor.setIndeterminate(false);
	        new Thread()
	        {
	            public void run()
	            {
	                try
	                {
	                    while (mCountColor <= 100)
	                    {
	                        mColor.setProgress(mCountColor++);
	                        Thread.sleep(100);
	                    }
	                    if (mCountColor > 100)
	                    {
	                        mHandler.sendEmptyMessage(HANDLER_COLOR);
	                    }
	                }
	                catch (Exception ex)
	                {
	                }
	            }
	        }.start();
	    }
	/**
	 * <一句话功能简述>展示进度条的进度<BR>
	 * <功能详细描述>
	 * 
	 * @see [类、类#方法、类#成员]
	 */
	private void fromLeftProgressBar() {

		mLeft = (ProgressBar) findViewById(R.id.progress_horizontal_left);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		mLeft.setMax(100);
		mLeft.setProgress(0);
		mLeft.setIndeterminate(false);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				mLeft.setProgress(progress);
			}
		});

	}
	   /**
     * <一句话功能简述>展示进度条的进度<BR>
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    private void fromRightProgressBar()
    {
    	mCountRight = 100;

        mRight = (ProgressBar) findViewById(R.id.progress_horizontal_right);
        mRight.setMax(100);
        mRight.setProgress(0);
        mRight.setIndeterminate(false);
        new Thread()
        {
            public void run()
            {
                try
                {
                    while (mCountRight >= 0)
                    {
                        mRight.setProgress(mCountRight--);
                        Thread.sleep(100);
                    }
                    if (mCountRight < 0)
                    {
                        mHandler.sendEmptyMessage(HANDLER_FROM_RIGHT);
                    }
                }
                catch (Exception ex)
                {
                }
            }
        }.start();
    }
}
