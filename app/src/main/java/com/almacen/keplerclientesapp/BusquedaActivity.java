package com.almacen.keplerclientesapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.almacen.keplerclientesapp.SetandGet.SetGetListMarca;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos2;
import com.almacen.keplerclientesapp.SetterandGetter.ListLineaSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueGeneral;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueProductos;
import com.almacen.keplerclientesapp.XMLS.xmlListLine;
import com.almacen.keplerclientesapp.XMLS.xmlListMarca;
import com.almacen.keplerclientesapp.XMLS.xmlListModelo;
import com.almacen.keplerclientesapp.XMLS.xmlProductoConsulta;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
import com.almacen.keplerclientesapp.includes.MyToolbar;
import com.almacen.keplerclientesapp.ui.home.HomeFragment;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class BusquedaActivity extends AppCompatActivity {
    SliderView sliderView;
    int[] images = {R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5};
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    ArrayList<SetGetListMarca> listMarca = new ArrayList<>();
    ArrayList<SetGetListMarca> listModelo = new ArrayList<>();
    ArrayList<SetGetListProductos> listProdu1 = new ArrayList<>();
    ArrayList<ListLineaSANDG> listaLinea = new ArrayList<>();

    TextView txtmarca,txtmodelo,txtlinea;

    String claveMarca;
    Button btnMarca, btnModelo, btfLinea;
    AlertDialog mDialog;
    EditText yearInicial, yearFinal;
    MaterialCheckBox yearCheck;
    RecyclerView RecyclerProductos;
    Context context;
    Button btnBuscar, btnfiltro;
    String fechainicio = "", fechafinal = "", marca = "", modelo = "", linea = "", check = "";
    String eagle = "";
    LinearLayout linearFiltro;
    int ban = 1;

    String BusquedaProducto = null;
    EditText BusquedaProductoed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);


        mDialog = new SpotsDialog.Builder().setContext(BusquedaActivity.this).setMessage("Espere un momento...").build();
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        MyToolbar.show(this, "Busqueda", false);


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


        btnMarca = findViewById(R.id.btnFMarca);
        btnModelo = findViewById(R.id.btnFModelo);
        btfLinea = findViewById(R.id.btfLinea);
        yearInicial = findViewById(R.id.yearInicio);
        yearFinal = findViewById(R.id.yearfinal);
        yearCheck = findViewById(R.id.checkyear);
        RecyclerProductos = findViewById(R.id.listProductos);
        btnBuscar = findViewById(R.id.btnBuscar);
        BusquedaProductoed = findViewById(R.id.idBusqueda);
        linearFiltro = findViewById(R.id.Filter);
        btnfiltro = findViewById(R.id.btnFilters);
        txtmarca =findViewById(R.id.marcatxt);
        txtmodelo =findViewById(R.id.modelotxt);
        txtlinea =findViewById(R.id.lineatxt);

        linearFiltro.setVisibility(View.GONE);

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

        yearInicial.setEnabled(false);
        yearFinal.setEnabled(false);
        check = "0";
        listProdu1 = new ArrayList<>();
        RecyclerProductos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check.equals("1") && (!marca.equals("") && !modelo.equals(""))) {
                    fechainicio = yearInicial.getText().toString();
                    fechafinal = yearFinal.getText().toString();
                    listProdu1.clear();
                    BusquedaActivity.ListProductos task = new BusquedaActivity.ListProductos();
                    task.execute();
                } else if (check.equals("0") && (!marca.equals("") && !modelo.equals(""))) {
                    fechainicio = "";
                    fechafinal = "";
                    listProdu1.clear();
                    BusquedaActivity.ListProductos task = new BusquedaActivity.ListProductos();
                    task.execute();
                } else {
                    listProdu1.clear();
                    AlertDialog.Builder alerta = new AlertDialog.Builder(BusquedaActivity.this);
                    alerta.setMessage("No has seleccionado una marca o modelo").setIcon(R.drawable.icons8_error_52).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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
                if (yearCheck.isChecked() == true) {
                    check = "1";
                    yearInicial.setEnabled(true);
                    yearFinal.setEnabled(true);
                    yearInicial.setText("");
                    yearFinal.setText("");

                } else {
                    yearInicial.setEnabled(false);
                    yearFinal.setEnabled(false);
                    yearInicial.setText("");
                    yearFinal.setText("");
                    check = "0";
                    fechainicio = "";
                    fechafinal = "";

                }
            }
        });

        btnModelo.setEnabled(false);
        btfLinea.setEnabled(false);
        BusquedaActivity.ListMarca task = new BusquedaActivity.ListMarca();
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btnModelo.setText(Html.fromHtml("Modelo"));

                        modelo = "";
                        linea="";

                        claveMarca = listMarca.get(which).getClaveMarca();//Html.fromHtml("Marca" <br/> C/U:$<font color ='#4CAF50' size=4> +listMarca.get(which).getDescripcion())+"</font>")
                        btnModelo.setEnabled(true);
                        btfLinea.setEnabled(false);
                        txtmarca.setVisibility(View.VISIBLE);
                        txtmarca.setText(listMarca.get(which).getDescripcion()+" "+"X");
                        txtmodelo.setVisibility(View.GONE);
                        txtmodelo.setText("");
                        txtlinea.setVisibility(View.GONE);
                        txtlinea.setText("");
                        listModelo.clear();
                        listaLinea.clear();
                        marca = listMarca.get(which).getClaveMarca();
                        BusquedaActivity.ListModelo task = new BusquedaActivity.ListModelo();
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modelo = listModelo.get(which).getClaveMarca();
                        listaLinea.clear();
                        txtmodelo.setVisibility(View.VISIBLE);
                        txtmodelo.setText(listModelo.get(which).getDescripcion()+" "+"X");
                        txtlinea.setVisibility(View.GONE);
                        txtlinea.setText("");


                        BusquedaActivity.ListLineas task = new BusquedaActivity.ListLineas();
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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        linea = "";
                        linea = listaLinea.get(which).getCodeLinea();
                        txtlinea.setVisibility(View.VISIBLE);
                        txtlinea.setText(listaLinea.get(which).getLinea()+" "+"X");
                        dialog.dismiss();
                    }
                });
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        BusquedaProductoed.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) { //Do Something } return false; } });

                    BusquedaProducto=BusquedaProductoed.getText().toString();
                    listProdu1.clear();


                    BusquedaActivity.BusquedaGeneral task = new BusquedaActivity.BusquedaGeneral();
                    task.execute();

                }
            return false;
            }
        });


        if (BusquedaProducto != null) {
            listProdu1.clear();
            BusquedaActivity.BusquedaGeneral task1 = new BusquedaActivity.BusquedaGeneral();
            task1.execute();
        }


    }



    public void borrarmarca(View view){
        marca = "";
        modelo = "";
        linea="";
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
    public void borrarmodelo(View view){

        modelo = "";
        linea="";
        btfLinea.setEnabled(false);
        txtmodelo.setVisibility(View.GONE);
        txtmodelo.setText("");
        txtlinea.setVisibility(View.GONE);
        txtlinea.setText("");
        listaLinea.clear();
    }
    public void borrarmlinea(View view){
        linea="";
        txtlinea.setVisibility(View.GONE);
        txtlinea.setText("");
        }

    private class ListLineas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar3();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            btfLinea.setEnabled(true);
            mDialog.dismiss();

        }


    }

    private void conectar3() {
        String SOAP_ACTION = "listlineas";
        String METHOD_NAME = "listlineas";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlListLine soapEnvelope = new xmlListLine(SoapEnvelope.VER11);
            soapEnvelope.xmlListLine(strusr, strpass,modelo);
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

                if (!response0.getPropertyAsString("k_Linea").equals("anyType{}")) {
                    listaLinea.add(new ListLineaSANDG((response0.getPropertyAsString("k_CodeLinea").equals("anyType{}") ? "" : response0.getPropertyAsString("k_CodeLinea")),
                            (response0.getPropertyAsString("k_Linea").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Linea"))));
                }

            }


        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
        }
    }


    private class ListMarca extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... params) {
            WebServiceListMarca();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



        }
    }


    private class ListModelo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            WebServiceListModelo();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
        }

    }


    private class ListProductos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebServiceListProducto();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AdapterSearchProduct adapter = new AdapterSearchProduct(listProdu1, context);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = RecyclerProductos.getChildAdapterPosition(RecyclerProductos.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(BusquedaActivity.this, DetalladoProductosActivity.class);
                    String Producto = listProdu1.get(position).getProductos();
                    ProductosDetallados.putExtra("Producto",Producto);
                    startActivity(ProductosDetallados);
                }
            });
            RecyclerProductos.setAdapter(adapter);
            mDialog.dismiss();

        }

    }

    private void WebServiceListProducto() {
        String SOAP_ACTION = "busqueProductos";
        String METHOD_NAME = "busqueProductos";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlBusqueProductos soapEnvelope = new xmlBusqueProductos(SoapEnvelope.VER11);
            soapEnvelope.xmlBusqueProductos(strusr, strpass, fechainicio, fechafinal, marca, modelo, linea, check,strco);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response = (SoapObject) soapEnvelope.bodyIn;

            String Producto;
            String Descripcion="";
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
                response0 = (SoapObject) response0.getProperty("precio_base");
                precio_base = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");
                response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                response0 = (SoapObject) response0.getProperty("precio_ajuste");
                precio_ajuste = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");

                listProdu1.add(new SetGetListProductos(Producto,Descripcion,precio_base,precio_ajuste));


            }







        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }


    private void WebServiceListMarca() {
        String SOAP_ACTION = "listmarca";
        String METHOD_NAME = "listmarca";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlListMarca soapEnvelope = new xmlListMarca(SoapEnvelope.VER11);
            soapEnvelope.xmlListMarca(strusr, strpass);
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

                listMarca.add(new SetGetListMarca(
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_ClaveMa").equals("anyType{}") ? "" : response0.getPropertyAsString("k_ClaveMa"))));


            }

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    private void WebServiceListModelo() {
        String SOAP_ACTION = "listmodelo";
        String METHOD_NAME = "listmodelo";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlListModelo soapEnvelope = new xmlListModelo(SoapEnvelope.VER11);
            soapEnvelope.xmlListModelo(strusr, strpass, claveMarca);
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

                listModelo.add(new SetGetListMarca(
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_ClaveMo").equals("anyType{}") ? "" : response0.getPropertyAsString("k_ClaveMo"))));


            }

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    private class BusquedaGeneral extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            BusquedaGeneral();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            AdapterSearchProduct adapter = new AdapterSearchProduct(listProdu1, context);
            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = RecyclerProductos.getChildAdapterPosition(RecyclerProductos.findContainingItemView(view));
                     Intent ProductosDetallados = new Intent(BusquedaActivity.this, DetalladoProductosActivity.class);
                     String Producto = listProdu1.get(position).getProductos();
                     ProductosDetallados.putExtra("Producto",Producto);
                     startActivity(ProductosDetallados);
                }
            });
            RecyclerProductos.setAdapter(adapter);
            mDialog.dismiss();


        }


    }

    private void BusquedaGeneral() {
        String SOAP_ACTION = "ProductoConsultaApp";
        String METHOD_NAME = "ProductoConsultaApp";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClienteSSoap/";
        String URL = "http://" + StrServer + "/WSk75ClienteSSoap";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlBusqueGeneral soapEnvelope = new xmlBusqueGeneral(SoapEnvelope.VER11);
            soapEnvelope.xmlBusqueGeneral(strusr, strpass, strco, BusquedaProducto);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;

            String Producto;
            String Descripcion="";
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
                response0 = (SoapObject) response0.getProperty("precio_base");
                precio_base = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");
                response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                response0 = (SoapObject) response0.getProperty("precio_ajuste");
                precio_ajuste = response0.getPropertyAsString("valor").equals("anyType{}") ? "0" : response0.getPropertyAsString("valor");

                listProdu1.add(new SetGetListProductos(Producto,Descripcion,precio_base,precio_ajuste));


            }




        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
        }
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

}
