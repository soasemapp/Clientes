package com.almacen.keplerclientesapp.SetterandGetter;

public class ConsulPediSANDG {
    String Folio;
    String Pedido;
    String FLiberacion;
    String FAduana;
    String FFactura;
    String Piezas;
    String Sucursal;
    String Fecha;
    String Cliente;
    String Nombre;
    String Importe;
    String comentario;
    String SucursalNombre;


    public ConsulPediSANDG(String folio, String pedido, String FLiberacion, String FAduana, String FFactura, String piezas, String sucursal, String fecha, String cliente, String nombre, String importe, String comentario, String sucursalNombre) {
        Folio = folio;
        Pedido = pedido;
        this.FLiberacion = FLiberacion;
        this.FAduana = FAduana;
        this.FFactura = FFactura;
        Piezas = piezas;
        Sucursal = sucursal;
        Fecha = fecha;
        Cliente = cliente;
        Nombre = nombre;
        Importe = importe;
        this.comentario = comentario;
        SucursalNombre = sucursalNombre;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getPedido() {
        return Pedido;
    }

    public void setPedido(String pedido) {
        Pedido = pedido;
    }

    public String getFLiberacion() {
        return FLiberacion;
    }

    public void setFLiberacion(String FLiberacion) {
        this.FLiberacion = FLiberacion;
    }

    public String getFAduana() {
        return FAduana;
    }

    public void setFAduana(String FAduana) {
        this.FAduana = FAduana;
    }

    public String getFFactura() {
        return FFactura;
    }

    public void setFFactura(String FFactura) {
        this.FFactura = FFactura;
    }

    public String getPiezas() {
        return Piezas;
    }

    public void setPiezas(String piezas) {
        Piezas = piezas;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImporte() {
        return Importe;
    }

    public void setImporte(String importe) {
        Importe = importe;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getSucursalNombre() {
        return SucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        SucursalNombre = sucursalNombre;
    }
}