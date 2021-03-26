package com.example.hw3;
import android.media.MediaPlayer;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {

    MediaPlayer player;
    int currentPosition = 0;
    private int musicStatus = 0;//0: before playing, 1 playing, 2 paused
    int musicIndex = -1;
    int musicNum = -1;
    private MusicService musicService;

    static final int[] MUSICPATH = new int[]{
            R.raw.gotechgo,
            R.raw.mario,
            R.raw.tetris,
            R.raw.cheering,
            R.raw.clapping,
            R.raw.lestgohokies
    };

    static final String[] MUSICNAME = new String[]{
            "Go Tech Go",
            "Mario",
            "Tetris",
            "Cheering",
            "Clapping",
            "Lets Go Hokies"
    };

    public MusicPlayer(MusicService service) {
        this.musicService = service;
    }

    public int getPosition(){
        if (player != null){
            return player.getCurrentPosition(); //position in milliseconds
        }
        return -1;
    }

    public int getDuration(){
        if (player != null){
            return player.getDuration(); //duration in milliseconds
        }
        return -1;
    }

    public int getMusicStatus() {
        return musicStatus;
    }

    public void playMusic(int idx) {
        if (idx != -1) {
            player = MediaPlayer.create(this.musicService, MUSICPATH[idx]);
            musicNum = idx;
            player.start();
            player.setOnCompletionListener(this);
            if (idx >= 0 && idx <=2) {
                musicIndex = idx;
                musicService.onUpdateMusicName(MUSICNAME[musicIndex]);
                musicService.onUpdatePicture(-1);
            }

            if (musicNum >=3 && musicNum <= 5)
                musicService.onUpdateMusicName(null);
                musicService.onUpdatePicture(musicNum);
            musicStatus = 1;

        }
    }

    public void pauseMusic(int idx) {
        if(player!= null && player.isPlaying()){
            player.pause();
            if (idx >= 0 && idx <=2 && musicIndex >= 0 && musicIndex <=2 && idx != musicIndex){
                playMusic(idx);
            }
            else {
                currentPosition = player.getCurrentPosition();
                musicStatus = 2;
            }
        }
    }

    public void resumeMusic(int idx) {
        if(player!= null){
            player.seekTo(currentPosition);
            player.start();
            musicStatus=1;
        }
    }

    public void restartMusic() {
        if(player!= null){
            player.seekTo(0);
            if (musicNum >= 0 && musicNum <= 2) {
                player.start();
                musicStatus=1;
            }
            else {
                player.pause();
                musicStatus=0;
                musicService.onUpdatePicture(-1);
                musicService.onUpdateMusicName(null);
            }

        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        player.release();
        if (musicNum >=3 && musicNum <= 5) {
            musicService.onUpdatePicture(-1);
            musicService.onUpdateMusicName(null);
        }

        musicStatus = 0;
        player= null;
    }



}