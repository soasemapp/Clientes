package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlListModelo extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Marca = "";
    public xmlListModelo(int version) {
        super(version);
    }

    public void xmlListModelo(String usuario, String clave,String Marca) {
        this.usuario = usuario;
        this.clave = clave;
        this.Marca =Marca;
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
        writer.startTag(tem, "listmodeloRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "listModelo");
        writer.startTag(tem, "marca");
        writer.text(Marca);
        writer.endTag(tem, "marca");

        writer.endTag(tem, "listModelo");



        writer.endTag(tem, "listmodeloRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}