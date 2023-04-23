package com.example.kayiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    String  listFruit[],
            listEpDescription[];
    int listImages[];
    LayoutInflater inflater;

    public CustomBaseAdapter(Context ctx, String [] fruitList, int [] images, String [] listDescription){
        this.context = ctx;
        this.listFruit = fruitList;
        this.listImages = images;
        this.listEpDescription = listDescription;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listFruit.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_episode_list_view,null);
        TextView txtView = (TextView) convertView.findViewById(R.id.epTitle);
        TextView txtView2 = (TextView) convertView.findViewById(R.id.epDescription);
        ImageView fruitImg = (ImageView) convertView.findViewById(R.id.epImage);
        txtView.setText(listFruit[position]);
        //txtView2.setText(listEpDescription[position]);
        fruitImg.setImageResource(listImages[0]);
        return convertView;
    }
    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }
}
