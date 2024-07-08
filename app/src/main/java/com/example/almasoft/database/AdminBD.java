package com.example.almasoft.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import com.example.almasoft.database.UsuarioContract.UsuarioEntry;

import androidx.annotation.Nullable;

public class AdminBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "almasoft.db";

    //Constructor del SQlite
    public AdminBD(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    //OnCreate de SQlite
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + UsuarioEntry.TABLE_NAME + " ("
                + UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UsuarioEntry.ID + " TEXT NOT NULL,"
                + UsuarioEntry.NOMBRE + " TEXT NOT NULL,"
                + UsuarioEntry.APELLIDO_P + " TEXT NOT NULL,"
                + UsuarioEntry.APELLIDO_M + " TEXT NOT NULL,"
                + UsuarioEntry.COD_USUARIO + " TEXT NOT NULL,"
                + UsuarioEntry.PASSWORD + " TEXT NOT NULL,"
                + "UNIQUE (" + UsuarioEntry.ID + "))");
        }
        //On Upgrade de SQlite
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){

        }
    }


