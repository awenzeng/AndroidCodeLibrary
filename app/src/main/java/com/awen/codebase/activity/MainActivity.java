package com.awen.codebase.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.awen.codebase.CodeBaseApp;
import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.MainAdapter;
import com.awen.codebase.common.badge.BadgeNumberManager;
import com.awen.codebase.common.badge.BadgeNumberManagerXiaoMi;
import com.awen.codebase.common.badge.MobileBrand;
import com.awen.codebase.model.AnnotationReflectModel;
import com.awen.codebase.model.ClassLoadModel;
import com.awen.codebase.model.SynchronizedTestModel;
import com.awen.codebase.service.AIDLService;
import com.awen.codebase.service.AIDLServiceConnection;
import com.awen.codebase.service.WorkService;
import com.awen.messagebus.MessageBus;

public class MainActivity extends Activity {
    private ListView listView;
    private ImageView animationImageView;
    private AnimationDrawable animationDrawable;
    private String[] iStrings = {"FloatCycleViewActivity", "GroupsActivity", "FragmentsActivity", "AnimationActivity", "ProgressBarsActivity",
            "SwitchButtoonActivity","CreditRoundActivity", "SwipeCardActivity", "KeybordActivity", "XRecycleView",
            "VerticalViewPagerActivity","InfiniteViewActivity","BannerActivity","DrawAnimActivity","MaterialDesignActivity","MarqueeTextActivity","FlexboxLayoutActivity","SVGActivity","WebViewActivity","VueFrameActivity"};

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
        initData();

    }


    private void initView() {
        listView = this.findViewById(R.id.listView);
        View view = getLayoutInflater().inflate(R.layout.headview_main, null);
        animationImageView =  view.findViewById(R.id.ImageView01);
        listView.addHeaderView(view);
        MainAdapter myAdapter = new MainAdapter(this, iStrings);
        listView.setAdapter(myAdapter);
        myAdapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onClick(int position, String data) {
               Intent intent = null;
                switch (data){
                    case "FloatCycleViewActivity":
                        intent = new Intent(MainActivity.this,FloatCycleViewActivity.class);
                        break;
                    case "GroupsActivity":
                        intent = new Intent(MainActivity.this,GroupsActivity.class);
                        break;
                    case "FragmentsActivity":
                        intent = new Intent(MainActivity.this,FragmentsActivity.class);
                        break;
                    case "AnimationActivity":
                        intent = new Intent(MainActivity.this,AnimationActivity.class);
                        break;
                    case "ProgressBarsActivity":
                        intent = new Intent(MainActivity.this,ProgressBarsActivity.class);
                        break;
                    case "SwitchButtoonActivity":
                        intent = new Intent(MainActivity.this,SwitchButtonActivity.class);
                        break;
                    case "CreditRoundActivity":
                        intent = new Intent(MainActivity.this,CreditRoundActivity.class);
                        break;
                    case "SwipeCardActivity":
                        intent = new Intent(MainActivity.this,SwipeCardActivity.class);
                        break;
                    case "KeybordActivity":
                        intent = new Intent(MainActivity.this,KeybordActivity.class);
                        break;
                    case "XRecycleView":
                        intent = new Intent(MainActivity.this,XRecyleviewActivity.class);
                        break;
                    case "VerticalViewPagerActivity":
                        intent = new Intent(MainActivity.this,VerticalViewPagerActivity.class);
                        break;
                    case "InfiniteViewActivity":
                        intent = new Intent(MainActivity.this,InfiniteViewActivity.class);
                        break;
                    case "BannerActivity":
                        intent = new Intent(MainActivity.this,BannerActivity.class);
                        break;
                    case "DrawAnimActivity":
                        intent = new Intent(MainActivity.this,DrawAnimActivity.class);
                        break;
                    case "MaterialDesignActivity":
                        intent = new Intent(MainActivity.this,MaterialDesignActivity.class);
                        break;
                    case "MarqueeTextActivity":
                        intent = new Intent(MainActivity.this,MarqueeTextActivity.class);
                        break;
                    case "FlexboxLayoutActivity":
                        intent = new Intent(MainActivity.this, FlexboxLayoutActivity.class);
                        break;
                    case "SVGActivity":
                        intent = new Intent(MainActivity.this, SVGActivity.class);
                        break;
                    case "WebViewActivity":
                        intent = new Intent(MainActivity.this, WebViewActivity.class);
                        break;
                    case "VueFrameActivity":
                        intent = new Intent(MainActivity.this, VueFrameActivity.class);
                        break;
                    default:
                        break;
                }
               startActivity(intent);
                Message message = new Message();
                message.obj = data;
                MessageBus.getDefault().sendMessage(message);

                Message msg = new Message();
                msg.obj = "延迟消息："+data;
                MessageBus.getDefault().sendMessageDelayed(msg,1500);
            }
        });
    }

    private void initData(){
        showBadgeIcon();
        handler.sendEmptyMessageDelayed(0, 300);
        new ClassLoadModel();

        testAnnotationReflect();
//        testAIDL();
//        testSynchronized();
    }

    /**
     * 测试注解和反射
     */
    private void testAnnotationReflect(){
        //获取注解
        AnnotationReflectModel.invokeAnnotation();
        //调用反射
        AnnotationReflectModel.invokeReflect();
    }

    /**
     * 测试AIDL跨进程通信
     */
    private void testAIDL(){
        Intent intent = new Intent(this, AIDLService.class);
        AIDLServiceConnection connection = new AIDLServiceConnection();
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    /**
     * 测试线程同步
     */
    private void testSynchronized(){
        SynchronizedTestModel test = new SynchronizedTestModel();
        SynchronizedTestModel.TestThread threadA =  test.new TestThread("线程A");
        SynchronizedTestModel.TestThread threadB =  test.new TestThread("线程B");
        threadA.start();
        threadB.start();
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        test.isClose = true;
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
        stopService(new Intent(this, WorkService.class));
    }
}
