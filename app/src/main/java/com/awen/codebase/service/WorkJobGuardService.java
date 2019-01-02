package com.awen.codebase.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Describe:JobScheduler调度守护进程
 * Created by AwenZeng on 2018/12/26
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class WorkJobGuardService extends JobService {
    private final int jobWakeUpId = 1;
    private JobScheduler jobScheduler;
    private JobInfo.Builder builder;

    private static final int INTERVAL_TIME = 5 * 1000;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        builder = new JobInfo.Builder(jobWakeUpId, new ComponentName(this, WorkJobGuardService.class));
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            builder.setMinimumLatency(INTERVAL_TIME);
            builder.setOverrideDeadline(INTERVAL_TIME * 2);
            builder.setBackoffCriteria(INTERVAL_TIME, JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(INTERVAL_TIME);
        }
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY).setPersisted(true);
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("SmartHome", "轮询-onStartJob");

        boolean isRunning = isServiceRunning(WorkService.class.getName());
        if (!isRunning) {//发现服务进程已被杀，重新启动进程
            startService(new Intent(this, WorkService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    /**
     * 判断服务是否在运行
     * @param serviceName
     * @return
     */
    private boolean isServiceRunning(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
