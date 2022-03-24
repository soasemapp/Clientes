package com.almacen.keplerclientesapp.XMLS;


import com.almacen.keplerclientesapp.SetterandGetter.DetallCotiSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.valExiPedSANDG;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class xmlNewDoc42 extends SoapSerializationEnvelope {
    String Comentario = "";
    String NombreCliente = "";
    String ClaveCliente = "";
    String FechaActual = "";
    String FechaVencimiento = "";
    String Sucursal = "";
    String usuario = "";
    String clave = "";
    String rfcCliente = "";
    String plazo = "";
    String Montototal = "";
    String Iva = "";
    String Descuento = "";
    String DescuentoPro = "";
    String Desc1 = "";
    String Calle = "";
    String Colonia = "";
    String Poblacion = "";
    String FolioPrev="";
    String Via="";
    String stridEnvio="";
    ArrayList<DetallCotiSANDG> listasearch2 = new ArrayList<>();
    ArrayList<valExiPedSANDG> listasearch3 = new ArrayList<>();
    double DescProstr = 0;
    double Descuentorec = 0;
    String DescuentoStr;
    double cantidad;
    double precio;
    double descuento;
    double monto;
    double monto2;
    double monto3;
    String Server;
    int canst;


    public xmlNewDoc42(int version) {
        super(version);
    }


    public void xmlNewDoc42( String comentario,String nombreCliente, String claveCliente, String fechaActual, String fechaVencimiento,
                           String sucursal, String usuario, String clave, String rfcCliente, String plazo, String montototal, String iva, String descuento, String descuentoPro,
                           String Desc1,String calle, String colonia, String poblacion, String folioPrev,String Via,String stridEnvio, ArrayList<DetallCotiSANDG> listasearch2, ArrayList<valExiPedSANDG> listasearch3 ,String StrServer) {
        this.Comentario = comentario;
        this.NombreCliente = nombreCliente;
        this.ClaveCliente = claveCliente;
        this.FechaActual = fechaActual;
        this.FechaVencimiento = fechaVencimiento;
        this.Sucursal = sucursal;
        this.usuario = usuario;
        this.clave = clave;
        this.rfcCliente = rfcCliente;
        this.plazo = plazo;
        this.Montototal = montototal;
        this.Iva = iva;
        this.Descuento = descuento;
        this.DescuentoPro = descuentoPro;
        this.Desc1 = Desc1;
        this.Calle = calle;
        this.Colonia = colonia;
        this.Poblacion = poblacion;
        this.FolioPrev = folioPrev;
        this.Via = Via;
        this.stridEnvio = stridEnvio;
        this.listasearch2 = listasearch2;
        this.listasearch3 = listasearch3;
        this.Server = StrServer;
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

        writer.startTag(tem, "newDocRequest");

        writer.startTag(tem, "Login");

        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");

        writer.endTag(tem, "Login");

        writer.startTag(tem, "Doc");

        writer.startTag(tem, "k_sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_sucursal");

        writer.startTag(tem, "k_almacen");
        writer.text("1");
        writer.endTag(tem, "k_almacen");

        writer.startTag(tem, "k_genero");
        writer.text("U");
        writer.endTag(tem, "k_genero");

        writer.startTag(tem, "k_naturaleza");
        writer.text("D");
        writer.endTag(tem, "k_naturaleza");

        writer.startTag(tem, "k_grupo");
        writer.text("42");
        writer.endTag(tem, "k_grupo");

        writer.startTag(tem, "k_tipo");
        writer.text("1");
        writer.endTag(tem, "k_tipo");

        writer.startTag(tem, "k_fecha");
        writer.text(FechaActual);
        writer.endTag(tem, "k_fecha");

        writer.startTag(tem, "k_clave");
        writer.text(ClaveCliente);
        writer.endTag(tem, "k_clave");

        writer.startTag(tem, "k_rfc");
        writer.text(rfcCliente);
        writer.endTag(tem, "k_rfc");

        writer.startTag(tem, "k_nombre");
        writer.text(NombreCliente);
        writer.endTag(tem, "k_nombre");


        writer.startTag(tem, "k_calle");
        writer.text(Calle);
        writer.endTag(tem, "k_calle");


        writer.startTag(tem, "k_colonia");
        writer.text(Colonia);
        writer.endTag(tem, "k_colonia");


        writer.startTag(tem, "k_poblacion");
        writer.text(Poblacion);
        writer.endTag(tem, "k_poblacion");

        writer.startTag(tem, "k_desc1");
        writer.text(Desc1);
        writer.endTag(tem, "k_desc1");

        writer.startTag(tem, "k_moneda");
        writer.text("PESOS");
        writer.endTag(tem, "k_moneda");

        writer.startTag(tem, "k_paridad");
        writer.text("1");
        writer.endTag(tem, "k_paridad");

        writer.startTag(tem, "k_refer");
        writer.text("");
        writer.endTag(tem, "k_refer");

        writer.startTag(tem, "k_plazo");
        writer.text(plazo);
        writer.endTag(tem, "k_plazo");

        writer.startTag(tem, "k_vence");
        writer.text(FechaVencimiento);
        writer.endTag(tem, "k_vence");

        writer.startTag(tem, "k_cond");
        writer.text("");
        writer.endTag(tem, "k_cond");

        writer.startTag(tem, "k_coment");
        writer.text(Comentario);
        writer.endTag(tem, "k_coment");

        double Subtotal = 0;


        for (int i = 0; i < listasearch2.size(); i++) {
            precio = Double.valueOf(listasearch2.get(i).getPrecio());
            cantidad = Double.valueOf(listasearch3.get(i).getBajNeg());
            descuento = Double.valueOf(listasearch2.get(i).getDesc());
            monto = precio * cantidad;
            monto2 = monto * (descuento / 100);
            monto3 = monto - monto2;

            Subtotal = Subtotal + monto3;

        }





        DescProstr=Double.parseDouble(Desc1)/100;
        Descuentorec = Subtotal*DescProstr;
        DescuentoStr = String.valueOf(Descuentorec);

        double Subtotal2;
        Subtotal2 =Subtotal-Descuentorec;


        double ivaCal;
        double MontoTotal;

        ivaCal = Subtotal2 * (!Server.equals("vazlocolombia.dyndns.org:9085")?0.16 : 0.19);
        MontoTotal = Subtotal2 + ivaCal;

        Iva = String.valueOf(ivaCal);
        Montototal = String.valueOf(MontoTotal);

        DecimalFormat formato1 = new DecimalFormat("#.00");

        writer.startTag(tem, "k_desc");
        writer.text(formatNumberCurrency(DescuentoStr));
        writer.endTag(tem, "k_desc");


        writer.startTag(tem, "k_87");
        writer.text(DescuentoPro);
        writer.endTag(tem, "k_87");


        writer.startTag(tem, "k_iva");
        writer.text(formatNumberCurrency(Iva));
        writer.endTag(tem, "k_iva");


        writer.startTag(tem, "k_monto");
        writer.text(formatNumberCurrency(Montototal));
        writer.endTag(tem, "k_monto");

        writer.startTag(tem, "k_70");
        writer.text(Via);
        writer.endTag(tem, "k_70");



        writer.startTag(tem, "k_74");
        writer.text(stridEnvio);
        writer.endTag(tem, "k_74");




        /*Items*/
        writer.startTag(tem, "k_items");
        for (int i = 0; i < listasearch2.size(); i++) {

            precio=Double.valueOf(listasearch2.get(i).getPrecio());
            cantidad =Double.valueOf(listasearch3.get(i).getBajNeg());
            descuento =Double.valueOf(listasearch2.get(i).getDesc());
            monto=precio * cantidad;
            monto2 = monto * (descuento/100);
            monto3 =monto-monto2;




            writer.startTag(tem, "item");

            writer.startTag(tem, "k_parte");
            writer.text(listasearch2.get(i).getClaveP());
            writer.endTag(tem, "k_parte");


            writer.startTag(tem, "k_Q");
            writer.text(String.valueOf(listasearch3.get(i).getBajNeg()));
            writer.endTag(tem, "k_Q");

            writer.startTag(tem, "k_desc1");
            writer.text(listasearch2.get(i).getDesc());
            writer.endTag(tem, "k_desc1");

            writer.startTag(tem, "k_descr");
            writer.text(listasearch3.get(i).getDescripcion());
            writer.endTag(tem, "k_descr");

            writer.startTag(tem, "k_unidad");
            writer.text(listasearch3.get(i).getUnidad());
            writer.endTag(tem, "k_unidad");




            writer.startTag(tem, "k_precio");
            writer.text(listasearch2.get(i).getPrecio());
            writer.endTag(tem, "k_precio");







            writer.startTag(tem, "k_monto");
            writer.text(formato1.format(monto3));
            writer.endTag(tem, "k_monto");

            writer.startTag(tem, "k_iva");
            writer.text((!Server.equals("vazlocolombia.dyndns.org:9085")?"16" : "19"));
            writer.endTag(tem, "k_iva");

            writer.startTag(tem, "k_ieps");
            writer.text("0");
            writer.endTag(tem, "k_ieps");

            writer.startTag(tem, "k_sucursal");
            writer.text(Sucursal);
            writer.endTag(tem, "k_sucursal");

            writer.startTag(tem, "k_genero");
            writer.text("U");
            writer.endTag(tem, "k_genero");



            writer.endTag(tem, "item");

        }
        writer.endTag(tem, "k_items");
        writer.endTag(tem, "Doc");

        writer.endTag(tem, "newDocRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));


    }
}
