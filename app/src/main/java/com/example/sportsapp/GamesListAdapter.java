// -----------------------------------
// Class: GamesListAdapter
// Author: Jessica Cao
// Description: Adapter for the custom list view for games.
// -----------------------------------

package com.example.sportsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GamesListAdapter extends BaseAdapter {

    private ArrayList<Game> listData;
    private LayoutInflater layoutInflater;

    public GamesListAdapter(Context aContext, ArrayList<Game> listData) {
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
            v = layoutInflater.inflate(R.layout.list_row_games, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.gameName);
            holder.uDate = (TextView) v.findViewById(R.id.date);
            holder.uTime = (TextView) v.findViewById(R.id.time);
            holder.uVenue = (TextView) v.findViewById(R.id.venue);
            holder.uCountry = (TextView) v.findViewById(R.id.country);
            holder.uStatus = (TextView) v.findViewById(R.id.gameStatus);
            holder.uGameImage = (ImageView) v.findViewById(R.id.imageViewGame);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).gameName);
        holder.uDate.setText(listData.get(position).date);
        holder.uTime.setText(listData.get(position).time);
        holder.uVenue.setText(listData.get(position).venue);
        holder.uCountry.setText(listData.get(position).country);
        holder.uStatus.setText(listData.get(position).status);
        Picasso.get().load(listData.get(position).thumbUrl).into(holder.uGameImage);
        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uDate;
        TextView uTime;
        TextView uVenue;
        TextView uCountry;
        TextView uStatus;
        ImageView uGameImage;
    }
}
