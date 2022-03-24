package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlConsulPedi extends SoapSerializationEnvelope {

    String Usuario = "";
    String Password = "";
    String sucursal = "";
    String vendedor =" ";
    String fecha =" ";


    public xmlConsulPedi(int version) {
        super(version);
    }

    public void xmlConsulPe(String usuario, String password, String sucursal, String vendedor, String fecha) {

        Usuario = usuario;
        Password = password;
        this.sucursal = sucursal;
        this.vendedor = vendedor;
        this.fecha = fecha;
    }

    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);

        writer.setPrefix("", tem);

        writer.startTag(env, "Envelope");

        writer.startTag(env, "Body");

        writer.startTag(tem, "ConsulPeRequest");

        writer.startTag(tem, "Login");

        writer.startTag(tem, "user");
        writer.text(Usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(Password);
        writer.endTag(tem, "pass");

        writer.endTag(tem, "Login");

        writer.startTag(tem, "ConsulPedi");


        writer.startTag(tem, "k_Sucursal");
        writer.text(sucursal);
        writer.endTag(tem, "k_Sucursal");


        writer.startTag(tem, "k_Cliente");
        writer.text(vendedor);
        writer.endTag(tem, "k_Cliente");


        writer.startTag(tem, "k_Fecha");
        writer.text(fecha);
        writer.endTag(tem, "k_Fecha");

        writer.endTag(tem, "ConsulPedi");

        writer.endTag(tem, "ConsulPeRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}