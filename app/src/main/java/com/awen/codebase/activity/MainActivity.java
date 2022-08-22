package com.awen.codebase.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.awen.codebase.CodeBaseApp;
import com.awen.codebase.R;
import com.awen.codebase.activity.adapter.MainAdapter;
import com.awen.codebase.common.badge.BadgeNumberManager;
import com.awen.codebase.common.badge.BadgeNumberManagerXiaoMi;
import com.awen.codebase.common.badge.MobileBrand;
import com.awen.codebase.common.base.BaseActivity;
import com.awen.codebase.common.utils.FPSFrameCallback;
import com.awen.codebase.common.utils.MMKVDataUtil;
import com.awen.codebase.model.algorithm.CommonSort;
import com.awen.codebase.model.algorithm.MergeSort;
import com.awen.codebase.model.algorithm.SortTestHelper;
import com.awen.codebase.model.classload.AnnotationReflectModel;
import com.awen.codebase.model.classload.ClassLoadModel;
import com.awen.codebase.model.thread.SynchronizedTest;
import com.awen.codebase.service.AIDLService;
import com.awen.codebase.service.AIDLServiceConnection;
import com.awen.codebase.service.WorkService;
import com.awen.messagebus.IHandleMessage;
import com.awen.messagebus.MessageBus;

import static com.awen.codebase.model.algorithm.CommonSort.bubbleSort;
import static com.awen.codebase.model.algorithm.CommonSort.shellSort;

public class MainActivity extends BaseActivity {
    private ListView listView;
    private ImageView animationImageView;
    private AnimationDrawable animationDrawable;
    private FPSFrameCallback mFpsFrameCallback;
    private String[] iStrings = {"FloatCycleViewActivity", "GroupsActivity", "FragmentsActivity", "AnimationActivity", "ProgressBarsActivity","CircleProgressActivity",
            "SwitchButtonActivity","CreditRoundActivity", "SwipeCardActivity", "KeybordActivity", "XRecyleviewActivity",
            "VerticalViewPagerActivity","InfiniteViewActivity","BannerActivity","DrawAnimActivity","MaterialDesignActivity",
            "MarqueeTextActivity","FlexboxLayoutActivity","SVGActivity","WebViewActivity","VueFrameActivity",
            "LottieActivity","VideoAnimPlayerActivity","LogcatActivity","LargeImageViewActivity","ViewPagerAnimActivity","MediaPlayerActivity","DKVideoPlayerActivity","ARouterActivity"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContentView(R.layout.activity_main);
        hideLeftRightLayout();
        setBarTitle("AWENCODEBASE");
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
        MessageBus.getDefault().handleMessage(new IHandleMessage() {
            @Override
            public void handleMessage(Message msg) {
                playAnimation();
            }
        });
        myAdapter.setItemClickListener(new MainAdapter.ItemClickListener() {
            @Override
            public void onClick(int position, String data) {
               String arouterPath = "/main/" + iStrings[position];
               ARouter.getInstance().build(arouterPath).navigation(MainActivity.this);
            }
        });
    }

    private void initData(){
        showBadgeIcon();
        MessageBus.getDefault().sendEmptyMessageDelayed(0,300);
        new ClassLoadModel();
        testAnnotationReflect();
//        addFPSMornitor();
//        testAIDL();
//        testSynchronized();
        testAlgorithm();
        saveMMKVTest();
    }

    private void saveMMKVTest(){
//        MMKVDataUtil.getInstance().getWithIdMMKV().encode(MMKVDataUtil.KEY_SAVE_PATTERN_JSON,"sjkdfksdj");
//        MMKVDataUtil.getInstance().getDefaultMMKV().decodeString(MMKVDataUtil.KEY_SAVE_PATTERN_JSON,"demo");
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

    private void addFPSMornitor(){
        Display display = getWindowManager().getDefaultDisplay();
        mFpsFrameCallback = new FPSFrameCallback(System.nanoTime(),display.getRefreshRate());
        Choreographer.getInstance().postFrameCallback(mFpsFrameCallback);
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
        SynchronizedTest test = new SynchronizedTest();
        SynchronizedTest.TestThread threadA =  test.new TestThread("线程A");
        SynchronizedTest.TestThread threadB =  test.new TestThread("线程B");
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
     * 测试算法
     */
    private void testAlgorithm(){
        int N = 10;
        CommonSort.insertSort(SortTestHelper.generateRandomArray(N, 0, 10));
        shellSort(SortTestHelper.generateRandomArray(N, 0, 10));
        bubbleSort(SortTestHelper.generateRandomArray(N, 0, 10));
        SortTestHelper.printResult(MergeSort.mergeSort(SortTestHelper.generateRandomArray(N, 0, 10)));
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
        Choreographer.getInstance().removeFrameCallback(mFpsFrameCallback);
    }
}
