package com.almacen.keplerclientesapp.SetterandGetter;

public class ConFaDetSANDG {
    String Productor;
    String Descripcion;
    String Cantidad;
    String PrecioU;
    String Descuento;
    String PrecioTXP;
    String Sucursal;

    public ConFaDetSANDG(String productor, String descripcion, String cantidad, String precioU, String descuento, String precioTXP, String sucursal) {
        Productor = productor;
        Descripcion = descripcion;
        Cantidad = cantidad;
        PrecioU = precioU;
        Descuento = descuento;
        PrecioTXP = precioTXP;
        Sucursal = sucursal;
    }

    public String getProductor() {
        return Productor;
    }

    public void setProductor(String productor) {
        Productor = productor;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getPrecioU() {
        return PrecioU;
    }

    public void setPrecioU(String precioU) {
        PrecioU = precioU;
    }

    public String getDescuento() {
        return Descuento;
    }

    public void setDescuento(String descuento) {
        Descuento = descuento;
    }

    public String getPrecioTXP() {
        return PrecioTXP;
    }

    public void setPrecioTXP(String precioTXP) {
        PrecioTXP = precioTXP;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }
}
