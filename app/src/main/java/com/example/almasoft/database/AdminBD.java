package com.example.almasoft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import com.example.almasoft.database.UsuarioContract.UsuarioEntry;
import com.example.almasoft.modelo.Usuario;

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
                + UsuarioEntry.NOMBRE + " TEXT NOT NULL,"
                + UsuarioEntry.APELLIDO_P + " TEXT NOT NULL,"
                + UsuarioEntry.APELLIDO_M + " TEXT NOT NULL,"
                + UsuarioEntry.COD_USUARIO + " TEXT NOT NULL,"
                + UsuarioEntry.PASSWORD + " TEXT NOT NULL)");

        guardarUsuario(db ,new Usuario( "Administrador", "", "", "Admin", "admin"));

        }
        //On Upgrade de SQlite
        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){

        }

        //Guadar Usuario
        public long guardarUsuario(SQLiteDatabase db, Usuario usuario){
        return db.insert(
                UsuarioEntry.TABLE_NAME,
                null,
                usuario.toContentValues());
        }

        //ELIMINAR ALUMNO
        public long eliminarUsuario(String id){
        return getReadableDatabase()
                .delete(
                        UsuarioEntry.TABLE_NAME,
                        UsuarioEntry._ID + " = ?",
                        new String[]{id}
                );
        }

    //ACTUALIZAR USUARIO
        public long actualizarUsuario(String id, String nombre, String apellidoP, String apellidoM, String usuario, String password){

        ContentValues datos = new ContentValues();

        if (!nombre.isEmpty()){
            datos.put(UsuarioEntry.NOMBRE, nombre);
        }
        if (!apellidoP.isEmpty()){
            datos.put(UsuarioEntry.APELLIDO_P, apellidoP);
        }
        if (!apellidoM.isEmpty()){
            datos.put(UsuarioEntry.APELLIDO_M, apellidoM);
        }
        if (!usuario.isEmpty()){
            datos.put(UsuarioEntry.COD_USUARIO, usuario);
        }
        if (!password.isEmpty()){
            datos.put(UsuarioEntry.PASSWORD, password);
        }

        return getReadableDatabase()
                .update(UsuarioEntry.TABLE_NAME,
                        datos,
                        UsuarioEntry._ID + " = ?",
                        new String[]{id});
    }


    //obtener todos los usuarios
        public Cursor getAllUsuarios(){
            return  getReadableDatabase()
                    .query(
                            UsuarioEntry.TABLE_NAME,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    );
        }

        public  Cursor getUsuarioById(String id){
        return getReadableDatabase()
                .query(
                        UsuarioEntry.TABLE_NAME,
                        null,
                        UsuarioEntry._ID + " LIKE ?",
                        new String[]{id},
                        null,
                        null,
                        null
                );
        }

    // Método para validar usuario y contraseña
        public boolean validateUser(String codUsuario, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {UsuarioEntry.COD_USUARIO};
            String selection = UsuarioEntry.COD_USUARIO + " = ? AND " + UsuarioEntry.PASSWORD + " = ?";
            String[] selectionArgs = {codUsuario, password};

            Cursor cursor = db.query(UsuarioEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            return cursorCount > 0;
    }

}


