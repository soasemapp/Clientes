package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlSegumientoPedidos extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String vendedor="";
    String cliente = "";
    String fechaEntrada = "";
    String fechaSalida = "";



    public xmlSegumientoPedidos(int version) {
        super(version);
    }

    public void xmlSegumientoPedi(String usuario, String clave,String ClaVendedor, String ClaCliente, String fechaEntrada, String fechaSalida) {
        this.usuario = usuario;
        this.clave = clave;
        this.vendedor = ClaVendedor;
        this.cliente = ClaCliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
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

        writer.startTag(tem, "SeguidoPediRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "SegudioPedi");



        writer.startTag(tem, "k_FechaInicial");
        writer.text(fechaEntrada);
        writer.endTag(tem, "k_FechaInicial");

        writer.startTag(tem, "k_FechaFinal");
        writer.text(fechaSalida);
        writer.endTag(tem, "k_FechaFinal");


        writer.startTag(tem, "k_ClaveVendedor");
        writer.text(vendedor);
        writer.endTag(tem, "k_ClaveVendedor");

        writer.startTag(tem, "k_ClaveCliente");
        writer.text(cliente);
        writer.endTag(tem, "k_ClaveCliente");
        writer.endTag(tem, "SegudioPedi");

        writer.endTag(tem, "SeguidoPediRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
