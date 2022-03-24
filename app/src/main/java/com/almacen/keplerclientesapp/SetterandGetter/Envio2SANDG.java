package com.almacen.keplerclientesapp.SetterandGetter;

public class Envio2SANDG {

    String id;
    String Calle;
    String Colonia;
    String Poblacion;
    String Numero;
    String latitud;
    String longitud;


    public Envio2SANDG(String id, String calle, String colonia, String poblacion, String numero, String latitud, String longitud) {
        this.id = id;
        Calle = calle;
        Colonia = colonia;
        Poblacion = poblacion;
        Numero = numero;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getPoblacion() {
        return Poblacion;
    }

    public void setPoblacion(String poblacion) {
        Poblacion = poblacion;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
