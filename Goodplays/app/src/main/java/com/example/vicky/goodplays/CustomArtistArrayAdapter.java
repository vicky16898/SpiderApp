package com.example.vicky.goodplays;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArtistArrayAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<TopArtists> mArtists;

    public CustomArtistArrayAdapter(Context context , ArrayList<TopArtists> list){
        super(context ,0 ,list);
        this.context = context;
        mArtists = list;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = LayoutInflater.from(context).inflate(R.layout.artist_list_item , parent , false);
        TopArtists indvidualArtist = mArtists.get(position);
        TextView topartists = (TextView)v.findViewById(R.id.topartists);
        topartists.setText(indvidualArtist.ArtistName.toString());
        return v;
    }
}
