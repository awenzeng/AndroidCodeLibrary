package com.awen.codebase.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.codebase.CodeBaseApp;
import com.awen.codebase.R;
import com.awen.codebase.adapter.MainAdapter;
import com.awen.codebase.badge.BadgeNumberManager;
import com.awen.codebase.badge.BadgeNumberManagerXiaoMi;
import com.awen.codebase.badge.MobileBrand;
import com.awen.codebase.model.AnnotationReflectModel;
import com.awen.codebase.service.ChangeService;

public class MainActivity extends Activity {
    private TextView myPlaceTextView;
    private ListView listView;
    private ImageView animationImageView;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private AnimationDrawable animationDrawable;
    private String[] iStrings = {"FloatCycleView", "GroupsActivity",
            "FragmentsActivity",
            "AnimationActivity", "ProgressBarsActivity", "SwitchButtoonActivity", "CreditRoundActivity", "SwipeCardActivity", "KeybordActivity", "XRecycleView","VerticalViewPagerActivity","InfiniteViewActivity"};
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //播放gif动画
            playAnimation();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        AnnotationReflectModel.invokeAnnotation();//获取注解
        AnnotationReflectModel.invokeReflect();//调用反射
        showBadgeIcon();
        handler.sendEmptyMessageDelayed(0, 300);
    }


    private void initView() {
        listView = (ListView) this.findViewById(R.id.listView);
        listView.addHeaderView(initHeadView());
        MainAdapter myAdapter = new MainAdapter(this, iStrings);
        listView.setAdapter(myAdapter);
    }

    private View initHeadView() {
        View view = getLayoutInflater().inflate(R.layout.headview_main, null);
        animationImageView = (ImageView) view.findViewById(R.id.ImageView01);
        myPlaceTextView = (TextView) view.findViewById(R.id.myplace);
        return view;
    }

    private void startService() {
        //  启动一个服务
        Intent intent = new Intent(this, ChangeService.class);
        pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, 0, 15000, pendingIntent);
    }

    /**
     * 在界面上加指引层
     */
    private void addWindow() {
        final WindowManager windowManager = (WindowManager) CodeBaseApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
        final ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.guide_recommend);
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowManager.addView(view, windowParams);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(view);
            }
        });
    }

    /**
     * 通知栏显示View
     */
    private void showNotify() {
        Notification notice = new Notification();
        notice.icon = R.drawable.icon;
        notice.tickerText = "您有一条新的信息";
        notice.defaults = Notification.DEFAULT_SOUND;
        notice.when = 10L;
//        notice.setLatestEventInfo(this, "通知", "开会啦", PendingIntent.getActivity(this, 0, new Intent(this, FloatCycleViewActivity.class), 0));//即将跳转页面，还没跳转
        NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        manager.notify(0, notice);
    }

    /**
     * 快捷图标上显示红点
     */
    private void showBadgeIcon(){
        //设置应用在桌面上显示的角标
        if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
            BadgeNumberManager.from(MainActivity.this).setBadgeNumber(10);
            Toast.makeText(MainActivity.this, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
        } else {
            BadgeNumberManagerXiaoMi.setXiaomiBadgeNumber(MainActivity.this,10);
        }
    }

    /**
     * 清理快捷图标上的红点
     */
    private void clearBadgeIcon(){
        //设置应用在桌面上显示的角标,小米机型只要用户点击了应用图标进入应用，会自动清除掉角标
        if (!Build.MANUFACTURER.equalsIgnoreCase(MobileBrand.XIAOMI)) {
            BadgeNumberManager.from(MainActivity.this).setBadgeNumber(0);
            Toast.makeText(MainActivity.this, "清除桌面角标成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * gif图片播放
     */
    private void playAnimation() {
        animationDrawable = (AnimationDrawable) animationImageView.getDrawable();
        animationDrawable.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
