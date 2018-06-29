package com.example.vicky.goodplays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtistActivity extends AppCompatActivity {

    public TextView ArtistName;
    public TextView genres;
    public TextView rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);


        ArtistName = (TextView)findViewById(R.id.ArtistName);
        genres = (TextView)findViewById(R.id.genres);
        rating = (TextView)findViewById(R.id.rating);

        Intent in = getIntent();

        String artistNAme = in.getStringExtra("ArtistName");
        String Ratings = in.getStringExtra("Rating");

        Bundle args = in.getBundleExtra("BUNDLE");

        ArrayList<String> object = (ArrayList<String>) args.getSerializable("ARRAYLIST");

        ArtistName.append(artistNAme);
        rating.append(Ratings);

        Log.e("ArrayStrength" ,String.valueOf(object.size()));

        if(object.size() == 1){

            genres.append(object.get(0));


        }

        else {

            for (int i = 0; i < object.size(); i++) {

                  if(i != ((object.size()) - 1)) {
                      genres.append(object.get(i) + " , ");
                  }
                  else {
                      genres.append(object.get(i));
                  }




            }

        }




    }
}
