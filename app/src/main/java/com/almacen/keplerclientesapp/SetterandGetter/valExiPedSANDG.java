package com.almacen.keplerclientesapp.SetterandGetter;

public class valExiPedSANDG {
    String Prodcuto;
    String Descripcion;
    String Existencia;
    String Unidad;
    String BajNeg;

    public valExiPedSANDG(String prodcuto, String descripcion, String existencia, String unidad, String bajNeg) {
        Prodcuto = prodcuto;
        Descripcion = descripcion;
        Existencia = existencia;
        Unidad = unidad;
        BajNeg = bajNeg;
    }

    public String getProdcuto() {
        return Prodcuto;
    }

    public void setProdcuto(String prodcuto) {
        Prodcuto = prodcuto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getExistencia() {
        return Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getBajNeg() {
        return BajNeg;
    }

    public void setBajNeg(String bajNeg) {
        BajNeg = bajNeg;
    }
}