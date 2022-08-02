package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlAplicaciones extends SoapSerializationEnvelope {
    String usuario = "";
    String clave = "";
    String clientes = "";
    String producto = "";


    public xmlAplicaciones(int version) {
        super(version);
    }

    public void xmlAplicaciones(String usuario, String clave, String clientes, String producto) {
        this.usuario = usuario;
        this.clave = clave;
        this.clientes = clientes;
        this.producto = producto;
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
        writer.startTag(tem, "AplicacionesRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "Aplicaciones");
        writer.startTag(tem, "k_clave");
        writer.text(producto);
        writer.endTag(tem, "k_clave");

        writer.startTag(tem, "k_cliente");
        writer.text(clientes);
        writer.endTag(tem, "k_cliente");


        writer.endTag(tem, "Aplicaciones");


        writer.endTag(tem, "AplicacionesRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }

}
