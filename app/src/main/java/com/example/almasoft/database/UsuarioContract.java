package com.example.almasoft.database;

import android.provider.BaseColumns;

public class UsuarioContract {

    private UsuarioContract() {}

    public static abstract class UsuarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String APELLIDO_P = "apellidoP";
        public static final String APELLIDO_M = "apellidoM";
        public static final String COD_USUARIO = "codUsuario";
        public static final String PASSWORD = "password";
    }
}
