package com.ider.test;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import java.io.File;


public class LocalService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    public int getFileCount(String path) {
        Log.i("tag", "本地服务所在的进程ID为  " + Process.myPid());
        return new File(path).list().length;
    }


}
