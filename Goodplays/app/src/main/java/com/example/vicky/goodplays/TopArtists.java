package com.example.vicky.goodplays;

import java.util.ArrayList;

public class TopArtists {

    String ArtistName;

    ArrayList<String> genre;

    String Rating;

    public TopArtists(String ArtistName , ArrayList<String> genre , String Rating){



        this.Rating = Rating;
        this.ArtistName = ArtistName;
        this.genre = genre;
    }

}
