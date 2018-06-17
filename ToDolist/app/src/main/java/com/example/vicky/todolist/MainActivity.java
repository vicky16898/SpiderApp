package com.example.vicky.todolist;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView list;
    public CustomArrayAdapter adapter;
    public ArrayList<NotesContent> object;
    public BackgroundTask mytask;
    public DatabaseHelper db;
    public int objectPos;







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buttonicon , menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_note){


            Intent intent = new Intent(MainActivity.this , Main2Activity.class);
            MainActivity.this.startActivityForResult(intent , 1);

            return true;


        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String title = data.getStringExtra("task");
                String date = data.getStringExtra("date");
                int id = data.getIntExtra("_id" , 0);

                mytask = new BackgroundTask(MainActivity.this);
                mytask.execute(String.valueOf(id) , title , date );




                adapter.add(new NotesContent(id , title , date));
                list.setAdapter(adapter);






            }
        }

        else if(requestCode == 2){

            if(resultCode == RESULT_OK){


                String title1 = data.getStringExtra("task");
                String date1 = data.getStringExtra("date");
                int id1 = data.getIntExtra("_id" , 0);


                object.remove(objectPos);
                db.deleteData(id1);


                mytask = new BackgroundTask(MainActivity.this);
                mytask.execute(String.valueOf(id1) , title1 , date1 );

                object.add(objectPos , new NotesContent(id1 , title1 , date1));
                list.setAdapter(adapter);














            }









        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        list = (ListView)findViewById(R.id.list);
        object = new ArrayList<NotesContent>();

        adapter = new CustomArrayAdapter(MainActivity.this , object);
        db = new DatabaseHelper(MainActivity.this);
        retriveData();






        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                final int tempId = object.get(position).id;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Delete!!");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        object.remove(position);

                        list.setAdapter(adapter);

                        db.deleteData(tempId);

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();



















                return true;
            }
        });




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                objectPos = position;
                int temp = object.get(position).id;



                Intent updateIntent = new Intent(MainActivity.this , upDateAct.class);
                updateIntent.putExtra("content" , object.get(position).details);
                updateIntent.putExtra("id_data" ,temp);



                MainActivity.this.startActivityForResult(updateIntent , 2);





            }
        });








    }

    public void retriveData(){


        Cursor res = db.getALLdata();

        if(res.getCount()!=0) {

            while (res.moveToNext()) {

                String id = res.getString(0);
                Log.d("Id_data" , id);
                String task = res.getString(1);
                String date = res.getString(2);



                adapter.add(new NotesContent(Integer.parseInt(id) , task , date));
                list.setAdapter(adapter);






            }
        }



    }






}
