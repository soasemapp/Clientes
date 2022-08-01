package com.almacen.keplerclientesapp.SetterandGetter;

public class ConsulCotiSANDG {
    String Sucursal;
    String Folio;
    String Fecha;
    String Clave;
    String Nombre;
    String Importe;
    String Piezas;
    String nomSucursal;
    String Comentario;


    public ConsulCotiSANDG(String sucursal, String folio, String fecha, String clave, String nombre, String importe, String piezas, String nomSucursal, String comentario) {
        Sucursal = sucursal;
        Folio = folio;
        Fecha = fecha;
        Clave = clave;
        Nombre = nombre;
        Importe = importe;
        Piezas = piezas;
        this.nomSucursal = nomSucursal;
        Comentario = comentario;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }

    public String getPiezas() {
        return Piezas;
    }

    public void setPiezas(String piezas) {
        Piezas = piezas;
    }

    public String getNomSucursal() {
        return nomSucursal;
    }

    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }
}