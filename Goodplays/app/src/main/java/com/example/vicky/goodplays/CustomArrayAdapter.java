package com.example.vicky.goodplays;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<SongDetails> {
    private Context mContext;
    private ArrayList<SongDetails> song;


    public CustomArrayAdapter(Context context , ArrayList<SongDetails> list){
        super(context ,0 ,list);
        mContext = context;
        song = list;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        SongDetails singleTrack = song.get(position);


        TextView trackname = (TextView)listItem.findViewById(R.id.trackname);
        ImageView poster = (ImageView)listItem.findViewById(R.id.poster);

        trackname.setText(singleTrack.track_name.toString());
        poster.setImageBitmap(singleTrack.posterImg);


        return listItem;







    }






}
