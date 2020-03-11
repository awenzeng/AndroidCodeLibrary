package com.awen.codebase.common.utils;

import android.os.Environment;
import android.util.Log;

import com.awen.codebase.CodeBaseApp;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日志打印工具类
 * Created by AwenZeng on 2016/12/2.
 */
public class LogUtil {
    private static final String TAG = "AWEN-CODEBASE";
    public static Boolean isDebug = CodeBaseApp.DEBUG; /* 日志文件总开关*/
    public static Boolean isSaveLogToSD = false;// 日志写入文件开关
    public static String logFilePath = FileUtil.getAppFileSaveRootDri() + CodeBaseApp.getAppContext().getPackageName()+"/error_log";// 日志文件在sdcard中的路径
    private static int saveDays = 3;// sd卡中日志文件的最多保存天数
    public static String logFileName = "Push_Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    public static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    public static void w(String tag, Object msg) { // 警告信息
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text){
//        log(tag, text, 'e');
    }

    public static void d(String tag, String text){
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }

    public static void json(String text) {
        if (isDebug) {
            Logger.json(text);
        }
    }

    public static void show(String text) {
        if (isDebug) {
            Logger.i(text);
        }
    }

    /**
     * 根据tag, msg和等级，输出日志
     * @param tag
     * @param msg
     * @param level
     * @return void
     */
    public static void log(String tag, String msg, char level) {
        if (isDebug) {
            if ('e' == level) { // 输出错误信息
                Logger.e(msg);
            } else if ('w' == level) {
                Logger.w(msg);
            } else if ('d' == level) {
                Logger.d(msg);
            } else if ('i' == level) {
                Logger.i(msg);
            } else {
                Logger.v(msg);
            }
            if (isSaveLogToSD) {
                writeLogtoFile(String.valueOf(level), tag, msg);
                delFile();
            }
        }
    }

    /**
     * Android原生log
     * 根据tmsg和等级，输出日志
     * @param msg
     * @param level
     * @return void
     */
    public static void log(String msg, char level) {
        if (isDebug) {
            if ('e' == level) { // 输出错误信息
                Log.e(TAG,msg);
            } else if ('w' == level) {
                Log.w(TAG,msg);
            } else if ('d' == level) {
                Log.d(TAG,msg);
            } else if ('i' == level) {
                Log.i(TAG,msg);
            } else {
                Log.v(TAG,msg);
            }
            if (isSaveLogToSD) {
                writeLogtoFile(String.valueOf(level), TAG, msg);
                delFile();
            }
        }
    }

    /**
     * Android原生log
     * @param msg
     */
    public static void androidLog(String msg) {
        if (isDebug) {
            Log.i(TAG,msg);
        }
    }

    /**
     * Android原生log
     * @param msg
     */
    public static void androidLog(String tag,String msg) {
        if (isDebug) {
            Log.i(tag,msg);
        }
    }


    /**
     * 打开日志文件并写入日志
     * @return
     **/
    private static void writeLogtoFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Date nowtime = new Date();
            logfile.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            String needWriteFiel = logfile.format(nowtime);
            myLogSdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype
                    + "    " + tag + "    " + text;
            File dir = new File(logFilePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(logFilePath, needWriteFiel
                    + logFileName);
            try {
                FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write(needWriteMessage);
                bufWriter.newLine();
                bufWriter.close();
                filerWriter.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            LogUtil.log("tag", "SDK 无法使用", 'e');
        }

    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件  BSPay_Log.txt
        try {
            File file = new File(logFilePath);
            File[] files = file.listFiles();
            if (files != null) {
                for (File f1 : files) {
                    String fileName = f1.getName();
                    if (fileName.contains(logFileName)) {
                        String needDelFiel = fileName.substring(0, fileName.indexOf(logFileName));
                        logfile.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
                        Date fileDate = logfile.parse(needDelFiel);
                        //比较时间date.before()
                        boolean flag = getDateBefore().before(fileDate);
                        if (!flag) {
                            File logFile = new File(logFilePath, needDelFiel + logFileName);
                            if (logFile.exists()) {
                                logFile.delete();
                            }
                        }
                    }

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到现在时间前的几天日期，用来得到需要删除的日志文件名
     */
    private static Date getDateBefore() {
        Date nowtime = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowtime);
        now.set(Calendar.DATE, now.get(Calendar.DATE)
                - saveDays);
        return now.getTime();
    }

}
