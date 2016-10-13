package com.ider.test;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class MusicService extends Service {

    File[] files;
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("tag", "MusicService onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("tag", "MusicService onCreate");
        files = new File(String.format("%s/netease/cloudmusic/Music/", Environment.getExternalStorageDirectory())).listFiles();
        player = new MediaPlayer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("tag", "MusicService onStartCommand");
        try {
            player.setDataSource(files[0].getAbsolutePath());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
