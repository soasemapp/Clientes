package com.almacen.keplerclientesapp.XMLS;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlRegisterUser extends SoapSerializationEnvelope {
    String usuario = "";
    String clave = "";
    String Nombre,Apellido,Puesto,RFC,Telefono,Correo,Contrasena;


    public xmlRegisterUser(int version) {
        super(version);
    }

    public void xmlRegisterUser( String usuario, String clave, String nombre, String apellido, String puesto, String RFC, String telefono, String correo, String contrasena) {
        this.usuario = usuario;
        this.clave = clave;
        Nombre = nombre;
        Apellido = apellido;
        Puesto = puesto;
        this.RFC = RFC;
        Telefono = telefono;
        Correo = correo;
        Contrasena = contrasena;
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

        writer.startTag(tem, "RegisterUserAppRequest");

        writer.startTag(tem, "Login");

        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");

        writer.endTag(tem, "Login");

        writer.startTag(tem, "RegisterUserApp");

        writer.startTag(tem, "k_Nombre");
        writer.text(Nombre);
        writer.endTag(tem, "k_Nombre");

        writer.startTag(tem, "k_Apellido");
        writer.text(Apellido);
        writer.endTag(tem, "k_Apellido");

        writer.startTag(tem, "k_Puesto");
        writer.text(Puesto);
        writer.endTag(tem, "k_Puesto");

        writer.startTag(tem, "k_Rfc");
        writer.text(RFC);
        writer.endTag(tem, "k_Rfc");

        writer.startTag(tem, "k_Telefono");
        writer.text(Telefono);
        writer.endTag(tem, "k_Telefono");

        writer.startTag(tem, "k_Correo");
        writer.text(Correo);
        writer.endTag(tem, "k_Correo");


        writer.startTag(tem, "k_Contra");
        writer.text(Contrasena);
        writer.endTag(tem, "k_Contra");


        writer.endTag(tem, "RegisterUserApp");

        writer.endTag(tem, "RegisterUserAppRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
