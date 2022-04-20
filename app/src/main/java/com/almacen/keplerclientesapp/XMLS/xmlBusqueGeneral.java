package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlBusqueGeneral extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String ClaveProdcuto = "";
    String DescripcionProdcuto = "";


    public xmlBusqueGeneral(int version) {
        super(version);
    }

    public void xmlBusqueGeneral(String usuario, String clave, String claveProdcuto, String descripcionProdcuto) {
        this.usuario = usuario;
        this.clave = clave;
        ClaveProdcuto = claveProdcuto;
        DescripcionProdcuto = descripcionProdcuto;
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
        writer.startTag(tem, "BusquedaGeneralRequest");
        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "BusquedaGeneral");
        writer.startTag(tem, "k_clave");
        writer.text(ClaveProdcuto);
        writer.endTag(tem, "k_clave");

        writer.startTag(tem, "k_descripcion");
        writer.text(DescripcionProdcuto);
        writer.endTag(tem, "k_descripcion");


        writer.endTag(tem, "BusquedaGeneral");


        writer.endTag(tem, "BusquedaGeneralRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}