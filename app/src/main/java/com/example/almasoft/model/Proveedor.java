package com.example.almasoft.model;

public class Proveedor {
    private int id;
    private String nombre;
    private String ruc;
    private String direccion;
    private String ciudad;
    private byte[] logo;  // Logo en formato byte array
    private byte[] contrato;  // Contrato en formato byte array

    // Constructor
    public Proveedor(int id, String nombre, String ruc, String direccion, String ciudad, byte[] logo, byte[] contrato) {
        this.id = id;
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.logo = logo;
        this.contrato = contrato;
    }

    // Constructor sin ID, para la creación
    public Proveedor(String nombre, String ruc, String direccion, String ciudad, byte[] logo, byte[] contrato) {
        this.nombre = nombre;
        this.ruc = ruc;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.logo = logo;
        this.contrato = contrato;
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public byte[] getContrato() {
        return contrato;
    }

    public void setContrato(byte[] contrato) {
        this.contrato = contrato;
    }
}
