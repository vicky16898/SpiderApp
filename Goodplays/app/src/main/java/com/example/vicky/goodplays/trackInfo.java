package com.example.vicky.goodplays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class trackInfo extends AppCompatActivity {

    public TextView trackName;
    public ImageView imageView;
    public TextView genre;
    public TextView year;
    public TextView artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_info);
        Intent i = getIntent();
        String track = i.getStringExtra("Title");
        String artist = i.getStringExtra("Artist");
        String geNre = i.getStringExtra("Genre");
        String yEar = i.getStringExtra("Year");

        trackName = (TextView)findViewById(R.id.trackName);
        imageView = (ImageView)findViewById(R.id.imageView);
        genre = (TextView)findViewById(R.id.Genre);
        year = (TextView)findViewById(R.id.year);
        artistName = (TextView)findViewById(R.id.Artistname);

        trackName.append(track);
        genre.append(geNre);
        year.append(yEar);
        artistName.append(artist);





    }
}
