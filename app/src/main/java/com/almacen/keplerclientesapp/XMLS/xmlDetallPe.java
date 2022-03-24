package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlDetallPe extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Folio= "";
    String Sucursal="";

    public xmlDetallPe(int version) {
        super(version);
    }

    public void xmlDetallP( String usuario, String clave, String folio,String Sucursals) {
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

        writer.startTag(tem, "DetallPeRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "DetallPedi");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");


        writer.endTag(tem, "DetallPedi");


        writer.endTag(tem, "DetallPeRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();
    }
}
