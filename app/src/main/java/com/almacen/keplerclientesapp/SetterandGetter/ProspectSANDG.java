package com.almacen.keplerclientesapp.SetterandGetter;

public class ProspectSANDG {
    String Nombre;
    String Cotizacion;
    String Tarea;
    String Comentario;

    public ProspectSANDG(String nombre, String cotizacion, String tarea, String comentario) {
        Nombre = nombre;
        Cotizacion = cotizacion;
        Tarea = tarea;
        Comentario = comentario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCotizacion() {
        return Cotizacion;
    }

    public void setCotizacion(String cotizacion) {
        Cotizacion = cotizacion;
    }

    public String getTarea() {
        return Tarea;
    }

    public void setTarea(String tarea) {
        Tarea = tarea;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }
}
