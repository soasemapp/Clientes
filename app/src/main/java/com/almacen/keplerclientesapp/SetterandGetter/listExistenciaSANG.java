package com.almacen.keplerclientesapp.SetterandGetter;

public class listExistenciaSANG {
  String  ClaveSu;
  String  Nombre;
  String Existencia;
  String ClaveProdu;
  String  Descr;
  String CodBarras;
  String Precio;

    public listExistenciaSANG(String claveSu, String nombre, String existencia, String claveProdu, String descr, String codBarras, String precio) {
        ClaveSu = claveSu;
        Nombre = nombre;
        Existencia = existencia;
        ClaveProdu = claveProdu;
        Descr = descr;
        CodBarras = codBarras;
        Precio = precio;
    }

    public String getClaveSu() {
        return ClaveSu;
    }

    public void setClaveSu(String claveSu) {
        ClaveSu = claveSu;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getExistencia() {
        return Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }

    public String getClaveProdu() {
        return ClaveProdu;
    }

    public void setClaveProdu(String claveProdu) {
        ClaveProdu = claveProdu;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getCodBarras() {
        return CodBarras;
    }

    public void setCodBarras(String codBarras) {
        CodBarras = codBarras;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }
}
