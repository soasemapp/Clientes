package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlvalExiPed extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String cantidad = "";
    String producto = "";
    String sucursal = "";


    public xmlvalExiPed(int version) {
        super(version);
    }

    public void xmlvalExiPed(String usuario, String clave, String cantidad, String producto, String sucursal) {
        this.usuario = usuario;
        this.clave = clave;
        this.cantidad = cantidad;
        this.producto = producto;
        this.sucursal = sucursal;
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

        writer.startTag(tem, "valExiPedRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "valExiPedi");


        writer.startTag(tem, "k_cantidad");
        writer.text(cantidad);
        writer.endTag(tem, "k_cantidad");

        writer.startTag(tem, "k_producto");
        writer.text(producto);
        writer.endTag(tem, "k_producto");


        writer.startTag(tem, "k_sucursal");
        writer.text(sucursal);
        writer.endTag(tem, "k_sucursal");

        writer.endTag(tem, "valExiPedi");

        writer.endTag(tem, "valExiPedRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
