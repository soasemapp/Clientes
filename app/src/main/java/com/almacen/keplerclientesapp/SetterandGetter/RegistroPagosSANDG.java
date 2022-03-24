package com.almacen.keplerclientesapp.SetterandGetter;

public class RegistroPagosSANDG {

    String Fecha;
    String Hora;
    String CCliente;
    String NCliente;
    String Facturas;
    String Importe;
    String NBanco;
    String FDPago;
    String Comentario1;
    String Comentario2;
    String Comentario3;

    public RegistroPagosSANDG(String fecha, String hora, String CCliente, String NCliente, String facturas, String importe, String NBanco, String FDPago, String comentario1, String comentario2, String comentario3) {
        Fecha = fecha;
        Hora = hora;
        this.CCliente = CCliente;
        this.NCliente = NCliente;
        Facturas = facturas;
        Importe = importe;
        this.NBanco = NBanco;
        this.FDPago = FDPago;
        Comentario1 = comentario1;
        Comentario2 = comentario2;
        Comentario3 = comentario3;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getCCliente() {
        return CCliente;
    }

    public void setCCliente(String CCliente) {
        this.CCliente = CCliente;
    }

    public String getNCliente() {
        return NCliente;
    }

    public void setNCliente(String NCliente) {
        this.NCliente = NCliente;
    }

    public String getFacturas() {
        return Facturas;
    }

    public void setFacturas(String facturas) {
        Facturas = facturas;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }

    public String getNBanco() {
        return NBanco;
    }

    public void setNBanco(String NBanco) {
        this.NBanco = NBanco;
    }

    public String getFDPago() {
        return FDPago;
    }

    public void setFDPago(String FDPago) {
        this.FDPago = FDPago;
    }

    public String getComentario1() {
        return Comentario1;
    }

    public void setComentario1(String comentario1) {
        Comentario1 = comentario1;
    }

    public String getComentario2() {
        return Comentario2;
    }

    public void setComentario2(String comentario2) {
        Comentario2 = comentario2;
    }

    public String getComentario3() {
        return Comentario3;
    }

    public void setComentario3(String comentario3) {
        Comentario3 = comentario3;
    }
}
