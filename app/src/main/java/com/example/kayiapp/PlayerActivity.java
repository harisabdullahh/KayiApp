package com.example.kayiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;

public class PlayerActivity extends AppCompatActivity {


    public static final String
            SHARED_PREFS = "info",
            POSITION = "position",
            DURATION = "MaxDuration";
    String
            pos,
            maxDur,
            episode,
            series;

    public StyledPlayerView playerView;

    boolean
            stillPlaying = false,
            homePressed = false;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        playerView = (StyledPlayerView) findViewById(R.id.playerView2);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i==0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        homePressed = true;
    }

    @Override
    public void onStart(){
        super.onStart();

        SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String
                s1 = sh.getString("Episode", "NULL"),        //Episode Number (1,2...n)
                s2 = sh.getString("Image", "NUll"),          //Series Name (Osman = 2 & Ertugrul = 1)
                positionEpisode = sh.getString(/*POSITION*/s2+s1+"position","0"),   //Position of Episode
                url = sh.getString("Link","NULL"),           //Episode "key" (this is not full url)
                quality = sh.getString("Quality", "480");
        episode = s1;
        series = s2;

        int dur = Integer.valueOf(sh.getString("duration", "100"));

        //initialize Player then send url and position of episode to player
        initializePlayer(url, positionEpisode, quality);
    }

    public void onDestroy(){
        super.onDestroy();
    }

    public void initializePlayer(String url, String positionEpisode, String quality){

        stillPlaying = true;
        homePressed = false;

        ExoPlayer player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = new MediaItem.Builder()
                .setUri(Uri.parse("https://maher.xtremestream.co/player/load_m3u8_xtremestream.php?data="+url+ "&q=" + quality))
                .setMimeType(MimeTypes.APPLICATION_M3U8)
                .build();
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
        player.seekTo(Long.parseLong(positionEpisode));



        if(player.getPlaybackState()==Player.STATE_ENDED){
            Toast.makeText(this, "Ended", Toast.LENGTH_SHORT).show();
        }

        player.addListener(
                new Player.Listener() {
                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        if (isPlaying) {
                            // Active playback.
                            maxDur = String.valueOf(player.getContentDuration());
                        } else {
                            // Not playing because playback is paused, ended, suppressed, or the player
                            // is buffering, stopped or failed. Check player.getPlayWhenReady,
                            // player.getPlaybackState, player.getPlaybackSuppressionReason and
                            // player.getPlaybackError for details.
                        }
                    }
                });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pos = String.valueOf(player.getCurrentPosition());
                handler.postDelayed(this, 2000); //2000ms frequency of updates.
                //saveData(); //saves position and duration in SharedPreferences("info")

                if(!stillPlaying) {
                    player.stop();
                    player.release();
                    saveData(); //saves position and duration in SharedPreferences("info")
                    stillPlaying = true;
                }
                if(homePressed == true){
                    player.pause();
                }
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        stillPlaying = false;
        Intent i = new Intent(PlayerActivity.this, Menu.class);
        startActivity(i);
        finish();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(/*POSITION*/series+episode+"position", pos);
        editor.putString(DURATION, maxDur);
        editor.commit();
    }
}