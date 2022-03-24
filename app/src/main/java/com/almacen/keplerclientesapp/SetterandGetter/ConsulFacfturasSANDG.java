package com.almacen.keplerclientesapp.SetterandGetter;

public class ConsulFacfturasSANDG {
    String Cliente;
    String nombreCliente;
    String foliodelDocumento;
    String fechadeDocumento;
    String plazo;
    String fechadepago;
    String saldo;
    String monto;
    String numSuc;
    String nomSuc;

    public ConsulFacfturasSANDG(String cliente, String nombreCliente, String foliodelDocumento, String fechadeDocumento, String plazo, String fechadepago, String saldo, String monto, String numSuc, String nomSuc) {
        Cliente = cliente;
        this.nombreCliente = nombreCliente;
        this.foliodelDocumento = foliodelDocumento;
        this.fechadeDocumento = fechadeDocumento;
        this.plazo = plazo;
        this.fechadepago = fechadepago;
        this.saldo = saldo;
        this.monto = monto;
        this.numSuc = numSuc;
        this.nomSuc = nomSuc;
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

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getNumSuc() {
        return numSuc;
    }

    public void setNumSuc(String numSuc) {
        this.numSuc = numSuc;
    }

    public String getNomSuc() {
        return nomSuc;
    }

    public void setNomSuc(String nomSuc) {
        this.nomSuc = nomSuc;
    }
}