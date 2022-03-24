package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlUsuarioConsulta extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String cliente ="";
    String claveDatos = "";

    public xmlUsuarioConsulta(int version) {
        super(version);
    }

    public void xmlUsuarioConsulta(String usuario, String clave, String cliente, String claveDatos) {
        this.usuario = usuario;
        this.clave = clave;
        this.cliente = cliente;
        this.claveDatos = claveDatos;
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
        writer.startTag(tem, "UserConsultaRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "User");


        writer.startTag(tem, "client");
        writer.text(cliente);
        writer.endTag(tem, "client");

        writer.startTag(tem, "claveDatos");
        writer.text(claveDatos);
        writer.endTag(tem, "claveDatos");



        writer.endTag(tem, "User");


        writer.endTag(tem, "UserConsultaRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}