package com.awen.codebase.common.system;

import android.content.Context;

import com.awen.codebase.common.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Describe:Log日志辅助类
 * Created by AwenZeng on 2021/03/02
 */
public class LogcatHelper {
    private static LogcatHelper INSTANCE = null;
    public static String LOG_PATH;
    private LogDumper mLogDumper = null;
    private String mFilterStr;
    private Context mContext;
    public static final String TAG = "LogcatHelper";

    public static LogcatHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LogcatHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private LogcatHelper(Context context) {
        mContext = context;
        mFilterStr = context.getPackageName().toString();
        LOG_PATH = FileUtil.getFilePath(mContext);
        mLogDumper = new LogDumper(mFilterStr, LOG_PATH);
    }

    public void start() {
        if (mLogDumper != null) {
            try{
                mLogDumper.start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }


    private class LogDumper extends Thread {
        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        private String cmds = null;
        private String mFilterStr;
        private FileOutputStream out = null;

        public LogDumper(String filterStr, String dir){
            mFilterStr = filterStr;
            try {
                out = new FileOutputStream(new File(dir, FileUtil.getFileName() + ".log"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */
            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
//             cmds = "logcat -v time  | grep \"(" + mPID + ")\"";//打印所有日志信息
            // cmds = "logcat -s way";//打印标签过滤信息
//            cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";
//            cmds = "logcat -c;logcat -v time | grep" + filterStr;//打印所有日志信息
            cmds = "logcat -v time | grep" + filterStr;//打印所有日志信息
        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
//                    if (out != null && line.contains(mFilterStr)) {
//                        out.write((line + "\n").getBytes());
//                    }
                    if (out != null) {
                        out.write((line + "\n").getBytes());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
}