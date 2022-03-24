package com.almacen.keplerclientesapp.SetterandGetter;

public class ValTipousSANDG {
    String Nombre;
    String Apellido;
    String Email;
    String Sucursal;
    String Clave;
    String tipo2;
    String Descr;

    public ValTipousSANDG(String nombre, String apellido, String email, String sucursal, String clave, String tipo2, String descr) {
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Email = email;
        this.Sucursal = sucursal;
        this.Clave = clave;
        this.tipo2 = tipo2;
        this.Descr = descr;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }
}