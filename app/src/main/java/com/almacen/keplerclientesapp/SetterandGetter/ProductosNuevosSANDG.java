package com.almacen.keplerclientesapp.SetterandGetter;

public class ProductosNuevosSANDG {
    String clave;
    String Descripcion;
    String Tipo;
    String FotoTipo;
    String FotoLinea;

    public ProductosNuevosSANDG(String clave, String descripcion, String tipo, String fotoTipo, String fotoLinea) {
        this.clave = clave;
        Descripcion = descripcion;
        Tipo = tipo;
        FotoTipo = fotoTipo;
        FotoLinea = fotoLinea;
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

    public String getFotoTipo() {
        return FotoTipo;
    }

    public void setFotoTipo(String fotoTipo) {
        FotoTipo = fotoTipo;
    }

    public String getFotoLinea() {
        return FotoLinea;
    }

    public void setFotoLinea(String fotoLinea) {
        FotoLinea = fotoLinea;
    }
}
