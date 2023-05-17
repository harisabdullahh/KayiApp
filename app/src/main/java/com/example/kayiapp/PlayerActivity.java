package com.example.kayiapp;

import static com.example.kayiapp.Menu.ABDULHAMID;
import static com.example.kayiapp.Menu.DURATION;
import static com.example.kayiapp.Menu.EPISODE;
import static com.example.kayiapp.Menu.ERTUGRUL;
import static com.example.kayiapp.Menu.IMAGE;
import static com.example.kayiapp.Menu.LINK;
import static com.example.kayiapp.Menu.NULLVALUE;
import static com.example.kayiapp.Menu.OSMAN;
import static com.example.kayiapp.Menu.POSITION;
import static com.example.kayiapp.Menu.QUALITY;
import static com.example.kayiapp.Menu.SHARED_PREFS;
import static com.example.kayiapp.Menu.WEB_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.MimeTypes;

import java.util.concurrent.atomic.AtomicReference;

public class PlayerActivity extends AppCompatActivity {
    String
            pos,
            maxDur,
            episode,
            series;

    public StyledPlayerView playerView;

    boolean
            stillPlaying = false,
            homePressed = false,
            changeEpisode = false;

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        changeEpisode = false;
        playerView = (StyledPlayerView) findViewById(R.id.playerView2);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(i -> {
            if (i==0){
                decorView.setSystemUiVisibility(hideSystemBars());
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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
                s1 = sh.getString(EPISODE, NULLVALUE),        //Episode Number (1,2...n)
                s2 = sh.getString(IMAGE, NULLVALUE),          //Series Name (Osman = 2 & Ertugrul = 1)
                positionEpisode = sh.getString(s2+s1+POSITION,"0"),   //Position of Episode
                url = sh.getString(LINK,NULLVALUE),           //Episode "key" (this is not full url)
                quality = sh.getString(QUALITY, "480");
        episode = s1;
        series = s2;

        //initialize Player then send url and position of episode to player
        initializePlayer(url, positionEpisode, quality);
    }

    @Override
    public void onStop() {
        super.onStop();
        // Add your onStop logic here
    }

    public void onDestroy(){
        super.onDestroy();
    }

    public void initializePlayer(String url, String positionEpisode, String quality){

        stillPlaying = true;
        homePressed = false;
        CardView nextButton = (CardView) findViewById(R.id.nextButton);

        ExoPlayer player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        AtomicReference<MediaItem> mediaItem = new AtomicReference<>(new MediaItem.Builder()
                .setUri(Uri.parse(WEB_URL + url + "&q=" + quality))
                .setMimeType(MimeTypes.APPLICATION_M3U8)
                .build());
        player.setMediaItem(mediaItem.get());
        player.prepare();
        player.setPlayWhenReady(true);
        player.seekTo(Long.parseLong(positionEpisode));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
                        }
                    }
                });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pos = String.valueOf(player.getCurrentPosition());
                if ((player.getContentDuration()-player.getCurrentPosition()) < 123000 && player.getCurrentPosition() != 0) {
                    while(!changeEpisode){
                        changeEpisode = true;
                        nextButton.setVisibility(View.VISIBLE);
                    };
                }
                handler.postDelayed(this, 2000); //2000ms frequency of updates.

                if(!stillPlaying) {
                    saveData(); //saves position and duration in SharedPreferences("info")
                    player.stop();
                    player.release();
                    stillPlaying = true;
                    handler.removeCallbacks(this);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                if(homePressed){
                    saveData(); //saves position and duration in SharedPreferences("info")
                    player.stop();
                    player.release();
                    homePressed = false;
                    handler.removeCallbacks(this);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            }
        }, 2000);

        nextButton.setVisibility(View.INVISIBLE);
        /*nextButton.setOnClickListener(view -> {
            SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();

            String episodeNumber = sh.getString(EPISODE, NULLVALUE);       //Episode Number (1,2...n)
            String seriesName = sh.getString(IMAGE, NULLVALUE);         //Series Name (Osman = 2 & Ertugrul = 1)
            String position = sh.getString(seriesName+episodeNumber+"position","0");   //Position of Episode
            String URL = "";

            switch (seriesName) {
                case ERTUGRUL:
                    URL = MainActivity2.episodeLink[Integer.parseInt(episodeNumber)+1];
                    //editor.putString(LINK, MainActivity2.episodeLink[Integer.parseInt(episodeNumber) - 1]);
                    break;
                case OSMAN:
                    URL = MainActivity.episodeLink[Integer.parseInt(episodeNumber)+1];
                    //editor.putString(LINK, MainActivity.episodeLink[Integer.parseInt(episodeNumber) - 1]);
                    break;
                case ABDULHAMID:
                    URL = MainActivity3.episodeLink[Integer.parseInt(episodeNumber)+1];
                    //editor.putString(LINK, MainActivity3.episodeLink[Integer.parseInt(episodeNumber) - 1]);
                    break;
            }
            Toast.makeText(this, seriesName+": "+episodeNumber, Toast.LENGTH_SHORT).show();
            player.stop();
            player.release();
            mediaItem.set(new MediaItem.Builder()
                    .setUri(Uri.parse("https://maher.xtremestream.co/player/load_m3u8_xtremestream.php?data=" + URL + "&q=" + quality))
                    .setMimeType(MimeTypes.APPLICATION_M3U8)
                    .build());
            player.setMediaItem(mediaItem.get());
            player.prepare();
            player.setPlayWhenReady(true);
            player.seekTo(Long.parseLong(position));
            nextButton.setVisibility(View.GONE);
        });*/
        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stillPlaying = false;
                Intent intent = new Intent(PlayerActivity.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        stillPlaying = false;
        Intent intent = new Intent(PlayerActivity.this, Menu.class);
        startActivity(intent);
        finish();
    }
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(series+episode+POSITION, pos);
        editor.putString(DURATION, maxDur);
        editor.apply();
    }
}