package com.example.reclaimportugal;

import android.content.Context;
import android.media.MediaPlayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;


public class BackgroundMusic extends Service {

    private static MediaPlayer mediaPlayer;
    public static boolean IsPlaying;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        IsPlaying = Local.getMusic(getApplicationContext());

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(50, 50);
    }

    public static boolean PlayPause(Context context){
        IsPlaying = !IsPlaying;
        PlayPause();
        Local.setMusic(IsPlaying, context);
        return IsPlaying;
    }

    private static void PlayPause(){
        if (IsPlaying){
            mediaPlayer.start();
        }
        else{
            mediaPlayer.pause();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        PlayPause();
        return startId;
    }
    public void onStart(Intent intent, int startId) {
    }
    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    @Override
    public void onLowMemory() {
    }
}
