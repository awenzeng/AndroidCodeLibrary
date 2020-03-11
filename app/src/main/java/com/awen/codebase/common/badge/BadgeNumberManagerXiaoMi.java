package com.awen.codebase.common.badge;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 小米机型的桌面角标设置管理类
 * Created by zlq on 2017 17/8/23 16:35.
 */

public class BadgeNumberManagerXiaoMi {

    public static void setBadgeNumber(Notification notification, int number) {
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setXiaomiBadgeNumber(Context context,int num) {
        NotificationManager notificationManager = (NotificationManager) context.
                getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("推送标题")
                .setContentText("我是推送内容")
                .setTicker("ticker")
                .setAutoCancel(true)
                .build();
        BadgeNumberManagerXiaoMi.setBadgeNumber(notification, num);
        notificationManager.notify(1000, notification);
        Toast.makeText(context, "设置桌面角标成功", Toast.LENGTH_SHORT).show();
    }
}
