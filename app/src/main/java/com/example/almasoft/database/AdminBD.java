package com.example.almasoft.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.almasoft.database.UsuarioContract.UsuarioEntry;
import com.example.almasoft.model.Proveedor;
import com.example.almasoft.model.Usuario;
import androidx.annotation.Nullable;

public class AdminBD extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "almasoft.db";

    public AdminBD(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

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

    @Override
    public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){

    }

    public long guardarUsuario(SQLiteDatabase db, Usuario usuario){
        return db.insert(
                UsuarioEntry.TABLE_NAME,
                null,
                usuario.toContentValues());
    }

    public long eliminarUsuario(String id){
        return getReadableDatabase().delete(
                UsuarioEntry.TABLE_NAME,
                UsuarioEntry._ID + " = ?",
                new String[]{id}
        );
    }

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

        return getReadableDatabase().update(UsuarioEntry.TABLE_NAME, datos,
                        UsuarioEntry._ID + " = ?", new String[]{id});
    }

    public Cursor getAllUsuarios(){
        return  getReadableDatabase().query(
                UsuarioEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public  Cursor getUsuarioById(String id){
        return getReadableDatabase().query(
                UsuarioEntry.TABLE_NAME,
                null,
                UsuarioEntry._ID + " LIKE ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

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

    public int actualizarProveedor(Proveedor proveedor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProveedorContract.ProveedorEntry.NOMBRE, proveedor.getNombre());
        values.put(ProveedorContract.ProveedorEntry.RUC, proveedor.getRuc());
        values.put(ProveedorContract.ProveedorEntry.DIRECCION, proveedor.getDireccion());
        values.put(ProveedorContract.ProveedorEntry.CIUDAD, proveedor.getCiudad());

        String selection = ProveedorContract.ProveedorEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(proveedor.getId())};

        int count = db.update(
                ProveedorContract.ProveedorEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        db.close();
        return count;
    }

    public int eliminarProveedor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ProveedorContract.ProveedorEntry.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int count = db.delete(ProveedorContract.ProveedorEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
        return count;
    }
}


