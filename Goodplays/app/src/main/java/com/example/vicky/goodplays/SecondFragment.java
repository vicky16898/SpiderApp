package com.example.vicky.goodplays;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondFragment extends Fragment {

    // Store instance variables
    private String title;
    private int page;
    public TextView topartists;
    public ListView list2;
    public ProgressBar progressBar2;
    public Boolean check2 = false;
    public ArrayList<TopArtists> artists;
    public CustomArtistArrayAdapter adapterArtist;
    public MyTask backTask;
    public MyDatabaseArtist myDB;
    public ArrayList<Boolean> clickcheck2;
    public int lengthArtist = 10000;




    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragmentSecond = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        artists = new ArrayList<TopArtists>();
        check2 = haveNetworkConnection();
        myDB = new MyDatabaseArtist(getContext());

        clickcheck2 = new ArrayList<Boolean>();

        for(int i =0 ; i<50 ; i++){

            clickcheck2.add(i , false);

        }


        if(check2 == true){

      backTask = new MyTask(getContext());
      backTask.execute("http://api.musixmatch.com/ws/1.1/chart.artists.get?apikey=f295ccd2e437632764f6cf1d8998b0b2&format=json&domain=www.musicapp16.com/?page=1&page_size=50&country=in");


        }

        else{

            Toast.makeText(getContext(),"Internet connectivity issues!",
                    Toast.LENGTH_SHORT).show();
        }






    }



    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, container, false);
        topartists = (TextView)view.findViewById(R.id.topartists);
        list2 = (ListView)view.findViewById(R.id.list2);
        progressBar2 = (ProgressBar)view.findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);

        adapterArtist = new CustomArtistArrayAdapter(getContext() , artists);
        list2.setAdapter(adapterArtist);

        if(lengthArtist == artists.size())
        {
            progressBar2.setVisibility(View.GONE);


        }





        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Log.e("click check2" , "checked");

                Intent myintent = new Intent(getContext() , ArtistActivity.class);

                myintent.putExtra("ArtistName" , artists.get(position).ArtistName);
                myintent.putExtra("Rating" , artists.get(position).Rating);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)artists.get(position).genre);
                myintent.putExtra("BUNDLE",args);

                startActivity(myintent);









            }
        });

        list2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if(clickcheck2.get(position) == false) {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    Boolean insertData = myDB.InsertData(artists.get(position).ArtistName,

                                            artists.get(position).genre.get(0),

                                            artists.get(position).Rating


                                    );


                                    Log.e("Store check", String.valueOf(insertData));
                                    Cursor res = myDB.getAllData();
                                    Log.e("size of the db", String.valueOf(res.getCount()));
                                    clickcheck2.remove(position);
                                    clickcheck2.add(position , true);


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure want to add this to your favourites?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();


                }

                else{



                    Toast.makeText(getActivity(), "Already in the favourite list",
                            Toast.LENGTH_LONG).show();
                }


                return true;

            }

        });





        return view;

    }


    public class MyTask extends AsyncTask<String , Integer , String>{



        public String server_response2;
        public int counter;
        public Context context;



        public ArrayList<String> genre_array;

        public MyTask(Context ctx){

            context = ctx;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();





        }


        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response2 = readStream2(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response2);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jobj = null;
            try {
                jobj = new JSONObject(server_response2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String message = jobj.getString("message");
                Log.d("message" , message);
                JSONObject mess = new JSONObject(message);
                String head = mess.getString("header");
                Log.e("head" , head);

                String body = mess.getString("body");
                Log.e("body" , body);
                JSONObject trackArtist = new JSONObject(body);
                JSONArray artistArray =trackArtist.getJSONArray("artist_list");
                Log.d("length" , String.valueOf(artistArray.length()));
                lengthArtist = artistArray.length();

                for(int i =0 ;i<artistArray.length();i++){

                    JSONObject indvidual = new JSONObject(artistArray.getJSONObject(i).getString("artist"));



                    Log.d("artist name" , indvidual.getString("artist_name"));


                    counter++;
                    Log.e("count" , String.valueOf(counter));

                    String genreList = indvidual.getString("primary_genres");
                    JSONObject primGenre = new JSONObject(genreList);

                    JSONArray music_array = null;

                    music_array = primGenre.getJSONArray("music_genre_list");

                    int size = music_array.length();

                    genre_array = new ArrayList<String>();

                    if(size != 0){

                        for(int j = 0;j<size;j++ ){

                            JSONObject ind = new JSONObject(music_array.getJSONObject(j).getString("music_genre"));

                            genre_array.add(ind.getString("music_genre_name"));

                            Log.d("genreS" , genre_array.get(j));


                        }


                    }

                    else{

                        genre_array.add("Currently unavailable");

                    }



                            Log.e("rating" , indvidual.getString("artist_rating"));


                    artists.add(new TopArtists(indvidual.getString("artist_name") ,

                            genre_array ,

                            indvidual.getString("artist_rating")

                            ));






                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }





        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Response", "" + server_response2);
            Log.e("List length" , String.valueOf(artists.size()));


            if(artists.size() == lengthArtist)
                progressBar2.setVisibility(View.GONE);

        }
    }


    private String readStream2(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }




}
