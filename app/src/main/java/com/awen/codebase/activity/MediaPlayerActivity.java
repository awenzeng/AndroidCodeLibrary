package com.awen.codebase.activity;

import static android.media.MediaPlayer.SEEK_CLOSEST_SYNC;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.awen.codebase.CodeBaseApp;
import com.awen.codebase.R;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.utils.LogUtil;

import java.io.IOException;

/**
 * @ClassName: MediaPlayerActivity
 * @Author: AwenZeng
 * @CreateDate: 2021/7/16 17:54
 * @Description:
 */
@Route(path = ActivityRouter.AROUTER_MediaPlayerActivity)
public class MediaPlayerActivity extends BaseActivity{

    private SurfaceView mSurfaceView;//能够播放图像的控件
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;//媒体播放器
    private int position = 0;
    private boolean isSurfaceDestroy = false;
    private int[] videos = {R.raw.video_1,R.raw.video_2};

    private int ipx = 0;
    //视频文件需要放在res下新建的raw文件下
    private String video1= "android.resource://com.awen.codebase/"+ R.raw.video_1;
    private String video2 = "android.resource://com.awen.codebase/"+ R.raw.video_2;

    private String[] resId = {video1,video2};
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.act_mediaplayer);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isSurfaceDestroy){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mMediaPlayer.seekTo(position, SEEK_CLOSEST_SYNC);
            } else {
                mMediaPlayer.seekTo(position);
            }
        }
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    //初始化控件，并且为进度条和图像控件添加监听
    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.sfv);
        mMediaPlayer = new MediaPlayer();
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//设置类型 (推送)
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //为了避免图像控件还没有创建成功，用户就开始播放视频，造成程序异常，所以在创建成功后才使播放按钮可点击
                Log.d("TAG", "surfaceCreated");
                if(mMediaPlayer!=null){
                    mMediaPlayer.setDisplay(mSurfaceHolder);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.d("TAG", "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //当程序没有退出，但不在前台运行时，因为surfaceview很耗费空间，所以会自动销毁，
                // 这样就会出现当你再次点击进程序的时候点击播放按钮，声音继续播放，却没有图像
                //为了避免这种不友好的问题，简单的解决方式就是只要surfaceview销毁，我就把媒体播放器等
                //都销毁掉，这样每次进来都会重新播放，当然更好的做法是在这里再记录一下当前的播放位置，
                //每次点击进来的时候把位置赋给媒体播放器，很简单加个全局变量就行了。
                Log.d("TAG", "surfaceDestroyed");
                if (mMediaPlayer != null) {
                    position = mMediaPlayer.getCurrentPosition();
                    isSurfaceDestroy = true;
                }
            }
        });
        size = resId.length;
        play();
    }

    private void play() {

        if (isPause) {//如果是暂停状态下播放，直接start
            isPause = false;
            mMediaPlayer.start();
            return;
        }
        try {
            String url = CodeBaseApp.httpProxyCacheServer.getProxyUrl(resId[ipx%size]);
            LogUtil.androidLog("video url:"+url);
            url = resId[ipx%size];
            mMediaPlayer.setDataSource(this,Uri.parse(url));
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//视频播放完成后，释放资源
//                    mPlayBtn.setEnabled(true);
//
                    ipx++;
                    mp.reset();
                    //循环播放下一个
                    String url = CodeBaseApp.httpProxyCacheServer.getProxyUrl(resId[ipx%size]);
                    LogUtil.androidLog("video url:"+url);
                    try {
                        url = resId[ipx%size];
                        mp.setDataSource(MediaPlayerActivity.this,Uri.parse(url));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mp.prepare();
                        mp.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    changeVideoSize(mp);
                }
            });

            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(View v) {
        play();
    }

    public void changeVideoSize(MediaPlayer mp) {
        int videoWidth = mp.getVideoWidth();
        int videoHeight = mp.getVideoHeight();
        int layoutWidth = 960;
        int layoutHeight = 540;

        float vRatio = videoWidth / layoutWidth;
        float hRatio = videoHeight / layoutHeight;
        float ratio = Math.max(vRatio, hRatio);
        videoWidth = (int) Math.ceil(videoWidth / ratio);
        videoHeight = (int) Math.ceil(videoHeight / ratio);
        LinearLayout.LayoutParams lp  = new LinearLayout.LayoutParams(videoWidth, videoHeight);
        lp.gravity = Gravity.CENTER;
        mSurfaceView.setLayoutParams(lp);
    }

    private boolean isPause;

    private void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }
    }

    public void pause(View v) {
        pause();
    }

    private void replay() {
        isPause = false;
        if (mMediaPlayer != null) {
            stop();
            play();
        }
    }

    public void replay(View v) {
        replay();
    }

    public void back(View v) {
        finish();
    }

    private void stop() {
        isPause = false;
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void stop(View v) {
        stop();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }


}