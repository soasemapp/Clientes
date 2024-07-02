package com.almacen.keplerclientesapp.SetterandGetter;

public class AplicacionesSANDG
{
    String Marca,Modelo,Ano,Motor,posicion;

    public AplicacionesSANDG(String marca, String modelo, String ano, String motor, String posicion) {
        Marca = marca;
        Modelo = modelo;
        Ano = ano;
        Motor = motor;
        this.posicion = posicion;
    }

    public String getMarca() {
        return Marca;
    }

    public String getModelo() {
        return Modelo;
    }

    public String getAno() {
        return Ano;
    }

    public String getMotor() {
        return Motor;
    }

    public String getPosicion() {
        return posicion;
    }

}
