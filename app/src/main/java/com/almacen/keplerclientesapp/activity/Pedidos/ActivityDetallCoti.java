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
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.DetallCotiSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.EnvioSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.ListaViaSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.valExiPedSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlDetallCoti;
import com.almacen.keplerclientesapp.XMLS.xmlDirEnvio;
import com.almacen.keplerclientesapp.XMLS.xmlNewDoc42;
import com.almacen.keplerclientesapp.XMLS.xmlPedido;
import com.almacen.keplerclientesapp.XMLS.xmlValidaPedColombia;
import com.almacen.keplerclientesapp.XMLS.xmlValidaPedMexico;
import com.almacen.keplerclientesapp.XMLS.xmlViaE;
import com.almacen.keplerclientesapp.XMLS.xmlvalExiPed;
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

public class ActivityDetallCoti extends AppCompatActivity {

    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcode, strcodBra, StrServer;
    ArrayList<DetallCotiSANDG> listasearch2 = new ArrayList<>();
    ArrayList<valExiPedSANDG> listasearch3 = new ArrayList<>();
    ArrayList<ListaViaSANDG> listasearch4 = new ArrayList<>();
    ArrayList<EnvioSANDG> listasearch5 = new ArrayList<>();
    ArrayList<DetallCotiSANDG> listasearch6 = new ArrayList<>();

    private TableLayout tableLayout;
    private Spinner spinnerVia;
    Button pedidoButton;
    TextView Sucursal, Folio,  NomClient;
    String ivstr;
    String MontoStr;
    TextView txtSubtotal;
    TextView txtSubtotal2;
    TextView txtDescuento;
    TextView txtiva;
    TextView txtIntrucciones;
    TextView txtMontototal;
    TableRow fila;
    AlertDialog mDialog;
    EditText Comentantario;
    TextView txtSucursal, txtFolio, txtClaveC, txtNombreC, txtClavePro, txtCant, txtPrecio, txtDesc, txtImporte;

    private boolean multicolor = true;

    String ClaveFolDialog = "";
    String ClaveNumDialog = "";
    String mensaje = "";
    String strComentario;
    String strNombreCliente;
    String strClaveCli;
    String StrFechaActaul;
    String StrFechaVencimiento;
    String StrRFC;
    String StrPlazo;
    String StrDescuentoPP;
    String StrDescuento1;
    String StrCalle;
    String StrColonia;
    String StrPoblacion;
    String StrVia;
    String stridEnvio = "";
    String DescPro;
    String Desc1;
    String MenValPedi;
    String ValPedido = "";
    String Desc1Valida = "";
    String DescuentoValida = "";
    String SubtotalValida = "";
    String SeCambiovalida = "";
    String strVia;

    String id;
    String CalleSec;
    String ColoniaSec;
    String PoblacionSec;
    String NumeroTel;
    double IvaVariado =0;
    String Cantidad;
    String Producto;

