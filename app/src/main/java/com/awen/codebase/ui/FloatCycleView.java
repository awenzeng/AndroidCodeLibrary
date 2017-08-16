package com.awen.codebase.ui;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.awen.codebase.R;
import com.awen.codebase.utils.ScreenSizeUtil;

public class FloatCycleView extends SurfaceView implements SurfaceHolder.Callback {
    private int mCount = 0; //圆圈的个数
    private Handler mainHandler;
    private int diameter;//画的圆圈直径
    private ArrayList<DataHolder> mData; //用链表来存储每个圆圈
    private Drawable backgroud;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mScreenHeight;
    private int mScreenWidth;
    private Context context_;
    private GestureDetector mGestureDetector;
    private Boolean startBreak = false;    //检测小球之间碰撞是否开始
    private Boolean showDelete = false;
    private Boolean dataUpdate = false;    //是否有数据更新
    private Boolean click = true;
    private DataUpThread mWorkThread;      //圆圈移动判断线程
    private int intervel = 1000 / 500;
    private int yOffset = 0;
    private int xOffset = 0;
    public static final int FLING_MIN_DISTANCE = 100;
    public static final int FLING_MIN_VELOCITY = 200;

    //贴图
    private int[] SOURCE_MAP = new int[]{
            R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two, R.drawable.three,
            R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two, R.drawable.three,
            R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.one,
            R.drawable.two, R.drawable.three, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.one,
            R.drawable.two, R.drawable.three
    };

    public FloatCycleView(Context context) {
        super(context);
        context_ = context;
        init();
    }

