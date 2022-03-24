 package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlProductoConsulta extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String clavecliente = "";
    String eagle = "";
    String trackone="";
    String shark="";
    String rodatech="";
    String partech="";

    public xmlProductoConsulta(int version) {
        super(version);
    }

    public void xmlProductoConsulta(String usuario, String clave, String clavecliente, String eagle, String trackone, String shark, String rodatech, String partech) {
        this.usuario = usuario;
        this.clave = clave;
        this.clavecliente = clavecliente;
        this.eagle = eagle;
        this.trackone = trackone;
        this.shark = shark;
        this.rodatech = rodatech;
        this.partech = partech;

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

        writer.startTag(tem, "ProductoConsultaRequest");

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
        writer.text(clavecliente);
        writer.endTag(tem, "client");

        writer.startTag(tem, "eagle");
        writer.text(eagle);
        writer.endTag(tem, "eagle");

        writer.startTag(tem, "trackone");
        writer.text(trackone);
        writer.endTag(tem, "trackone");

        writer.startTag(tem, "shark");
        writer.text(shark);
        writer.endTag(tem, "shark");

        writer.startTag(tem, "rodatech");
        writer.text(rodatech);
        writer.endTag(tem, "rodatech");

        writer.startTag(tem, "partech");
        writer.text(partech);
        writer.endTag(tem, "partech");


        writer.startTag(tem, "claveDatos ");
        writer.text("1");
        writer.endTag(tem, "claveDatos ");



        writer.endTag(tem, "ProdConsu");

        writer.endTag(tem, "ProductoConsultaRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}