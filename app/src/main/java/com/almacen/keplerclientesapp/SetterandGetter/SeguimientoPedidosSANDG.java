package com.almacen.keplerclientesapp.SetterandGetter;

public class SeguimientoPedidosSANDG {
    String Pedidos;
    String FechaPedido;
    String Cliente;
    String Liberacion;
    String Aduana;
    String Facturas;
    String Fecha_Facturacion;
    String Hora;
    String FolioWeb;

    public SeguimientoPedidosSANDG(String pedidos, String fechaPedido, String cliente, String liberacion, String aduana, String facturas, String fecha_Facturacion, String hora, String folioWeb) {
        Pedidos = pedidos;
        FechaPedido = fechaPedido;
        Cliente = cliente;
        Liberacion = liberacion;
        Aduana = aduana;
        Facturas = facturas;
        Fecha_Facturacion = fecha_Facturacion;
        Hora = hora;
        FolioWeb = folioWeb;
    }

    public String getPedidos() {
        return Pedidos;
    }

    public void setPedidos(String pedidos) {
        Pedidos = pedidos;
    }

    public String getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        FechaPedido = fechaPedido;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getLiberacion() {
        return Liberacion;
    }

    public void setLiberacion(String liberacion) {
        Liberacion = liberacion;
    }

    public String getAduana() {
        return Aduana;
    }

    public void setAduana(String aduana) {
        Aduana = aduana;
    }

    public String getFacturas() {
        return Facturas;
    }

    public void setFacturas(String facturas) {
        Facturas = facturas;
    }

    public String getFecha_Facturacion() {
        return Fecha_Facturacion;
    }

    public void setFecha_Facturacion(String fecha_Facturacion) {
        Fecha_Facturacion = fecha_Facturacion;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getFolioWeb() {
        return FolioWeb;
    }

    public void setFolioWeb(String folioWeb) {
        FolioWeb = folioWeb;
    }
}