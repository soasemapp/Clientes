package com.almacen.keplerclientesapp.SetandGet;


public class SetGetListModelo {
   String Descripcion;
   String ClaveMarca;

    public SetGetListModelo(String descripcion, String claveMarca) {
        Descripcion = descripcion;
        ClaveMarca = claveMarca;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getClaveMarca() {
        return ClaveMarca;
    }

    public void setClaveMarca(String claveMarca) {
        ClaveMarca = claveMarca;
    }
}