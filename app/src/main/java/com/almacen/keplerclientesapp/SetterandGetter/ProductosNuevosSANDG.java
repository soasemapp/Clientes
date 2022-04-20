package com.almacen.keplerclientesapp.SetterandGetter;

public class ProductosNuevosSANDG {
    String clave;
    String Descripcion;
    String Tipo;

    public ProductosNuevosSANDG(String clave, String descripcion, String tipo) {
        this.clave = clave;
        Descripcion = descripcion;
        Tipo = tipo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
