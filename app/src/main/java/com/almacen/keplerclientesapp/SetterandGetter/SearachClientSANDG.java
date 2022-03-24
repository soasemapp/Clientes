package com.almacen.keplerclientesapp.SetterandGetter;

public class SearachClientSANDG {
    String userCliente;
    String nombreCliente;

    public SearachClientSANDG(String userCliente, String nombreCliente) {
        this.userCliente = userCliente;
        this.nombreCliente = nombreCliente;
    }

    public String getUserCliente() {
        return userCliente;
    }

    public void setUserCliente(String userCliente) {
        this.userCliente = userCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
