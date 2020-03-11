package com.awen.codebase.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ClassName: LoadingCircleView
 * @Author: AwenZeng
 * @CreateDate: 2020/1/14 16:22
 * @Description: 圆形加载弹框
 */
public class LoadingCircleView extends View {
    //背景圆
    private Paint paintBgCircle;
    //填充圆
    private Paint paintCircle;
    //进度圆
    private Paint paintProgressCircle;
    //开始角度
    private float startAngle = -90f;
    //进度角度
    private float sweepAngle = 0;
    //进度圆与背景圆的间距
    private int progressCirclePadding = 0;
    //进度圆是否填充
    private boolean fillIn = false;

    public LoadingCircleView(Context context) {
        super(context);
        init();
    }

    public LoadingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LoadingCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        progressCirclePadding = dip2px(getContext(), 3);
        paintBgCircle = new Paint();
        paintBgCircle.setAntiAlias(true);
        paintBgCircle.setStyle(Paint.Style.FILL);
        paintBgCircle.setColor(Color.WHITE);

        paintCircle = new Paint();
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);
        paintCircle.setColor(Color.GRAY);

        paintProgressCircle = new Paint();
        paintProgressCircle.setAntiAlias(true);
        paintProgressCircle.setStyle(Paint.Style.FILL);
        paintProgressCircle.setColor(Color.WHITE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(
                getMeasuredWidth() / 2,
                getMeasuredWidth() / 2,
                getMeasuredWidth() / 2,
                paintBgCircle);
        canvas.drawCircle(
                getMeasuredWidth() / 2,
                getMeasuredWidth() / 2,
                getMeasuredWidth() / 2 - progressCirclePadding / 2,
                paintCircle);

        RectF rf = new RectF(progressCirclePadding, progressCirclePadding, getMeasuredWidth() - progressCirclePadding, getMeasuredWidth() - progressCirclePadding);
        canvas.drawArc(rf, startAngle, sweepAngle, true, paintProgressCircle);
        if (!fillIn) {
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredWidth() / 2, getMeasuredWidth() / 2 - progressCirclePadding * 2,
                    paintCircle);

        }

    }


    public void startAnimAutomatic(boolean fillIn) {
        setProgress(0, fillIn);
    }


    public void setProgress(int progress, boolean fillIn) {
        this.fillIn = fillIn;
        sweepAngle = (float) (360 / 100.0 * progress);
        invalidate();
    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
