// -----------------------------------
// Class: PlayerListAdapter
// Author: Jessica Cao
// Description: Adapter for the custom list view for Players.
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

public class PlayerListAdapter extends BaseAdapter {

    private ArrayList<Player> listData;
    private LayoutInflater layoutInflater;

    public PlayerListAdapter(Context aContext, ArrayList<Player> listData) {
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
            v = layoutInflater.inflate(R.layout.list_row_players, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uStatus = (TextView) v.findViewById(R.id.playerStatus);
            holder.uHeight = (TextView) v.findViewById(R.id.playerHeight);
            holder.uNationality = (TextView) v.findViewById(R.id.playerNationality);
            holder.uPosition = (TextView) v.findViewById(R.id.playerPosition);
            holder.uPlayerImage = (ImageView) v.findViewById(R.id.imageViewPlayer);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.uName.setText(listData.get(position).name);
        holder.uStatus.setText(listData.get(position).status);
        holder.uHeight.setText(listData.get(position).height);
        holder.uNationality.setText(listData.get(position).nationality);
        holder.uPosition.setText(listData.get(position).position);
        Picasso.get().load(listData.get(position).thumbUrl).into(holder.uPlayerImage);
        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uNationality;
        TextView uStatus;
        TextView uHeight;
        TextView uPosition;
        ImageView uPlayerImage;
    }
}
