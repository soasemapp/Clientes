package com.almacen.keplerclientesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos2;
import com.almacen.keplerclientesapp.SetterandGetter.AplicacionesSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoBD;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoVentasSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.DisponibilidadSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlAplicaciones;
import com.almacen.keplerclientesapp.XMLS.xmlCarritoVentas;
import com.almacen.keplerclientesapp.XMLS.xmlEquiva;
import com.almacen.keplerclientesapp.XMLS.xmlProductoConsulta;
import com.almacen.keplerclientesapp.activity.Carrito.CarritoComprasActivity;
import com.almacen.keplerclientesapp.adapter.AdapterDetalleExistencia;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
import com.almacen.keplerclientesapp.includes.MyToolbar;
import com.almacen.keplerclientesapp.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

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

public class DetalladoProductosActivity extends AppCompatActivity {


    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    String Producto, Descripcion, PrecioAjustado, PrecioBase;
    private SharedPreferences preference;
    TextView txtSucursal, txtFolio, txtClaveC, txtNombreC, txtClavePro, txtCant, txtPrecio, txtDesc, txtImporte;
    TableRow fila;
    private TableLayout tableLayout;
    private SharedPreferences.Editor editor;
    ArrayList<AplicacionesSANDG> Aplicaciones = new ArrayList<>();
    ArrayList<DisponibilidadSANDG> Existencias = new ArrayList<>();
    TextView Descripciontxt, ClaveProdcutotxt, Preciotxt, Marcatxt, Modelotxt, Yeartxt;
    String strClave = " ", strDesc = " ", strCodeBar = " ", strPrecio = " ";
    String strCantidad = "1", strscliente, strscliente2, strscliente3;
    EditText Cantidad;
    RecyclerView RecyclerProductos;
    Context context = this;
    ImageView imageproducto;
    Button btnCarShoping;
    private SharedPreferences preferenceClie;
    ArrayList<CarritoVentasSANDG> listaCarShoping = new ArrayList<>();
    ArrayList<CarritoBD> listaCarShoping2 = new ArrayList<>();
    AlertDialog mDialog;
    String mensaje = "";
    String MensajePro;
    String ProductoEqui;
    String ValidaEqui;
    int ban = 1;
    String rfc;
    String plazo;
    String Vendedor;
    String Nombre;
    String Calle;
    String Colonia;
    String Poblacion;
    String Via;
    String K87;
    ConexionSQLiteHelper conect;
    String Desc1fa;
    String Comentario1;
    String Comentario2;
    String Comentario3;
    String StrFechaVencimiento;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallado_productos);

        MyToolbar.show(this, " ", false);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        mDialog = new SpotsDialog.Builder().setContext(DetalladoProductosActivity.this).setMessage("Espere un momento...").build();

        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype = preference.getString("type", "null");
        strbran = preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        strco = preference.getString("code", "null");
        StrServer = preference.getString("Servidor", "null");

        Producto = getIntent().getStringExtra("Producto");

        RecyclerProductos = findViewById(R.id.listExistencias);
        Descripciontxt = findViewById(R.id.Descr);
        ClaveProdcutotxt = findViewById(R.id.Clave);
        Preciotxt = findViewById(R.id.Precio);
        imageproducto = findViewById(R.id.imageproducto);
        Cantidad = (EditText) findViewById(R.id.Canti);
        btnCarShoping = (Button) findViewById(R.id.Add);
        tableLayout = (TableLayout) findViewById(R.id.table);

        preferenceClie = getSharedPreferences("clienteCompra", Context.MODE_PRIVATE);
        editor = preferenceClie.edit();

        Aplicaciones = new ArrayList<>();
        RecyclerProductos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Cantidad.setText("1");

        DetalladoProductosActivity.ListProductosPrecios task = new DetalladoProductosActivity.ListProductosPrecios();
        task.execute();


        btnCarShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnlineNet() == true) {


                    for (int i = 0; i < listaCarShoping2.size(); i++) {

                        if (listaCarShoping2.get(i).getParte().equals(Producto)) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta.setMessage("Ya cuentas con este producto en tu carrito de compras").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle(Producto);
                            titulo.show();
                            ban = 0;
                            break;
                        } else {
                            ban = 1;
                        }
                    }
                    if (ban == 1) {

                        String strExistencia = "";
                        int Existencia;
                        listaCarShoping.clear();
                        strCantidad = Cantidad.getText().toString();


                        if (!strCantidad.isEmpty() && !strCantidad.equals("0")) {
                            for (int i = 0; i < Existencias.size(); i++) {
                                if (strcodBra.equals(Existencias.get(i).getClave())) {
                                    strExistencia = Existencias.get(i).getDisponibilidad();
                                    break;
                                }
                            }
                            Existencia = Integer.parseInt(strExistencia);
                            if (Existencia == 0 && ValidaEqui.equals("0")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(DetalladoProductosActivity.this);

                                builder.setTitle("Confirma");
                                builder.setMessage("El " + Producto + " tiene una equivalencia  con el producto " + ProductoEqui + "\n" +
                                        "Deseas utilizar este producto equivalente o deseas conservar el producto sin disponibilidad");

                                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {

                                        Producto = ProductoEqui;

                                        DetalladoProductosActivity.AsyncCallWS4 task4 = new DetalladoProductosActivity.AsyncCallWS4();
                                        task4.execute();

                                    }
                                });

                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        DetalladoProductosActivity.AsyncCallWS4 task4 = new DetalladoProductosActivity.AsyncCallWS4();
                                        task4.execute();

                                    }
                                });

                                AlertDialog alert = builder.create();
                                alert.show();


                            } else {

                                DetalladoProductosActivity.AsyncCallWS4 task4 = new DetalladoProductosActivity.AsyncCallWS4();
                                task4.execute();

                            }


                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta.setMessage("No has ingresado una cantidad").setCancelable(false).setIcon(R.drawable.ic_baseline_error_24).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("¡Error datos!");
                            titulo.show();
                        }

                    }

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(DetalladoProductosActivity.this);
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
            }
        });


        Consulta();

    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    private class EquivaProdu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            Equiva();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            }


    }


    private void Equiva() {
        String SOAP_ACTION = "Equiva";
        String METHOD_NAME = "Equiva";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlEquiva soapEnvelope = new xmlEquiva(SoapEnvelope.VER11);
            soapEnvelope.xmlEquiva(strusr, strpass, Producto, strcodBra);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            for (int i = 0; i < response.getPropertyCount(); i++) {

                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty("MENSAJE");
                MensajePro = response0.getPropertyAsString("k_mensaje");
                ProductoEqui = response0.getPropertyAsString("k_Producto");
                ValidaEqui = response0.getPropertyAsString("k_Val");

            }


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


    private class ListProductosPrecios extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            WebServiceListProductoprecios();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Picasso.with(context).
                    load("https://www.pressa.mx/es-mx/img/products/xl/"+Producto+"/4.webp")
                    .error(R.drawable.icons8_error_52)
                    .fit()
                    .centerInside()
                    .into(imageproducto);

            Descripciontxt.setText(Descripcion);
            ClaveProdcutotxt.setText(Producto);
            Preciotxt.setText("$" + (Double.valueOf(PrecioAjustado) == 0 ? formatNumberCurrency(PrecioBase) : formatNumberCurrency(PrecioAjustado)));

            AdapterDetalleExistencia adapter = new AdapterDetalleExistencia(Existencias);
            RecyclerProductos.setAdapter(adapter);

            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < Aplicaciones.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                if (i == -1) {

                    txtClavePro = new TextView(getApplicationContext());
                    txtClavePro.setText("Marca");
                    txtClavePro.setGravity(Gravity.START);
                    txtClavePro.setBackgroundColor(Color.RED);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);


                    txtCant = new TextView(getApplicationContext());
                    txtCant.setText("Modelo");
                    txtCant.setGravity(Gravity.START);
                    txtCant.setBackgroundColor(Color.RED);
                    txtCant.setTextColor(Color.WHITE);
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);


                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setText("Año");
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setBackgroundColor(Color.RED);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);


                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setText("Motor");
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setBackgroundColor(Color.RED);
                    txtDesc.setTextColor(Color.WHITE);
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setText("Posición");
                    txtImporte.setGravity(Gravity.START);
                    txtImporte.setBackgroundColor(Color.RED);
                    txtImporte.setTextColor(Color.WHITE);
                    txtImporte.setPadding(20, 20, 20, 20);
                    txtImporte.setLayoutParams(layaoutDes);
                    fila.addView(txtImporte);

                    tableLayout.addView(fila);
                } else {


                    txtClavePro = new TextView(getApplicationContext());
                    txtClavePro.setBackgroundColor(Color.BLACK);
                    txtClavePro.setGravity(Gravity.START);
                    txtClavePro.setText(Aplicaciones.get(i).getMarca());
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);

                    txtCant = new TextView(getApplicationContext());
                    txtCant.setBackgroundColor(Color.GRAY);
                    txtCant.setGravity(Gravity.START);
                    txtCant.setText(Aplicaciones.get(i).getModelo());
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setTextColor(Color.WHITE);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);

                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setBackgroundColor(Color.BLACK);
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setText(Aplicaciones.get(i).getAno());
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);

                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setBackgroundColor(Color.GRAY);
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setText(Aplicaciones.get(i).getCilindraje() + " " + Aplicaciones.get(i).getLitraje());
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setTextColor(Color.WHITE);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setBackgroundColor(Color.BLACK);
                    txtImporte.setGravity(Gravity.START);
                    txtImporte.setText(Aplicaciones.get(i).getPosicion());
                    txtImporte.setPadding(20, 20, 20, 20);
                    txtImporte.setTextColor(Color.WHITE);
                    txtImporte.setLayoutParams(layaoutDes);
                    fila.addView(txtImporte);
                    fila.setPadding(2, 2, 2, 2);

                    tableLayout.addView(fila);

                }
            }

            DetalladoProductosActivity.EquivaProdu task1 = new DetalladoProductosActivity.EquivaProdu();
            task1.execute();
        }

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    private void WebServiceListProductoprecios() {
        String SOAP_ACTION = "Aplicaciones";
        String METHOD_NAME = "Aplicaciones";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlAplicaciones soapEnvelope = new xmlAplicaciones(SoapEnvelope.VER11);
            soapEnvelope.xmlAplicaciones(strusr, strpass, strco, Producto);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;

            String Marca = "";
            String Modelo = "";
            String Ano = "";
            String cilindraje;
            String litraje;
            String posicion;
            String precio_base;
            String precio_ajuste;
            String sucursal;
            String existencia;
            String nomSucursal;


            for (int i = 0; i < response.getPropertyCount(); i++) {

                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);

                Producto = response0.getPropertyAsString("Producto");
                Descripcion = response0.getPropertyAsString("Descripcion");
                Marca = response0.getPropertyAsString("Marca").equals("anyType{}") ? "" : response0.getPropertyAsString("Marca");
                Modelo = response0.getPropertyAsString("Modelo").equals("anyType{}") ? "" : response0.getPropertyAsString("Modelo");
                Ano = response0.getPropertyAsString("carano").equals("anyType{}") ? "" : response0.getPropertyAsString("carano");
                cilindraje = response0.getPropertyAsString("cilindraje").equals("anyType{}") ? "" : response0.getPropertyAsString("cilindraje");
                posicion = response0.getPropertyAsString("posicion").equals("anyType{}") ? "" : response0.getPropertyAsString("posicion");
                litraje = response0.getPropertyAsString("litrage").equals("anyType{}") ? "" : response0.getPropertyAsString("litrage");
                response0 = (SoapObject) response0.getProperty("precio_base");
                PrecioBase = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");
                response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                response0 = (SoapObject) response0.getProperty("precio_ajuste");
                PrecioAjustado = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");

                if (i <= 0) {
                    response0 = (SoapObject) soapEnvelope.bodyIn;
                    response0 = (SoapObject) response0.getProperty(i);
                    response0 = (SoapObject) response0.getProperty("existencia");
                    for (int j = 0; j < response0.getPropertyCount(); j++) {
                        response0 = (SoapObject) soapEnvelope.bodyIn;
                        response0 = (SoapObject) response0.getProperty(i);
                        response0 = (SoapObject) response0.getProperty("existencia");
                        response0 = (SoapObject) response0.getProperty(j);
                        String Clave, Disponibilidad, Nombre;
                        Clave = response0.getPropertyAsString("clavesuc");
                        Disponibilidad = response0.getPropertyAsString("existencia");
                        Nombre = response0.getPropertyAsString("nomSucursal");

                        Existencias.add(new DisponibilidadSANDG(Clave, Disponibilidad, Nombre));
                    }

                }
                Aplicaciones.add(new AplicacionesSANDG(Marca, Modelo, Ano, cilindraje, litraje, posicion));


            }


        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private class AsyncCallWS4 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar4();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (preferenceClie.contains("RFC") && preferenceClie.contains("PLAZO")) {

            } else {
                Nombre = listaCarShoping.get(0).getNombre();
                rfc = listaCarShoping.get(0).getRfc();
                plazo = listaCarShoping.get(0).getPlazo();
                Calle = listaCarShoping.get(0).getCalle();
                Colonia = listaCarShoping.get(0).getColonia();
                Poblacion = listaCarShoping.get(0).getPoblacion();
                Via = listaCarShoping.get(0).getVia();
                K87 = listaCarShoping.get(0).getDescPro();
                Desc1fa = listaCarShoping.get(0).getDesc1Fac();
                Comentario1 = listaCarShoping.get(0).getComentario1();
                Comentario2 = listaCarShoping.get(0).getComentario2();
                Comentario3 = listaCarShoping.get(0).getComentario3();
                Vendedor = listaCarShoping.get(0).getVendedor();


                guardarDatos2();
            }
            try {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(DetalladoProductosActivity.this, "bd_Carrito", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                ContentValues values = new ContentValues();
                for (int i = 0; i < listaCarShoping.size(); i++) {

                    String Cli = listaCarShoping.get(i).getCliente();
                    String Par = listaCarShoping.get(i).getParte();
                    String Exi = listaCarShoping.get(i).getExistencia();
                    String Can = listaCarShoping.get(i).getCantidad();
                    String Uni = listaCarShoping.get(i).getUnidad();
                    String Pre = listaCarShoping.get(i).getPrecio();
                    String Des1 = listaCarShoping.get(i).getDesc1();
                    String Des2 = listaCarShoping.get(i).getDesc2();
                    String Des3 = listaCarShoping.get(i).getDesc3();
                    String Monto = listaCarShoping.get(i).getMonto();
                    String Des = listaCarShoping.get(i).getDescr();

                    db.execSQL("INSERT INTO  carrito (Cliente,Parte,Existencia,Cantidad,Unidad,Precio,Desc1,Desc2,Desc3,Monto,Descri) values ('" + Cli + "','" + Par + "','" + Exi + "','" + Can + "','" + Uni + "','" + Pre + "','" + Des1 + "','" + Des2 + "','" + Des3 + "','" + Monto + "','" + Des + "')");
                }
                db.close();
                Intent carrito = new Intent(DetalladoProductosActivity.this, CarritoComprasActivity.class);
                startActivity(carrito);
            } catch (Exception e) {

            }
        }


    }

    private void conectar4() {
        String SOAP_ACTION = "CarShop";
        String METHOD_NAME = "CarShop";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlCarritoVentas soapEnvelope = new xmlCarritoVentas(SoapEnvelope.VER11);
            soapEnvelope.xmlCarritoVen(strusr, strpass, strco, Producto, strCantidad, "0", strcodBra);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                listaCarShoping.add(new CarritoVentasSANDG(
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_parte").equals("anyType{}") ? " " : response0.getPropertyAsString("k_parte")),
                        (response0.getPropertyAsString("k_exis").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_exis")),
                        (response0.getPropertyAsString("k_Q").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Q")),
                        (response0.getPropertyAsString("k_unidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_unidad")),
                        (response0.getPropertyAsString("k_precio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_precio")),
                        (response0.getPropertyAsString("k_desc1").equals("anyType{}") ? " " : response0.getPropertyAsString("k_desc1")),
                        (response0.getPropertyAsString("k_desc2").equals("anyType{}") ? " " : response0.getPropertyAsString("k_desc2")),
                        (response0.getPropertyAsString("k_desc3").equals("anyType{}") ? " " : response0.getPropertyAsString("k_desc3")),
                        (response0.getPropertyAsString("k_monto").equals("anyType{}") ? " " : response0.getPropertyAsString("k_monto")),
                        (response0.getPropertyAsString("k_descr").equals("anyType{}") ? " " : response0.getPropertyAsString("k_descr")),
                        (response0.getPropertyAsString("k_rfc").equals("anyType{}") ? " " : response0.getPropertyAsString("k_rfc")),
                        (response0.getPropertyAsString("k_plazo").equals("anyType{}") ? " " : response0.getPropertyAsString("k_plazo")),
                        (response0.getPropertyAsString("k_calle").equals("anyType{}") ? " " : response0.getPropertyAsString("k_calle")),
                        (response0.getPropertyAsString("k_colo").equals("anyType{}") ? " " : response0.getPropertyAsString("k_colo")),
                        (response0.getPropertyAsString("k_pobla").equals("anyType{}") ? " " : response0.getPropertyAsString("k_pobla")),
                        (response0.getPropertyAsString("k_via").equals("anyType{}") ? " " : response0.getPropertyAsString("k_via")),
                        (response0.getPropertyAsString("k_87").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_87")),
                        (response0.getPropertyAsString("k_desc1fac").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_desc1fac")),
                        (response0.getPropertyAsString("k_comentario1").equals("anyType{}") ? "" : response0.getPropertyAsString("k_comentario1")),
                        (response0.getPropertyAsString("k_comentario2").equals("anyType{}") ? "" : response0.getPropertyAsString("k_comentario2")),
                        (response0.getPropertyAsString("k_comentario3").equals("anyType{}") ? "" : response0.getPropertyAsString("k_comentario3")),
                        (response0.getPropertyAsString("k_nombre").equals("anyType{}") ? "" : response0.getPropertyAsString("k_nombre")),
                        (response0.getPropertyAsString("k_agent").equals("anyType{}") ? "" : response0.getPropertyAsString("k_agent"))));


            }


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


    private void guardarDatos2() {

        editor.putString("Nombre", Nombre);
        editor.putString("RFC", rfc);
        editor.putString("PLAZO", plazo);
        editor.putString("Calle", Calle);
        editor.putString("Colonia", Colonia);
        editor.putString("Poblacion", Poblacion);
        editor.putString("Via", Via);
        editor.putString("DescPro", K87);
        editor.putString("Desc1", Desc1fa);
        editor.putString("Comentario1", Comentario1);
        editor.putString("Comentario2", Comentario2);
        editor.putString("Comentario3", Comentario3);
        editor.putString("Vendedor", Vendedor);


        editor.commit();
    }

    private void Consulta() {
        listaCarShoping2 = new ArrayList<>();
        conect = new ConexionSQLiteHelper(DetalladoProductosActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conect.getReadableDatabase();
        Cursor fila = db.rawQuery("select * from carrito", null);
        if (fila != null && fila.moveToFirst()) {
            do {
                listaCarShoping2.add(new CarritoBD(fila.getInt(0),
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
                        fila.getString(11)));
            } while (fila.moveToNext());
        }
        db.close();

    }
    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();//No se porqué puse lo mismo O.o
        }

    }
/*
public void eliminar(View view){
        editor.clear().commit();
}*/

/*
    public void subirCantidad(View view) {
        int position = RecyclerProductos.getChildAdapterPosition(RecyclerProductos.findContainingItemView(view));
        int valor = Integer.parseInt(listProdu1.get(position).getExistencia());
        int nuevovalor = Integer.parseInt(listProdu1.get(position).getCantidad());

        if (valor == nuevovalor) {

        } else {
            nuevovalor = Integer.parseInt(listProdu1.get(position).getCantidad()) + 1 ;
            listProdu1.get(position).setCantidad(String.valueOf(nuevovalor));
            AdapterDetalleExistencia adapter = new AdapterDetalleExistencia(listProdu1);
            RecyclerProductos.setAdapter(adapter);
            RecyclerProductos.scrollToPosition(position);

        }
    }

    public void bajarCantidad(View view) {
        int position = RecyclerProductos.getChildAdapterPosition(RecyclerProductos.findContainingItemView(view));
        int valor = Integer.parseInt(listProdu1.get(position).getCantidad());

        if (valor == 0) {

        } else {
            int nuevovalor = Integer.parseInt(listProdu1.get(position).getCantidad()) - 1;
            listProdu1.get(position).setCantidad(String.valueOf(nuevovalor));
            AdapterDetalleExistencia adapter = new AdapterDetalleExistencia(listProdu1);
            RecyclerProductos.setAdapter(adapter);
            RecyclerProductos.scrollToPosition(position);

        }
    }
*/

}