    double DescProstr = 0;
    double Descuento = 0;
    String DescuentoStr;
    int n = 2000;
    String[] search2 = new String[n];
    String[] search3 = new String[n];

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    String Mensaje;
    String Documento;
    String Folio1;
    String FolioResul;
    String SubdescuentoValida;
    AlertDialog.Builder builder3;
    AlertDialog dialog3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detall_coti);

        tableLayout = (TableLayout) findViewById(R.id.table);

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        mDialog = new SpotsDialog.Builder().setContext(ActivityDetallCoti.this).setMessage("Espere un momento...").build();

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
        ClaveFolDialog = getIntent().getStringExtra("Folio");
        ClaveNumDialog = getIntent().getStringExtra("NumSucu");

        Folio = (TextView) findViewById(R.id.txtFolio);
        Sucursal = (TextView) findViewById(R.id.txtSucursal);
        NomClient = (TextView) findViewById(R.id.txtNom);
       
        txtSubtotal = findViewById(R.id.SubTotal);
        txtDescuento = findViewById(R.id.Descuento);
        txtiva = findViewById(R.id.iva);
        txtSubtotal2 = findViewById(R.id.SubTotal2);
        txtMontototal = (TextView) findViewById(R.id.MontoTotal);
        spinnerVia = findViewById(R.id.spinnerVia);
        pedidoButton= findViewById(R.id.PedidoButton);

        IvaVariado= ((!StrServer.equals("vazlocolombia.dyndns.org:9085"))?0.16 : 0.19);

        MyToolbar.show(this, "Orden de Venta:" + ClaveFolDialog, true);




        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (isOnlineNet()==true) {


            AsyncCallWS task = new AsyncCallWS();
            task.execute();

            AsyncCallWS5 task2 = new AsyncCallWS5();
            task2.execute();

            AsyncCallWS6 task3 = new AsyncCallWS6();
            task3.execute();

            //Diponibilidad
            builder3 = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.pantallacargacarrito, null);
            txtIntrucciones = (TextView) dialogView.findViewById(R.id.Instucciones);
            txtIntrucciones.setText("Se esta revisando la disponibilidad de los productos.");
            builder3.setView(dialogView);
            dialog3 = builder3.create();
            dialog3.show();

            AsyncCallWS4 task4 = new AsyncCallWS4();
            task4.execute();





        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
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





        pedidoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidoButton.setEnabled(false);

              if(StrServer.equals("vazlocolombia.dyndns.org:9085")){

                  strClaveCli = listasearch2.get(0).getClaveC();
                  strNombreCliente = listasearch2.get(0).getNombreC();
                  strComentario =  listasearch2.get(0).getComentario();
                  StrRFC = listasearch2.get(0).getRFC();
                  StrPlazo = listasearch2.get(0).getPLAZO();
                  StrDescuentoPP = listasearch2.get(0).getDESCUENTOPP();
                  StrDescuento1 = listasearch2.get(0).getDESCUENTO1();
                  StrCalle = listasearch2.get(0).getCALLE();
                  StrColonia = listasearch2.get(0).getCOLONIA();
                  StrPoblacion = listasearch2.get(0).getPOBLACION();
                  Folio1 = Folio.getText().toString();

                  int Contador=0;
                  Contador=spinnerVia.getSelectedItemPosition();

                  for (int i = 0; i < listasearch4.size(); i++) {
                      int posi = spinnerVia.getSelectedItemPosition();
                      if (posi == i) {
                          strVia = listasearch4.get(i).getClave();
                          break;
                      }
                  }

                  AsyncCallWS2 task = new AsyncCallWS2();
                  task.execute();

              }else{
                  strClaveCli = listasearch2.get(0).getClaveC();
                  strNombreCliente = listasearch2.get(0).getNombreC();
                  strComentario =  listasearch2.get(0).getComentario();
                  StrRFC = listasearch2.get(0).getRFC();
                  StrPlazo = listasearch2.get(0).getPLAZO();
                  StrDescuentoPP = listasearch2.get(0).getDESCUENTOPP();
                  StrDescuento1 = listasearch2.get(0).getDESCUENTO1();
                  StrCalle = listasearch2.get(0).getCALLE();
                  StrColonia = listasearch2.get(0).getCOLONIA();
                  StrPoblacion = listasearch2.get(0).getPOBLACION();
                  Folio1 = Folio.getText().toString();

                  int Contador=0;
                   Contador=spinnerVia.getSelectedItemPosition();

                  for (int i = 0; i < listasearch4.size(); i++) {
                      int posi = spinnerVia.getSelectedItemPosition();
                      if (posi == i) {
                          strVia = listasearch4.get(i).getClave();
                          break;
                      }
                  }



                      ValidaPedidoMexico task = new ValidaPedidoMexico();
                  task.execute();
              }
            }
        });


        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
        String fechaactual = dateformatActually.format(c.getTime());
        StrFechaActaul = fechaactual;
        if(isOnlineNet()==true){

        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
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
            conecta1();

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            Folio.setText(listasearch2.get(0).getFolio());
            Sucursal.setText(listasearch2.get(0).getSucursal());
            NomClient.setText(listasearch2.get(0).getNombreC());
            StrVia = listasearch2.get(0).getVIA();
            strClaveCli = listasearch2.get(0).getClaveC();
            DescPro = listasearch2.get(0).getDESCUENTOPP();
            Desc1 = listasearch2.get(0).getDESCUENTO1();
            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < listasearch2.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                if (i == -1) {

                    txtClavePro = new TextView(getApplicationContext());
                    txtClavePro.setText("Producto");
                    txtClavePro.setGravity(Gravity.START);
                    txtClavePro.setBackgroundColor(Color.RED);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);


                    txtCant = new TextView(getApplicationContext());
                    txtCant.setText("Cantidad");
                    txtCant.setGravity(Gravity.START);
                    txtCant.setBackgroundColor(Color.RED);
                    txtCant.setTextColor(Color.WHITE);
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);


                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setText("Precio");
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setBackgroundColor(Color.RED);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);


                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setText("Descuento");
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setBackgroundColor(Color.RED);
                    txtDesc.setTextColor(Color.WHITE);
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setText("Importe");
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
                    txtClavePro.setText(listasearch2.get(i).getClaveP());
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);

                    txtCant = new TextView(getApplicationContext());
                    txtCant.setBackgroundColor(Color.GRAY);
                    txtCant.setGravity(Gravity.START);
                    txtCant.setText(listasearch2.get(i).getCant());
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setTextColor(Color.WHITE);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);

                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setBackgroundColor(Color.BLACK);
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setText("$" + formatNumberCurrency(listasearch2.get(i).getPrecio()));
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);

                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setBackgroundColor(Color.GRAY);
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setText(listasearch2.get(i).getDesc() + "%");
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setTextColor(Color.WHITE);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setBackgroundColor(Color.BLACK);
                    txtImporte.setGravity(Gravity.START);
                    txtImporte.setText("$" + formatNumberCurrency(listasearch2.get(i).getImporte()));
                    txtImporte.setPadding(20, 20, 20, 20);
                    txtImporte.setTextColor(Color.WHITE);
                    txtImporte.setLayoutParams(layaoutDes);
                    fila.addView(txtImporte);
                    fila.setPadding(2, 2, 2, 2);

                    tableLayout.addView(fila);

                }
            }
            Montototal();
            mDialog.dismiss();
        }

    }


    private void conecta1() {


        String SOAP_ACTION = "DetallCot";
        String METHOD_NAME = "DetallCot";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlDetallCoti soapEnvelope = new xmlDetallCoti(SoapEnvelope.VER11);
            soapEnvelope.xmlDetallCo(strusr, strpass, ClaveFolDialog, ClaveNumDialog);
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

                listasearch2.add(new DetallCotiSANDG((response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("ClaveC").equals("anyType{}") ? "" : response0.getPropertyAsString("ClaveC")),
                        (response0.getPropertyAsString("k_NombreC").equals("anyType{}") ? "" : response0.getPropertyAsString("k_NombreC")),
                        (response0.getPropertyAsString("k_ClaveP").equals("anyType{}") ? "" : response0.getPropertyAsString("k_ClaveP")),
                        (response0.getPropertyAsString("k_Cant").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cant")),
                        (response0.getPropertyAsString("k_Precio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Precio")),
                        (response0.getPropertyAsString("k_Desc").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Desc")),
                        (response0.getPropertyAsString("k_Import").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Import")),
                        (response0.getPropertyAsString("k_RFC").equals("anyType{}") ? "" : response0.getPropertyAsString("k_RFC")),
                        (response0.getPropertyAsString("k_Plazo").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Plazo")),
                        (response0.getPropertyAsString("k_87").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_87")),
                        (response0.getPropertyAsString("k_desc1").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_desc1")),
                        (response0.getPropertyAsString("k_calle").equals("anyType{}") ? "" : response0.getPropertyAsString("k_calle")),
                        (response0.getPropertyAsString("k_colonia").equals("anyType{}") ? "" : response0.getPropertyAsString("k_colonia")),
                        (response0.getPropertyAsString("k_poblacion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_poblacion")),
                        (response0.getPropertyAsString("k_via").equals("anyType{}") ? "" : response0.getPropertyAsString("k_via")),
                        (response0.getPropertyAsString("k_comentario").equals("anyType{}") ? "" : response0.getPropertyAsString("k_comentario"))));


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


    private class AsyncCallWS2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conecta2();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
        if(Integer.parseInt(SeCambiovalida) == 1 &&  Integer.parseInt(ValPedido) == 1){
          double monto;
          double ivasr;
            ivasr= Double.parseDouble(SubtotalValida) * IvaVariado;
            monto = Double.parseDouble(SubtotalValida) + ivasr;


            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
            alerta.setMessage("El descuento sera modificado ¿Deseas realizar el pedido? \n" +
                    "Descuento=%" + Desc1Valida + "\n" +
                    "Descuento Total=$" + formatNumberCurrency(String.valueOf(DescuentoValida)) + "\n" +
                    "Subtotal = $" + formatNumberCurrency(String.valueOf(SubtotalValida)) + "\n" +
                    "Iva = $" + formatNumberCurrency(String.valueOf(ivasr)) + "\n" +
                    "Monto = $" + formatNumberCurrency(String.valueOf(monto))).setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    if (ValPedido.equals("1")) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                        c.add(Calendar.DAY_OF_YEAR, 30);
                        String fechaactual = dateformatActually.format(c.getTime());
                        StrFechaVencimiento = fechaactual;


                        double subtotal;
                        double monto;
                        double ivasr;
                        Desc1= Desc1Valida;
                        DescuentoStr=DescuentoValida ;
                        ivasr= Double.parseDouble(SubtotalValida) * IvaVariado;
                        ivstr = String.valueOf(ivasr);
                        monto = Double.parseDouble(SubtotalValida) + ivasr;
                        MontoStr=String.valueOf(monto);


                        int num=0;
                        int cont=0;

                        for (int j = 0; j < listasearch3.size(); j++) {
                            num=Integer.parseInt(listasearch3.get(j).getExistencia());
                            if (num>0){

                            }else{
                                cont++;
                            }
                        }

                        if (listasearch3.size()==cont){
                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                            alerta.setMessage("El pedido no cuenta con ningun producto en existencia por el momento").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("AVISO");
                            titulo.show();
                            mDialog.dismiss();
                        }else{
                            AsyncCallWS3 task3 = new AsyncCallWS3();
                            task3.execute();
                        }







                    } else if (ValPedido .equals("2") ) {
                        pedidoButton.setEnabled(true);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                        alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                                overridePendingTransition(0, 0);
                                startActivity(regreso);
                                overridePendingTransition(0, 0);


                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("Error");
                        titulo.show();
                    } else if (ValPedido .equals("3")) {
                        pedidoButton.setEnabled(true);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                        alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                                overridePendingTransition(0, 0);
                                startActivity(regreso);
                                overridePendingTransition(0, 0);

                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("Error");
                        titulo.show();
                    } else {
                        pedidoButton.setEnabled(true);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                        alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                                overridePendingTransition(0, 0);
                                startActivity(regreso);
                                overridePendingTransition(0, 0);


                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("Error");
                        titulo.show();

                    }



                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    mDialog.dismiss();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Modificar");
            titulo.show();



        }else{
            if (ValPedido.equals("1")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                c.add(Calendar.DAY_OF_YEAR, 30);
                String fechaactual = dateformatActually.format(c.getTime());
                StrFechaVencimiento = fechaactual;

                int num=0;
                int cont=0;

                for (int i = 0; i < listasearch3.size(); i++) {
                    num=Integer.parseInt(listasearch3.get(i).getExistencia());
                    if (num>0){

                    }else{
                        cont++;
                    }
                }

                if (listasearch3.size()==cont){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                    alerta.setMessage("El pedido no cuenta con ningun producto en existencia por el momento").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("AVISO");
                    titulo.show();
                    mDialog.dismiss();
                }else{
                    AsyncCallWS3 task3 = new AsyncCallWS3();
                    task3.execute();
                }



            } else if (ValPedido .equals("2") ) {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);


                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            } else if (ValPedido .equals("3")) {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);

                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            } else {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);


                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();

            }
        }



        }

    }
    private void conecta2() {
        String SOAP_ACTION = "ValidaPedColombia";
        String METHOD_NAME = "ValidaPedColombia";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlValidaPedColombia soapEnvelope = new xmlValidaPedColombia(SoapEnvelope.VER11);
            soapEnvelope.xmlValidaPedColombia(strusr, strpass, strClaveCli, MontoStr,SubdescuentoValida,Desc1);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            response0 = (SoapObject) response0.getProperty("MENSAJE");
            MenValPedi = response0.getPropertyAsString("k_messenge");
            ValPedido = response0.getPropertyAsString("k_ValPe");
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


    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));


    }


    private class ValidaPedidoMexico extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ValidaPedido();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {



            if (ValPedido.equals("1")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                c.add(Calendar.DAY_OF_YEAR, 30);
                String fechaactual = dateformatActually.format(c.getTime());
                StrFechaVencimiento = fechaactual;


                int num=0;
                int cont=0;

                for (int i = 0; i < listasearch3.size(); i++) {
                    num=Integer.parseInt(listasearch3.get(i).getExistencia());
                    if (num>0){

                    }else{
                        cont++;
                    }
                }

                if (listasearch3.size()==cont){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                    alerta.setMessage("El pedido no cuenta con ningun producto en existencia por el momento").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("AVISO");
                    titulo.show();
                    mDialog.dismiss();
                }else{
                    AsyncCallWS3 task3 = new AsyncCallWS3();
                    task3.execute();
                }




            } else if (ValPedido .equals("2") ) {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            } else if (ValPedido .equals("3")) {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);

                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            } else {
                pedidoButton.setEnabled(true);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                alerta.setMessage(MenValPedi).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
                        overridePendingTransition(0, 0);
                        startActivity(regreso);
                        overridePendingTransition(0, 0);


                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();

            }

        }

    }
    private void ValidaPedido() {
        String SOAP_ACTION = "ValidaPedMexico";
        String METHOD_NAME = "ValidaPedMexico";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP ";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlValidaPedMexico soapEnvelope = new xmlValidaPedMexico(SoapEnvelope.VER11);
            soapEnvelope.xmlValidaPedi(strusr, strpass, strClaveCli, MontoStr);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            response0 = (SoapObject) response0.getProperty("MENSAJE");
            MenValPedi = response0.getPropertyAsString("k_messenge");
            ValPedido = response0.getPropertyAsString("k_ValPe");


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





    private class AsyncCallWS3 extends AsyncTask<Void, Void, Void> {

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

            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
            alerta.setMessage(Documento).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    AsyncCallWS7 task4 = new AsyncCallWS7();
                    task4.execute();




                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("Pedido:" + FolioResul);
            titulo.show();

            mDialog.dismiss();

        }

    }

    private void conectar3() {
        String SOAP_ACTION = "NewDoc";
        String METHOD_NAME = "NewDoc";
        String NAMESPACE = "http://" + StrServer + "/WSk80Docs/";
        String URL = "http://" + StrServer + "/WSk80Docs";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlPedido soapEnvelope = new xmlPedido(SoapEnvelope.VER11);
            soapEnvelope.xmlPedido(strComentario, strcode, strNombreCliente, strClaveCli, StrFechaActaul, StrFechaVencimiento, strcodBra, strusr, strpass,
                    StrRFC, StrPlazo, MontoStr, ivstr, DescuentoStr, DescPro, Desc1, StrCalle, StrColonia, StrPoblacion, Folio1, strVia, stridEnvio, listasearch2, listasearch3, StrServer);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);

            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            Mensaje = response0.getPropertyAsString("message");
            Documento = response0.getPropertyAsString("doc");
            FolioResul = response0.getPropertyAsString("folio");


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

            tableLayout.removeAllViews();
            double cantidad;
            double precio;
            double descuento;
            double monto;
            double monto2;
            double monto3;


            Folio.setText(listasearch2.get(0).getFolio());
            Sucursal.setText(listasearch2.get(0).getSucursal());
            NomClient.setText(listasearch2.get(0).getNombreC());
            StrVia = listasearch2.get(0).getVIA();
            strClaveCli = listasearch2.get(0).getClaveC();
            DescPro = listasearch2.get(0).getDESCUENTOPP();
            Desc1 = listasearch2.get(0).getDESCUENTO1();
            TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            for (int i = -1; i < listasearch2.size(); i++) {
                fila = new TableRow(getApplicationContext());
                fila.setLayoutParams(layaoutFila);
                if (i == -1) {

                    txtClavePro = new TextView(getApplicationContext());
                    txtClavePro.setText("Producto");
                    txtClavePro.setGravity(Gravity.START);
                    txtClavePro.setBackgroundColor(Color.RED);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);


                    txtCant = new TextView(getApplicationContext());
                    txtCant.setText("Cant");
                    txtCant.setGravity(Gravity.START);
                    txtCant.setBackgroundColor(Color.RED);
                    txtCant.setTextColor(Color.WHITE);
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);


                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setText("Precio");
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setBackgroundColor(Color.RED);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);


                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setText("Desc");
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setBackgroundColor(Color.RED);
                    txtDesc.setTextColor(Color.WHITE);
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setText("Importe");
                    txtImporte.setGravity(Gravity.START);
                    txtImporte.setBackgroundColor(Color.RED);
                    txtImporte.setTextColor(Color.WHITE);
                    txtImporte.setPadding(20, 20, 20, 20);
                    txtImporte.setLayoutParams(layaoutDes);
                    fila.addView(txtImporte);

                    tableLayout.addView(fila);
                } else {
                    precio=Double.valueOf(listasearch2.get(i).getPrecio());
                    cantidad =Double.valueOf(listasearch3.get(i).getExistencia());
                    descuento =Double.valueOf(listasearch2.get(i).getDesc());
                    monto=precio * cantidad;
                    monto2 = monto * (descuento/100);
                    monto3 =monto-monto2;

                    txtClavePro = new TextView(getApplicationContext());
                    txtClavePro.setBackgroundColor(Color.BLACK);
                    txtClavePro.setGravity(Gravity.START);
                    txtClavePro.setText(listasearch2.get(i).getClaveP());
                    txtClavePro.setPadding(20, 20, 20, 20);
                    txtClavePro.setTextColor(Color.WHITE);
                    txtClavePro.setLayoutParams(layaoutDes);
                    fila.addView(txtClavePro);

                    txtCant = new TextView(getApplicationContext());
                    txtCant.setBackgroundColor(Color.WHITE);
                    txtCant.setGravity(Gravity.START);
                    txtCant.setText(listasearch3.get(i).getExistencia());
                    txtCant.setPadding(20, 20, 20, 20);
                    txtCant.setTextColor(Color.BLACK);
                    txtCant.setLayoutParams(layaoutDes);
                    fila.addView(txtCant);

                    txtPrecio = new TextView(getApplicationContext());
                    txtPrecio.setBackgroundColor(Color.BLACK);
                    txtPrecio.setGravity(Gravity.START);
                    txtPrecio.setText("$" + formatNumberCurrency(listasearch2.get(i).getPrecio()));
                    txtPrecio.setPadding(20, 20, 20, 20);
                    txtPrecio.setTextColor(Color.WHITE);
                    txtPrecio.setLayoutParams(layaoutDes);
                    fila.addView(txtPrecio);

                    txtDesc = new TextView(getApplicationContext());
                    txtDesc.setBackgroundColor(Color.WHITE);
                    txtDesc.setGravity(Gravity.START);
                    txtDesc.setText(listasearch2.get(i).getDesc() + "%");
                    txtDesc.setPadding(20, 20, 20, 20);
                    txtDesc.setTextColor(Color.BLACK);
                    txtDesc.setLayoutParams(layaoutDes);
                    fila.addView(txtDesc);


                    txtImporte = new TextView(getApplicationContext());
                    txtImporte.setBackgroundColor(Color.BLACK);
                    txtImporte.setGravity(Gravity.START);
                    txtImporte.setText("$" + formatNumberCurrency(String.valueOf(monto3)));
                    txtImporte.setPadding(20, 20, 20, 20);
                    txtImporte.setTextColor(Color.WHITE);
                    txtImporte.setLayoutParams(layaoutDes);
                    fila.addView(txtImporte);
                    fila.setPadding(2, 2, 2, 2);

                    tableLayout.addView(fila);

                }
            }
            Montototal2();
            dialog3.dismiss();
            mDialog.dismiss();

                for (int i = 0; i < listasearch4.size(); i++) {
                int posi = spinnerVia.getSelectedItemPosition();
                if (posi == i) {
                    strVia = listasearch4.get(i).getClave();
                    break;
                }
            }








        }

    }


    private void conectar4() {


        for (int i = 0; i < listasearch2.size(); i++) {

            Cantidad = "";
            Producto = "";
            Cantidad = listasearch2.get(i).getCant();
            Producto = listasearch2.get(i).getClaveP();

            String SOAP_ACTION = "valExiPed";
            String METHOD_NAME = "valExiPed";
            String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
            String URL = "http://" + StrServer + "/WSk75Branch";
            String ProductoResponse = "";
            String Descripcion = "";
            String Existencia = "";
            String Unidad = "";
            String bajnNeg = "";


            try {

                SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                xmlvalExiPed soapEnvelope = new xmlvalExiPed(SoapEnvelope.VER11);
                soapEnvelope.xmlvalExiPed(strusr, strpass, Cantidad, Producto, strcodBra);
                soapEnvelope.dotNet = true;
                soapEnvelope.implicitTypes = true;
                soapEnvelope.setOutputSoapObject(Request);
                HttpTransportSE trasport = new HttpTransportSE(URL);
                trasport.debug = true;
                trasport.call(SOAP_ACTION, soapEnvelope);


                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty("Existencia");
                ProductoResponse = (response0.getPropertyAsString("k_Producto").equals("anyType{}")?"":response0.getPropertyAsString("k_Producto"));
                Descripcion = (response0.getPropertyAsString("k_Descripcion").equals("anyType{}")?"":response0.getPropertyAsString("k_Descripcion"));
                Existencia = (response0.getPropertyAsString("k_Existencia").equals("anyType{}")?"0":response0.getPropertyAsString("k_Existencia"));
                Unidad = (response0.getPropertyAsString("k_Unidad").equals("anyType{}")?"":response0.getPropertyAsString("k_Unidad"));
                bajnNeg = (response0.getPropertyAsString("k_bajNeg").equals("anyType{}")?"0":response0.getPropertyAsString("k_bajNeg"));

                listasearch3.add(new valExiPedSANDG(ProductoResponse,Descripcion, Existencia, Unidad, bajnNeg));


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

    private class AsyncCallWS5 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar5();

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            String[] opciones = new String[listasearch4.size()];

            for (int i = 0; i < listasearch4.size(); i++) {
                opciones[i] = listasearch4.get(i).getNombre();
                search2[i] = listasearch4.get(i).getClave();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, opciones);
            spinnerVia.setAdapter(adapter);
            if (StrVia != ""){
                String clave;
                for (int i = 0; i < listasearch4.size(); i++) {
                    clave = listasearch4.get(i).getClave();
                    if (clave.equals(StrVia)) {
                        spinnerVia.setSelection(i);
                        break;
                    }
                }
            }

        }

    }


    private void conectar5() {
        String SOAP_ACTION = "ListaVia";
        String METHOD_NAME = "ListaVia";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlViaE soapEnvelope = new xmlViaE(SoapEnvelope.VER11);
            soapEnvelope.xmlViaE(strusr, strpass);
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

                listasearch4.add(new ListaViaSANDG((response0.getPropertyAsString("k_claveVia").equals("anyType{}") ? " " : response0.getPropertyAsString("k_claveVia")),
                        (response0.getPropertyAsString("k_nombreVia").equals("anyType{}") ? " " : response0.getPropertyAsString("k_nombreVia"))));


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

    private class AsyncCallWS6 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar6();

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


        }

    }


    private void conectar6() {
        String SOAP_ACTION = "Envio";
        String METHOD_NAME = "Envio";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlDirEnvio soapEnvelope = new xmlDirEnvio(SoapEnvelope.VER11);
            soapEnvelope.xmlDirEnvio(strusr, strpass, strcode);
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

                listasearch5.add(new EnvioSANDG((response0.getPropertyAsString("k_id").equals("anyType{}") ? " " : response0.getPropertyAsString("k_id")),
                        (response0.getPropertyAsString("k_CalleyNumero").equals("anyType{}") ? " " : response0.getPropertyAsString("k_CalleyNumero")),
                        (response0.getPropertyAsString("k_ColoClinte").equals("anyType{}") ? " " : response0.getPropertyAsString("k_ColoClinte")),
                        (response0.getPropertyAsString("k_Poblacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Poblacion")),
                        (response0.getPropertyAsString("k_Numero").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Numero"))));


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

    private class AsyncCallWS7 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar7();

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            Intent regreso = new Intent(ActivityDetallCoti.this, ActivityConsulCoti.class);
            overridePendingTransition(0, 0);
            startActivity(regreso);
            overridePendingTransition(0, 0);

        }

    }


    private void conectar7() {
        String SOAP_ACTION = "NewDoc";
        String METHOD_NAME = "NewDoc";
        String NAMESPACE = "http://" + StrServer + "/WSk80Docs/";
        String URL = "http://" + StrServer + "/WSk80Docs";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlNewDoc42 soapEnvelope = new xmlNewDoc42(SoapEnvelope.VER11);
            soapEnvelope.xmlNewDoc42(strComentario, strNombreCliente, strcode, StrFechaActaul, StrFechaVencimiento, strcodBra, strusr, strpass,
                    StrRFC, StrPlazo, MontoStr, ivstr, DescuentoStr, DescPro, Desc1, StrCalle, StrColonia, StrPoblacion, Folio1, strVia, stridEnvio, listasearch2, listasearch3, StrServer);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;


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

    private void Montototal() {




        double Subtotal = 0;
        String Subtotal1;
        for (int i = 0; i < listasearch2.size(); i++) {
            Subtotal = Subtotal + Double.parseDouble(listasearch2.get(i).getImporte());
        }



        Subtotal1 = String.valueOf(Subtotal);
        SubdescuentoValida=Subtotal1;
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


    private void Montototal2() {

        double cantidad;
        double precio;
        double descuento;
        double monto;
        double monto2;
        double monto3;

        listasearch6 =listasearch2;

        for (int i = 0; i < listasearch6.size(); i++) {
            for (int j = 0; j < listasearch3.size(); j++) {
                if (listasearch6.get(i).getClaveP().equals(listasearch3.get(j).getProdcuto())){
                    Double Importe=0.00;
                    listasearch6.get(i).setCant(listasearch3.get(j).getExistencia());
                    precio=Double.valueOf(listasearch6.get(i).getPrecio());
                    cantidad =Double.valueOf(listasearch3.get(i).getExistencia());
                    descuento =Double.valueOf(listasearch6.get(i).getDesc());
                    monto=precio * cantidad;
                    monto2 = monto * (descuento/100);
                    monto3 =monto-monto2;
                    listasearch6.get(i).setImporte(String.valueOf(monto3));



                }
            }
        }
        double Subtotal = 0;
        String Subtotal1;
        for (int i = 0; i < listasearch6.size(); i++) {
            Subtotal = Subtotal + Double.parseDouble(listasearch6.get(i).getImporte());
        }



        Subtotal1 = String.valueOf(Subtotal);
        SubdescuentoValida=Subtotal1;
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


    public void DirEnvio(View view) {
        strClaveCli = listasearch2.get(0).getClaveC();

        String[] opciones = new String[listasearch5.size()];

        for (int i = 0; i < listasearch5.size(); i++) {
            opciones[i] = listasearch5.get(i).getCalle();
            search3[i] = listasearch5.get(i).getId();
        }


        mDialog.dismiss();

        if (listasearch5.size() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetallCoti.this);
            builder.setTitle("DIRECCION DE ENVIO ");


            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    for (int i = 0; i < listasearch5.size(); i++) {
                        int posi = which;
                        if (posi == i) {
                            id = listasearch5.get(i).getId();
                            CalleSec = listasearch5.get(i).getCalle();
                            ColoniaSec = listasearch5.get(i).getColonia();
                            PoblacionSec = listasearch5.get(i).getPoblacion();
                            NumeroTel = listasearch5.get(i).getNumero();
                            break;
                        }
                    }

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                    alerta.setMessage("ID:" + id + "\n" +
                            "C:" + CalleSec + "\n" +
                            "COLONIA:" + ColoniaSec + "\n" +
                            "POBLACION:" + PoblacionSec + "\n" +
                            "NUMERO:" + NumeroTel + "\n").setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stridEnvio = id;
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stridEnvio = "";
                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
                            alerta.setMessage("EL PEDIDO SE MANDARA A LA DIRECCIÓN DE FACTURACIÓN").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("AVISO");
                            titulo.show();

                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("¿DESEAS MANDAR EL PEDIDO A?");
                    titulo.show();


                }

            });

// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityDetallCoti.this);
            alerta.setMessage("ESTE CLIENTE NO CUENTA CON DIRECCIONES SECUNDARIAS EL PEDIDO SE MANDARA A LA DIRECCION DE FACTURACION.").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("AVISO");
            titulo.show();
        }


    }


}