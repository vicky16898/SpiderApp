package com.example.vicky.goodplays;


import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {

    private String title;
    private int page;
    public MyDatabaseHelper db;
    public CustomFavouriteTrackAdapter myAdapter;
    public ArrayList<FavouriteTrackClass> myFavList;
    public boolean flag = false;

    public static ThirdFragment newInstance(int page, String title) {


       ThirdFragment fragmentThird = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentThird.setArguments(args);
        return fragmentThird;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

        myFavList = new ArrayList<FavouriteTrackClass>();
        db = new MyDatabaseHelper(getContext());
        Log.e("fragment check" , String.valueOf(myFavList.size()));
        retriveAll();







    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment, container, false);
        ListView list3 = (ListView)view.findViewById(R.id.list3);

        Log.e("fragment check" , "check");



        Log.e("size check" , String.valueOf(myFavList.size()));



        myFavList = new ArrayList<FavouriteTrackClass>();
        db = new MyDatabaseHelper(getContext());
        Log.e("fragment check" , String.valueOf(myFavList.size()));
        retriveAll();

        myAdapter = new CustomFavouriteTrackAdapter(getContext() , myFavList);
        list3.setAdapter(myAdapter);


        return view;
    }

    public void retriveAll(){

        Cursor res = db.getAllData();

        myFavList.clear();

        Log.e("store size" , String.valueOf(res.getCount()));

        if(res.getCount() == 0){

            return ;
        }

        String item0 , item1 , item2 , item3 = null;


        while(res.moveToNext()){

            item0 = res.getString(1);
            item1 = res.getString(2);
            item2 = res.getString(3);
            item3 = res.getString(4);

            myFavList.add(new FavouriteTrackClass(item0 , item1 , item2 , item3));


        }







    }
}
