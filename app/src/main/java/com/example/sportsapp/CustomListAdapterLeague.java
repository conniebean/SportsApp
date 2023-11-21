package com.example.sportsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterLeague extends BaseAdapter {

    private ArrayList<League> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapterLeague(Context aContext, ArrayList<League> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {

        ViewHolder holder;

        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row_leagues, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).name);
        return v;
    } // end getView()

    static class ViewHolder {
        TextView uName;
    } // end inner class

} // end CustomListAdapter
