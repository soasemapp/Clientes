package com.almacen.keplerclientesapp.SetterandGetter;

public class BackOrdersSANDG {
    String Clave;
    String Fecha;
    String ClaveProducto;
    String Descripcion;
    String BackOrder;
    String Folio;
    String Existencia;

    public BackOrdersSANDG(String clave, String fecha, String claveProducto, String descripcion, String backOrder, String folio, String existencia) {
        Clave = clave;
        Fecha = fecha;
        ClaveProducto = claveProducto;
        Descripcion = descripcion;
        BackOrder = backOrder;
        Folio = folio;
        Existencia = existencia;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getClaveProducto() {
        return ClaveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        ClaveProducto = claveProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getBackOrder() {
        return BackOrder;
    }

    public void setBackOrder(String backOrder) {
        BackOrder = backOrder;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getExistencia() {
        return Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }
}