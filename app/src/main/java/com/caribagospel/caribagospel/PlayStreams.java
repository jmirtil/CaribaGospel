package com.caribagospel.caribagospel;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayStreams extends AppCompatActivity {

    protected MediaPlayer mp;

    protected Map<Integer, String> streamMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_streams);

        setUpStream();
        ViewGroup layout = findViewById(R.id.layout_container_buttons);
        for (int i = 0; i < layout.getChildCount(); i++) {

            View child = layout.getChildAt(i);
            if (child instanceof Button) {
                final Button button = (Button) child;
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        play(streamMap.get(button.getId()));
                    }
                });
            }
        }

        final Button play = findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resumeStream();
            }
        });

        Button stop = findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopStream();
            }
        });

        Button close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shutDown();
            }
        });
    }

    /**
     * Setup links to the stream for each button in the app.
     */
    protected void setUpStream(){
        streamMap.put(R.id.button, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button2, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button3, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button4, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button5, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button6, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button7, "http://www.all-birds.com/Sound/western%20bluebird.wav");
        streamMap.put(R.id.button8, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
    }

    /**
     * This is the action handler for the play button. It toggles between play and pause.
     */
    public void resumeStream() {
        if (mp != null) {
            mp.start();
            Button play = findViewById(R.id.playButton);
            play.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            play.setEnabled(false);
        }
    }

    public void stopStream() {
        if (mp != null) {
            mp.pause();
        }

        Button play = findViewById(R.id.playButton);
        play.getBackground().setColorFilter(null);
        play.setEnabled(true);
    }

    public void shutDown() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
        }
        this.finish();
        System.exit(0);
    }

    public void play(String url) {
        try {
            if (mp != null) {
                mp.stop();
                mp.release();
                mp = null;
            }
            this.mp = new MediaPlayer();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(url);
            mp.prepareAsync();

            Button play = findViewById(R.id.playButton);
            play.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (mp != null) mp.release();
        super.onDestroy();
    }
}
