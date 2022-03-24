package com.almacen.keplerclientesapp.SetterandGetter;

public class ListLineaSANDG {
    String codeLinea;
    String Linea;

    public ListLineaSANDG(String codeLinea, String linea) {
        this.codeLinea = codeLinea;
        Linea = linea;
    }

    public String getCodeLinea() {
        return codeLinea;
    }

    public void setCodeLinea(String codeLinea) {
        this.codeLinea = codeLinea;
    }

    public String getLinea() {
        return Linea;
    }

    public void setLinea(String linea) {
        Linea = linea;
    }
}
