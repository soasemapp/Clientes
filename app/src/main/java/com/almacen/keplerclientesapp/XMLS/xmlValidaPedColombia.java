package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlValidaPedColombia extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String cliente="";
    String monto = "";
    String subdescuento = "";
    String descuento = "";




    public xmlValidaPedColombia(int version) {
        super(version);
    }


    public void xmlValidaPedColombia(String usuario, String clave, String cliente, String monto,String Subdescuento,String Descuento) {
        this.usuario = usuario;
        this.clave = clave;
        this.cliente = cliente;
        this.monto = monto;
        this.subdescuento=Subdescuento;
        this.descuento=Descuento;
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

        writer.startTag(tem, "ValidaPedColombiaRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "ValidaPediColombia");



        writer.startTag(tem, "k_Cliente");
        writer.text(cliente);
        writer.endTag(tem, "k_Cliente");

        writer.startTag(tem, "k_Monto");
        writer.text(monto);
        writer.endTag(tem, "k_Monto");


        writer.startTag(tem, "k_Subtota");
        writer.text(subdescuento);
        writer.endTag(tem, "k_Subtota");


        writer.startTag(tem, "k_Descuento");
        writer.text(descuento);
        writer.endTag(tem, "k_Descuento");

        writer.endTag(tem, "ValidaPediColombia");

        writer.endTag(tem, "ValidaPedColombiaRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }


}