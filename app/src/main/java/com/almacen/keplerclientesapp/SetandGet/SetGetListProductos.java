package com.almacen.keplerclientesapp.SetandGet;


public class SetGetListProductos {
String Productos;
String Descripcion;
String Marca;
String Modelo;
String Litros;
String Year;
String PrecioBase;
String PrecioAjuste;

    public SetGetListProductos(String productos, String descripcion, String marca, String modelo, String litros, String year, String precioBase, String precioAjuste) {
        Productos = productos;
        Descripcion = descripcion;
        Marca = marca;
        Modelo = modelo;
        Litros = litros;
        Year = year;
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

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getLitros() {
        return Litros;
    }

    public void setLitros(String litros) {
        Litros = litros;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
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