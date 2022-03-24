package com.almacen.keplerclientesapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConFaDetSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlConFactDetall;
import com.almacen.keplerclientesapp.includes.MyToolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;


public class ActivityFactuDetall extends AppCompatActivity {
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcode, strcodBra, StrServer;
    ArrayList<ConFaDetSANDG> listasearch2 = new ArrayList<>();
    private TableLayout tableLayout;
    TableRow fila;
    AlertDialog mDialog;
    TextView txtProducto, txtDescripcion, txtCantidad, txtPrecioU, txtDescuento, txtPrecioTXP, txtSucursal;
    private boolean multicolor = true;
    String ClaveFolDialog = "";
    String ClaveNumDialog = "";
    String mensaje = "";
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factu_detall);

        tableLayout = (TableLayout) findViewById(R.id.table);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);

        editor = preference.edit();

        mDialog = new SpotsDialog.Builder().setContext(ActivityFactuDetall.this).setMessage("Espere un momento...").build();

        strusr = preference.getString("user", "null");
        strpass =preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype =preference.getString("type", "null");
        strbran =preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        strcode =preference.getString("code", "null");
        StrServer = preference.getString("Servidor", "null");
        ClaveFolDialog = getIntent().getStringExtra("Folio");
        ClaveNumDialog = getIntent().getStringExtra("NumSucu");
        MyToolbar.show(this, "Factura:" + ClaveFolDialog, true);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (isOnlineNet()==true) {


            ActivityFactuDetall.AsyncCallWS task = new ActivityFactuDetall.AsyncCallWS();
            task.execute();

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityFactuDetall.this);
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

        if(isOnlineNet()==true){

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityFactuDetall.this);
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
            conecta3();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < listasearch2.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                if (i == -1) {
                    txtProducto = new TextView(getApplicationContext());
                    txtProducto.setText("Producto");
                    txtProducto.setGravity(Gravity.START);
                    txtProducto.setBackgroundColor(Color.RED);
                    txtProducto.setTextColor(Color.WHITE);
                    txtProducto.setPadding(20, 20, 20, 20);
                    txtProducto.setLayoutParams(layaoutDes);
                    fila.addView(txtProducto);

                    txtDescripcion = new TextView(getApplicationContext());
                    txtDescripcion.setText("Descripcion");
                    txtDescripcion.setGravity(Gravity.START);
                    txtDescripcion.setBackgroundColor(Color.RED);
                    txtDescripcion.setTextColor(Color.WHITE);
                    txtDescripcion.setPadding(20, 20, 20, 20);
                    txtDescripcion.setLayoutParams(layaoutDes);
                    fila.addView(txtDescripcion);

                    txtCantidad = new TextView(getApplicationContext());
                    txtCantidad.setText("Cant");
                    txtCantidad.setGravity(Gravity.START);
                    txtCantidad.setBackgroundColor(Color.RED);
                    txtCantidad.setTextColor(Color.WHITE);
                    txtCantidad.setPadding(20, 20, 20, 20);
                    txtCantidad.setLayoutParams(layaoutDes);
                    fila.addView(txtCantidad);

                    txtPrecioU = new TextView(getApplicationContext());
                    txtPrecioU.setText("P/U");
                    txtPrecioU.setGravity(Gravity.START);
                    txtPrecioU.setBackgroundColor(Color.RED);
                    txtPrecioU.setTextColor(Color.WHITE);
                    txtPrecioU.setPadding(20, 20, 20, 20);
                    txtPrecioU.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecioU);

                    txtDescuento = new TextView(getApplicationContext());
                    txtDescuento.setText("Descu");
                    txtDescuento.setGravity(Gravity.START);
                    txtDescuento.setBackgroundColor(Color.RED);
                    txtDescuento.setTextColor(Color.WHITE);
                    txtDescuento.setPadding(20, 20, 20, 20);
                    txtDescuento.setLayoutParams(layaoutDes);
                    fila.addView(txtDescuento);


                    txtPrecioTXP = new TextView(getApplicationContext());
                    txtPrecioTXP.setText("Import");
                    txtPrecioTXP.setGravity(Gravity.END);
                    txtPrecioTXP.setBackgroundColor(Color.RED);
                    txtPrecioTXP.setTextColor(Color.WHITE);
                    txtPrecioTXP.setPadding(20, 20, 20, 20);
                    txtPrecioTXP.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecioTXP);
                    tableLayout.addView(fila);
                } else {

                    txtProducto = new TextView(getApplicationContext());

                    txtProducto.setGravity(Gravity.START);
                    txtProducto.setBackgroundColor(Color.BLACK);
                    txtProducto.setText(listasearch2.get(i).getProductor());
                    txtProducto.setPadding(20, 20, 20, 20);
                    txtProducto.setTextColor(Color.WHITE);
                    txtProducto.setLayoutParams(layaoutDes);
                    fila.addView(txtProducto);


                    txtDescripcion = new TextView(getApplicationContext());
                    txtDescripcion.setBackgroundColor(Color.WHITE);
                    txtDescripcion.setGravity(Gravity.START);
                    txtDescripcion.setText(listasearch2.get(i).getDescripcion());
                    txtDescripcion.setPadding(20, 20, 20, 20);
                    txtDescripcion.setTextColor(Color.BLACK);
                    txtDescripcion.setLayoutParams(layaoutDes);
                    fila.addView(txtDescripcion);

                    txtCantidad = new TextView(getApplicationContext());
                    txtCantidad.setBackgroundColor(Color.BLACK);
                    txtCantidad.setGravity(Gravity.START);
                    txtCantidad.setText(listasearch2.get(i).getCantidad());
                    txtCantidad.setPadding(20, 20, 20, 20);
                    txtCantidad.setTextColor(Color.WHITE);
                    txtCantidad.setLayoutParams(layaoutDes);
                    fila.addView(txtCantidad);

                    txtPrecioU = new TextView(getApplicationContext());
                    txtPrecioU.setBackgroundColor(Color.WHITE);
                    txtPrecioU.setGravity(Gravity.END);
                    txtPrecioU.setText("$" + formatNumberCurrency(listasearch2.get(i).getPrecioU()));
                    txtPrecioU.setPadding(20, 20, 20, 20);
                    txtPrecioU.setTextColor(Color.BLACK);
                    txtPrecioU.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecioU);

                    txtDescuento = new TextView(getApplicationContext());
                    txtDescuento.setBackgroundColor(Color.BLACK);
                    txtDescuento.setGravity(Gravity.START);
                    txtDescuento.setText(listasearch2.get(i).getDescuento() + "%");
                    txtDescuento.setPadding(20, 20, 20, 20);
                    txtDescuento.setTextColor(Color.WHITE);
                    txtDescuento.setLayoutParams(layaoutDes);
                    fila.addView(txtDescuento);

                    txtPrecioTXP = new TextView(getApplicationContext());
                    txtPrecioTXP.setBackgroundColor(Color.WHITE);
                    txtPrecioTXP.setGravity(Gravity.END);
                    txtPrecioTXP.setText("$" + formatNumberCurrency(listasearch2.get(i).getPrecioTXP()));
                    txtPrecioTXP.setPadding(20, 20, 20, 20);
                    txtPrecioTXP.setTextColor(Color.BLACK);
                    txtPrecioTXP.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecioTXP);


                    fila.setPadding(2, 2, 2, 2);

                    tableLayout.addView(fila);

                }
            }
            mDialog.dismiss();
        }

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    private void conecta3() {


        String SOAP_ACTION = "FacturaDetall";
        String METHOD_NAME = "FacturaDetall";
        String NAMESPACE = "http://" + StrServer + "/WSk75items/";
        String URL = "http://" + StrServer + "/WSk75items";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlConFactDetall soapEnvelope = new xmlConFactDetall(SoapEnvelope.VER11);
            soapEnvelope.xmlConFactDeta(strusr, strpass, ClaveFolDialog, ClaveNumDialog);
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

                listasearch2.add(new ConFaDetSANDG((response0.getPropertyAsString("k_Producto").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Producto")),
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_PrecioU").equals("anyType{}") ? " " : response0.getPropertyAsString("k_PrecioU")),
                        (response0.getPropertyAsString("k_Descuento").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Descuento")),
                        (response0.getPropertyAsString("k_PrecioTXP").equals("anyType{}") ? " " : response0.getPropertyAsString("k_PrecioTXP")),
                        (response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Sucursal"))));


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
}