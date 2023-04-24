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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Log;

import org.w3c.dom.Text;

import java.io.File;
import java.net.URI;

public class Menu extends AppCompatActivity {

    public boolean active;

    String
            s1,
            s2,
            url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("info", MODE_APPEND);

        s1 = sh.getString("Episode", "NULL");   //Episode Number (1,2...n)
        s2 = sh.getString("Image", "NUll");     //Series Name (Osman = 2 & Ertugrul = 1)
        url = sh.getString("Link","NULL");      //Episode "key" (this is not full url)


        CardView cv1 = (CardView) findViewById(R.id.ErtugrulCard);
        CardView cv2 = (CardView) findViewById(R.id.OsmanCard);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.continueLinear);
        TextView t1 = (TextView) findViewById(R.id.epTitle);
        TextView t2 = (TextView) findViewById(R.id.epNameMenu);
        ImageView continueText = (ImageView) findViewById(R.id.continueText);
        ImageButton btn = (ImageButton) findViewById(R.id.testBtn);
        ImageView image1 = (ImageView) findViewById(R.id.epImageMenu);

        //IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        //registerReceiver(onComplete, filter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Using Download Manager to download a file using downloadFile() function;
                Uri urifile = Uri.parse("https://github.com/harisabdullahh/TheSeriesApp/raw/master/Kayi.apk");
                downloadFile(urifile);
            }
        });

        //delaying getting the value of progress of continue watching episode because of delay in episode data saving in PlayerActivity.java;
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int pos = Integer.valueOf(sh.getString(s2+s1+"position","0"));     //Sample: (45position)
                int dur = Integer.valueOf(sh.getString("MaxDuration", "100"));

                //set progress of current Episode setProgress(int dur, int pos)
                setProgress(dur, pos);
            }
        }, 2000);


        //set Image & Title of Episode
        if(s2.equals("1")) {
            image1.setImageResource(R.drawable.ertugrul_img1);
            t2.setText("Ertugrul");
        }
        else if(s2.equals("2")) {
            image1.setImageResource(R.drawable.osman_img1);
            t2.setText("Osman");
        }
        else{

        }

        //set Episode number
        t1.setText(" Episode "+s1);

        //Check if there is any episode was previously played and hide/unhide "Continue Watching" accordingly
        if(s1=="NULL") {
            continueText.setVisibility(View.GONE);
            l1.setVisibility(View.GONE);
        }
        else {
            l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int epNum = Integer.valueOf(s1);
                    //CallPlayer(url,"Episode "+epNum, epNum);
                    active = false;
                    Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){

    }

    void downloadFile(Uri uri) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading App");
        request.setDescription("");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Kayi.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        Long reference = downloadManager.enqueue(request);
    }

    // Register a broadcast receiver to listen for download completion events
    /*BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(reference);

            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                    @SuppressLint("Range")
                    String downloadPath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    installApk(downloadPath);
                }
            }
            cursor.close();
        }
    };*/


    /*protected void installApk(String apkFilePath) {
        File apkFile = new File(apkFilePath);

        Uri apkUri = FileProvider.getUriForFile(
                this,
                "com.example.kayiapp",
                apkFile);

        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setData(apkUri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }*/

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
        //unregisterReceiver(onComplete);
    }

    void CallPlayer(String url, String epName, int episodeNumber) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.mxtech.videoplayer.ad");
        intent.setClassName("com.mxtech.videoplayer.ad", "com.mxtech.videoplayer.ad.ActivityScreen");
        Uri videoUri = Uri.parse("https://maher.xtremestream.co/player/load_m3u8_xtremestream.php?data=" + url);
        intent.setDataAndType(videoUri, "video/*");
        intent.setPackage("com.mxtech.videoplayer.ad"); // com.mxtech.videoplayer.pro
        intent.putExtra("return_result", true);
        intent.putExtra("title", epName);
        startActivityForResult(intent, 0);
    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)

            // -1 RESULT_OK : Playback was completed or stopped by user request.
            //Activity.RESULT_CANCELED: User canceled before starting any playback.
            //RESULT_ERROR (=Activity.RESULT_FIRST_USER): Last playback was ended with an error.

            if (data.getAction().equals("com.mxtech.intent.result.VIEW")) {
                int pos = data.getIntExtra("position", -1); // Last playback position in milliseconds. This extra will not exist if playback is completed.
                int dur = data.getIntExtra("duration", -1); // Duration of last played video in milliseconds. This extra will not exist if playback is completed.
                String cause = data.getStringExtra("end_by"); //  Indicates reason of activity closure.

                SharedPreferences info = getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = info.edit();

                myEdit.putString("position", String.valueOf(pos));
                myEdit.putString("duration", String.valueOf(dur));
                myEdit.commit();

                setProgress(dur, pos);

                //int resID = getResources().getIdentifier(title + "p", "id", getPackageName());

            }
    }*/
    void setProgress(int dur, int pos){
        ProgressBar pBar;
        pBar = (ProgressBar) findViewById(R.id.epProgress);

        pBar.setMax(dur);
        pBar.setProgress(pos);

        int b = dur - pos;

        if(b<123000 && b!=100){
            int ep=0;
            if(s1.equals("NULL")){

            }
            else
                ep = Integer.parseInt(s1)+1;

            //ImageView image1 = (ImageView) findViewById(R.id.epImageMenu);
            //TextView t2 = (TextView) findViewById(R.id.epNameMenu);

            SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(s2=="1") {
                /*image1.setImageResource(R.drawable.ertugrul_img1);
                t2.setText("Ertugrul");*/
                editor.putString("Link",MainActivity2.episodeLink[ep]);

            }
            else if(s2=="1"){
                /*image1.setImageResource(R.drawable.osman_img1);
                t2.setText("Osman");*/
                editor.putString("Link",MainActivity.episodeLink[ep]);

            }
            else{

            }

            editor.putString("Episode",String.valueOf(ep));

            editor.commit();
            TextView t1 = (TextView) findViewById(R.id.epTitle);
            t1.setText(" Episode "+ep);

            @SuppressLint("WrongConstant")
            SharedPreferences sh = getSharedPreferences("info", MODE_APPEND);

            pBar.setMax(Integer.valueOf(sh.getString("MaxDuration", "125000")));
            pBar.setProgress(Integer.valueOf(sh.getString(s2+ep+"position","0")));


        }
    }
}