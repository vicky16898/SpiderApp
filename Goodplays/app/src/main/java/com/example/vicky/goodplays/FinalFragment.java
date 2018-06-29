package com.example.vicky.goodplays;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class FinalFragment extends Fragment {

    private String title;
    private int page;
    public ListView listFav;
    public ArrayList<FavouriteArtists> favArt;
    public MyDatabaseArtist MyDB;
    public FavouriteArtistAdapter myAdapter;

    public static FinalFragment newInstance(int page, String title) {
        FinalFragment fragmentFinal = new FinalFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFinal.setArguments(args);
        return fragmentFinal;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        favArt = new ArrayList<FavouriteArtists>();
        MyDB = new MyDatabaseArtist(getContext());
        retriveAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View finalview = inflater.inflate(R.layout.final_fragmentlayout, container, false);

        listFav = (ListView)finalview.findViewById(R.id.listFav);

        favArt = new ArrayList<FavouriteArtists>();
        MyDB = new MyDatabaseArtist(getContext());
        retriveAll();

        myAdapter = new FavouriteArtistAdapter(getContext() , favArt);
        listFav.setAdapter(myAdapter);




        return finalview;

    }


    public void retriveAll(){

        Cursor res = MyDB.getAllData();

        favArt.clear();

        Log.e("store size" , String.valueOf(res.getCount()));

        if(res.getCount() == 0){

            return ;
        }

        String item0 , item1 , item2 , item3 = null;


        while(res.moveToNext()){

            item0 = res.getString(1);
            item1 = res.getString(2);
            item2 = res.getString(3);


            favArt.add(new FavouriteArtists(item0 , item1 , item2));


        }







    }

}
