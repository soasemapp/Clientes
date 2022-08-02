package com.almacen.keplerclientesapp.SetterandGetter;

public class DisponibilidadSANDG {
    String Clave,Disponibilidad,Nombre;

    public DisponibilidadSANDG(String clave, String disponibilidad, String nombre) {
        Clave = clave;
        Disponibilidad = disponibilidad;
        Nombre = nombre;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        Disponibilidad = disponibilidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
