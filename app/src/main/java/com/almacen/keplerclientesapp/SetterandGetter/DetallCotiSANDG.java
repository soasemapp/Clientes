package com.almacen.keplerclientesapp.SetterandGetter;

public class DetallCotiSANDG {

    String Sucursal;
    String Folio;
    String ClaveC;
    String NombreC;
    String ClaveP;
    String Cant;
    String Precio;
    String Desc;
    String Importe;
    String RFC;
    String PLAZO;
    String DESCUENTOPP;
    String DESCUENTO1;
    String CALLE;
    String COLONIA;
    String POBLACION;
    String VIA;
    String Comentario;
    String Disponible;
    String BackOrder;
    String Surtido;
    String PrecioNuevo;
    String fecha;
    String hora;
    String montoBack;
    String saldo;
    String NombreSucursal;
    String Descripcion;
    String Unidad;
    String Vendedor;
    String MontoPedido;

    public DetallCotiSANDG(String sucursal, String folio, String claveC, String nombreC, String claveP, String cant, String precio, String desc, String importe, String RFC, String PLAZO, String DESCUENTOPP, String DESCUENTO1, String CALLE, String COLONIA, String POBLACION, String VIA, String comentario, String disponible, String backOrder, String surtido, String precioNuevo, String fecha, String hora, String montoBack, String saldo, String nombreSucursal, String descripcion, String unidad, String vendedor, String montoPedido) {
        Sucursal = sucursal;
        Folio = folio;
        ClaveC = claveC;
        NombreC = nombreC;
        ClaveP = claveP;
        Cant = cant;
        Precio = precio;
        Desc = desc;
        Importe = importe;
        this.RFC = RFC;
        this.PLAZO = PLAZO;
        this.DESCUENTOPP = DESCUENTOPP;
        this.DESCUENTO1 = DESCUENTO1;
        this.CALLE = CALLE;
        this.COLONIA = COLONIA;
        this.POBLACION = POBLACION;
        this.VIA = VIA;
        Comentario = comentario;
        Disponible = disponible;
        BackOrder = backOrder;
        Surtido = surtido;
        PrecioNuevo = precioNuevo;
        this.fecha = fecha;
        this.hora = hora;
        this.montoBack = montoBack;
        this.saldo = saldo;
        NombreSucursal = nombreSucursal;
        Descripcion = descripcion;
        Unidad = unidad;
        Vendedor = vendedor;
        MontoPedido = montoPedido;
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

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getPLAZO() {
        return PLAZO;
    }

    public void setPLAZO(String PLAZO) {
        this.PLAZO = PLAZO;
    }

    public String getDESCUENTOPP() {
        return DESCUENTOPP;
    }

    public void setDESCUENTOPP(String DESCUENTOPP) {
        this.DESCUENTOPP = DESCUENTOPP;
    }

    public String getDESCUENTO1() {
        return DESCUENTO1;
    }

    public void setDESCUENTO1(String DESCUENTO1) {
        this.DESCUENTO1 = DESCUENTO1;
    }

    public String getCALLE() {
        return CALLE;
    }

    public void setCALLE(String CALLE) {
        this.CALLE = CALLE;
    }

    public String getCOLONIA() {
        return COLONIA;
    }

    public void setCOLONIA(String COLONIA) {
        this.COLONIA = COLONIA;
    }

    public String getPOBLACION() {
        return POBLACION;
    }

    public void setPOBLACION(String POBLACION) {
        this.POBLACION = POBLACION;
    }

    public String getVIA() {
        return VIA;
    }

    public void setVIA(String VIA) {
        this.VIA = VIA;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public String getDisponible() {
        return Disponible;
    }

    public void setDisponible(String disponible) {
        Disponible = disponible;
    }

    public String getBackOrder() {
        return BackOrder;
    }

    public void setBackOrder(String backOrder) {
        BackOrder = backOrder;
    }

    public String getSurtido() {
        return Surtido;
    }

    public void setSurtido(String surtido) {
        Surtido = surtido;
    }

    public String getPrecioNuevo() {
        return PrecioNuevo;
    }

    public void setPrecioNuevo(String precioNuevo) {
        PrecioNuevo = precioNuevo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMontoBack() {
        return montoBack;
    }

    public void setMontoBack(String montoBack) {
        this.montoBack = montoBack;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getNombreSucursal() {
        return NombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        NombreSucursal = nombreSucursal;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String vendedor) {
        Vendedor = vendedor;
    }

    public String getMontoPedido() {
        return MontoPedido;
    }

    public void setMontoPedido(String montoPedido) {
        MontoPedido = montoPedido;
    }
}