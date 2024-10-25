package com.almacen.keplerclientesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.almacen.keplerclientesapp.SetandGet.SetGetListMarca;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos2;
import com.almacen.keplerclientesapp.SetterandGetter.ListLineaSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.ListLineaSANDG2;
import com.almacen.keplerclientesapp.SetterandGetter.SearachClientSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.SetGetListMarca2;
import com.almacen.keplerclientesapp.SetterandGetter.SetGetListModelo2;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueGeneral;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueProductos;
import com.almacen.keplerclientesapp.XMLS.xmlListLine;
import com.almacen.keplerclientesapp.XMLS.xmlListMarca;
import com.almacen.keplerclientesapp.XMLS.xmlListModelo;
import com.almacen.keplerclientesapp.XMLS.xmlProductoConsulta;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
import com.almacen.keplerclientesapp.includes.HttpHandler;
import com.almacen.keplerclientesapp.includes.MyToolbar;
import com.almacen.keplerclientesapp.ui.home.HomeFragment;
import com.google.android.material.checkbox.MaterialCheckBox;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class BusquedaActivity extends AppCompatActivity {
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strco, strcodBra, StrServer;
    private SharedPreferences.Editor editor;
    ArrayList<SetGetListMarca2> listMarca = new ArrayList<>();
    ArrayList<SetGetListModelo2> listModelo = new ArrayList<>();
    ArrayList<SetGetListProductos> listProdu1 = new ArrayList<>();
    ArrayList<ListLineaSANDG2> listaLinea = new ArrayList<>();


    TextView txtmarca, txtmodelo, txtlinea;
    private Spinner spineryear;
    String claveMarca;
    Button btnMarca, btnModelo, btfLinea;
    AlertDialog mDialog;
    MaterialCheckBox yearCheck;
    RecyclerView RecyclerProductos;
    Context context;
    Button btnBuscar, btnfiltro;
    String fechainicio = "", fechafinal = "", marca = "", modelo = "", linea = "", check = "";
    LinearLayout linearFiltro;
    int ban = 1;

    String[] search = new String[75];

    String BusquedaProducto = null;

    EditText BusquedaProductoed;
    String Empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);


        mDialog = new SpotsDialog(BusquedaActivity.this);
        mDialog.setCancelable(false);
        SharedPreferences preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        MyToolbar.show(this, "Busqueda", true);
        editor = preference.edit();


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
        BusquedaProducto = getIntent().getStringExtra("Producto");


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

        btnMarca = findViewById(R.id.btnFMarca);
        btnModelo = findViewById(R.id.btnFModelo);
        btfLinea = findViewById(R.id.btfLinea);
        yearCheck = findViewById(R.id.checkyear);
        RecyclerProductos = findViewById(R.id.listProductos);
        btnBuscar = findViewById(R.id.btnBuscar);
        BusquedaProductoed = findViewById(R.id.idBusqueda);
        linearFiltro = findViewById(R.id.Filter);
        btnfiltro = findViewById(R.id.btnFilters);
        txtmarca = findViewById(R.id.marcatxt);
        txtmodelo = findViewById(R.id.modelotxt);
        txtlinea = findViewById(R.id.lineatxt);
        spineryear = findViewById(R.id.spinneryear);
        linearFiltro.setVisibility(View.GONE);
        spineryear.setEnabled(false);
        btnfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ban == 0) {
                    ban = 1;
                    linearFiltro.setVisibility(View.GONE);
                } else {
                    ban = 0;
                    linearFiltro.setVisibility(View.VISIBLE);
                }

            }
        });

        String[] opciones = new String[75];
        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = String.valueOf(1950 + i);
            search[i] = String.valueOf(1950 + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, opciones);
        spineryear.setAdapter(adapter);


        check = "0";
        listProdu1 = new ArrayList<>();
        RecyclerProductos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    if (check.equals("1") && (!marca.equals("") && !modelo.equals(""))) {
                        for (int i = 0; i < search.length; i++) {
                            int posi = spineryear.getSelectedItemPosition();
                            if (posi == i) {
                                fechainicio = search[i];
                                break;
                            }
                        }
                        listProdu1.clear();
                        ListProductos task = new ListProductos();
                        task.execute();
                    } else if (check.equals("0") && (!marca.equals("") && !modelo.equals(""))) {

                        fechainicio = "";
                        listProdu1.clear();
                        ListProductos task = new ListProductos();
                        task.execute();
                    } else {
                        listProdu1.clear();
                        AlertDialog.Builder alerta = new AlertDialog.Builder(BusquedaActivity.this);
                        alerta.setMessage("No has seleccionado una marca o modelo").setIcon(R.drawable.ic_baseline_error_24).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("!ERROR!");
                        titulo.show();
                    }



            }
        });

        yearCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yearCheck.isChecked()) {
                    check = "1";
                    spineryear.setEnabled(true);
                } else {
                    check = "0";
                    spineryear.setEnabled(false);
                }
            }
        });

        btnModelo.setEnabled(false);
        btfLinea.setEnabled(false);
        ListMarca task = new ListMarca();
        task.execute();
        btnMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] opciones = new String[listMarca.size()];

                for (int i = 0; i < listMarca.size(); i++) {
                    opciones[i] = listMarca.get(i).getDescripcion();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaActivity.this);
                builder.setTitle("¿Que Marca Buscas?");


                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnModelo.setText(Html.fromHtml("Modelo"));

                        modelo = "";
                        linea = "";

                        claveMarca = listMarca.get(which).getDescripcion();//Html.fromHtml("Marca" <br/> C/U:$<font color ='#4CAF50' size=4> +listMarca.get(which).getDescripcion())+"</font>")
                        btnModelo.setEnabled(true);
                        btfLinea.setEnabled(false);
                        txtmarca.setVisibility(View.VISIBLE);
                        txtmarca.setText(listMarca.get(which).getDescripcion() + " " + "X");
                        txtmodelo.setVisibility(View.GONE);
                        txtmodelo.setText("");
                        txtlinea.setVisibility(View.GONE);
                        txtlinea.setText("");
                        listModelo.clear();
                        listaLinea.clear();
                        marca = listMarca.get(which).getDescripcion();
                        ListModelo task = new ListModelo();
                        task.execute();
                        dialog.dismiss();
                    }
                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        btnModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] opciones = new String[listModelo.size()];

                for (int i = 0; i < listModelo.size(); i++) {
                    opciones[i] = listModelo.get(i).getDescripcion();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaActivity.this);
                builder.setTitle("¿Que Modelo Buscas?");


                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        modelo = listModelo.get(which).getDescripcion();
                        listaLinea.clear();
                        txtmodelo.setVisibility(View.VISIBLE);
                        txtmodelo.setText(listModelo.get(which).getDescripcion() + " " + "X");
                        txtlinea.setVisibility(View.GONE);
                        txtlinea.setText("");


                        ListLineas task = new ListLineas();
                        task.execute();
                    }
                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        btfLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] opciones = new String[listaLinea.size()];

                for (int i = 0; i < listaLinea.size(); i++) {
                    opciones[i] = listaLinea.get(i).getLinea();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(BusquedaActivity.this);
                builder.setTitle("¿Que Linea Buscas?");


                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        linea = "";
                        linea = listaLinea.get(which).getLinea();
                        txtlinea.setVisibility(View.VISIBLE);
                        txtlinea.setText(listaLinea.get(which).getLinea() + " " + "X");
                        dialog.dismiss();
                    }
                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        BusquedaProductoed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) { //Do Something } return false; } });

                        BusquedaProducto = BusquedaProductoed.getText().toString();
                        listProdu1.clear();
                        BusquedaGeneral task = new BusquedaGeneral();
                        task.execute();


                }
                return false;
            }
        });


        if (BusquedaProducto != null) {
            listProdu1.clear();
            BusquedaGeneral task1 = new BusquedaGeneral();
            task1.execute();
        }


    }





    public void borrarmarca(View view) {
        marca = "";
        modelo = "";
        linea = "";
        btnModelo.setEnabled(false);
        btfLinea.setEnabled(false);
        txtmarca.setVisibility(View.GONE);
        txtmodelo.setVisibility(View.GONE);
        txtmodelo.setText("");
        txtlinea.setVisibility(View.GONE);
        txtlinea.setText("");
        listModelo.clear();
        listaLinea.clear();
    }

    public void borrarmodelo(View view) {

        modelo = "";
        linea = "";
        btfLinea.setEnabled(false);
        txtmodelo.setVisibility(View.GONE);
        txtmodelo.setText("");
        txtlinea.setVisibility(View.GONE);
        txtlinea.setText("");
        listaLinea.clear();
    }

    public void borrarmlinea(View view) {
        linea = "";
        txtlinea.setVisibility(View.GONE);
        txtlinea.setText("");
    }

    @SuppressLint("StaticFieldLeak")
    private class ListLineas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "marca=" + marca + "&modelo=" + modelo;
            String url = "http://" + StrServer + "/listalineasapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {


                    JSONObject jitems, Numero;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jitems = jsonObject.getJSONObject("Item");

                        for (int i = 0; i < jitems.length(); i++) {
                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");
                            if (!Numero.getString("k_Linea").equals("")) {
                                listaLinea.add(new ListLineaSANDG2((Numero.getString("k_Linea").equals("") ? "" : Numero.getString("k_Linea"))));
                            }

                        }
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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

            btfLinea.setEnabled(true);
            mDialog.dismiss();

        }


    }


    @SuppressLint("StaticFieldLeak")
    private class ListMarca extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = "http://" + StrServer + "/listamarcaapp";
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {


                    JSONObject jitems, Numero;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jitems = jsonObject.getJSONObject("Item");

                        for (int i = 0; i < jitems.length(); i++) {
                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");
                            listMarca.add(new SetGetListMarca2(
                                    (Numero.getString("k_Descripcion").equals("anyType{}") ? "" : Numero.getString("k_Descripcion"))));
                        }
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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

        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class ListModelo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "marca=" + claveMarca;
            String url = "http://" + StrServer + "/listalineasmodelosapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {


                    JSONObject jitems, Numero;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jitems = jsonObject.getJSONObject("Item");

                        for (int i = 0; i < jitems.length(); i++) {
                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");
                            listModelo.add(new SetGetListModelo2(
                                    (Numero.getString("k_Descripcion").equals("") ? "" : Numero.getString("k_Descripcion"))));
                        }
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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

        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class ListProductos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "cliente=" + strco  + "&fechainicial=" + fechainicio + "&marca=" + marca + "&modelo=" + modelo + "&linea=" + linea + "&checkano=" + check;
            String url = "http://" + StrServer + "/buscadormmfapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {

                    JSONObject jitems, Numero, Precio;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jitems = jsonObject.getJSONObject("Item");

                        String Producto;
                        String Descripcion;
                        String Linea;
                        String precio_base;
                        String precio_ajuste;
                        String TipoFotos;
                        String LineaFotos;

                        for (int i = 0; i < jitems.length(); i++) {

                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");

                            Producto = Numero.getString("Producto");
                            Descripcion = Numero.getString("Descripcion");
                            Linea = Numero.getString("Linea");
                            TipoFotos = Numero.getString("TipoFotos");
                            LineaFotos = Numero.getString("LineaFotos");

                            Precio = Numero.getJSONObject("precio_base");
                            precio_base = Precio.getString("valor").equals("") ? "0" : Precio.getString("valor");
                            Precio = Numero.getJSONObject("precio_ajuste");
                            precio_ajuste = Precio.getString("valor").equals("") ? "0" : Precio.getString("valor");

                            listProdu1.add(new SetGetListProductos(Producto, Descripcion, Linea, precio_base, precio_ajuste,TipoFotos,LineaFotos));



                        }
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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

        @Override
        protected void onPostExecute(Void result) {

            if (listProdu1.size() == 0) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(BusquedaActivity.this);
                alerta.setMessage("No se ah encontrado ningun resultado").setIcon(R.drawable.ic_baseline_error_24).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("");
                titulo.show();
            }


            AdapterSearchProduct adapter = new AdapterSearchProduct(listProdu1, context, Empresa);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = RecyclerProductos.getChildAdapterPosition(Objects.requireNonNull(RecyclerProductos.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(BusquedaActivity.this, DetalladoProductosActivity.class);
                    String Producto = listProdu1.get(position).getProductos();
                    String Descripcion = listProdu1.get(position).getDescripcion();
                    String PrecioAjuste = listProdu1.get(position).getPrecioAjuste();
                    String PrecioBase = listProdu1.get(position).getPrecioBase();
                    ProductosDetallados.putExtra("Producto", Producto);
                    ProductosDetallados.putExtra("Descripcion", Descripcion);
                    ProductosDetallados.putExtra("PrecioAjuste", PrecioAjuste);
                    ProductosDetallados.putExtra("PrecioBase", PrecioBase);
                    ProductosDetallados.putExtra("claveVentana", "1");
                    startActivity(ProductosDetallados);

                }
            });
            RecyclerProductos.setAdapter(adapter);
            mDialog.dismiss();

        }

    }


    @SuppressLint("StaticFieldLeak")
    private class BusquedaGeneral extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String parametros = "cliente=" + strco + "&producto=" + BusquedaProducto ;
            String url = "http://" + StrServer + "/buscadorgeneralapp?" + parametros;
            String jsonStr = sh.makeServiceCall(url, strusr, strpass);
            if (jsonStr != null) {
                try {


                    JSONObject jitems, Numero, Precio;
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        jitems = jsonObject.getJSONObject("Item");

                        String Producto;
                        String Descripcion;
                        String Linea;
                        String precio_base;
                        String precio_ajuste;
                        String TipoFotos="";
                        String LineaFotos="";
                        for (int i = 0; i < jitems.length(); i++) {

                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");

                            jitems = jsonObject.getJSONObject("Item");
                            Numero = jitems.getJSONObject("" + i + "");

                            Producto = Numero.getString("Producto");
                            Descripcion = Numero.getString("Descripcion");
                            Linea = Numero.getString("Linea");
                            if(StrServer.equals("jacve.dyndns.org:9085")) {
                                TipoFotos = Numero.getString("TipoFotos");
                                LineaFotos = Numero.getString("LineaFotos");
                            }
                            Precio = Numero.getJSONObject("precio_base");
                            precio_base = Precio.getString("valor").equals("") ? "0" : Precio.getString("valor");
                            Precio = Numero.getJSONObject("precio_ajuste");
                            precio_ajuste = Precio.getString("valor").equals("") ? "0" : Precio.getString("valor");

                            listProdu1.add(new SetGetListProductos(Producto, Descripcion, Linea, precio_base, precio_ajuste,TipoFotos,LineaFotos));




                        }
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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
                        AlertDialog.Builder alerta1 = new AlertDialog.Builder(BusquedaActivity.this);
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


            if (listProdu1.size() == 0) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(BusquedaActivity.this);
                alerta.setMessage("No se ah encontrado ningun resultado").setIcon(R.drawable.ic_baseline_error_24).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("");
                titulo.show();
            }
            AdapterSearchProduct adapter = new AdapterSearchProduct(listProdu1, context, Empresa);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = RecyclerProductos.getChildAdapterPosition(Objects.requireNonNull(RecyclerProductos.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(BusquedaActivity.this, DetalladoProductosActivity.class);
                    String Producto = listProdu1.get(position).getProductos();
                    String Descripcion = listProdu1.get(position).getDescripcion();
                    String PrecioAjuste = listProdu1.get(position).getPrecioAjuste();
                    String PrecioBase = listProdu1.get(position).getPrecioBase();
                    ProductosDetallados.putExtra("Producto", Producto);
                    ProductosDetallados.putExtra("Descripcion", Descripcion);
                    ProductosDetallados.putExtra("PrecioAjuste", PrecioAjuste);
                    ProductosDetallados.putExtra("PrecioBase", PrecioBase);
                    ProductosDetallados.putExtra("claveVentana", "1");

                    startActivity(ProductosDetallados);
                }
            });
            RecyclerProductos.setAdapter(adapter);
            mDialog.dismiss();


        }


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
