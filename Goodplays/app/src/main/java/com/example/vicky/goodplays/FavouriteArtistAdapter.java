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

public class FavouriteArtistAdapter extends ArrayAdapter<FavouriteArtists> {

    private Context mContext;
    private ArrayList<FavouriteArtists> favoriteArtists;

    public FavouriteArtistAdapter(Context context , ArrayList<FavouriteArtists> list){
        super(context ,0 ,list);
        mContext = context;
        favoriteArtists = list;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null)
            v = LayoutInflater.from(mContext).inflate(R.layout.fourth_list_itemlayout,parent,false);

        FavouriteArtists favArt = favoriteArtists.get(position);

        TextView favArtist = (TextView)v.findViewById(R.id.ArtistFavourite);
        TextView favGenre = (TextView)v.findViewById(R.id.GenreFavourite);
        TextView favRating = (TextView)v.findViewById(R.id.RatingFavourite);

        favArtist.setText("Artist Name : " + favArt.Artist_Favourite);
        favGenre.setText("Genre : " + favArt.Genre_Favourite);
        favRating.setText("Rating : " + favArt.Rating_Favourite);







        return v;
    }
}
