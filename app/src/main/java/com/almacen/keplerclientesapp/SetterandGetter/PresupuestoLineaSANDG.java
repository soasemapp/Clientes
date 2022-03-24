package com.almacen.keplerclientesapp.SetterandGetter;

public class PresupuestoLineaSANDG {
    String Vendido;
    String Linea ;
    String PresopUesto;
    String ResultadoPV;

    public PresupuestoLineaSANDG(String vendido, String linea, String presopUesto, String resultadoPV) {
        Vendido = vendido;
        Linea = linea;
        PresopUesto = presopUesto;
        ResultadoPV = resultadoPV;
    }

    public String getVendido() {
        return Vendido;
    }

    public void setVendido(String vendido) {
        Vendido = vendido;
    }

    public String getLinea() {
        return Linea;
    }

    public void setLinea(String linea) {
        Linea = linea;
    }

    public String getPresopUesto() {
        return PresopUesto;
    }

    public void setPresopUesto(String presopUesto) {
        PresopUesto = presopUesto;
    }

    public String getResultadoPV() {
        return ResultadoPV;
    }

    public void setResultadoPV(String resultadoPV) {
        ResultadoPV = resultadoPV;
    }
}
