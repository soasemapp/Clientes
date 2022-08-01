package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlBusqueProductos extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String fechaInicio = "";
    String fechaFinal = "";
    String marca = "";
    String modelo = "";
    String linea = "";
    String check = "";
    String cliente = "";

    public xmlBusqueProductos(int version) {
        super(version);
    }

    public void xmlBusqueProductos(String usuario, String clave, String fechaInicio, String fechaFinal, String marca, String modelo,String linea, String check , String Cliente) {
        this.usuario = usuario;
        this.clave = clave;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.marca = marca;
        this.modelo = modelo;
        this.linea = linea;
        this.check = check;
        this.cliente = Cliente;
    }

    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);
        writer.setPrefix("", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "busqueProductosRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "busquedaProductos");
        writer.startTag(tem, "fechainicial");
        writer.text(fechaInicio);
        writer.endTag(tem, "fechainicial");

        writer.startTag(tem, "fechafinal");
        writer.text(fechaFinal);
        writer.endTag(tem, "fechafinal");


        writer.startTag(tem, "k_marca");
        writer.text(marca);
        writer.endTag(tem, "k_marca");


        writer.startTag(tem, "k_modelo");
        writer.text(modelo);
        writer.endTag(tem, "k_modelo");

        writer.startTag(tem, "k_linea");
        writer.text(linea);
        writer.endTag(tem, "k_linea");


        writer.startTag(tem, "k_checkano");
        writer.text(check);
        writer.endTag(tem, "k_checkano");


        writer.startTag(tem, "k_cliente");
        writer.text(cliente);
        writer.endTag(tem, "k_cliente");

        writer.endTag(tem, "busquedaProductos");


        writer.endTag(tem, "busqueProductosRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}