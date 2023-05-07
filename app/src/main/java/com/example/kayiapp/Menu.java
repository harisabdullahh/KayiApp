package com.example.kayiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Log;

import org.junit.internal.RealSystem;
import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;

public class Menu extends AppCompatActivity {

    public static final String
            SHARED_PREFS = "info",
            IMAGE = "Image",
            EPISODE = "Episode",
            LINK = "Link",
            NULLVALUE = "NULL",
            DURATION = "MaxDuration",
            POSITION = "position",
            ERTUGRUL = "1",
            OSMAN = "2",
            ABDULHAMID = "3",
            QUALITY = "Quality";
    public boolean active;

    String
            episodeNumber,
            seriesName,
            minDuration;

    int
            ep = 0,
            episodeLeft = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        @SuppressLint("WrongConstant")
        SharedPreferences shared_prefs = getSharedPreferences(SHARED_PREFS, MODE_APPEND);
        episodeNumber = shared_prefs.getString(EPISODE, NULLVALUE);       //Episode Number (1,2...n)
        seriesName = shared_prefs.getString(IMAGE, NULLVALUE);         //Series Name (Osman = 2 & Ertugrul = 1)

        //Initialize Views
        CardView ertugrulCard = (CardView) findViewById(R.id.ErtugrulCard);
        CardView osmanCard = (CardView) findViewById(R.id.OsmanCard);
        CardView abdulhamidCard = (CardView) findViewById(R.id.AbdulHamidCard);
        LinearLayout continueWatching = (LinearLayout) findViewById(R.id.continueWatching);
        TextView t1 = (TextView) findViewById(R.id.epTitle);
        TextView t2 = (TextView) findViewById(R.id.epNameMenu);
        ImageView continueText = (ImageView) findViewById(R.id.continueText);
        ImageButton btn = (ImageButton) findViewById(R.id.testBtn);
        ImageView image1 = (ImageView) findViewById(R.id.epImageMenu);
        RelativeLayout relativeDescription = (RelativeLayout) findViewById(R.id.relativeDescription);
        ProgressBar pBar2 = (ProgressBar) findViewById(R.id.epProgress);

        btn.setOnClickListener(view -> {
            //Using Download Manager to download a file using downloadFile() function;
            Uri urifile = Uri.parse("https://github.com/harisabdullahh/TheSeriesApp/raw/master/Kayi.apk");
            downloadFile(urifile);
        });

        //delaying getting the value of progress of continue watching episode because of delay in episode data saving in PlayerActivity.java;
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            int pos = Integer.parseInt(shared_prefs.getString(seriesName + episodeNumber + POSITION, "0"));     //Sample: (45position)
            int dur = Integer.parseInt(shared_prefs.getString(DURATION, "100"));

            //set progress of current Episode setProgress(int dur, int pos)
            setProgress(dur, pos);
        }, 1200);


        //set Image & Title of Episode
        if (seriesName.equals(ERTUGRUL)) {
            image1.setImageResource(R.drawable.ertugrul_img1);
            t2.setText("Ertugrul");
        } else if (seriesName.equals(OSMAN)) {
            image1.setImageResource(R.drawable.osman_img3);
            t2.setText("Osman");
        } else if (seriesName.equals(ABDULHAMID)) {
            image1.setImageResource(R.drawable.abdulhamid_img1);
            t2.setText("Abdul Hamid");
        }

        //set Episode number
        t1.setText(" Episode " + episodeNumber);

        //Check if there is any episode was previously played and hide/unhide "Continue Watching" accordingly
        if (episodeNumber.equals(NULLVALUE)) {
            continueText.setVisibility(View.GONE);
            continueWatching.setVisibility(View.GONE);
        } else {
            continueWatching.setOnClickListener(view -> {

                //CallPlayer(url,"Episode "+epNum, epNum);

                float scale = getResources().getDisplayMetrics().density;
                int widthInDp = 150;
                int widthInPixels = (int) (widthInDp * scale + 0.5f);
                relativeDescription.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
                ((LinearLayout.LayoutParams) relativeDescription.getLayoutParams()).setMargins(0, 0, 0, 0);
                pBar2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                image1.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

                PopupMenu popup = new PopupMenu(Menu.this, view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.sd:
                            active = false;
                            editor.putString(QUALITY, "480");
                            editor.apply();
                            startActivity(intent);
                            finish();
                            return true;
                        case R.id.hd:
                            active = false;
                            editor.putString(QUALITY, "720");
                            editor.apply();
                            startActivity(intent);
                            finish();
                            return true;
                        case R.id.fhd:
                            active = false;
                            editor.putString(QUALITY, "1080");
                            editor.apply();
                            startActivity(intent);
                            finish();
                            return true;
                        default:
                            return false;
                    }
                });
                // show menu
                popup.show();

                popup.setOnDismissListener(menu -> {
                    relativeDescription.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    ((LinearLayout.LayoutParams) relativeDescription.getLayoutParams()).setMargins(15, 0, 0, 0);
                    pBar2.setLayoutParams(new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
                    image1.setLayoutParams(new FrameLayout.LayoutParams(widthInPixels, FrameLayout.LayoutParams.WRAP_CONTENT));
                });
            });
        }
        ertugrulCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(intent);
        });
        osmanCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        abdulhamidCard.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            startActivity(intent);
        });
    }

    void downloadFile(Uri uri) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Downloading App");
        request.setDescription("");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Kayi.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void setProgress(int dur, int pos) {
        ProgressBar pBar;
        pBar = (ProgressBar) findViewById(R.id.epProgress);

        pBar.setMax(dur);
        pBar.setProgress(pos);

        episodeLeft = dur - pos;

        if (episodeLeft < 123000 && episodeLeft != 100) {

            if (!episodeNumber.equals(NULLVALUE))
                ep = Integer.parseInt(episodeNumber) + 1;

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (seriesName.equals(ERTUGRUL))
                editor.putString(LINK, MainActivity2.episodeLink[ep - 1]);

            else if (seriesName.equals(OSMAN))
                editor.putString(LINK, MainActivity.episodeLink[ep - 1]);

            else if (seriesName.equals(ABDULHAMID))
                editor.putString(LINK, MainActivity3.episodeLink[ep - 1]);

            editor.putString(EPISODE, String.valueOf(ep));
            editor.apply();

            TextView t1 = (TextView) findViewById(R.id.epTitle);
            t1.setText(" Episode " + ep);

            @SuppressLint("WrongConstant")
            SharedPreferences sh = getSharedPreferences(SHARED_PREFS, MODE_APPEND);

            pBar.setMax(Integer.parseInt(sh.getString(DURATION, minDuration)));
            pBar.setProgress(Integer.parseInt(sh.getString(seriesName + ep + POSITION, "0")));


        }
    }
}