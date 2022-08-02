package com.almacen.keplerclientesapp.SetterandGetter;

public class AplicacionesSANDG
{
String Marca,Modelo,Ano,Cilindraje,Litraje,posicion;

    public AplicacionesSANDG(String marca, String modelo, String ano, String cilindraje, String litraje, String posicion) {
        Marca = marca;
        Modelo = modelo;
        Ano = ano;
        Cilindraje = cilindraje;
        Litraje = litraje;
        this.posicion = posicion;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getAno() {
        return Ano;
    }

    public void setAno(String ano) {
        Ano = ano;
    }

    public String getCilindraje() {
        return Cilindraje;
    }

    public void setCilindraje(String cilindraje) {
        Cilindraje = cilindraje;
    }

    public String getLitraje() {
        return Litraje;
    }

    public void setLitraje(String litraje) {
        Litraje = litraje;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
}
