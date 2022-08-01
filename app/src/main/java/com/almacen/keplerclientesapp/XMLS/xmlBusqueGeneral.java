package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlBusqueGeneral extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String clientes = "";
    String busqueda = "";


    public xmlBusqueGeneral(int version) {
        super(version);
    }

    public void xmlBusqueGeneral(String usuario, String clave, String clientes, String busqueda) {
        this.usuario = usuario;
        this.clave = clave;
        this.clientes = clientes;
        this.busqueda = busqueda;
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
        writer.startTag(tem, "ProductoConsultaAppRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ProdConsu");
        writer.startTag(tem, "client");
        writer.text(clientes);
        writer.endTag(tem, "client");

        writer.startTag(tem, "eagle");
        writer.text(busqueda);
        writer.endTag(tem, "eagle");


        writer.endTag(tem, "ProdConsu");


        writer.endTag(tem, "ProductoConsultaAppRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}