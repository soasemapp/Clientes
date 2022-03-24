package com.almacen.keplerclientesapp.SetterandGetter;

public class listDipoSucuSANDG {

String Clave;
String Nombre;
String Disponibilidad;


    public listDipoSucuSANDG(String clave, String nombre, String disponibilidad) {
        Clave = clave;
        Nombre = nombre;
        Disponibilidad = disponibilidad;
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

    public String getDisponibilidad() {
        return Disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        Disponibilidad = disponibilidad;
    }
}
