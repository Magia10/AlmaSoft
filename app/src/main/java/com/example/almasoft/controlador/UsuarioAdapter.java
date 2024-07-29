package com.example.almasoft.controlador;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.almasoft.R;
import com.example.almasoft.database.UsuarioContract;

public class UsuarioAdapter extends CursorAdapter {

    public UsuarioAdapter(Context context, Cursor c, int flags) {super(context, c, 0);}

    //crea elementos completos y agrega a una lista
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_usuario, viewGroup, false);
    }

    //trae los elementos de la base de datos
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //referencias interfaz
        TextView nombre = (TextView) view.findViewById(R.id.tv_nombre);
        TextView usuario = (TextView) view.findViewById(R.id.tv_usuario);
        TextView password = (TextView) view.findViewById(R.id.tv_password);

        //obtencion de datos BD
        String name = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.NOMBRE));
        String usu = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.COD_USUARIO));
        String pass = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.PASSWORD));

        //asignar datos
        nombre.setText("Nombre: " + name);
        usuario.setText("Usuario: " + usu );
        password.setText("Contraseña: " + pass);
    }
}
