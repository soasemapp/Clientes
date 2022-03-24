package com.almacen.keplerclientesapp.SetterandGetter;

public class EnvioSANDG {

    String id;
    String Calle;
    String Colonia;
    String Poblacion;
    String Numero;

    public EnvioSANDG(String id, String calle, String colonia, String poblacion, String numero) {
        this.id = id;
        Calle = calle;
        Colonia = colonia;
        Poblacion = poblacion;
        Numero = numero;
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
}
