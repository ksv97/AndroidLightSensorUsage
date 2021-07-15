package com.example.playmusiconlightchange;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MediaService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("SERVICE", "Service started");

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rington);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("SERVICE", "Service destroyed");
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
    }
}
