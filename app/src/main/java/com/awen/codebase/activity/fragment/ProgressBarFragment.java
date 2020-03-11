package com.awen.codebase.activity.fragment;


import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.codebase.R;
import com.awen.codebase.common.ui.LoadingCircleView;
import com.awen.codebase.common.widget.Down360LoadingView;
import com.awen.codebase.common.widget.Down360ViewGroup;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ProgressBarFragment extends Fragment implements Down360LoadingView.OnProgressStateChangeListener {
    private static final int QR_WIDTH = 400;
    private static final int QR_HEIGHT = 400;
    private TextView persent;
    private ImageView img_progress;
    private ImageView show2codeImg;
    private LoadingCircleView loadingCircleView;
    private int num = 100;
    private boolean minus = true;
    private float progess = 0;
    private Bitmap bmp;
    private Bitmap bmp1;
    private Bitmap bmp2;
    private TextView text;
    private Down360ViewGroup down360ViewGroup;
    //模拟进度的计时器
    private Timer timer;
    private int progress;
    private static final String TAG = ProgressBarFragment.class.getSimpleName();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    progess += 0.01;
                    if (progess > 1) {
                        progess = 1;
                    }
                    if (progess < 0.5) {
                        img_progress.setImageBitmap(getRoundedCornerBitmap(bmp, progess));
                    } else if (progess > 0.5 && progess < 0.8) {
                        img_progress.setImageBitmap(getRoundedCornerBitmap(bmp1, progess));
                    } else {
                        img_progress.setImageBitmap(getRoundedCornerBitmap(bmp2, progess));
                    }
                    text.setText((int) (progess * 100) + "%");
                    break;
                case 2:
                    img_progress.setImageBitmap(getRoundedCornerBitmap(bmp, (float) 0.8));
                    break;
                    default:
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progressbar, container);
        init(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init(View view) {
        img_progress = (ImageView) view.findViewById(R.id.img_progress);
        show2codeImg = (ImageView) view.findViewById(R.id.show2code);
        persent = (TextView) view.findViewById(R.id.persent);
        text = (TextView) view.findViewById(R.id.text);
        Button button = (Button) view.findViewById(R.id.button);
        loadingCircleView = view.findViewById(R.id.loadingCircleView);
        down360ViewGroup = (Down360ViewGroup) view.findViewById(R.id.down_loading_viewgroup);

        // 这里是3个不同状态的时候的图片资源
        Resources res = getResources();
        bmp = BitmapFactory.decodeResource(res, R.drawable.taskmanager_circle_min);
        bmp1 = BitmapFactory.decodeResource(res, R.drawable.taskmanager_circle_mid);
        bmp2 = BitmapFactory.decodeResource(res, R.drawable.taskmanager_circle_full);



        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 点击可以重复播放
                if (progess == 0) {
                    persent.post(new LoopRunnable());
                } else {
                    progess = 0;
                    img_progress.setImageBitmap(getRoundedCornerBitmap(bmp, progess));
                }

            }
        });

        createImage();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!down360ViewGroup.isStop()) {
                            if (down360ViewGroup.getStatus() == Down360LoadingView.Status.Load) {
                                progress++;
                                Log.d(TAG, "DownActivity:" + progress);
                                down360ViewGroup.setProgress(progress);
                                loadingCircleView.setProgress(progress,true);
                            }
                        }
                    }
                });
            }
        }, 0, 500);


    }


    // 这里把bitmap图片截取出来pr是指截取比例
    public static Bitmap getRoundedCornerBitmap(Bitmap bit, float pr) {

        Bitmap bitmap = Bitmap.createBitmap(bit.getWidth(), bit.getHeight(), Config.ARGB_8888);
        Rect rect = new Rect(1, 1, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0, 0, 0, 0);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 把图片按中心旋转180度，去掉的话就是从上往下显示
        canvas.rotate(-180, bit.getWidth() / 2, bit.getHeight() / 2);
        // 截取的区域，从左上角开始（0，0），根据传入比例得出显示高度
        canvas.clipRect(0, 0, bit.getWidth(), pr * bit.getHeight());
        canvas.drawBitmap(bit, 0, 0, paint);
        return bitmap;

    }

    // 控制时间，对每一次刷新进行延时
    class LoopRunnable implements Runnable {
        @Override
        public void run() {
            if (num == 100 || minus) // 一开始
            {
                minus = true;
                num--;
            }
            if (num == -1 || !minus) {
                minus = false;
                num++;
            }
            if (num < 101) {
                handler.sendEmptyMessage(1);
                if (progess != 0.5) {
                    persent.post(this);
                }

            }
        }
    }

    /**
     * 生成二维码图片
     */
    private void createImage() {
        try {
            QRCodeWriter writer = new QRCodeWriter();// 需要引入core包
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode("http://www.yinyuetai.com/", BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            show2codeImg.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        timer.cancel();
        Toast.makeText(getActivity(), "下载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        progress = 0;
    }

    @Override
    public void onContinue() {
    }

    @Override
    public void onStopDown() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
