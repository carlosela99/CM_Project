package com.example.reclaimportugal;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

    private static MediaPlayer mediaPlayer;
    private static Boolean SoundOn = null;

    private static void Load(Context context){
        if (SoundOn == null){
            SoundOn = Local.getSound(context);
        }
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context, R.raw.sound_click);
            mediaPlayer.setVolume(100,100);
        }
    }

    public static boolean IsSoundOn(Context context){
        Load(context);
        return SoundOn;
    }

    public static void ClickSound(Context context){
        Load(context);

        if (SoundOn){
            mediaPlayer.start();
        }
    }

    public static boolean SoundOnOff(Context context){
        Load(context);
        SoundOn = !SoundOn;
        ClickSound(context);
        Local.setSound(SoundOn, context);
        return SoundOn;
    }
}
