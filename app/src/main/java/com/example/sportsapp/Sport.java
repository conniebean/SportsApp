package com.example.sportsapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sports")
public class Sport {
    @PrimaryKey
    public int id;
    public String name;
}