    public FloatCycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        context_ = context;
        init();
    }

    public FloatCycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        context_ = context;

        init();
    }

    private void init() {
        setFocusable(Boolean.TRUE);
        setClickable(Boolean.TRUE);
        setLongClickable(Boolean.TRUE);
        mData = new ArrayList<FloatCycleView.DataHolder>();
        mGestureDetector = new GestureDetector(new MyGestureListener());
        getHolder().addCallback(this);
        final Resources res = getResources();
        BitmapDrawable d = (BitmapDrawable) res.getDrawable(SOURCE_MAP[0]);
        diameter = d.getBitmap().getWidth();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        frame = makeDst(diameter, diameter);
        mainHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    update();
                    this.sendEmptyMessageDelayed(0, intervel);
                } else if (msg.what == 1) {
                    mWorkThread.start();
                }
            }

        };
        mWorkThread = new DataUpThread();
        backgroud = res.getDrawable(R.drawable.background);
        mScreenHeight = res.getDisplayMetrics().heightPixels;

    }

    public void closeThread() {
        startBreak = false;
        mWorkThread.mStop = Boolean.TRUE;
        mainHandler.removeCallbacksAndMessages(null);
        mWorkThread = new DataUpThread();
        mainHandler.sendEmptyMessage(0);
        mainHandler.sendEmptyMessageDelayed(1, 200);
    }

    public void recylcle() {
    }

    //设置圆圈数目
    public void setCount(int count, final int updateCount) {
        mCount = count;
        mHeight = Math.max(mScreenHeight, mCount / 3 * diameter);
        DisplayMetrics dis = getResources().getDisplayMetrics();
        int cx = dis.widthPixels >> 1;
        int cy = dis.heightPixels >> 1;
        mData.clear();
        int r = diameter >> 1;
        float everyDegree = 360 / mCount;
        for (int i = 0; i < mCount; i++) {
            DataHolder h = new DataHolder(i, r, cx, cy, i * everyDegree);
            mData.add(h);

        }

        new Thread() {

            @Override
            public void run() {
                super.run();
                Resources res = getResources();
                int i = 1;
                for (DataHolder dataHolder : mData) {
                    BitmapDrawable d = (BitmapDrawable) res.getDrawable(SOURCE_MAP[i - 1]);
                    if (dataHolder.id == updateCount) {
                        dataUpdate = true;
                    } else {
                        dataUpdate = false;
                    }
                    //添加文字
                    dataHolder.text = "  ";
                    dataHolder.createBitmap(d.getBitmap(), dataHolder.text, mPaint, showDelete, dataUpdate);
                    i++;
                }
            }

        }.start();
    }


    private int lastY = 0;
    private int lastX = 0;

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            if (click) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                for (DataHolder h : mData) {
                    if (Math.abs(h.centerX - x) < h.radius && (Math.abs(h.centerY - yOffset - y) < h.radius)) {
                        Toast.makeText(getContext(), "You click on " + h.id, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
            lastX = 0;
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (lastY != 0) {
                int y = (int) event.getY();
                int t = -y + lastY;
                if (Math.abs(t) > 10) {
                    yOffset += (-y + lastY);
                    lastY = y;
                }
            } else {
                lastY = (int) event.getY();
            }
            if (yOffset < 0)
                yOffset = 0;
            else if ((yOffset + mScreenHeight) > mHeight) {
                yOffset = mHeight - mScreenHeight;
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mScreenHeight = height;
        mWidth = width;
        backgroud.setBounds(0, 0, width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainHandler.sendEmptyMessage(0);
        mainHandler.sendEmptyMessageDelayed(1, 200);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mWorkThread.mStop = Boolean.TRUE;
        mainHandler.removeCallbacksAndMessages(null);

    }

    private void update() {
        SurfaceHolder holder = getHolder();
        Canvas c = holder.lockCanvas();
        if (backgroud != null) {
            backgroud.draw(c);
        } else {
            c.drawColor(Color.BLACK);
        }
        for (DataHolder h : mData) {
            if (h.isShow())
                h.draw(c, mPaint);
        }
        holder.unlockCanvasAndPost(c);
    }


    private final static PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private Bitmap frame = null;

    private class DataHolder {
        float centerX;
        float centerY;
        int id;
        final int radius;
        Bitmap mBitmap;
        float addX;
        float addY;
        LinearGradient lg = null;
        String text;

        public DataHolder(int _id, int r, int cx, int cy, float degress) {
            id = _id;
            radius = r;
            centerX = cx;
            centerY = cy;
            double h = degress * Math.PI / 180;
            int t = (int) (Math.sin(h) * 100);
            addX = t * 1.0f / 100;
            if (addX == 0)
                addX = 0.01f;
            t = (int) (Math.cos(h) * 100);
            addY = t * 1.0f / 100;
            if (addY == 0)
                addY = 0.01f;
        }

        public boolean isShow() {
            return centerY - radius + diameter >= yOffset && centerY - radius <= mHeight;
        }

        public void createBitmap(Bitmap source, String text, Paint p, Boolean showDelete, Boolean DataUpdate) {

            int q = radius << 1;
            int hw = source.getWidth() >> 1;
            int hh = source.getHeight() >> 1;
            p.setStyle(Paint.Style.FILL);

            //贴图到圆圈上
            mBitmap = Bitmap.createBitmap(q, q, Config.ARGB_8888);
            Canvas c = new Canvas(mBitmap);
            int fhw = frame.getWidth() >> 1;
            int fhh = frame.getHeight() >> 1;
            p.setFlags(Paint.ANTI_ALIAS_FLAG);
            c.drawBitmap(frame, radius - fhw + 1, radius - hh + 3, p);
            p.setXfermode(mXfermode);
            c.drawBitmap(source, radius - hw + 1, radius - hh + 3, p);
            p.setXfermode(null);

            //画彩圈
            p.setStyle(Style.STROKE);
            p.setFlags(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.GRAY);
            p.setMaskFilter(new BlurMaskFilter(0.5f, Blur.NORMAL));
            p.setStrokeWidth(5);
            c.drawOval(new RectF(4, 4, q - 3, q - 3), p);
            p.reset();

            if (lg == null)
                lg = new LinearGradient(radius, 0, radius, radius, getRandomColor(0), getRandomColor(0), Shader.TileMode.MIRROR);
            p.setShader(lg);
            p.setStrokeWidth(5);
            p.setFlags(Paint.ANTI_ALIAS_FLAG);
            p.setStyle(Style.STROKE);
            c.drawOval(new RectF(4, 4, q - 3, q - 3), p);
            p.reset();

            //写字
            p.setColor(Color.RED);
            p.setTextSize(ScreenSizeUtil.dp2px(20));
            p.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            c.drawText(text, 40, q - 10, p);
            p.reset();

            //删除按钮
            if (showDelete) {
                p.setColor(Color.RED);
                p.setStyle(Paint.Style.FILL);
                p.setAntiAlias(true);
                c.drawCircle(125, q - 130, 15, p);// 小圆
                p.reset();
            }

            //数据更新显示
            if (DataUpdate) {
                p.setColor(Color.GREEN);
                p.setShader(lg);
                p.setStyle(Paint.Style.FILL);
                p.setAntiAlias(true);
                c.drawCircle(30, q - 130, 15, p);// 小圆
                p.reset();

                p.setColor(Color.WHITE);
                p.setTextSize(20);
                p.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
                c.drawText("12", 20, q - 123, p);
                p.reset();
            }
        }

        public void draw(Canvas c, Paint paint) {
            if (mBitmap != null)
                c.drawBitmap(mBitmap, centerX - radius, centerY - radius - yOffset, paint);
            else {
                if (lg == null)
                    lg = new LinearGradient(radius, 0, radius, radius >> 1,
                            getRandomColor(0), getRandomColor(0), Shader.TileMode.MIRROR);
                paint.setFlags(Paint.ANTI_ALIAS_FLAG);
                paint.setShader(lg);
                paint.setStrokeWidth(2);
                paint.setStyle(Style.STROKE);
                float y = centerY - yOffset;
                c.drawOval(new RectF(centerX - radius, y - radius, centerX + radius, y + radius), paint);
                paint.reset();
            }
        }


    }

    //圆圈移动和碰撞线程
    private class DataUpThread extends Thread {
        private boolean mStop = Boolean.FALSE;

        @Override
        public void run() {
            super.run();
            float x = 0, x1 = 0, x2 = 0;
            float y = 0, y1 = 0, y2 = 0;
            int dis = 0;
            int radius = diameter >> 1;
            int i = mCount / 10 + 16;
            float t = 0;
            while (!mStop) {

                //与上下左右手机壁的碰撞
                for (DataHolder d : mData) {
                    t = (i != 0 ? (d.addX * i) : d.addX);
                    if (t > 0)
                        x = d.centerX + t + radius;
                    else
                        x = d.centerX + t - radius;
                    if (x <= 0) {
                        d.addX = Math.abs(d.addX);
                        d.centerX = radius;
                        startBreak = true;
                    } else if (x >= mWidth) {
                        d.addX = -Math.abs(d.addX);
                        d.centerX = mWidth - radius;
                        startBreak = true;
                    } else {
                        d.centerX = d.centerX + t;
                    }
                    t = (i != 0 ? d.addY * i : d.addY);
                    if (t > 0)
                        y = d.centerY + t + radius;
                    else
                        y = d.centerY + t - radius;
                    if (y <= 0) {
                        d.addY = Math.abs(d.addY);
                        d.centerY = radius;
                        startBreak = true;
                    } else if (y >= mHeight) {
                        d.addY = -Math.abs(d.addY);
                        d.centerY = mHeight - radius;
                        startBreak = true;
                    } else
                        d.centerY = d.centerY + t;

                }
                if (i > 0)
                    i--;


                //两个圆圈之间的碰撞
                if (startBreak) {
                    for (DataHolder d1 : mData) {
                        for (DataHolder d2 : mData) {
                            x1 = d1.centerX + d1.addX;
                            x2 = d2.centerX + d2.addX;
                            y1 = d1.centerY + d1.addY;
                            y2 = d2.centerY + d2.addY;
                            dis = (int) Math.floor(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
                            if (dis > 2 * radius - 1 && dis < 2 * radius + 1) {
                                d1.addX = -d1.addX;
                                d1.addY = -d1.addY;
                                d2.addX = -d2.addX;
                                d2.addY = -d2.addY;
                            } else if (dis <= 2 * radius) {
                                d1.addX = -d1.addX;
                                d1.addY = -d1.addY;
                                d2.addX = -d2.addX;
                                d2.addY = -d2.addY;
                            } else {
                                d1.addX = d1.addX;
                                d1.addY = d1.addY;
                                d2.addX = d2.addX;
                                d2.addY = d2.addY;
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(15);
                } catch (Exception e) {
                }
            }
        }

    }


    private int getRandomColor(int base) {
        Random r = getRandom();
        return Color.rgb(r.nextInt(0xff), r.nextInt(0xff), r.nextInt(0xff));
    }

    private Random getRandom() {
        Random r = null;
        if (randomCache != null)
            r = randomCache.get();
        if (r == null) {
            r = new Random();
            randomCache = new SoftReference<Random>(r);
        }
        return r;
    }

    private static SoftReference<Random> randomCache;


    Bitmap makeDst(int w, int h) {

        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFCCEEFF);
        c.drawOval(new RectF(2, 2, w - 2, h - 2), p);
        return bm;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // TODO Auto-generated method stub
            click = false;
            if ((e1.getX() - e2.getX() > FLING_MIN_DISTANCE) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                closeThread();
                setCount(6, 5);
                click = true;
            } else if ((e2.getX() - e1.getX() > FLING_MIN_DISTANCE) && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                closeThread();
                setCount(8, 5);
                click = true;
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // if (mPaused) return false;
            return true;
        }
    }

}
