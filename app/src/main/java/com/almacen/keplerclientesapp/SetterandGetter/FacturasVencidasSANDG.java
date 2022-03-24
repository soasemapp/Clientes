package com.almacen.keplerclientesapp.SetterandGetter;

public class FacturasVencidasSANDG {
    String Cliente;
    String nombreCliente;
    String foliodelDocumento;
    String fechadeDocumento;
    String plazo;
    String fechadepago;
    String saldo;


    public FacturasVencidasSANDG(String cliente, String nombreCliente, String foliodelDocumento, String fechadeDocumento, String plazo, String fechadepago, String saldo) {
        Cliente = cliente;
        this.nombreCliente = nombreCliente;
        this.foliodelDocumento = foliodelDocumento;
        this.fechadeDocumento = fechadeDocumento;
        this.plazo = plazo;
        this.fechadepago = fechadepago;
        this.saldo = saldo;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFoliodelDocumento() {
        return foliodelDocumento;
    }

    public void setFoliodelDocumento(String foliodelDocumento) {
        this.foliodelDocumento = foliodelDocumento;
    }

    public String getFechadeDocumento() {
        return fechadeDocumento;
    }

    public void setFechadeDocumento(String fechadeDocumento) {
        this.fechadeDocumento = fechadeDocumento;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getFechadepago() {
        return fechadepago;
    }

    public void setFechadepago(String fechadepago) {
        this.fechadepago = fechadepago;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
