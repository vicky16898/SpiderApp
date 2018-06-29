package com.example.vicky.goodplays;

import android.graphics.Bitmap;

public class SongDetails {

    public String track_id;
    public String track_name;
    public Bitmap posterImg;
    public String artistName;
    public String genre;
    public String year_of_release;

    public SongDetails(String track_name , Bitmap posterImg , String track_id , String artistName , String genre , String year_of_release){


        this.track_name = track_name;
        this.posterImg = posterImg;
        this.artistName = artistName;
        this.genre = genre;
        this.track_id = track_id;
        this.year_of_release = year_of_release;
    }
}
