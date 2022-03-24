package com.almacen.keplerclientesapp.SetterandGetter;

public class CarritoBD {
    int ID;
    String cliente;
    String parte;
    String existencia;
    String cantidad;
    String unidad;
    String precio;
    String desc1;
    String desc2;
    String desc3;
    String monto;
    String descr;

    public CarritoBD(int ID, String cliente, String parte, String existencia, String cantidad, String unidad, String precio, String desc1, String desc2, String desc3, String monto, String descr) {
        this.ID = ID;
        this.cliente = cliente;
        this.parte = parte;
        this.existencia = existencia;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.precio = precio;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
        this.monto = monto;
        this.descr = descr;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDesc1() {
        return desc1;
    }

    public void setDesc1(String desc1) {
        this.desc1 = desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getDesc3() {
        return desc3;
    }

    public void setDesc3(String desc3) {
        this.desc3 = desc3;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
