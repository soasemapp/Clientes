package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlConFactDetall extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Folio= "";
    String Sucursal="";

    public xmlConFactDetall(int version) {
        super(version);
    }

    public void xmlConFactDeta( String usuario, String clave, String folio,String Sucursals) {
        this.usuario = usuario;
        this.clave = clave;
        Folio = folio;
        Sucursal=Sucursals;
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

        writer.startTag(tem, "ConFactDeRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "ConFacturaDe");

        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");



        writer.endTag(tem, "ConFacturaDe");


        writer.endTag(tem, "ConFactDeRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
