package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlOrdenConsulta extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String folio = "";
    String claveDatos="";
    ;
    public xmlOrdenConsulta(int version) {
        super(version);
    }

    public void xmlOrdenConsulta(String usuario, String clave,String folio,String claveDatos) {
        this.usuario = usuario;
        this.clave = clave;
        this.folio =folio;
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
        writer.startTag(tem, "OrdenConsultaRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "OrdenConsu");
        writer.startTag(tem, "folio");
        writer.text(folio);
        writer.endTag(tem, "folio");

        writer.startTag(tem, "claveDatos");
        writer.text(claveDatos);
        writer.endTag(tem, "claveDatos");

        writer.endTag(tem, "OrdenConsu");


        writer.endTag(tem, "OrdenConsultaRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}