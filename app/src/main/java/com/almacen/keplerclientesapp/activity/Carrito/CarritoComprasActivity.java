package com.almacen.keplerclientesapp.activity.Carrito;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.almacen.keplerclientesapp.ConexionSQLiteHelper;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoBD;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoVentasSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.SearachClientSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.listExistenciaSANG;
import com.almacen.keplerclientesapp.XMLS.xmlCarritoCompras;
import com.almacen.keplerclientesapp.XMLS.xmlValidaPedColombia2;
import com.almacen.keplerclientesapp.XMLS.xmlValidaPedMexico2;
import com.almacen.keplerclientesapp.adapter.AdaptadorCarrito;
import com.almacen.keplerclientesapp.includes.MyToolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class CarritoComprasActivity extends AppCompatActivity {


    int vald;
    int n = 2000;
    int ban = 0;

    double DescProstr = 0;
    double Descuento = 0;
    double IvaVariado = 0;
    String Clave = "";
    Context context = this;
    String Desc1="0";

    TextView txtSubtotal;
    TextView txtSubtotal2;
    TextView txtDescuento;
    TextView txtiva;
    TextView txtMontototal;
    EditText comentario;
    Button ButtonCot;
    LinearLayout pantallacarrito;
    LinearLayout pantallacarritovacio;

    RecyclerView recyclerCarrtio;
    ArrayList<listExistenciaSANG> listaExistencia = new ArrayList<>();
    ArrayList<CarritoBD> listaCarShoping = new ArrayList<>();
    ArrayList<SearachClientSANDG> listaclientG = new ArrayList<>();
    ArrayList<CarritoVentasSANDG> listaCarShoping2 = new ArrayList<>();
    private SharedPreferences preferenceClie;


    ConexionSQLiteHelper conn;


    AlertDialog mDialog;

    String strusr, strpass, strname, strlname, strtype, strbran, strma, StrServer, strcodBra, strcode;
    String strClave = " ";
    String MenValPedi;
    String ValPedido = "";
    String Desc1Valida = "";
    String DescuentoValida = "";
    String SubtotalValida = "";
    String SeCambiovalida = "";
    String strCantidad, strscliente, strscliente2, strscliente3;
    String K87;
    String Desc1fa;
    String Comentario1;
    String Comentario2;
    String Comentario3;
    String Vendedor;
    String MensajePro;
    String ProductoEqui;
    String ValidaEqui;
    TextView textInstrucciones;


    String ExistenciaProd;
    String ClaveProducto;
    String DescripcionProd;
    String CodBarras;
    String Precios;

    String Producto;
    String Precio;
    String Existencia;
    String Descripcion;
    String Cantidad;

    String Empresa = "";
    String ivstr;
    String MontoStr;
    String Comentario="";
    String mensaje = "";
    String Cliente;
    String Nombre="";
    String rfc;
    String plazo;
    String Calle;
    String Colonia;
    String Poblacion;
    String Via;
    String DescPro;

    String StrFechaActaul;
    String StrFechaVencimiento;
    String SubdescuentoValida;
    String Mensaje;
    String Documento;
    String Folio;
    String DescuentoStr;

    String descuEagle;
    String descuRodatech;
    String descuPartec;
    String descuShark;
    String descuTrackone;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    Spinner spinerClie;



    LinearLayout CliOcul;
    AlertDialog.Builder builder6;
    AlertDialog dialog6 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_compras);
        mDialog = new SpotsDialog.Builder().setContext(CarritoComprasActivity.this).setMessage("Espere un momento...").build();

        MyToolbar.show(this, "", false);
        recyclerCarrtio = (RecyclerView) findViewById(R.id.lisCarrito);

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        preferenceClie = getSharedPreferences("clienteCompra", Context.MODE_PRIVATE);
        editor = preferenceClie.edit();
        comentario = findViewById(R.id.edComentario);
        ButtonCot = (Button) findViewById(R.id.btnCotizar);
        spinerClie = (Spinner) findViewById(R.id.spinnerClie);
        txtSubtotal = findViewById(R.id.SubTotal);
        txtDescuento = findViewById(R.id.Descuento);
        txtiva = findViewById(R.id.iva);
        txtSubtotal2 = findViewById(R.id.SubTotal2);
        txtMontototal = (TextView) findViewById(R.id.MontoTotal);
        pantallacarrito =findViewById(R.id.linepantalla);
        pantallacarritovacio =findViewById(R.id.linepantallaVacio);



        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype = preference.getString("type", "null");
        strbran = preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        strcode = preference.getString("code", "null");
        StrServer = preference.getString("Servidor", "null");


        Nombre =preferenceClie.getString("Nombre", "null");
        rfc = preferenceClie.getString("RFC", "null");
        plazo = preferenceClie.getString("PLAZO", "null");
        Calle = preferenceClie.getString("Calle", "null");
        Colonia = preferenceClie.getString("Colonia", "null");
        Poblacion = preferenceClie.getString("Poblacion", "null");
        Via = preferenceClie.getString("Via", "null");
        DescPro = preferenceClie.getString("DescPro", "0");
        Desc1 = preferenceClie.getString("Desc1", "0");
        Comentario1 = preferenceClie.getString("Comentario1", "");
        Comentario2 = preferenceClie.getString("Comentario2", "");
        Comentario3 = preferenceClie.getString("Comentario3", "");
        Vendedor = preferenceClie.getString("Vendedor", "");

        descuEagle = preferenceClie.getString("Eagle", "");
        descuRodatech = preferenceClie.getString("Rodatech", "");
        descuPartec = preferenceClie.getString("Partech", "");
        descuShark = preferenceClie.getString("Shark", "");
        descuTrackone = preferenceClie.getString("Trackone", "");


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Empresa = "https://www.jacve.mx/imagenes/";
                break;
            case "autodis.ath.cx:9085":
                Empresa = "https://www.autodis.mx/es-mx/img/products/xl/";
                break;
            case "cecra.ath.cx:9085":
                Empresa = "https://www.cecra.mx/es-mx/img/products/xl/";
                break;
            case "guvi.ath.cx:9085":
                Empresa = "https://www.guvi.mx/es-mx/img/products/xl/";
                break;
            case "cedistabasco.ddns.net:9085":
                Empresa = "https://www.pressa.mx/es-mx/img/products/xl/";
                break;
            case "sprautomotive.servehttp.com:9090":
            case "sprautomotive.servehttp.com:9095":
            case "sprautomotive.servehttp.com:9080":
            case "sprautomotive.servehttp.com:9085":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                break;
            case "vazlocolombia.dyndns.org:9085":
                Empresa = "https://vazlo.com.mx/assets/img/productos/chica/jpg/";
                break;
            default:
                Empresa = "https://www.pressa.mx/es-mx/img/products/xl/";
                break;
        }




        IvaVariado = ((!StrServer.equals("vazlocolombia.dyndns.org:9085")) ? 0.16 : 0.19);


        vald = getIntent().getIntExtra("val", 0);

        ButtonCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonCot.setEnabled(false);
                if (StrServer.equals("vazlocolombia.dyndns.org:9085")) {
                    if (listaCarShoping.size() != 0) {

                        Comentario = comentario.getText().toString();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                        c.add(Calendar.DAY_OF_YEAR, 30);
                        String fechaactual = dateformatActually.format(c.getTime());
                        StrFechaVencimiento = fechaactual;
                        CarritoComprasActivity.AsyncCallWS2 task = new CarritoComprasActivity.AsyncCallWS2();
                        task.execute();


                    } else {

                        ButtonCot.setEnabled(true);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
                        alerta.setMessage("Agrege productos al Carrito de Compras").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("No hay productos en el carrito de compras");
                        titulo.show();
                    }
                } else {
                    if (listaCarShoping.size() != 0) {
                        Comentario = comentario.getText().toString();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                        c.add(Calendar.DAY_OF_YEAR, 30);
                        String fechaactual = dateformatActually.format(c.getTime());
                        StrFechaVencimiento = fechaactual;
                        CarritoComprasActivity.ValidaPedidoMexico task = new CarritoComprasActivity.ValidaPedidoMexico();
                        task.execute();


                    } else {

                        ButtonCot.setEnabled(true);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
                        alerta.setMessage("Agrege productos al Carrito de Compras").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("No hay productos en el carrito de compras");
                        titulo.show();
                        ButtonCot.setEnabled(true);
                    }
                }

            }
        });



        if(isOnlineNet()==true){

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
            alerta.setMessage("Problemas de conexion verifique su red").setCancelable(false).setIcon(R.drawable.ic_baseline_signal_wifi_statusbar_connected_no_internet_4_24).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡Error de Conexion!");
            titulo.show();
        }
        Consulta();






        if(listaCarShoping.size()==0){
            pantallacarrito.setVisibility(View.GONE);
            pantallacarritovacio.setVisibility(View.VISIBLE);
        }else{
            pantallacarrito.setVisibility(View.VISIBLE);
            pantallacarritovacio.setVisibility(View.GONE);
        }

        Montototal();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
        String fechaactual = dateformatActually.format(c.getTime());
        StrFechaActaul = fechaactual;


    }


    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.mx");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }







    private void DialogCarga() {
        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargacarrito, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        textInstrucciones = (TextView) dialogView.findViewById(R.id.Instucciones);

    }





    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            conectar1();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
            alerta.setMessage("Documento = " + Documento + "\n" +
                    "Folio = " + Folio).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                    SQLiteDatabase db = conn.getReadableDatabase();

                    editor.clear();
                    editor.commit();
                    BorrarCarrito();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    finish();

                    dialogInterface.cancel();

                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle(Mensaje);
            titulo.show();

            ButtonCot.setEnabled(true);


        }


    }

    private void conectar1() {
        String SOAP_ACTION = "NewDoc";
        String METHOD_NAME = "NewDoc";
        String NAMESPACE = "http://" + StrServer + "/WSk80Docs/";
        String URL = "http://" + StrServer + "/WSk80Docs";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlCarritoCompras soapEnvelope = new xmlCarritoCompras(SoapEnvelope.VER11);
            soapEnvelope.xmlCarritoCompras(Comentario, Nombre, strcode, StrFechaActaul, StrFechaVencimiento, strcodBra, strusr, strpass, rfc, plazo, MontoStr, ivstr, DescuentoStr, DescPro, Desc1, Calle, Colonia, Poblacion, listaCarShoping, StrServer,Clave,Vendedor, descuEagle, descuRodatech, descuPartec, descuShark, descuTrackone);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            Mensaje = response0.getPropertyAsString("message");
            Documento = response0.getPropertyAsString("doc");
            Folio = response0.getPropertyAsString("folio");


        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            mensaje = "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }
    }


    private class AsyncCallWS2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar2();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
            alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CarritoComprasActivity.AsyncCallWS task4 = new CarritoComprasActivity.AsyncCallWS();
                    task4.execute();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Pedido");
            titulo.show();


        }


    }


    private void conectar2() {
        String SOAP_ACTION = "ValidaPedColombia2";
        String METHOD_NAME = "ValidaPedColombia2";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlValidaPedColombia2 soapEnvelope = new xmlValidaPedColombia2(SoapEnvelope.VER11);
            soapEnvelope.xmlValidaPedColombia(strusr, strpass, strcode, MontoStr, SubdescuentoValida, Desc1);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            response0 = (SoapObject) response0.getProperty("MENSAJE");
            MenValPedi = response0.getPropertyAsString("k_messenge");
            Desc1Valida = response0.getPropertyAsString("k_desc1");
            DescuentoValida = response0.getPropertyAsString("Descuento");
            SubtotalValida = response0.getPropertyAsString("Subtotal");
            SeCambiovalida = response0.getPropertyAsString("SeCambio");


        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            mensaje = "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }
    }


    private class ValidaPedidoMexico extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ValidaPed();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(CarritoComprasActivity.this);
            alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CarritoComprasActivity.AsyncCallWS task4 = new CarritoComprasActivity.AsyncCallWS();
                    task4.execute();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Pedido");
            titulo.show();


        }


    }


    private void ValidaPed() {
        String SOAP_ACTION = "ValidaPedMexico2";
        String METHOD_NAME = "ValidaPedMexico2";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlValidaPedMexico2 soapEnvelope = new xmlValidaPedMexico2(SoapEnvelope.VER11);
            soapEnvelope.xmlValidaPedi(strusr, strpass, strcode, MontoStr);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            response0 = (SoapObject) response0.getProperty("MENSAJE");
            MenValPedi = response0.getPropertyAsString("k_messenge");


        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            mensaje = "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }
    }


    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    private void Montototal() {
        double Subtotal = 0;
        String Subtotal1;
        for (int i = 0; i < listaCarShoping.size(); i++) {
            Subtotal = Subtotal + Double.parseDouble(listaCarShoping.get(i).getMonto());
        }

        SubdescuentoValida = String.valueOf(Subtotal);
        ;
        Subtotal1 = String.valueOf(Subtotal);
        txtSubtotal.setText(Html.fromHtml("Subtotal:<font color=#000000>$</font><font color=#000000>" + formatNumberCurrency(Subtotal1) + "</font>"));
        DescProstr = Double.parseDouble(Desc1) / 100;
        Descuento = Subtotal * DescProstr;
        DescuentoStr = String.valueOf(Descuento);
        txtDescuento.setText(Html.fromHtml("Descuento:<font color=#000000>$</font><font color=#000000>" + formatNumberCurrency(DescuentoStr) + "</font>"));

        double Subtotal2;
        Subtotal2 = Subtotal - Descuento;


        double ivaCal;
        double MontoTotal;

        ivaCal = Subtotal2 * IvaVariado;
        MontoTotal = Subtotal2 + ivaCal;
        String SubtotalStr = String.valueOf(Subtotal2);
        ivstr = String.valueOf(ivaCal);
        MontoStr = String.valueOf(MontoTotal);
        txtSubtotal2.setText(Html.fromHtml("SubTotal:<font color=#000000>$</font><font color=#000000>" + formatNumberCurrency(SubtotalStr) + "</font>"));
        txtiva.setText(Html.fromHtml("Iva:<font color=#000000>$</font><font color=#000000>" + formatNumberCurrency(ivstr) + "</font>"));
        txtMontototal.setText(Html.fromHtml("Total:<font color=#000000>$</font><font color=#FF0000>" + formatNumberCurrency(MontoStr) + "</font>"));

    }


    public void Delete(View view) {
        int position = recyclerCarrtio.getChildAdapterPosition(recyclerCarrtio.findContainingItemView(view));


        if (listaCarShoping.size() > 1) {
            if (listaCarShoping.size() >= 3) {
                int posiEnd = listaCarShoping.size() - 1;
                if (position == 0) {
                    String Parte1 = listaCarShoping.get(position).getParte();
                    String Parte2 = listaCarShoping.get(position + 1).getParte();
                    Double precio = Double.parseDouble(listaCarShoping.get(position + 1).getPrecio());
                    if (Parte1.equals(Parte2) && precio == 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position + 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();
                        int ID = listaCarShoping.get(position).getID();
                        db.delete("carrito", "ID=" + ID, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();


                    }
                } else if (position == posiEnd) {
                    String Parte1 = listaCarShoping.get(position).getParte();
                    String Parte2 = listaCarShoping.get(position - 1).getParte();
                    Double precio = Double.parseDouble(listaCarShoping.get(position).getPrecio());
                    Double precio1 = Double.parseDouble(listaCarShoping.get(position - 1).getPrecio());
                    if (Parte1.equals(Parte2) && precio == 0 && precio1 > 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position - 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();
                        int ID = listaCarShoping.get(position).getID();
                        db.delete("carrito", "ID=" + ID, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    }
                } else {
                    String centro = listaCarShoping.get(position).getParte();
                    String abajo = listaCarShoping.get(position + 1).getParte();
                    String arriba = listaCarShoping.get(position - 1).getParte();
                    Double precioCen = Double.parseDouble(listaCarShoping.get(position).getPrecio());
                    Double precioArr = Double.parseDouble(listaCarShoping.get(position - 1).getPrecio());
                    Double precioAbajo = Double.parseDouble(listaCarShoping.get(position + 1).getPrecio());
                    if (centro.equals(arriba) && precioCen == 0 && precioArr > 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position - 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else if (centro.equals(abajo) && precioAbajo == 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position + 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        db.delete("carrito", "ID=" + ID, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            } else {
                if (position == 0) {
                    String PartePrec = listaCarShoping.get(position).getParte();
                    String ParAbajo = listaCarShoping.get(position + 1).getParte();
                    Double PrecioAbajo = Double.parseDouble(listaCarShoping.get(position + 1).getPrecio());
                    if (PartePrec.equals(ParAbajo) && PrecioAbajo == 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position + 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();

                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        db.delete("carrito", "ID=" + ID, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    }
                } else if (position == 1) {
                    String PartePrec = listaCarShoping.get(position).getParte();
                    double PrecioPrec = Double.parseDouble(listaCarShoping.get(position).getPrecio());
                    String ParArriba = listaCarShoping.get(position - 1).getParte();
                    double PrecioArriba = Double.parseDouble(listaCarShoping.get(position - 1).getPrecio());
                    if (PartePrec.equals(ParArriba) && PrecioArriba > 0 && PrecioPrec == 0) {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        int ID2 = listaCarShoping.get(position - 1).getID();

                        db.delete("carrito", "ID=" + ID, null);
                        db.delete("carrito", "ID=" + ID2, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
                        SQLiteDatabase db = conn.getReadableDatabase();

                        int ID = listaCarShoping.get(position).getID();
                        db.delete("carrito", "ID=" + ID, null);
                        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
                        db.close();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }
            }
        } else {
            conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
            SQLiteDatabase db = conn.getReadableDatabase();
            int ID = listaCarShoping.get(position).getID();
            db.delete("carrito", "ID=" + ID, null);
            db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
            db.close();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            finish();
        }
    }


    private void BorrarCarrito() {
        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        db.delete("carrito", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
        db.close();
    }

    private void Consulta() {
        recyclerCarrtio.setLayoutManager(new LinearLayoutManager(this));
        listaCarShoping = new ArrayList<>();
        conn = new ConexionSQLiteHelper(CarritoComprasActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor fila = db.rawQuery("select * from carrito", null);
        if (fila != null && fila.moveToFirst()) {
            do {
                listaCarShoping.add(new CarritoBD(fila.getInt(0),
                        fila.getString(1),
                        fila.getString(2),
                        fila.getString(3),
                        fila.getString(4),
                        fila.getString(5),
                        fila.getString(6),
                        fila.getString(7),
                        fila.getString(8),
                        fila.getString(9),
                        fila.getString(10),
                        fila.getString(11),
                        fila.getString(12),
                        fila.getString(13)));
            } while (fila.moveToNext());
        }
        AdaptadorCarrito adapter = new AdaptadorCarrito(listaCarShoping, Desc1, StrServer, context, Empresa);
        recyclerCarrtio.setAdapter(adapter);
        db.close();

    }




}