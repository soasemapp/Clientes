package com.almacen.keplerclientesapp.SetterandGetter;

public class ExistClasifiSANDG {
    String Producto;
    String Precio;
    String Descripcion;
    String CodigoBar;
    String Existencia1;

    public ExistClasifiSANDG(String producto, String precio, String descripcion, String codigoBar, String existencia1) {
        Producto = producto;
        Precio = precio;
        Descripcion = descripcion;
        CodigoBar = codigoBar;
        Existencia1 = existencia1;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCodigoBar() {
        return CodigoBar;
    }

    public void setCodigoBar(String codigoBar) {
        CodigoBar = codigoBar;
    }

    public String getExistencia1() {
        return Existencia1;
    }

    public void setExistencia1(String existencia1) {
        Existencia1 = existencia1;
    }
}

