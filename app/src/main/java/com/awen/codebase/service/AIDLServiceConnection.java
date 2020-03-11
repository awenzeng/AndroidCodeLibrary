package com.awen.codebase.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.awen.codebase.IMainService;
import com.awen.codebase.common.utils.LogUtil;

/**
 * Created by Administrator on 2017/12/11.
 */

public class AIDLServiceConnection implements ServiceConnection {

    private IMainService mService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
             mService = IMainService.Stub.asInterface(service);
             try{
                 mService.start("Android IPC机制，Bindler跨进程通信~~~~~~~");
             }catch (RemoteException e){
                 e.printStackTrace();
             }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        LogUtil.androidLog("AIDL服务断开连接");
    }
}
