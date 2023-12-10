package com.example.sportsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouritesListAdapter extends BaseAdapter {

    private ArrayList<Favourites> listData;
    private LayoutInflater layoutInflater;

    public FavouritesListAdapter(Context aContext, ArrayList<Favourites> listData) {
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
            v = layoutInflater.inflate(R.layout.list_row_favourites, null);
            holder = new ViewHolder();
            holder.uTeamName = (TextView) v.findViewById(R.id.teamName);
            holder.uViewTeam = (Button) v.findViewById(R.id.btnViewTeam);
            holder.uRemoveTeam = (Button) v.findViewById(R.id.btnRemove);
            holder.uLogo = (ImageView) v.findViewById(R.id.imageViewLogo);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }
        holder.uTeamName.setText(listData.get(position).teamName);
        Picasso.get().load(listData.get(position).logoUrl).into(holder.uLogo);
        return v;
    }

    static class ViewHolder {
        TextView uTeamName;
        Button  uViewTeam;
        Button  uRemoveTeam;
        ImageView uLogo;
    }
}
