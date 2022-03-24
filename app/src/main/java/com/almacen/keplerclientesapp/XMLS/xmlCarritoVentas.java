package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlCarritoVentas extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String cliente = "";
    String producto = "";
    String cantidad = "";
    String existencia = "";
    String sucursal = "";



    public xmlCarritoVentas(int version) {
        super(version);
    }


    public void xmlCarritoVen(String usuario, String clave, String cliente, String producto, String cantidad, String existencia, String sucursal) {

        this.usuario = usuario;
        this.clave = clave;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.existencia = existencia;
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

        writer.startTag(tem, "CarShopRequest");

        writer.startTag(tem, "Login");

        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");

        writer.endTag(tem, "Login");

        writer.startTag(tem, "CarShoping");

        writer.startTag(tem, "k_Cliente");
        writer.text(cliente);
        writer.endTag(tem, "k_Cliente");

        writer.startTag(tem, "k_Producto");
        writer.text(producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Cantidad");
        writer.text(cantidad);
        writer.endTag(tem, "k_Cantidad");

        writer.startTag(tem, "k_Existencia");
        writer.text(existencia);
        writer.endTag(tem, "k_Existencia");

        writer.startTag(tem, "k_Sucursal");
        writer.text(sucursal);
        writer.endTag(tem, "k_Sucursal");

        writer.endTag(tem, "CarShoping");

        writer.endTag(tem, "CarShopRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }

}
