package com.ider.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import java.io.File;


public class RemoteService extends Service {

    IFileCount.Stub mBinder = new IFileCount.Stub() {
        @Override
        public int fileCount(String path) throws RemoteException {
            String[] names = new File(path).list();
            Log.i("tag", "远程服务运行所在进程ID为  " + Process.myPid());
            return names.length;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
