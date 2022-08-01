package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlListLine extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String modelo = "";


    public xmlListLine(int version) {
        super(version);
    }


    public void xmlListLine(String usuario, String clave, String modelo) {
        this.usuario = usuario;
        this.clave = clave;
        this.modelo =modelo;
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

        writer.startTag(tem, "listlineasRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "Lineas");
        writer.startTag(tem, "modelo");
        writer.text(modelo);
        writer.endTag(tem, "modelo");
        writer.endTag(tem, "Lineas");

        writer.endTag(tem, "listlineasRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
