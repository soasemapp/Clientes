package com.almacen.keplerclientesapp.activity.Pedidos;

import android.app.AlertDialog;
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
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConsulCotiSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlConsulCoti;
import com.almacen.keplerclientesapp.activity.Carrito.CarritoComprasActivity;
import com.almacen.keplerclientesapp.activity.MainActivity;
import com.almacen.keplerclientesapp.adapter.AdaptadorConsulCoti;
import com.almacen.keplerclientesapp.includes.MyToolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class ActivityConsulCoti extends AppCompatActivity {
    ImageView SeguimiPed, ConsulPed, ConsulCot;
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, strcode, StrServer;
    String mensaje = "";
    AlertDialog mDialog;
    ArrayList<ConsulCotiSANDG> listaConsulCoti = new ArrayList<>();
    RecyclerView recyclerConsulCoti;

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_coti);

        MyToolbar.show(this, "Orden de Venta", false);
        mDialog = new SpotsDialog(ActivityConsulCoti.this);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        strusr = preference.getString("user", "null");
        strpass =preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype = preference.getString("type", "null");
        strbran = preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        strcode = preference.getString("code", "null");
        StrServer = preference.getString("Servidor", "null");


        recyclerConsulCoti = (RecyclerView) findViewById(R.id.listCoti);




        listaConsulCoti = new ArrayList<>();
        recyclerConsulCoti.setLayoutManager(new LinearLayoutManager(this));

        if(isOnlineNet()==true){
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsulCoti.this);
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

            if (listaConsulCoti.size() == 0) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsulCoti.this);
                alerta.setMessage("No cuenta con ninguna orden de venta").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Orden de ventas");
                titulo.show();
            } else {
                AdaptadorConsulCoti adapter = new AdaptadorConsulCoti(listaConsulCoti);
                recyclerConsulCoti.setAdapter(adapter);
                mDialog.dismiss();
            }

        }
    }


    public void detalladoCoti(View view) {
        String ClaveFolDialog, ClaveNumDialog;

        int position = recyclerConsulCoti.getChildAdapterPosition(recyclerConsulCoti.findContainingItemView(view));
        ClaveFolDialog = listaConsulCoti.get(position).getFolio();
        ClaveNumDialog = listaConsulCoti.get(position).getSucursal();
        Intent Coti = new Intent(ActivityConsulCoti.this, ActivityDetallCoti.class);
        Coti.putExtra("Folio", ClaveFolDialog);
        Coti.putExtra("NumSucu", ClaveNumDialog);
        startActivity(Coti);

    }

    private void conecta() {
        String SOAP_ACTION = "ConsulCot";
        String METHOD_NAME = "ConsulCot";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlConsulCoti soapEnvelope = new xmlConsulCoti(SoapEnvelope.VER11);
            soapEnvelope.xmlConsulCoti(strusr, strpass, strcodBra, strcode);
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

                listaConsulCoti.add(new ConsulCotiSANDG((response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_cCliente").equals("anyType{}") ? " " : response0.getPropertyAsString("k_cCliente")),
                        (response0.getPropertyAsString("k_Nombre").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Nombre")),
                        (response0.getPropertyAsString("k_Importe").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Importe")),
                        (response0.getPropertyAsString("k_Piezas").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Piezas")),
                        (response0.getPropertyAsString("k_nSucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_nSucursal")),
                        (response0.getPropertyAsString("k_comentario").equals("anyType{}") ? " " : response0.getPropertyAsString("k_comentario"))));


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

        switch (StrServer) {
            case "sprautomotive.servehttp.com:9090": {
                MenuItem item = menu.findItem(R.id.MenuSPR);
                MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
                MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
                MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
                itemRod.setVisible(false);
                itemPartech.setVisible(true);
                itemSharck.setVisible(true);
                item.setVisible(true);
                break;
            }
            case "sprautomotive.servehttp.com:9095": {
                MenuItem item = menu.findItem(R.id.MenuSPR);

                MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
                MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
                MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
                itemRod.setVisible(true);
                itemPartech.setVisible(false);
                itemSharck.setVisible(true);

                item.setVisible(true);
                break;
            }
            case "sprautomotive.servehttp.com:9080": {
                MenuItem item = menu.findItem(R.id.MenuSPR);


                MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
                MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
                MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
                itemRod.setVisible(true);
                itemPartech.setVisible(true);
                itemSharck.setVisible(false);


                item.setVisible(true);
                break;
            }
            default: {
                MenuItem item = menu.findItem(R.id.MenuSPR);
                item.setVisible(false);
                break;
            }
        }

        return true;
    }





    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cerrarSe) {
            editor.clear().commit();
            Intent cerrar = new Intent(this, MainActivity.class);
            startActivity(cerrar);
            System.exit(0);
            finish();

        }else if (id == R.id.CarrComp){
            Intent Shoping = new Intent(this, CarritoComprasActivity.class);
            startActivity(Shoping);


        }else if (id == R.id.RodatechMenu){
            StrServer = "sprautomotive.servehttp.com:9090";
            editor.putString("Servidor", StrServer);
            editor.commit();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.PartechMenu){
            StrServer = "sprautomotive.servehttp.com:9095";
            editor.putString("Servidor", StrServer);
            editor.commit();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            finish();
        }else if (id == R.id.SharkMenu){
            StrServer = "sprautomotive.servehttp.com:9080";
            editor.putString("Servidor", StrServer);
            editor.commit();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            finish();
        }



        return super.onOptionsItemSelected(item);
    }

}