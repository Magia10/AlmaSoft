package com.example.almasoft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.almasoft.model.Proveedor;
import com.example.almasoft.model.Usuario;

public class AdminBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "almasoft.db";

    public AdminBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String SQL_CREATE_USUARIO_TABLE = "CREATE TABLE " + UsuarioContract.UsuarioEntry.TABLE_NAME + " ("
                + UsuarioContract.UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsuarioContract.UsuarioEntry.NOMBRE + " TEXT NOT NULL, "
                + UsuarioContract.UsuarioEntry.APELLIDO_P + " TEXT NOT NULL, "
                + UsuarioContract.UsuarioEntry.APELLIDO_M + " TEXT NOT NULL, "
                + UsuarioContract.UsuarioEntry.COD_USUARIO + " TEXT NOT NULL, "
                + UsuarioContract.UsuarioEntry.PASSWORD + " TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_USUARIO_TABLE);

        // Crear tabla de proveedores
        String SQL_CREATE_PROVEEDOR_TABLE = "CREATE TABLE " + ProveedorContract.ProveedorEntry.TABLE_NAME + " ("
                + ProveedorContract.ProveedorEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProveedorContract.ProveedorEntry.NOMBRE + " TEXT NOT NULL, "
                + ProveedorContract.ProveedorEntry.RUC + " TEXT NOT NULL, "
                + ProveedorContract.ProveedorEntry.DIRECCION + " TEXT NOT NULL, "
                + ProveedorContract.ProveedorEntry.CIUDAD + " TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_PROVEEDOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioContract.UsuarioEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProveedorContract.ProveedorEntry.TABLE_NAME);
        onCreate(db);
    }

    // Métodos para manejar usuarios

    public long guardarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(UsuarioContract.UsuarioEntry.TABLE_NAME, null, usuario.toContentValues());
        db.close();
        return newRowId;
    }

    public boolean validarUsuario(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {UsuarioContract.UsuarioEntry._ID};
        String selection = UsuarioContract.UsuarioEntry.COD_USUARIO + "=? AND " + UsuarioContract.UsuarioEntry.PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                UsuarioContract.UsuarioEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    // Métodos para manejar proveedores

    public long guardarProveedor(String nombre, String ruc, String direccion, String ciudad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProveedorContract.ProveedorEntry.NOMBRE, nombre);
        values.put(ProveedorContract.ProveedorEntry.RUC, ruc);
        values.put(ProveedorContract.ProveedorEntry.DIRECCION, direccion);
        values.put(ProveedorContract.ProveedorEntry.CIUDAD, ciudad);

        long newRowId = db.insert(ProveedorContract.ProveedorEntry.TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }

    public Proveedor obtenerProveedorPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                ProveedorContract.ProveedorEntry.ID,
                ProveedorContract.ProveedorEntry.NOMBRE,
                ProveedorContract.ProveedorEntry.RUC,
                ProveedorContract.ProveedorEntry.DIRECCION,
                ProveedorContract.ProveedorEntry.CIUDAD
        };

        String selection = ProveedorContract.ProveedorEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                ProveedorContract.ProveedorEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Proveedor proveedor = new Proveedor(
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.NOMBRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.RUC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.DIRECCION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.ProveedorEntry.CIUDAD))
            );
            cursor.close();
            db.close();
            return proveedor;
        } else {
            if (cursor != null) cursor.close();
            db.close();
            return null;
        }
    }

    public Cursor obtenerProveedores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                ProveedorContract.ProveedorEntry.TABLE_NAME,
                null, // Selecciona todas las columnas
                null,
                null,
                null,
                null,
                null
        );
    }
}
