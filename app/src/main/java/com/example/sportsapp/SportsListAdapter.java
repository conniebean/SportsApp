package com.example.sportsapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SportsListAdapter extends BaseAdapter {

    private ArrayList<Sport> listData;
    private LayoutInflater layoutInflater;

    public SportsListAdapter(Context aContext, ArrayList<Sport> listData) {
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
            v = layoutInflater.inflate(R.layout.list_row_sports, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uImageUrl = (ImageView) v.findViewById(R.id.imageViewSport);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).name);
        Picasso.get().load(listData.get(position).imageUrl).into(holder.uImageUrl);
        return v;
    }

    static class ViewHolder {
        TextView uName;
        ImageView uImageUrl;
    }
}
