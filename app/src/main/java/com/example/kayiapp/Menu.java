package com.example.kayiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;

public class Menu extends AppCompatActivity {

    public boolean active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("info", MODE_APPEND);
        String
                s1 = sh.getString("Episode", "NULL"),
                s2 = sh.getString("Image", "NUll"),
                url = sh.getString("Link","NULL");

        int pos = Integer.valueOf(sh.getString("position","0"));
        int dur = Integer.valueOf(sh.getString("MaxDuration", "100"));

        CardView cv1 = (CardView) findViewById(R.id.ErtugrulCard);
        CardView cv2 = (CardView) findViewById(R.id.OsmanCard);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.continueLinear);
        TextView t1 = (TextView) findViewById(R.id.epTitle);
        TextView t2 = (TextView) findViewById(R.id.epNameMenu);
        ImageView continueText = (ImageView) findViewById(R.id.continueText);
        Button btn = (Button) findViewById(R.id.testBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Using Download Manager to download a file using downloadFile() function;
                Uri urifile = Uri.parse("https://github.com/harisabdullahh/TheSeriesApp/raw/master/Kayi.apk");
                downloadFile(urifile);


            }
        });

        ImageView image1 = (ImageView) findViewById(R.id.epImageMenu);

        //set progress of current Episode
        setProgress(dur, pos);

        //set Image & Title of Episode
        if(s2=="1") {
            image1.setImageResource(R.drawable.ertugrul_img1);
            t2.setText("Ertugrul");
        }
        else {
            image1.setImageResource(R.drawable.osman_img1);
            t2.setText("Osman");
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

    void downloadFile(Uri uri) {

        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Downloading App");
        request.setDescription("");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Kayi.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        Long reference = downloadManager.enqueue(request);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }
    void setProgress(int dur, int pos){
        ProgressBar pBar;
        pBar = (ProgressBar) findViewById(R.id.epProgress);

        pBar.setMax(dur);
        pBar.setProgress(pos);
    }
}