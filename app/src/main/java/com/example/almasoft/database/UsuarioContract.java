package com.example.almasoft.database;

import android.provider.BaseColumns;

public class UsuarioContract {

    public static abstract class UsuarioEntry implements BaseColumns{
        //PLANTILLA PARA CREAR LA TABLA USUARIOS
        //Las clases abstractas es un modelo para crear más clases
        //BaseColumns es el miniadministrador de la BD 
        public static final String TABLE_NAME = "usuario";
        public static final String ID="id";
        public static final String NOMBRE="nombre";
        public static final String APELLIDO_P="apellidoP";
        public static final String APELLIDO_M="apellidoM";
        public static final String COD_USUARIO="codUsuario";
        public static final String PASSWORD ="password";
    }
}
