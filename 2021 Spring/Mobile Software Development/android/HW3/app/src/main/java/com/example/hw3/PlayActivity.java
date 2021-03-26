package com.example.hw3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class PlayActivity extends AppCompatActivity {

    TextView name;
    ImageView image;
    Button play;

    MusicService musicService;

    MusicCompletionReceiver musicCompletionReceiver;
    Intent startMusicServiceIntent;
    boolean isBound = false;
    boolean isInitialized = false;
    boolean run = true;
    int[] music;
    MyAsyncTask task1;

    public static final String INITIALIZE_STATUS = "intialization status";
    public static final String MUSIC_PLAYING = "music playing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        name = (TextView) findViewById(R.id.name);
        play= (Button) findViewById(R.id.playPause);
        image = (ImageView) findViewById((R.id.image));
        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            name.setText(savedInstanceState.getString(MUSIC_PLAYING));
        }

        startMusicServiceIntent= new Intent(this, MusicService.class);
        if(!isInitialized){
            startService(startMusicServiceIntent);
            isInitialized= true;
        }

        musicCompletionReceiver = new MusicCompletionReceiver(this);
        Intent i = getIntent();
        music = i.getIntArrayExtra("music");
        switch (music[0]) {
            case 0:
                name.setText("Go Tech Go");
                image.setImageResource(R.drawable.football);
                break;
            case 1:
                name.setText("Mario");
                image.setImageResource(R.drawable.mushroom);
                break;
            case 2:
                name.setText("Tetris");
                image.setImageResource(R.drawable.tetris_logo);
                break;
        }
        task1 = new MyAsyncTask(music);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (isBound) {
            if (id == R.id.playPause){
                switch (musicService.getPlayingStatus()){
                    case 0:
                        musicService.startMusic(music[0], 0);
                        System.out.println("before async " + Arrays.toString(music));
                        task1.execute();
                        play.setText("Pause");
                        break;
                    case 1:
                        musicService.pauseMusic(music[0], 0);
                        musicService.pauseMusic(music[1], 1);
                        musicService.pauseMusic(music[2], 2);
                        musicService.pauseMusic(music[3], 3);
                        play.setText("Play");
                        break;
                    case 2:
                        musicService.resumeMusic(music[0], 0);
                        musicService.resumeMusic(music[1], 1);
                        musicService.resumeMusic(music[2], 2);
                        musicService.resumeMusic(music[3], 3);
                        break;
                }
            }
            else {
                task1.resetPlayed();
                musicService.restartMusic();
            }

        }
    }


    public void updateName(String musicName) {
        if (musicName != null) {
            name.setText(musicName);
        }
    }

    public void updatePicture(int idx) {


        if (idx == -1) {
            idx = music[0];
        }
        if (idx == 0) {
            image.setImageResource(R.drawable.football);
        }
        if (idx == 1) {
            image.setImageResource(R.drawable.mushroom);
        }
        if (idx == 2) {
            image.setImageResource(R.drawable.tetris_logo);
        }
        if (idx == 3) {
            image.setImageResource(R.drawable.cheer);
        }
        if (idx == 4) {
            image.setImageResource(R.drawable.clap);
        }
        if (idx == 5) {
            image.setImageResource(R.drawable.hokies);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isInitialized && !isBound){
            bindService(startMusicServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        }

        registerReceiver(musicCompletionReceiver, new IntentFilter(MusicService.COMPLETE_INTENT));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(isBound){
            unbindService(musicServiceConnection);
            isBound= false;
        }

        unregisterReceiver(musicCompletionReceiver);
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(INITIALIZE_STATUS, isInitialized);
        outState.putString(MUSIC_PLAYING, name.getText().toString());
        super.onSaveInstanceState(outState);
    }


    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MyBinder binder = (MusicService.MyBinder) iBinder;
            musicService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
            isBound = false;
        }
    };

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        private int[] values;
        private boolean[] played = {false, false, false};
        public MyAsyncTask(int[] v){
            super();
            values = v;
            if (values[4] == -1){
                played[0] = true;
            }
            if (values[5] == -1){
                played[1] = true;
            }
            if (values[6] == -1){
                played[2] = true;
            }
        }

        public void resetPlayed() {
            played[0] = false;
            played[1] = false;
            played[2] = false;
        }

        @Override
        protected Void doInBackground(Integer ... params) {
            while(run){
                try{
                    //checking if the asynctask has been cancelled, end loop if so
                    if(isCancelled()) break;

                    Thread.sleep(1000);
                    for (int i = 0; i < 3; i++) {
                        int startTime = (int) (musicService.getDuration() * ((double) values[i + 3] / 100));
                        if (values[i+1] != -1 && !played[i] && musicService.getPosition() > startTime) {
                            int j = i + 1;
                            System.out.println(values[i+1] + ", " + j);
                            musicService.startMusic(values[i+1], i + 1);
                            played[i] = true;
                        }
                    }

                    if (played[0] && played[1] && played[2]){
                        break;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
}