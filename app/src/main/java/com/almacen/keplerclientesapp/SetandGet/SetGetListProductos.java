package com.almacen.keplerclientesapp.SetandGet;


public class SetGetListProductos {
    String Productos;
    String Descripcion;
    String linea;
    String PrecioBase;
    String PrecioAjuste;
    String TipoFotos;
    String LineaFotos;

    public SetGetListProductos(String productos, String descripcion, String linea, String precioBase, String precioAjuste, String tipoFotos, String lineaFotos) {
        Productos = productos;
        Descripcion = descripcion;
        this.linea = linea;
        PrecioBase = precioBase;
        PrecioAjuste = precioAjuste;
        TipoFotos = tipoFotos;
        LineaFotos = lineaFotos;
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

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
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

    public String getTipoFotos() {
        return TipoFotos;
    }

    public void setTipoFotos(String tipoFotos) {
        TipoFotos = tipoFotos;
    }

    public String getLineaFotos() {
        return LineaFotos;
    }

    public void setLineaFotos(String lineaFotos) {
        LineaFotos = lineaFotos;
    }
}