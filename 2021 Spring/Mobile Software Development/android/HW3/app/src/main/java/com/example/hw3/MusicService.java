package com.example.hw3;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.util.Arrays;

public class MusicService extends Service {

    MusicPlayer musicPlayer;
    MusicPlayer effect1;
    MusicPlayer effect2;
    MusicPlayer effect3;
    private final IBinder iBinder= new MyBinder();

    public static final String COMPLETE_INTENT = "complete intent";
    public static final String MUSICNAME = "music name";
    public static final String PICTURENAME = "picture name";

    @Override
    public void onCreate() {
        musicPlayer = new MusicPlayer(this);
        effect1 = new MusicPlayer(this);
        effect2 = new MusicPlayer(this);
        effect3 = new MusicPlayer(this);
    }

    public int getPosition() {
        return musicPlayer.getPosition();
    }

    public int getDuration() {
        return musicPlayer.getDuration();
    }
    
    public void startMusic(int idx, int num) {
        if (num == 0) {
            musicPlayer.playMusic(idx);
        }
        else if (num == 1) {
            effect1.playMusic(idx);
        }
        else if (num == 2) {
            effect2.playMusic(idx);
        }
        else if (num == 3) {
            effect3.playMusic(idx);
        }
    }

    public void pauseMusic(int idx , int num){
        if (num == 0) {
            musicPlayer.pauseMusic(idx);
        }
        else if (num == 1) {
            effect1.pauseMusic(idx);
        }
        else if (num == 2) {
            effect2.pauseMusic(idx);
        }
        else if (num == 3) {
            effect3.pauseMusic(idx);
        }
    }

    public void resumeMusic(int idx, int num){
        if (num == 0) {
            musicPlayer.resumeMusic(idx);
        }
        else if (num == 1) {
            effect1.resumeMusic(idx);
        }
        else if (num == 2) {
            effect2.resumeMusic(idx);
        }
        else if (num == 3) {
            effect3.resumeMusic(idx);
        }
    }

    public void restartMusic(){

        musicPlayer.restartMusic();
        effect1.restartMusic();
        effect2.restartMusic();
        effect3.restartMusic();
    }

    public int getPlayingStatus(){

        return musicPlayer.getMusicStatus();
    }


    public void onUpdateMusicName(String musicname) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(MUSICNAME, musicname);
        sendBroadcast(intent);
    }

    public void onUpdatePicture(int picture) {
        Intent intent = new Intent(COMPLETE_INTENT);
        intent.putExtra(PICTURENAME, picture);
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }


    public class MyBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }
    }
}