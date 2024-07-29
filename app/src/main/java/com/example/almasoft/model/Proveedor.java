package com.example.almasoft.model;

public class Proveedor {
    private int id;
    private String nombre;
    private String ruc;
    private String direccion;
    private String ciudad;

    // Constructor
    public Proveedor(int id, String nombre, String ruc, String direccion, String ciudad) {
        this.id = id;
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    // Constructor sin ID, para la creación
    public Proveedor(String nombre, String ruc, String direccion, String ciudad) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
