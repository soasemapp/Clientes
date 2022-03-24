package com.almacen.keplerclientesapp.SetandGet;


import org.ksoap2.serialization.SoapObject;

public class SetGetListProductos2 {
    String Producto;
String precio_base;
String precio_ajuste;
String sucursal;
String existencia;
String nomSucursal;
String Cantidad;


    public SetGetListProductos2(String producto, String precio_base, String precio_ajuste, String sucursal, String existencia, String nomSucursal, String cantidad) {
        Producto = producto;
        this.precio_base = precio_base;
        this.precio_ajuste = precio_ajuste;
        this.sucursal = sucursal;
        this.existencia = existencia;
        this.nomSucursal = nomSucursal;
        Cantidad = cantidad;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getPrecio_base() {
        return precio_base;
    }

    public void setPrecio_base(String precio_base) {
        this.precio_base = precio_base;
    }

    public String getPrecio_ajuste() {
        return precio_ajuste;
    }

    public void setPrecio_ajuste(String precio_ajuste) {
        this.precio_ajuste = precio_ajuste;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getNomSucursal() {
        return nomSucursal;
    }

    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}