package com.example.kayiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.PopupMenu;

public class MainActivity3 extends AppCompatActivity {

    String fruitList[] = {"1"};
    int fruitImages [] = {R.drawable.abdulhamid_img2};

    Menu menuObj = new Menu();

    public String episodeDescription[] = {"test"};

    public static String[] episodeLink = {
            "ef0d3930a7b6c95bd2b32ed45989c61f"
    };
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView = (ListView) findViewById(R.id.customListView3);
        CustomBaseAdapter customerBaseAdapter = new CustomBaseAdapter(getApplicationContext(), fruitList, fruitImages, episodeDescription);
        listView.setAdapter(customerBaseAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {

            SharedPreferences sharedPreferences = getSharedPreferences(Menu.SHARED_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            int epNum = i + 1;

            PopupMenu popup = new PopupMenu(MainActivity3.this, view);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.sd:
                        //save osman episode info for player to get episode number, series name, link, quality
                        editor.putString(Menu.QUALITY, "480");
                        editor.putString(Menu.EPISODE, String.valueOf(epNum));
                        editor.putString(Menu.IMAGE, "3");                     //Abdul Hamid
                        editor.putString(Menu.LINK, episodeLink[i]);
                        editor.commit();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.hd:
                        editor.putString(Menu.QUALITY, "720");
                        editor.putString(Menu.EPISODE, String.valueOf(epNum));
                        editor.putString(Menu.IMAGE, "3");                     //Abdul Hamid
                        editor.putString(Menu.LINK, episodeLink[i]);
                        editor.commit();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent3 = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.fhd:
                        editor.putString(Menu.QUALITY, "1080");
                        editor.putString(Menu.EPISODE, String.valueOf(epNum));
                        editor.putString(Menu.IMAGE, "3");                     //Abdul Hamid
                        editor.putString(Menu.LINK, episodeLink[i]);
                        editor.commit();
                        menuObj.active = true;

                        //exit this activity and go to player
                        finish();
                        Intent intent2 = new Intent(getApplicationContext(), PlayerActivity.class);
                        startActivity(intent2);
                        return true;
                    default:
                        return false;
                }
            });
            // show menu
            popup.show();
        });
        //updateEpisodeProgress();


    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
        finish();
    }
}