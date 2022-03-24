package com.almacen.keplerclientesapp.SetterandGetter;

public class ConversionesSANDG {

    String Clave="";
    String ClaveCompetencia="";
    String NombreCompetencia="";

    public ConversionesSANDG(String clave, String claveCompetencia, String nombreCompetencia) {
        Clave = clave;
        ClaveCompetencia = claveCompetencia;
        NombreCompetencia = nombreCompetencia;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getClaveCompetencia() {
        return ClaveCompetencia;
    }

    public void setClaveCompetencia(String claveCompetencia) {
        ClaveCompetencia = claveCompetencia;
    }

    public String getNombreCompetencia() {
        return NombreCompetencia;
    }

    public void setNombreCompetencia(String nombreCompetencia) {
        NombreCompetencia = nombreCompetencia;
    }
}
