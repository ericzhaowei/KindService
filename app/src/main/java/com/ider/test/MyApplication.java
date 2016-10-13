package com.ider.test;

import android.app.Application;
import android.os.Process;
import android.util.Log;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("tag", "application onCreate" + "/" + Process.myPid());
    }
}
