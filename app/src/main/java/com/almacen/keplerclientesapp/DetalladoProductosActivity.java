package com.almacen.keplerclientesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos2;
import com.almacen.keplerclientesapp.SetterandGetter.AplicacionesSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoBD;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoVentasSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.ConversionesSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.DisponibilidadSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlAplicaciones;
import com.almacen.keplerclientesapp.XMLS.xmlCarritoVentas;
import com.almacen.keplerclientesapp.XMLS.xmlEquiva;
import com.almacen.keplerclientesapp.XMLS.xmlProductoConsulta;
import com.almacen.keplerclientesapp.activity.Carrito.CarritoComprasActivity;
import com.almacen.keplerclientesapp.adapter.AdapterDetalleExistencia;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
import com.almacen.keplerclientesapp.includes.HttpHandler;
import com.almacen.keplerclientesapp.includes.MyToolbar;
import com.almacen.keplerclientesapp.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
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
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strco, strcodBra, StrServer;
    String Producto="", claveVentana = null, Descripcion="", PrecioAjustado="0", PrecioBase="0",Linea="",TipoFotos="",LineaFotos="";
    TextView  txtClavePro, txtCant, txtPrecio, txtDesc, txtImporte;
    TextView txtClave, txtClavecompetencia, txtnombreCompetencia;
    LinearLayout TableProd;


    String descuEagle;
    String descuRodatech;
    String descuPartec;
    String descuShark;
    String descuTrackone;
    private boolean multicolor = true;
    TableRow fila;
    private TableLayout tableLayout;
    private TableLayout tableLayout2;

    ArrayList<ConversionesSANDG> listaconversiones = new ArrayList<>();

    private SharedPreferences.Editor editor;
    ArrayList<AplicacionesSANDG> Aplicaciones = new ArrayList<>();
    ArrayList<DisponibilidadSANDG> Existencias = new ArrayList<>();
    TextView Descripciontxt, ClaveProdcutotxt, Preciotxt,Lineatxt;
    String strClave = null;
    String strCantidad = "1";
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
    String Cliente = "";
    String DescPro;
    String Desc1;
    String Empresa;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallado_productos);

        MyToolbar.show(this, " ", true);
        SharedPreferences preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        preferenceClie = getSharedPreferences("clienteCompra", Context.MODE_PRIVATE);
        editor = preferenceClie.edit();
        mDialog = new SpotsDialog.Builder().setContext(DetalladoProductosActivity.this).setMessage("Espere un momento...").build();
        mDialog.setCancelable(false);
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
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                break;
            case "sprautomotive.servehttp.com:9095":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                break;
            case "sprautomotive.servehttp.com:9080":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                break;
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

        Cliente = preferenceClie.getString("CodeClien", "null");
        Nombre = preferenceClie.getString("NomClien", "null");
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


        descuEagle = preferenceClie.getString("Eagle", "");
        descuRodatech = preferenceClie.getString("Rodatech", "");
        descuPartec = preferenceClie.getString("Partech", "");
        descuShark = preferenceClie.getString("Shark", "");
        descuTrackone = preferenceClie.getString("Trackone", "");

        TableProd = findViewById(R.id.ProTable);


        Producto = getIntent().getStringExtra("Producto");

        claveVentana = getIntent().getStringExtra("claveVentana");
        strCantidad = getIntent().getStringExtra("Cantidad");
        if (claveVentana == null) {
            strClave = Producto;
        }
        RecyclerProductos = findViewById(R.id.listExistencias);
        Descripciontxt = findViewById(R.id.Descr);
        Lineatxt = findViewById(R.id.Linea);
        ClaveProdcutotxt = findViewById(R.id.Clave);
        Preciotxt = findViewById(R.id.Precio);
        imageproducto = findViewById(R.id.imageproducto);
        Cantidad =  findViewById(R.id.Canti);
        btnCarShoping =  findViewById(R.id.Add);
        tableLayout =  findViewById(R.id.table);
        tableLayout2 =  findViewById(R.id.table2);

        preferenceClie = getSharedPreferences("clienteCompra", Context.MODE_PRIVATE);
        editor = preferenceClie.edit();

        Aplicaciones = new ArrayList<>();
        RecyclerProductos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (strClave == null && strCantidad == null) {
            ListProductosPrecios task = new ListProductosPrecios();
            task.execute();
            Cantidad.setText((strCantidad == null) ? "1" : strCantidad);
        } else if (strClave != null && strCantidad.equals("1")) {
            Producto = strClave;
            ListProductosPrecios task = new ListProductosPrecios();
            task.execute();
            Cantidad.setText((strCantidad == null) ? "1" : strCantidad);
        } else if (strClave != null) {
            Producto = strClave;
            ListProductosPrecios task = new ListProductosPrecios();
            task.execute();
            Cantidad.setText((strCantidad == null) ? "1" : strCantidad);

        } else {
            ListProductosPrecios task = new ListProductosPrecios();
            task.execute();
        }


        btnCarShoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCarShoping.setEnabled(false);
                mDialog.show();

                for (int i = 0; i < listaCarShoping2.size(); i++) {

                    if (listaCarShoping2.get(i).getParte().equals(Producto)) {
                        mDialog.dismiss();
                        AlertDialog.Builder alerta = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta.setMessage("Ya cuentas con este producto en tu carrito de compras").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                mDialog.dismiss();
                                btnCarShoping.setEnabled(true);
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



                                    new DetalladoProductosActivity.AsyncBackOrder().execute();

                                }
                            });

                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    new DetalladoProductosActivity.AsyncBackOrder().execute();


                                }
                            });

                            AlertDialog alert = builder.create();
                            alert.show();


                        } else {

                            new DetalladoProductosActivity.AsyncBackOrder().execute();


                        }


                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta.setMessage("No has ingresado una cantidad").setCancelable(false).setIcon(R.drawable.ic_baseline_error_24).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                btnCarShoping.setEnabled(true);
                                mDialog.dismiss();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡Error datos!");
                        titulo.show();
                    }

                }


            }
        });


        Consulta();

    }



    private class AsyncBackOrder extends AsyncTask<Void, Void, Void> {


        private boolean conn;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }//onPreExecute

        @Override
        protected Void doInBackground(Void... voids) {


            HttpHandler sh = new HttpHandler();
            String parametros = "cliente=" + strco  + "&sucursal=" + strcodBra + "&producto=" + Producto;
            String url = "http://" + StrServer + "/backorder?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {
                    JSONObject jfacturas;
                    JSONObject jitems;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jfacturas = jsonObject.getJSONObject("Response");
                        jitems = jfacturas.getJSONObject("Back");
                        mensaje = jitems.getString("Valida");
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mensaje = "Problemas al cambiar caja";
                        }//run
                    });
                }//catch JSON EXCEPTION
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mensaje = "Problemas con el servidor";
                    }//run
                });//runUniTthread
            }//else
            return null;


        }//doInBackground

        @Override
        protected void onPostExecute(Void aBoolean) {
            super.onPostExecute(aBoolean);
            if (mensaje.equals("0")) {

                Carrito task4 = new Carrito();
                task4.execute();
            } else if(mensaje.equals("1")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetalladoProductosActivity.this);

                builder.setTitle("Confirma");
                builder.setMessage("El producto ya cuenta  con BackOrder,¿Deseas agregar este producto a tu pedido?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Carrito task4 = new Carrito();
                        task4.execute();
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnCarShoping.setEnabled(true);
                        mDialog.dismiss();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }else{
                mDialog.dismiss();
                btnCarShoping.setEnabled(true);
            }

        }//onPost
    }//CambiarCajas

    public boolean firtMet() {//firtMet
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {//si hay conexion a internet
            return true;
        } else {
            return false;
        }//else
    }//FirtMet saber si hay conexio
    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class Converciones extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "producto=" + Producto;
            String url = "http://" + StrServer + "/conversionesapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {
                    JSONObject jitems, Numero;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()>0){
                        jitems = jsonObject.getJSONObject("Item");

                        for (int i = 0; i < jitems.length(); i++) {
                            Numero = jitems.getJSONObject("" + i + "");

                            listaconversiones.add(new ConversionesSANDG(
                                    (Numero.getString("k_ClavePro").equals("") ? "" : Numero.getString("k_ClavePro")),
                                    (Numero.getString("k_ClaveCompe").equals("") ? "" : Numero.getString("k_ClaveCompe")),
                                    (Numero.getString("k_NomCompetencia").equals("") ? "" : Numero.getString("k_NomCompetencia"))));


                        }
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta1.setMessage("El Json tiene un problema").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });
                            AlertDialog titulo1 = alerta1.create();
                            titulo1.setTitle("Hubo un problema");
                            titulo1.show();

                        }//run
                    });
                }//catch JSON EXCEPTION
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta1.setMessage("Upss hubo un problema verifica tu conexion a internet").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                        AlertDialog titulo1 = alerta1.create();
                        titulo1.setTitle("Hubo un problema");
                        titulo1.show();

                    }//run
                });//runUniTthread
            }//else
            return null;
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            TableProd.setVisibility(View.VISIBLE);
            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < listaconversiones.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                if (i == -1) {
                    txtClave = new TextView(getApplicationContext());
                    txtClave.setText("Clave");
                    txtClave.setGravity(Gravity.START);
                    txtClave.setBackgroundColor(Color.RED);
                    txtClave.setTextColor(Color.WHITE);
                    txtClave.setPadding(20, 20, 20, 20);
                    txtClave.setLayoutParams(layaoutDes);
                    fila.addView(txtClave);

                    txtClavecompetencia = new TextView(getApplicationContext());
                    txtClavecompetencia.setText("Clave de la Competencia");
                    txtClavecompetencia.setGravity(Gravity.START);
                    txtClavecompetencia.setBackgroundColor(Color.RED);
                    txtClavecompetencia.setTextColor(Color.WHITE);
                    txtClavecompetencia.setPadding(20, 20, 20, 20);
                    txtClavecompetencia.setLayoutParams(layaoutDes);
                    fila.addView(txtClavecompetencia);

                    txtnombreCompetencia = new TextView(getApplicationContext());
                    txtnombreCompetencia.setText("Nombre de la Competencia");
                    txtnombreCompetencia.setGravity(Gravity.START);
                    txtnombreCompetencia.setBackgroundColor(Color.RED);
                    txtnombreCompetencia.setTextColor(Color.WHITE);
                    txtnombreCompetencia.setPadding(20, 20, 20, 20);
                    txtnombreCompetencia.setLayoutParams(layaoutDes);
                    fila.addView(txtnombreCompetencia);

                } else {
                    multicolor = !multicolor;
                    txtClave = new TextView(getApplicationContext());
                    txtClave.setGravity(Gravity.START);
                    txtClave.setBackgroundColor((multicolor) ? Color.GRAY : Color.BLACK);
                    txtClave.setText(listaconversiones.get(i).getClave());
                    txtClave.setPadding(20, 20, 20, 20);
                    txtClave.setTextColor(Color.WHITE);
                    txtClave.setLayoutParams(layaoutDes);
                    fila.addView(txtClave);

                    txtClavecompetencia = new TextView(getApplicationContext());
                    txtClavecompetencia.setBackgroundColor((multicolor) ? Color.GRAY : Color.BLACK);
                    txtClavecompetencia.setGravity(Gravity.START);
                    txtClavecompetencia.setText(listaconversiones.get(i).getClaveCompetencia());
                    txtClavecompetencia.setPadding(20, 20, 20, 20);
                    txtClavecompetencia.setTextColor(Color.WHITE);
                    txtClavecompetencia.setLayoutParams(layaoutDes);
                    fila.addView(txtClavecompetencia);

                    txtnombreCompetencia = new TextView(getApplicationContext());
                    txtnombreCompetencia.setBackgroundColor((multicolor) ? Color.GRAY : Color.BLACK);
                    txtnombreCompetencia.setGravity(Gravity.START);
                    txtnombreCompetencia.setText(listaconversiones.get(i).getNombreCompetencia());
                    txtnombreCompetencia.setPadding(20, 20, 20, 20);
                    txtnombreCompetencia.setTextColor(Color.WHITE);
                    txtnombreCompetencia.setLayoutParams(layaoutDes);
                    fila.addView(txtnombreCompetencia);


                    fila.setPadding(2, 2, 2, 2);

                }
                tableLayout2.addView(fila);

            }
            mDialog.dismiss();


        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class EquivaProdu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "producto="+Producto+"&sucursal="+strcodBra;
            String url = "http://" + StrServer + "/equivalenciaapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {


                    JSONObject jitems;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    jitems = jsonObject.getJSONObject("MENSAJE");
                    MensajePro = jitems.getString("k_mensaje");
                    ProductoEqui = jitems.getString("k_Producto");
                    ValidaEqui = jitems.getString("k_Val");


                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta1.setMessage("El Json tiene un problema").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });
                            AlertDialog titulo1 = alerta1.create();
                            titulo1.setTitle("Hubo un problema");
                            titulo1.show();

                        }//run
                    });
                }//catch JSON EXCEPTION
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta1.setMessage("Upss hubo un problema verifica tu conexion a internet").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                        AlertDialog titulo1 = alerta1.create();
                        titulo1.setTitle("Hubo un problema");
                        titulo1.show();

                    }//run
                });//runUniTthread
            }//else
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            Converciones task1 = new Converciones();
            task1.execute();
            mDialog.dismiss();

        }


    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class ListProductosPrecios extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            String Marca;
            String Modelo;
            String Ano;
            String Motor;
            String Posicion;

            String clavesuc;
            String existencia;
            String nomsucursal;

            HttpHandler sh = new HttpHandler();
            String parametros = "producto="+Producto+"&cliente="+strco;
            String url = "http://" + StrServer + "/aplicacionesapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {
                    JSONObject itemjson, Aplicacionesjson,Disponibilidadjson,Precios,Numerojson,Numero2json;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    itemjson = jsonObject.getJSONObject("Item");
                    Aplicacionesjson= itemjson .getJSONObject("Aplicaciones");

                    for (int i = 0; i < Aplicacionesjson.length(); i++) {
                        Numerojson = Aplicacionesjson.getJSONObject("" + i + "");
                        Marca=(Numerojson.getString("Marca").equals("") ? "" : Numerojson.getString("Marca"));
                        Modelo=(Numerojson.getString("Modelo").equals("") ? "" : Numerojson.getString("Modelo"));
                        Motor=(Numerojson.getString("Motor").equals("") ? "" : Numerojson.getString("Motor"));
                        Ano=(Numerojson.getString("Ano").equals("") ? "" : Numerojson.getString("Ano"));
                        Posicion=(Numerojson.getString("Posicion").equals("") ? "" : Numerojson.getString("Posicion"));
                        Aplicaciones.add(new AplicacionesSANDG(Marca, Modelo, Ano,Motor, Posicion));
                    }
                    Disponibilidadjson= itemjson .getJSONObject("Disponibilidad");

                    for (int i = 0; i < Disponibilidadjson.length(); i++) {
                        Numero2json = Disponibilidadjson.getJSONObject("" + i + "");

                        clavesuc=(Numero2json.getString("clavesuc").equals("") ? " " : Numero2json.getString("clavesuc"));
                        existencia=(Numero2json.getString("existencia").equals("") ? " " : Numero2json.getString("existencia"));
                        nomsucursal=(Numero2json.getString("nomSucursal").equals("") ? " " : Numero2json.getString("nomSucursal"));
                        Existencias.add(new DisponibilidadSANDG(clavesuc, existencia, nomsucursal));
                    }
                    Precios= itemjson .getJSONObject("Precios");
                    PrecioAjustado=(Precios.getString("precio_ajuste").equals("") ? " " : Precios.getString("precio_ajuste"));
                    PrecioBase=(Precios.getString("precio_base").equals("") ? " " : Precios.getString("precio_base"));
                    Descripcion=(Precios.getString("Descripcion").equals("") ? " " : Precios.getString("Descripcion"));
                    Linea=(Precios.getString("Linea").equals("") ? " " : Precios.getString("Linea"));
                    if(StrServer.equals("jacve.dyndns.org:9085")) {
                        TipoFotos = (Precios.getString("TipoFotos").equals("") ? " " : Precios.getString("TipoFotos"));
                        LineaFotos = (Precios.getString("LineaFotos").equals("") ? " " : Precios.getString("LineaFotos"));
                    }




                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta1.setMessage("El Json tiene un problema").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });
                            AlertDialog titulo1 = alerta1.create();
                            titulo1.setTitle("Hubo un problema");
                            titulo1.show();

                        }//run
                    });
                }//catch JSON EXCEPTION
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta1.setMessage("Upss hubo un problema verifica tu conexion a internet").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
                        AlertDialog titulo1 = alerta1.create();
                        titulo1.setTitle("Hubo un problema");
                        titulo1.show();

                    }//run
                });//runUniTthread
            }//else
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void result) {


            String EmpresaFotos="";
            if(Empresa.equals("https://www.jacve.mx/imagenes/")){
                EmpresaFotos=Empresa+TipoFotos+"/"+LineaFotos+"/"+Producto+"/2.jpg";
            }else  if (!Empresa.equals("https://vazlo.com.mx/assets/img/productos/chica/jpg/")){
                EmpresaFotos=Empresa+Producto+"/4.webp";
            }else{
                EmpresaFotos=Empresa+Producto+".jpg";

            }


            Picasso.with(context).
                    load(EmpresaFotos)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageproducto);

            Descripciontxt.setText(Descripcion);
            Lineatxt.setText(Linea);
            ClaveProdcutotxt.setText(Producto);
            Preciotxt.setText("$" + (Double.parseDouble(PrecioAjustado) == 0 ? formatNumberCurrency(PrecioBase) : formatNumberCurrency(PrecioAjustado)));

            AdapterDetalleExistencia adapter = new AdapterDetalleExistencia(Existencias);
            RecyclerProductos.setAdapter(adapter);

            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < Aplicaciones.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                txtClavePro = new TextView(getApplicationContext());
                if (i == -1) {

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

                } else {


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
                    txtDesc.setText(Aplicaciones.get(i).getMotor());
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

                }
                tableLayout.addView(fila);
            }

            EquivaProdu task1 = new EquivaProdu();
            task1.execute();
        }

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class Carrito extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "cliente=" + strco+"&producto="+Producto+"&cantidad="+strCantidad+"&existencia=0&sucursal="+strcodBra;
            String url = "http://" + StrServer + "/carritoapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {
                    JSONObject json = new JSONObject(jsonStr);


                    JSONObject jitems, Numero, Clave, Nombre;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    jitems = jsonObject.getJSONObject("Cotizacion");

                    for (int i = 0; i < jitems.length(); i++) {
                        jitems = jsonObject.getJSONObject("Cotizacion");
                        Numero = jitems.getJSONObject("" + i + "");
                        listaCarShoping.add(new CarritoVentasSANDG(
                                (Numero.getString("k_Cliente").equals("")?"":Numero.getString("k_Cliente")),
                                (Numero.getString("k_parte").equals("")?"":Numero.getString("k_parte")),
                                (Numero.getString("k_exis").equals("")?"":Numero.getString("k_exis")),
                                (Numero.getString("k_Q").equals("")?"":Numero.getString("k_Q")),
                                (Numero.getString("k_unidad").equals("")?"":Numero.getString("k_unidad")),
                                (Numero.getString("k_precio").equals("")?"":Numero.getString("k_precio")),
                                (Numero.getString("k_desc1").equals("")?"0":Numero.getString("k_desc1")),
                                (Numero.getString("k_desc2").equals("")?"0":Numero.getString("k_desc2")),
                                (Numero.getString("k_desc3").equals("")?"0":Numero.getString("k_desc3")),
                                (Numero.getString("k_monto").equals("")?"0":Numero.getString("k_monto")),
                                (Numero.getString("k_descr").equals("")?"":Numero.getString("k_descr")),
                                (Numero.getString("k_rfc").equals("")?"":Numero.getString("k_rfc")),
                                (Numero.getString("k_plazo").equals("")?"0":Numero.getString("k_plazo")),
                                (Numero.getString("k_calle").equals("")?"":Numero.getString("k_calle")),
                                (Numero.getString("k_colo").equals("")?"":Numero.getString("k_colo")),
                                (Numero.getString("k_pobla").equals("")?"":Numero.getString("k_pobla")),
                                (Numero.getString("k_via").equals("")?"":Numero.getString("k_via")),
                                (Numero.getString("k_87").equals("")?"":Numero.getString("k_87")),
                                (Numero.getString("k_desc1fac").equals("")?"0":Numero.getString("k_desc1fac")),
                                (Numero.getString("k_comentario1").equals("")?"":Numero.getString("k_comentario1")),
                                (Numero.getString("k_comentario2").equals("")?"":Numero.getString("k_comentario2")),
                                (Numero.getString("k_comentario3").equals("")?"":Numero.getString("k_comentario3")),
                                (Numero.getString("k_descEAGLE").equals("")?"0":Numero.getString("k_descEAGLE")),
                                (Numero.getString("k_descRODATECH").equals("")?"0":Numero.getString("k_descRODATECH")),
                                (Numero.getString("k_descPARTECH").equals("")?"0":Numero.getString("k_descPARTECH")),
                                (Numero.getString("k_descSHARK").equals("")?"0":Numero.getString("k_descSHARK")),
                                (Numero.getString("k_descTRACKONE").equals("")?"0":Numero.getString("k_descTRACKONE")),
                                (Numero.getString("k_nombre").equals("")?"0":Numero.getString("k_nombre")),
                                (Numero.getString("k_agent").equals("")?"0":Numero.getString("k_agent")),
                                 (Numero.getString("TipoFotos").equals("") ? "0" : Numero.getString("TipoFotos")),
                                (Numero.getString("LineaFotos").equals("") ? "0" : Numero.getString("LineaFotos"))));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                            alerta1.setMessage("El Json tiene un problema").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    btnCarShoping.setEnabled(true);
                                }
                            });
                            AlertDialog titulo1 = alerta1.create();
                            titulo1.setTitle("Hubo un problema");
                            titulo1.show();

                        }//run
                    });
                }//catch JSON EXCEPTION
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(DetalladoProductosActivity.this);
                        alerta1.setMessage("Upss hubo un problema verifica tu conexion a internet").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                btnCarShoping.setEnabled(true);
                            }
                        });
                        AlertDialog titulo1 = alerta1.create();
                        titulo1.setTitle("Hubo un problema");
                        titulo1.show();

                    }//run
                });//runUniTthread
            }//else
            return null;

        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (preferenceClie.contains("RFC") && preferenceClie.contains("PLAZO")) {

            } else {
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
                Nombre= listaCarShoping.get(0).getNombre();
                Vendedor=listaCarShoping.get(0).getAgente();

                descuEagle = listaCarShoping.get(0).getEagle();
                descuRodatech = listaCarShoping.get(0).getRodatech();
                descuPartec = listaCarShoping.get(0).getPartech();
                descuShark = listaCarShoping.get(0).getShark();
                descuTrackone = listaCarShoping.get(0).getTrackoone();


                guardarDatos2();
            }
            try {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(DetalladoProductosActivity.this, "bd_Carrito", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
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
                    String FotoTipo = listaCarShoping.get(i).getTipoFotos();
                    String FotoLinea = listaCarShoping.get(i).getLineaFotos();

                    db.execSQL("INSERT INTO  carrito (Cliente,Parte,Existencia,Cantidad,Unidad,Precio,Desc1,Desc2,Desc3,Monto,Descri,FotosTipo,FotosLinea) values ('" + Cli + "','" + Par + "','" + Exi + "','" + Can + "','" + Uni + "','" + Pre + "','" + Des1 + "','" + Des2 + "','" + Des3 + "','" + Monto + "','" + Des + "','"+FotoTipo+"','"+FotoLinea+"')");
                }
                db.close();
                Intent carrito = new Intent(DetalladoProductosActivity.this, CarritoComprasActivity.class);
                startActivity(carrito);
                finish();
                mDialog.dismiss();
            } catch (Exception e) {

            }
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
        editor.putString("Eagle", descuEagle);
        editor.putString("Rodatech", descuRodatech);
        editor.putString("Partech", descuPartec);
        editor.putString("Shark", descuShark);
        editor.putString("Trackone", descuTrackone);


        editor.commit();
    }

    private void Consulta() {
        listaCarShoping2 = new ArrayList<>();
        conect = new ConexionSQLiteHelper(DetalladoProductosActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conect.getReadableDatabase();
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from carrito", null);
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
                        fila.getString(11),
                        fila.getString(12),
                        fila.getString(13)));
            } while (fila.moveToNext());
        }
        db.close();

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        //No se porqué puse lo mismo O.o
        if (count == 0) {
            super.onBackPressed();
        }
        getFragmentManager().popBackStack();

    }
}