package com.awen.codebase.common.utils;

import android.view.Choreographer;

public class FPSFrameCallback implements Choreographer.FrameCallback {

    private static final String TAG = "FPS_TEST";
    private long mLastFrameTimeNanos = 0;
    private int mSystemRate = FRAME_RATE;
    private static final long FRAME_INTERVAL_NANOS = 1000 * 1000000/60;//一帧：1s/60 = 1000 * 1000000/60;
    private static final int FRAME_RATE = 60;//60FPS

    public FPSFrameCallback(long lastFrameTimeNanos, float systemRate) {
        mLastFrameTimeNanos = lastFrameTimeNanos;
        mSystemRate = (int) Math.floor(systemRate);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        //初始化时间
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = frameTimeNanos;
        }
        long drawTimeNanos = frameTimeNanos - mLastFrameTimeNanos;
        if (drawTimeNanos >= FRAME_INTERVAL_NANOS) {
            long drawFrames = drawTimeNanos / FRAME_INTERVAL_NANOS;
            long showFrame = mSystemRate - drawFrames + 1;
            LogUtil.androidLog(TAG, "帧率：" + showFrame + "/" + mSystemRate);
        }
        mLastFrameTimeNanos = frameTimeNanos;
        //注册下一帧回调
        Choreographer.getInstance().postFrameCallback(this);
    }
}