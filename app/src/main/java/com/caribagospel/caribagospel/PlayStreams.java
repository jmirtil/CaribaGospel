package com.caribagospel.caribagospel;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayStreams extends AppCompatActivity {

    protected MediaPlayer mp;
    protected boolean isPlaying;

    protected Map<Integer, String> streamMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_streams);

        setUpStream();
        isPlaying = false;
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

        final ToggleButton play = findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resumeStream(play);
            }
        });

        Button stop = findViewById(R.id.stopButton);
        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                stopStream(play);
            }
        });

        Button close = findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeApp();
            }
        });
    }

    /**
     * Setup links to the stream for each button in the app.
     */
    protected void setUpStream(){
        streamMap.put(R.id.button, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button4, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button5, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button6, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button7, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button8, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button9, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button15, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button16, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button17, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button18, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button19, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button20, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button21, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button22, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
        streamMap.put(R.id.button23, "https://samcloud.spacial.com/api/listen?sid=92412&m=sc&rid=166620");
    }

    /**
     * This is the action handler for the play button. It toggles between play and pause.
     * @param playButton
     */
    public void resumeStream(ToggleButton playButton) {
        if (mp != null) {
            if(!isPlaying)
            {
                mp.start();
                isPlaying = true;
                playButton.setChecked(true);
            }
            else
            {
                mp.pause();
                isPlaying = false;
                playButton.setChecked(false);
            }
        }
    }

    public void stopStream(ToggleButton playButton) {
        if (mp != null) {
            mp.pause();
            isPlaying = false;
            playButton.setChecked(false);
        }
    }

    public void closeApp() {
        if (mp != null) {
            mp.stop();
            mp.release();
            mp = null;
            isPlaying = false;
        }
        closeApp();
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
            mp.prepare();
            isPlaying = true;
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
