package com.almacen.keplerclientesapp.SetterandGetter;

public class AgendaSANDG {
    String Fecha;
    String Cliente;
    String ClienNom;
    String Actividad;
    String Estatus;

    public AgendaSANDG(String fecha, String cliente, String clienNom, String actividad, String estatus) {
        Fecha = fecha;
        Cliente = cliente;
        ClienNom = clienNom;
        Actividad = actividad;
        Estatus = estatus;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getClienNom() {
        return ClienNom;
    }

    public void setClienNom(String clienNom) {
        ClienNom = clienNom;
    }

    public String getActividad() {
        return Actividad;
    }

    public void setActividad(String actividad) {
        Actividad = actividad;
    }

    public String getEstatus() {
        return Estatus;
    }

    public void setEstatus(String estatus) {
        Estatus = estatus;
    }
}
