package com.example.vicky.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<NotesContent> {


    public ArrayList<NotesContent> subject = new ArrayList<NotesContent>();
    public CustomArrayAdapter(Context context,  ArrayList<NotesContent> objects) {
        super(context, 0 ,objects);
        subject = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.edit_content, null);

        TextView content = (TextView)v.findViewById(R.id.content);
        TextView date1 = (TextView)v.findViewById(R.id.date1);


        content.setText(subject.get(position).details);
        date1.setText(subject.get(position).date);






        return  v;
    }
}
