package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlValidaPedMexico extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String cliente="";
    String monto = "";



    public xmlValidaPedMexico(int version) {
        super(version);
    }


    public void xmlValidaPedi(String usuario, String clave, String cliente, String monto) {
        this.usuario = usuario;
        this.clave = clave;
        this.cliente = cliente;
        this.monto = monto;
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

        writer.startTag(tem, "ValidaPedMexicoRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "ValidaPediMexico");



        writer.startTag(tem, "k_Cliente");
        writer.text(cliente);
        writer.endTag(tem, "k_Cliente");


        writer.startTag(tem, "k_Monto");
        writer.text(monto);
        writer.endTag(tem, "k_Monto");

        writer.endTag(tem, "ValidaPediMexico");

        writer.endTag(tem, "ValidaPedMexicoRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}