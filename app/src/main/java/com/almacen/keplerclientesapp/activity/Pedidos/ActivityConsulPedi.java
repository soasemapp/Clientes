package com.almacen.keplerclientesapp.activity.Pedidos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConsulPediSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlConsulPedi;
import com.almacen.keplerclientesapp.activity.Carrito.CarritoComprasActivity;
import com.almacen.keplerclientesapp.activity.MainActivity;
import com.almacen.keplerclientesapp.adapter.AdaptadorConsulPedi;
import com.almacen.keplerclientesapp.includes.MyToolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;

public class ActivityConsulPedi extends AppCompatActivity {


    ImageView SeguimiPed, ConsulPed, ConsulCot;
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, strco, StrServer;
    String FechaIncial;
    private EditText fechaEn;
    private Button btnsearch;
    String date;
    String mensaje = "";
    AlertDialog mDialog;
    ArrayList<ConsulPediSANDG> listaConsulPedi = new ArrayList<>();
    RecyclerView recyclerConsulPedi;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_pedi);

        MyToolbar.show(this, "Pedido", false);
        mDialog = new SpotsDialog.Builder().setContext(ActivityConsulPedi.this).setMessage("Espere un momento...").build();
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
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

        btnsearch = (Button) findViewById(R.id.btnSearch);
        recyclerConsulPedi = (RecyclerView) findViewById(R.id.lisPedi);
        fechaEn = (EditText) findViewById(R.id.fecha);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaEn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityConsulPedi.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        date = year + "-" + month + "-" + day;
                        fechaEn.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (isOnlineNet()==true) {

                    listaConsulPedi = new ArrayList<>();
                    recyclerConsulPedi.setLayoutManager(new LinearLayoutManager(ActivityConsulPedi.this));
                    FechaIncial = fechaEn.getText().toString();
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsulPedi.this);
                    alerta.setMessage("Problemas de conexion verifique su red").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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

        Calendar c = Calendar.getInstance();

        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
        String fechaactual = dateformatActually.format(c.getTime());

        fechaEn.setText(fechaactual);
        FechaIncial = fechaEn.getText().toString();
        listaConsulPedi = new ArrayList<>();
        recyclerConsulPedi.setLayoutManager(new LinearLayoutManager(this));
        if(isOnlineNet()==true){

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsulPedi.this);
            alerta.setMessage("Problemas de conexion verifique su red").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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

    public Boolean isOnlineNet() {

        try {
            Process p = Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            conecta();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            if (listaConsulPedi.size() == 0) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsulPedi.this);
                alerta.setMessage("No se encontraron datos dentro de la fecha establecida").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Pedidos");
                titulo.show();
            } else {
                AdaptadorConsulPedi adapter = new AdaptadorConsulPedi(listaConsulPedi);
                recyclerConsulPedi.setAdapter(adapter);
                mDialog.dismiss();
            }

        }
    }

    public void detalladoPedi(View view) {
        String ClaveFolDialog, ClaveNumDialog;

        int position = recyclerConsulPedi.getChildAdapterPosition(recyclerConsulPedi.findContainingItemView(view));

        ClaveFolDialog = listaConsulPedi.get(position).getFolio();
        ClaveNumDialog = listaConsulPedi.get(position).getSucursal();
        Intent Coti = new Intent(ActivityConsulPedi.this, ActivityDetallPedi.class);
        Coti.putExtra("Folio", ClaveFolDialog);
        Coti.putExtra("NumSucu", ClaveNumDialog);
        startActivity(Coti);
    }


    private void conecta() {
        String SOAP_ACTION = "ConsulPe";
        String METHOD_NAME = "ConsulPe";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlConsulPedi soapEnvelope = new xmlConsulPedi(SoapEnvelope.VER11);
            soapEnvelope.xmlConsulPe(strusr, strpass, strcodBra, strco, FechaIncial);
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

                listaConsulPedi.add(new ConsulPediSANDG((response0.getPropertyAsString("k_Folio").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Pedido").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Pedido")),
                        (response0.getPropertyAsString("k_Liberacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Liberacion")),
                        (response0.getPropertyAsString("k_Aduana").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Aduana")),
                        (response0.getPropertyAsString("k_Facturar").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Facturar")),
                        (response0.getPropertyAsString("k_Piezas").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Piezas")),
                        (response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Nombre").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Nombre")),
                        (response0.getPropertyAsString("k_Importe").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Importe")),
                        (response0.getPropertyAsString("k_comentario").equals("anyType{}") ? " " : response0.getPropertyAsString("k_comentario")),
                        (response0.getPropertyAsString("k_nSucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_nSucursal"))));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (id == R.id.cerrarSe) {
            editor.clear().commit();
            editor.apply();
            Intent cerrar = new Intent(this, MainActivity.class);
            startActivity(cerrar);
            System.exit(0);
            finish();
        }else if (id == R.id.CarrComp){
            Intent Shoping = new Intent(this, CarritoComprasActivity.class);
            startActivity(Shoping);


        }


        return super.onOptionsItemSelected(item);
    }

}