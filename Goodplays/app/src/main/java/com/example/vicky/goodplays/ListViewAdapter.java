package com.example.vicky.goodplays;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<SearchContent> SearchList;
    private ArrayList<SearchContent> arraylist;

    public ListViewAdapter(Context context, ArrayList<SearchContent> searchItems) {
        mContext = context;
        SearchList = new ArrayList<SearchContent>();
        SearchList = searchItems;
        inflater = LayoutInflater.from(mContext);
        arraylist = new ArrayList<SearchContent>();
        arraylist = searchItems;
        Log.e("run check" , "check");

    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return SearchList.size();
    }

    @Override
    public SearchContent getItem(int position) {
        return SearchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(SearchList.get(position).getName());
        return view;
    }

    // Filter Class
    public void filter(String charText) {

        Log.e("value of type" , charText);
        charText = charText.toLowerCase(Locale.getDefault());
        Log.e("size of original list" , String.valueOf(arraylist.size()));
        Log.e("value of search string" , charText);

        SearchList = new ArrayList<>();

        if (charText.length() == 0) {
            SearchList.addAll(arraylist);
        } else {
            for (SearchContent wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    SearchList.add(wp);
                }
            }
        }


        Log.e("size of original list" , String.valueOf(arraylist.size()));

        Log.e("size of arrayList" , String.valueOf(SearchList.size()));
        notifyDataSetChanged();




    }
}
