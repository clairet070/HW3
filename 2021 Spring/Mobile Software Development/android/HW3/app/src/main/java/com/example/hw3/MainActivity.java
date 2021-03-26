package com.example.hw3;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    Button play;
    Spinner musicSpinner;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    SeekBar bar1;
    SeekBar bar2;
    SeekBar bar3;

    static int music[];
    boolean in = true;
    boolean isInitialized = false;
    public static final String INITIALIZE_STATUS = "intialization status";
    public static final String MUSIC_PLAYING = "music playing";

    AdapterView.OnItemSelectedListener musicListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            if (item.equals("Go Tech Go")) {
                music[0] = 0;
            }
            else if (item.equals("Mario")){
                music[0] = 1;
            }
            else if (item.equals("Tetris")){
                music[0] = 2;
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    };

    AdapterView.OnItemSelectedListener effect1Listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            if (item.equals("Cheering")) {
                music[1] = 3;
            }
            else if (item.equals("Clapping")){
                music[1] = 4;
            }
            else if (item.equals("Lets Go Hokies")){
                music[1] = 5;
            }
            else if (item.equals("None")){
                music[1] = -1;
                bar1.setProgress(0);
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    };

    AdapterView.OnItemSelectedListener effect2Listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            if (item.equals("Cheering")) {
                music[2] = 3;
            }
            else if (item.equals("Clapping")){
                music[2] = 4;
            }
            else if (item.equals("Lets Go Hokies")){
                music[2] = 5;
            }
            else if (item.equals("None")){
                music[2] = -1;
                bar1.setProgress(0);
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    };

    AdapterView.OnItemSelectedListener effect3Listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = parent.getItemAtPosition(position).toString();
            if (item.equals("Cheering")) {
                music[3] = 3;
            }
            else if (item.equals("Clapping")){
                music[3] = 4;
            }
            else if (item.equals("Lets Go Hokies")){
                music[3] = 5;
            }
            else if (item.equals("None")){
                music[3] = -1;
                bar1.setProgress(0);
            }
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // do nothing
        }
    };

    SeekBar.OnSeekBarChangeListener barListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            if (seekBar.getId() == R.id.seekBar1){
                music[4] = progress;
            }
            if (seekBar.getId() == R.id.seekBar2){
                music[5] = progress;
            }
            if (seekBar.getId() == R.id.seekBar3){
                music[6] = progress;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play= findViewById(R.id.playScreen);
        musicSpinner = findViewById(R.id.musicSpinner);
        List<String> options = new ArrayList<String>();
        options.add("Go Tech Go");
        options.add("Mario");
        options.add("Tetris");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        musicSpinner.setAdapter(dataAdapter);
        musicSpinner.setOnItemSelectedListener(musicListener);

        spinner1 = findViewById(R.id.effect1);
        List<String> options1 = new ArrayList<String>();
        options1.add("None");
        options1.add("Cheering");
        options1.add("Clapping");
        options1.add("Lets Go Hokies");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);
        spinner1.setOnItemSelectedListener(effect1Listener);

        spinner2 = findViewById(R.id.effect2);
        spinner2.setAdapter(dataAdapter1);
        spinner2.setOnItemSelectedListener(effect2Listener);

        spinner3 = findViewById(R.id.effect3);
        spinner3.setAdapter(dataAdapter1);
        spinner3.setOnItemSelectedListener(effect3Listener);

        bar1 = findViewById(R.id.seekBar1);
        bar2 = findViewById(R.id.seekBar2);
        bar3 = findViewById(R.id.seekBar3);

        bar1.setOnSeekBarChangeListener(barListener);
        bar2.setOnSeekBarChangeListener(barListener);
        bar3.setOnSeekBarChangeListener(barListener);
        if(savedInstanceState != null){
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS);
            music = savedInstanceState.getIntArray("music");
            saved();
        }

        try {
            if (music[0] == -1) {
                //
            }
        }
        catch (Exception e) {
            music = new int[7];
            music[0] = 0;
            for (int i = 1; i < 7; i++) {
                music[i] = -1;
            }
            in = false;
        }

        System.out.println(Arrays.toString(music));

        if (in) {
            System.out.println("in " + Arrays.toString(music));
            saved();

        }

    }

    public void saved() {
        if (music[0] == -1)
            musicSpinner.setSelection(0);
        else
            musicSpinner.setSelection(music[0]);

        if (music[1] == -1)
            musicSpinner.setSelection(0);
        else
            musicSpinner.setSelection(music[1] - 2);

        if (music[2] == -1)
            musicSpinner.setSelection(0);
        else
            musicSpinner.setSelection(music[2] - 2);

        if (music[3] == -1)
            musicSpinner.setSelection(0);
        else
            musicSpinner.setSelection(music[3] - 2);

        bar1.setProgress(music[4]);
        bar2.setProgress(music[5]);
        bar3.setProgress(music[6]);
    }


    public void onClickPlay(View view) {
        if (view.getId() == R.id.playScreen) {
            Intent intent = new Intent(this, PlayActivity.class);
            intent.putExtra("music", music);
            startActivity(intent);
        }
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(INITIALIZE_STATUS, isInitialized);
        savedInstanceState.putIntArray("music", music);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        music = savedInstanceState.getIntArray("music");
    }

}