package com.awen.codebase.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awen.codebase.R;

public class MultiTouchScaleFragment extends Fragment {
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int ROTATE = 3;

    private int mode = NONE;
    private double oldDist;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    /**
     * 起始点
     */
    private PointF start = new PointF();
    /**
     * 中点
     */
    private PointF mid = new PointF();
    private int width;
    private int height;
    private Context mContext;

    public MultiTouchScaleFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.act_multi_touch_scale, container);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        Display display = getActivity().getWindowManager().getDefaultDisplay(); // 获取手机屏幕大小
        width = display.getWidth();
        height = display.getHeight();
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        savedMatrix.set(matrix);
                        start.set(event.getX(), event.getY());
                        mode = DRAG;
                        Log.i("good", "x:" + event.getX() + "y:" + event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("good", "点起");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        Log.i("good", "两点放");
                        mode = NONE;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        if (oldDist > 10f) {
                            savedMatrix.set(matrix);
                            midPoint(mid, event);
                            mode = ZOOM;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {
                            matrix.set(savedMatrix);
                            matrix.postTranslate(event.getX() - start.x, event.getY()
                                    - start.y);
                        } else if (mode == ZOOM) {
                            double newDist = spacing(event);
                            if (newDist > 10f) {
                                matrix.set(savedMatrix);
                                float scale = Float.parseFloat(newDist/oldDist+"");
                                matrix.postScale(scale, scale, width / 2, height / 2);
                            }
                        } else if (mode == ROTATE) {
                        }
                        break;
                }

                view.setImageMatrix(matrix);
                return true;
            }

            /**
             * 两点之间的距离
             * @param event
             * @return
             */
            private double spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return Math.sqrt(x * x + y * y);
            }

            /**
             * 两点的中点
             * @param point
             * @param event
             */
            private void midPoint(PointF point, MotionEvent event) {
                float x = event.getX(0) + event.getX(1);
                float y = event.getY(0) + event.getY(1);
                point.set(x / 2, y / 2);
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
}
