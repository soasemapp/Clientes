package com.almacen.keplerclientesapp.SetterandGetter;

public class DetallPediSANDG {

    String Sucursal;
    String Folio;
    String ClaveC;
    String NombreC;
    String ClaveP;
    String Cant;
    String Precio;
    String Desc;
    String Importe;

    public DetallPediSANDG(String sucursal, String folio, String claveC, String nombreC, String claveP, String cant, String precio, String desc, String importe) {
        Sucursal = sucursal;
        Folio = folio;
        ClaveC = claveC;
        NombreC = nombreC;
        ClaveP = claveP;
        Cant = cant;
        Precio = precio;
        Desc = desc;
        Importe = importe;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getClaveC() {
        return ClaveC;
    }

    public void setClaveC(String claveC) {
        ClaveC = claveC;
    }

    public String getNombreC() {
        return NombreC;
    }

    public void setNombreC(String nombreC) {
        NombreC = nombreC;
    }

    public String getClaveP() {
        return ClaveP;
    }

    public void setClaveP(String claveP) {
        ClaveP = claveP;
    }

    public String getCant() {
        return Cant;
    }

    public void setCant(String cant) {
        Cant = cant;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }
}
