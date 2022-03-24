package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlEstadoCuenta extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Cliente = "";
    String mindate="";
    String maxdate ="";
    String claveDatos ="";
;
    public xmlEstadoCuenta(int version) {
        super(version);
    }

    public void xmlEstadoCuenta(String usuario, String clave,String cliente ,String mindate ,String maxdate,String claveDatos) {
        this.usuario = usuario;
        this.clave = clave;
        this.Cliente =cliente;
        this.mindate = mindate;
        this.maxdate = maxdate;
        this.claveDatos = maxdate;

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
        writer.startTag(tem, "estadoDeCuentaRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "EstadoCuenta");
        writer.startTag(tem, "client");
        writer.text(Cliente);
        writer.endTag(tem, "client");

        writer.startTag(tem, "mindate");
        writer.text(mindate);
        writer.endTag(tem, "mindate");

        writer.startTag(tem, "maxdate");
        writer.text(maxdate);
        writer.endTag(tem, "maxdate");

        writer.startTag(tem, "claveDatos");
        writer.text(claveDatos);
        writer.endTag(tem, "claveDatos");

        writer.endTag(tem, "EstadoCuenta");


        writer.endTag(tem, "estadoDeCuentaRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}