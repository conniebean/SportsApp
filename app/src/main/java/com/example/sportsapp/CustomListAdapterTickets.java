// -----------------------------------
// Class: CustomListAdapterTickets
// Author: Ava Schembri-Kress
// Description: Adapter for the custom list view for tickets.
// -----------------------------------
package com.example.sportsapp;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomListAdapterTickets extends BaseAdapter {
    private ArrayList<Ticket> ticketModalArrayList;
    private ArrayList<Game> gameArrayList;
    private LayoutInflater layoutInflater;

    public CustomListAdapterTickets(Context aContext, ArrayList<Ticket> ticketModalArrayList, ArrayList<Game> gameArrayList) {
        this.ticketModalArrayList = ticketModalArrayList;
        this.gameArrayList = gameArrayList;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return ticketModalArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketModalArrayList.get(position);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View v, ViewGroup vg) {
        ViewHolder holder;

        if (v == null) {
            v = layoutInflater.inflate(R.layout.list_row_tickets, null);
            holder = new ViewHolder();
            holder.gameNameTV = (TextView) v.findViewById(R.id.idGameName);
            holder.gameImgTV = (ImageView) v.findViewById(R.id.idGameImg);
            holder.tickeyQuantityTV = (TextView) v.findViewById(R.id.idTicketQuantity);
            holder.ticketTotalTV = (TextView) v.findViewById(R.id.idTicketTotal);
            holder.gameLocationTV = (TextView) v.findViewById(R.id.idGameLocation);
            holder.gameDateTV = (TextView) v.findViewById(R.id.idGameDate);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.gameNameTV.setText(gameArrayList.get(position).gameName);
        holder.tickeyQuantityTV.setText(String.valueOf("Tickets: " + ticketModalArrayList.get(position).ticketQuantity));
        holder.ticketTotalTV.setText(String.valueOf("Total: $" + (int) ticketModalArrayList.get(position).getTotal()));
        holder.gameLocationTV.setText(gameArrayList.get(position).venue);
        holder.gameDateTV.setText(gameArrayList.get(position).date);
        Picasso.get().load(gameArrayList.get(position).thumbUrl).into(holder.gameImgTV);
        return v;
    }

    static class ViewHolder {
        TextView gameNameTV;
        ImageView gameImgTV;
        TextView tickeyQuantityTV;
        TextView ticketTotalTV;
        TextView gameLocationTV;
        TextView gameDateTV;
    }

}

