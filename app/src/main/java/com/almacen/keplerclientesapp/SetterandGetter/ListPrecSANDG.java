package com.almacen.keplerclientesapp.SetterandGetter;

public class ListPrecSANDG {

    String CodeProdu;
    String NomProd;
    String CodBarras;
    String Existencia1;
    String Importe;

    public ListPrecSANDG(String codeProdu, String nomProd, String codBarras, String existencia1, String importe) {
        CodeProdu = codeProdu;
        NomProd = nomProd;
        CodBarras = codBarras;
        Existencia1 = existencia1;
        Importe = importe;
    }

    public String getCodeProdu() {
        return CodeProdu;
    }

    public void setCodeProdu(String codeProdu) {
        CodeProdu = codeProdu;
    }

    public String getNomProd() {
        return NomProd;
    }

    public void setNomProd(String nomProd) {
        NomProd = nomProd;
    }

    public String getCodBarras() {
        return CodBarras;
    }

    public void setCodBarras(String codBarras) {
        CodBarras = codBarras;
    }

    public String getExistencia1() {
        return Existencia1;
    }

    public void setExistencia1(String existencia1) {
        Existencia1 = existencia1;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }
}