package com.almacen.keplerclientesapp.SetterandGetter;

public class BusquedaCliente0VentasSANDG {

    String Clave;
    String Nombre;
    String Ciudad;
    String Direccion;
    String Activos;

    public BusquedaCliente0VentasSANDG(String clave, String nombre, String ciudad, String direccion, String activos) {
        Clave = clave;
        Nombre = nombre;
        Ciudad = ciudad;
        Direccion = direccion;
        Activos = activos;
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

    public String getCiudad() {
        return Ciudad;
    }

    public void setCiudad(String ciudad) {
        Ciudad = ciudad;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getActivos() {
        return Activos;
    }

    public void setActivos(String activos) {
        Activos = activos;
    }
}