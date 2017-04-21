package com.awen.codebase.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.codebase.R;
import com.awen.codebase.ui.FloatCycleView;
import com.awen.codebase.utils.ToastUtil;

public class FloatCycleViewActivity extends Activity {

    private FloatCycleView cycleView;
    private GestureDetector mGestureDetector;
    public static final int FLING_MIN_DISTANCE = 100;
    public static final int FLING_MIN_VELOCITY = 200;
    private Button dingYue;
    private ImageButton before;
    private ImageButton next;
    int[] i = {2, 5};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_play_acitivity);
        cycleView = (FloatCycleView) this.findViewById(R.id.SurfaceView);
        before = (ImageButton) this.findViewById(R.id.before);
        next = (ImageButton) this.findViewById(R.id.next);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        cycleView.setCount(10, 0);
        before.setOnClickListener(new ImageButtonOnClickListener());
        next.setOnClickListener(new ImageButtonOnClickListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class ImageButtonOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.before: {
                    ToastUtil.showToast(FloatCycleViewActivity.this, "Hello, The Code Project!", R.drawable.one, false);
                    break;
                }
                case R.id.next: {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.llToast));
                    ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
                    image.setImageResource(R.drawable.one);
                    TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
                    title.setText("Attention");
                    TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
                    text.setText("Hello, The Code Project!");
                    ToastUtil.showToast(FloatCycleViewActivity.this, layout, false);
                    break;
                }
                default:
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        cycleView.recylcle();
        cycleView.surfaceDestroyed(cycleView.getHolder());
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


}
