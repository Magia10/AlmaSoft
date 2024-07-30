package com.example.almasoft.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.almasoft.model.Movement;
import java.util.List;

@Dao
public interface MovementDao {

    @Insert
    void insert(Movement movement);

    @Update
    void update(Movement movement);

    @Delete
    void delete(Movement movement);

    @Query("SELECT * FROM movement_table")
    LiveData<List<Movement>> getAllMovement();

}
