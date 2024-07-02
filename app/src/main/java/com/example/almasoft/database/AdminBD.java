package com.example.almasoft.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

import androidx.annotation.Nullable;

public class AdminBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "almasoft.db";
    //TABLAS
    public static final String TABLA_USUARIOS = "USUARIOS";

    //Constructor del SQlite
    public AdminBD(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    //OnCreate de SQlite
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLA_USUARIOS+"(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COD_USUARIO TEXT NOT NULL," +
                "NOMBRE TEXT NOT NULL," +
                "APELLIDO_P TEXT NOT NULL," +
                "APELLIDO_M TEXT NOT NULL," +
                "PASSWORD TEXT NOT NULL)");

    }

    //On Upgrade de SQlite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
