package com.almacen.keplerclientesapp.SetandGet;

public class SetGetLisFacturas {
    String FolioFactura;
    String PlazoFactura;
    String FechadeNacimiento;
    String SaldodeFactura;
    String MontodeFactura;

    public SetGetLisFacturas(String folioFactura, String plazoFactura, String fechadeNacimiento, String saldodeFactura, String montodeFactura) {
        FolioFactura = folioFactura;
        PlazoFactura = plazoFactura;
        FechadeNacimiento = fechadeNacimiento;
        SaldodeFactura = saldodeFactura;
        MontodeFactura = montodeFactura;
    }

    public String getFolioFactura() {
        return FolioFactura;
    }

    public void setFolioFactura(String folioFactura) {
        FolioFactura = folioFactura;
    }

    public String getPlazoFactura() {
        return PlazoFactura;
    }

    public void setPlazoFactura(String plazoFactura) {
        PlazoFactura = plazoFactura;
    }

    public String getFechadeNacimiento() {
        return FechadeNacimiento;
    }

    public void setFechadeNacimiento(String fechadeNacimiento) {
        FechadeNacimiento = fechadeNacimiento;
    }

    public String getSaldodeFactura() {
        return SaldodeFactura;
    }

    public void setSaldodeFactura(String saldodeFactura) {
        SaldodeFactura = saldodeFactura;
    }

    public String getMontodeFactura() {
        return MontodeFactura;
    }

    public void setMontodeFactura(String montodeFactura) {
        MontodeFactura = montodeFactura;
    }
}
