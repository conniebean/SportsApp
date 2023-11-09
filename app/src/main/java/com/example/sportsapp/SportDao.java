package com.example.sportsapp;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SportDao {
    //this interface is used to grabbing info from the database
    @Query("SELECT * FROM sports")
    List<Sport> getAll();
}
