package com.example.vicky.goodplays;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstFragment extends Fragment {


    // Store instance variables
    private String title;
    private int page;
    public TextView toptracks;
    public ListView list1;
    public ArrayList<SongDetails> songs;
    public MyBackgroundTask myTask;
    public ProgressBar progressBar;
    public CustomArrayAdapter adapter;
    public Boolean check = false;
    public ArrayList<Boolean> clickcheck;
    public MyDatabaseHelper myDb;
    public int len = 100000;
    public ArrayList<SearchContent> track_Name;
    public Spinner spinner;
    public SearchView searchView;
    public ListViewAdapter mySpinnerAdapter;







    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;

    }


    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        songs = new ArrayList<SongDetails>();

        myDb = new MyDatabaseHelper(getContext());

        check = haveNetworkConnection();

        clickcheck = new ArrayList<Boolean>();

        for(int i =0 ; i<50 ; i++){

            clickcheck.add(i , false);

        }

        track_Name = new ArrayList<SearchContent>();




        if(check == true) {

            myTask = new MyBackgroundTask(getContext());
            myTask.execute("http://api.musixmatch.com/ws/1.1/chart.tracks.get?apikey=f295ccd2e437632764f6cf1d8998b0b2&format=json&domain=www.musicapp16.com/?page=1&page_size=50&country=in&f_has_lyrics=1");
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
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        toptracks = (TextView)view.findViewById(R.id.toptracks);
        list1 = (ListView)view.findViewById(R.id.list1);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        searchView = (SearchView)view.findViewById(R.id.searchview);

        adapter = new CustomArrayAdapter(getContext() , songs );
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setVisibility(View.INVISIBLE);





        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
            popupWindow.setWidth(450);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }


        list1.setAdapter(adapter);
        mySpinnerAdapter = new ListViewAdapter(getContext() , track_Name);
        spinner.setAdapter(mySpinnerAdapter);



        if(len == songs.size())
        {
            progressBar.setVisibility(View.GONE);


        }

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
                Log.d("click check" , "click");
            }
        });










        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {





            @Override
            public boolean onQueryTextSubmit(String query) {


                spinner.performClick();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                String text = newText;



                mySpinnerAdapter.filter(newText);



                return true;

            }
        });












        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("click check" , "checked" + String.valueOf(position));

                Intent myIntent = new Intent(getContext(), trackInfo.class);
                myIntent.putExtra("Title" , songs.get(position).track_name);
                myIntent.putExtra("Artist" , songs.get(position).artistName);
                myIntent.putExtra("Genre" , songs.get(position).genre);
                myIntent.putExtra("Year" , songs.get(position).year_of_release);
                startActivity(myIntent);


            }
        });



        list1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Log.e("flag value" , String.valueOf(clickcheck.get(position)));


                if(clickcheck.get(position) == false) {

                    Log.d("value" , String.valueOf(clickcheck.get(position)));


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                   Boolean insertData =  myDb.InsertData(songs.get(position).track_name
                                            , songs.get(position).artistName,
                                            songs.get(position).genre,
                                            songs.get(position).year_of_release);

                                   Log.e("Store check" , String.valueOf(insertData));
                                    Cursor res = myDb.getAllData();
                                    Log.e("size of the db" , String.valueOf(res.getCount()));




                                    clickcheck.remove(position);
                                    clickcheck.add(position, true);




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

                else
                    Toast.makeText(getActivity(), "Already in the favourite list",
                            Toast.LENGTH_LONG).show();


                    return true;
            }
        });


        return view;
    }







    public class MyBackgroundTask extends AsyncTask<String , Integer , String> {

        String server_response;
        public int count = 0;
        public Context ctx;
        public Bitmap image;

        public int length;
        public MyBackgroundTask(Context ctx){
            this.ctx = ctx;

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
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("CatalogClient", server_response);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jobj = null;
            try {
                jobj = new JSONObject(server_response);
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
                JSONObject track = new JSONObject(body);
                JSONArray track_list =track.getJSONArray("track_list");
                Log.d("length" , String.valueOf(track_list.length()));


                length = track_list.length();
                len = track_list.length();

                for(int i =0 ;i<track_list.length();i++){

                    JSONObject indvidual = new JSONObject(track_list.getJSONObject(i).getString("track"));



                    Log.d("track name" , indvidual.getString("track_name"));

                    track_Name.add(new SearchContent(indvidual.getString("track_name")));
                    Log.e("lenghth of array" , String.valueOf(track_Name.size()));


                    count++;
                    Log.e("count" , String.valueOf(count));

                    try {
                        URL Img_url = new URL(indvidual.getString("album_coverart_100x100"));
                        image = BitmapFactory.decodeStream(Img_url.openConnection().getInputStream());
                    } catch(IOException e) {
                        System.out.println(e);
                    }

                    Log.e("url value" , indvidual.getString("album_coverart_100x100"));



                    String genre = indvidual.getString("primary_genres");
                    JSONObject Genre = new JSONObject(genre);
                    JSONArray genre_sub = null;

                    genre_sub = Genre.getJSONArray("music_genre_list");
                    Log.d("Size" , String.valueOf(genre_sub.length()));

                    JSONObject music_genre;
                    String gEnre;

                    if(genre_sub.length() != 0) {
                        music_genre = new JSONObject(genre_sub.getJSONObject(0).getString("music_genre"));
                        gEnre = music_genre.getString("music_genre_name");

                    }

                    else{

                        gEnre = "currently unavailable";
                    }








                    songs.add(new SongDetails(indvidual.getString("track_name")
                            , image ,
                            indvidual.getString("track_id") ,

                            indvidual.getString("artist_name"),

                            gEnre,

                            indvidual.getString("first_release_date")

                            ));

                    Log.e("genre" , gEnre);




                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("Response", "" + server_response);
            Log.e("List length" , String.valueOf(songs.size()));





            if(songs.size() == length)
                progressBar.setVisibility(View.GONE);



        }



        // Converting InputStream to String

        private String readStream(InputStream in) {
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










}
