package com.ider.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ider.test.databinding.ActivityImageBinding;


public class MainActivity extends Activity {
    String TAG = "MainActivity";
    public static boolean flag = false;
    LocalService localService;
    IFileCount remoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_image);

        binding.buttonLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocalService.class);
                bindService(intent, localConnection, Context.BIND_AUTO_CREATE);
            }
        });

        binding.buttonRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RemoteService.class);
                bindService(intent, remoteConnection, Context.BIND_AUTO_CREATE);
            }
        });

        binding.localFileCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localService == null) {
                    Toast.makeText(MainActivity.this, "请先绑定本地服务", Toast.LENGTH_SHORT).show();
                } else {
                    int fileCount = localService.getFileCount(Environment.getExternalStorageDirectory().getAbsolutePath());
                    Log.i("tag", "客户端所在进程ID为  " + Process.myPid());
                    Log.i("tag", "文件个数为  " + fileCount);
                }
            }
        });

        binding.remoteFileCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (remoteService == null) {
                    Toast.makeText(MainActivity.this, "请先绑定远程服务", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int fileCount = remoteService.fileCount(Environment.getExternalStorageDirectory().getAbsolutePath());
                        Log.i("tag", "客户端所在进程ID为  " + Process.myPid());
                        Log.i("tag", "文件个数为  " + fileCount);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.startMusicService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                startService(intent);
            }
        });

    }

    ServiceConnection localConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("tag", "bind LOCAL service success");
            LocalService.MyBinder binder = (LocalService.MyBinder) iBinder;
            localService = binder.getService();
            Toast.makeText(MainActivity.this, "本地服务绑定成功", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("tag", "bind service failed");
        }
    };


    ServiceConnection remoteConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("tag", "bind REMOTE service success");
            remoteService = IFileCount.Stub.asInterface(iBinder);
            Toast.makeText(MainActivity.this, "远程服务绑定成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("tag", "onDestroy");
        if (localService != null) {
            unbindService(localConnection);
        }
        if (remoteService != null) {
            unbindService(remoteConnection);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Process.killProcess(Process.myPid());
    }
}
