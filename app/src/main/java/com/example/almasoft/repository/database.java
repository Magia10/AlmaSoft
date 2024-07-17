package com.example.almasoft.repository;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.almasoft.model.Product;

@Database(entities = {Product.class}, version = 1)
public abstract class database extends RoomDatabase{
    private static database instance;

    public abstract ProductDao productDao();

    public static synchronized database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            database.class, "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
