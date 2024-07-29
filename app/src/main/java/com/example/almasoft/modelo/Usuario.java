package com.example.almasoft.modelo;

import android.content.ContentValues;

import com.example.almasoft.database.UsuarioContract;

public class Usuario {
    //Atributos
     private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String codUsuario;
    private String password;

    //inicializacion - contructor

    public Usuario(String nombre, String apellidoP, String apellidoM, String codUsuario, String password) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.codUsuario = codUsuario;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //el contenedor es un objeto que va contener valores
    public ContentValues toContentValues(){
        ContentValues contenedor = new ContentValues();
        contenedor.put(UsuarioContract.UsuarioEntry.NOMBRE, nombre);
        contenedor.put(UsuarioContract.UsuarioEntry.APELLIDO_P, apellidoP);
        contenedor.put(UsuarioContract.UsuarioEntry.APELLIDO_M, apellidoM);
        contenedor.put(UsuarioContract.UsuarioEntry.COD_USUARIO, codUsuario);
        contenedor.put(UsuarioContract.UsuarioEntry.PASSWORD, password);
        return  contenedor;
    }
}
