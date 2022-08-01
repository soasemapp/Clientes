package com.almacen.keplerclientesapp.SetandGet;


public class SetGetListProductos {
String Productos;
String Descripcion;
String PrecioBase;
String PrecioAjuste;

    public SetGetListProductos(String productos, String descripcion,  String precioBase, String precioAjuste) {
        Productos = productos;
        Descripcion = descripcion;
        PrecioBase = precioBase;
        PrecioAjuste = precioAjuste;
    }

    public String getProductos() {
        return Productos;
    }

    public void setProductos(String productos) {
        Productos = productos;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecioBase() {
        return PrecioBase;
    }

    public void setPrecioBase(String precioBase) {
        PrecioBase = precioBase;
    }

    public String getPrecioAjuste() {
        return PrecioAjuste;
    }

    public void setPrecioAjuste(String precioAjuste) {
        PrecioAjuste = precioAjuste;
    }
}