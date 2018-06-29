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

public class CustomFavouriteTrackAdapter extends ArrayAdapter<FavouriteTrackClass> {

    private Context mContext;
    private ArrayList<FavouriteTrackClass> fav;

    public CustomFavouriteTrackAdapter(Context context , ArrayList<FavouriteTrackClass> list){
        super(context ,0 ,list);
        mContext = context;
        fav = list;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = LayoutInflater.from(mContext).inflate(R.layout.favourite_tracklist,parent,false);



        FavouriteTrackClass favInd = fav.get(position);

        TextView favTrack = (TextView)v.findViewById(R.id.favTrack);
        TextView favArtist = (TextView)v.findViewById(R.id.favArtist);
        TextView favGenre = (TextView)v.findViewById(R.id.favGenre);
        TextView favYear = (TextView)v.findViewById(R.id.favyear);

        favTrack.setText("Track Name : " + favInd.favTrack);
        favArtist.setText("Artist Name : " + favInd.favArtist);
        favGenre.setText("Genre : " + favInd.favGenre);
        favYear.setText("Year : " + favInd.favYear);




          return v;
    }



}

