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

    public xmlBusqueProductos(int version) {
        super(version);
    }

    public void xmlBusqueProductos(String usuario, String clave, String fechaInicio, String fechaFinal, String marca, String modelo,String linea, String check) {
        this.usuario = usuario;
        this.clave = clave;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.marca = marca;
        this.modelo = modelo;
        this.linea = linea;
        this.check = check;
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


        writer.startTag(tem, "marca");
        writer.text(marca);
        writer.endTag(tem, "marca");


        writer.startTag(tem, "modelo");
        writer.text(modelo);
        writer.endTag(tem, "modelo");

        writer.startTag(tem, "linea");
        writer.text(linea);
        writer.endTag(tem, "linea");


        writer.startTag(tem, "checkano");
        writer.text(check);
        writer.endTag(tem, "checkano");

        writer.endTag(tem, "busquedaProductos");


        writer.endTag(tem, "busqueProductosRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}