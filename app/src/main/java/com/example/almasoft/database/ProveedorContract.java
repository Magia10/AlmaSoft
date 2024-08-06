package com.example.almasoft.database;

import android.provider.BaseColumns;

public final class ProveedorContract {

    // Previene la instanciación de esta clase
    private ProveedorContract() {}

    public static final class ProveedorEntry implements BaseColumns {
        public static final String TABLE_NAME = "proveedores";
        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String RUC = "ruc";
        public static final String DIRECCION = "direccion";
        public static final String CIUDAD = "ciudad";
        public static final String LOGO = "logo";  // Columna para el logo
        public static final String CONTRATO = "contrato";  // Columna para el contrato
    }
}

