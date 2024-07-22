package com.example.almasoft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.almasoft.database.UsuarioContract.UsuarioEntry;
import com.example.almasoft.model.Usuario;

public class AdminBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "almasoft.db";

    public AdminBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USUARIO_TABLE = "CREATE TABLE " + UsuarioEntry.TABLE_NAME + " ("
                + UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UsuarioEntry.NOMBRE + " TEXT NOT NULL, "
                + UsuarioEntry.APELLIDO_P + " TEXT NOT NULL, "
                + UsuarioEntry.APELLIDO_M + " TEXT NOT NULL, "
                + UsuarioEntry.COD_USUARIO + " TEXT NOT NULL, "
                + UsuarioEntry.PASSWORD + " TEXT NOT NULL)";

        db.execSQL(SQL_CREATE_USUARIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Puedes implementar lógica para actualizar la base de datos si cambia la versión
        // por ejemplo, eliminar tablas antiguas y recrearlas o migrar datos
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioEntry.TABLE_NAME);
        onCreate(db);
    }

    public long guardarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        long newRowId = db.insert(UsuarioEntry.TABLE_NAME, null, usuario.toContentValues());
        db.close();
        return newRowId;
    }

    public boolean validarUsuario(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {UsuarioEntry._ID};
        String selection = UsuarioEntry.COD_USUARIO + "=? AND " + UsuarioEntry.PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                UsuarioEntry.TABLE_NAME,
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
}
