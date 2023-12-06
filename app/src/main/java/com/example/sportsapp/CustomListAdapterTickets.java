package com.example.sportsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomListAdapterTickets extends RecyclerView.Adapter<CustomListAdapterTickets.ViewHolder> {
        private ArrayList<Ticket> ticketModalArrayList;
        private ArrayList<Game> gameArrayList;
        private Context context;

        public CustomListAdapterTickets(ArrayList<Ticket> ticketModalArrayList, ArrayList<Game> gameArrayList, Context context) {
            this.ticketModalArrayList = ticketModalArrayList;
            this.gameArrayList = gameArrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_tickets, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Ticket ticket = ticketModalArrayList.get(position);
            Game game = gameArrayList.get(position);
            Glide.with(context)
                    .load(game.thumbUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.gameImgTV);
            holder.gameNameTV.setText(game.gameName);
            holder.tickeyQuantityTV.setText(String.valueOf(ticket.getTicketQuantity()));
            holder.ticketTotalTV.setText(String.valueOf(ticket.getTotal()));
            holder.gameLocationTV.setText(game.venue);
            holder.gameDateTV.setText(game.date);
        }

        @Override
        public int getItemCount() {
            return ticketModalArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView gameNameTV, tickeyQuantityTV, ticketTotalTV, gameLocationTV, gameDateTV;
            private ImageView gameImgTV;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                gameNameTV = itemView.findViewById(R.id.idGameName);
                gameImgTV = itemView.findViewById(R.id.idGameImg);
                tickeyQuantityTV = itemView.findViewById(R.id.idTicketQuantity);
                ticketTotalTV = itemView.findViewById(R.id.idTicketTotal);
                gameLocationTV = itemView.findViewById(R.id.idGameLocation);
                gameDateTV = itemView.findViewById(R.id.idGameDate);
            }
        }
    }
