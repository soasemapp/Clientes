package com.almacen.keplerclientesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.almacen.keplerclientesapp.SetandGet.SetGetLisFacturas;
import com.almacen.keplerclientesapp.XMLS.xmlUsuarioConsulta;
import com.almacen.keplerclientesapp.activity.ActivityFactuDetall;
import com.almacen.keplerclientesapp.includes.MyToolbar;
import com.almacen.keplerclientesapp.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dmax.dialog.SpotsDialog;

public class EstadodeCuentaActivity extends AppCompatActivity {


    View view;
    TextView Cliente;
    RecyclerView idlistFacturas;
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    String strnombreclientre;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    TextView txtFolio, txtPlazo, txtFechaVencimiento, txtSaldo, txtMontoFactura;
    AlertDialog mDialog;
    String Folio;
    FloatingActionButton btnBusCot;
    ArrayList<SetGetLisFacturas> listFacturas = new ArrayList<>();
    ArrayList<SetGetLisFacturas> listFacturas2vencidas = new ArrayList<>();
    private TableLayout tableLayout;
    TableRow fila;
    String strFolio = "";
    Calendar c1 = Calendar.getInstance();
    SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
    String fechaactual = dateformatActually.format(c1.getTime());
    int ban;
    private SharedPreferences preference2;
    private SharedPreferences.Editor editor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadode_cuenta);

        mDialog = new SpotsDialog(EstadodeCuentaActivity.this);
        Cliente = findViewById(R.id.idcliente);
        idlistFacturas = findViewById(R.id.idlistFacturas);
        tableLayout = findViewById(R.id.table);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        preference2 = getSharedPreferences("EstadoCuenta", Context.MODE_PRIVATE);
        editor2 = preference2.edit();

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
        ban = preference2.getInt("valor", 0);


        if(ban==1){

            MyToolbar.show(this, "Facturas-Vencidas", true);

        }else{
            MyToolbar.show(this, "Facturas", true);
        }

        listFacturas = new ArrayList<>();
        btnBusCot = findViewById(R.id.btnbusquedacoti);


        btnBusCot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent FactutaDetall = new Intent(EstadodeCuentaActivity.this, ActivityFactuDetall.class);
                FactutaDetall.putExtra("Folio", strFolio);
                FactutaDetall.putExtra("NumSucu", strcodBra);
                FactutaDetall.putExtra("val", ban);

                startActivity(FactutaDetall);

            }
        });


        EstadodeCuentaActivity.Facturas task = new EstadodeCuentaActivity.Facturas();
        task.execute();
    }


    private class Facturas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebServicePerfil();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            if (ban == 1) {

                tableLayout.setVisibility(View.VISIBLE);
                Cliente.setText(strnombreclientre);


                 for (int i = 0; i < listFacturas.size(); i++) {
                     SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                     try {
                         Date date1 = sdformat.parse(fechaactual);
                         Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                         if (date1.after(date2)) {
                             String folio_factura2 =listFacturas.get(i).getFolioFactura();
                             String plazo_factura2=listFacturas.get(i).getPlazoFactura();
                             String fechav2=listFacturas.get(i).getFechadeNacimiento();
                             String saldo_factura2=listFacturas.get(i).getSaldodeFactura();
                             String monto_factura2=listFacturas.get(i).getMontodeFactura();
                             listFacturas2vencidas.add(new SetGetLisFacturas(folio_factura2, plazo_factura2, fechav2, saldo_factura2, monto_factura2));

                         }
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }


                TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                for (int i = -1; i < listFacturas2vencidas.size(); i++) {


                    fila = new TableRow(getApplicationContext());
                    fila.setLayoutParams(layaoutFila);
                    if (i == -1) {
                        fila.setId(i + 1);
                        txtFolio = new TextView(getApplicationContext());
                        txtFolio.setText("Folio");
                        txtFolio.setGravity(Gravity.START);
                        txtFolio.setBackgroundColor(Color.RED);

                        fila.addView(txtFolio);

                        txtFolio.setTextColor(Color.WHITE);
                        txtFolio.setPadding(20, 20, 20, 20);
                        txtFolio.setLayoutParams(layaoutDes);
                      txtPlazo = new TextView(getApplicationContext());
                        txtPlazo.setText("Plazo");
                        txtPlazo.setGravity(Gravity.START);
                        txtPlazo.setBackgroundColor(Color.RED);
                        txtPlazo.setTextColor(Color.WHITE);
                        txtPlazo.setPadding(20, 20, 20, 20);
                        txtPlazo.setLayoutParams(layaoutDes);
                        fila.addView(txtPlazo);

                        txtFechaVencimiento = new TextView(getApplicationContext());
                        txtFechaVencimiento.setText("Vence");
                        txtFechaVencimiento.setGravity(Gravity.START);
                        txtFechaVencimiento.setBackgroundColor(Color.RED);
                        txtFechaVencimiento.setTextColor(Color.WHITE);
                        txtFechaVencimiento.setPadding(20, 20, 20, 20);
                        txtFechaVencimiento.setLayoutParams(layaoutDes);
                        fila.addView(txtFechaVencimiento);

                        txtSaldo = new TextView(getApplicationContext());
                        txtSaldo.setText("Saldo");
                        txtSaldo.setGravity(Gravity.END);
                        txtSaldo.setBackgroundColor(Color.RED);
                        txtSaldo.setTextColor(Color.WHITE);
                        txtSaldo.setPadding(20, 20, 20, 20);
                        txtSaldo.setLayoutParams(layaoutDes);
                        fila.addView(txtSaldo);

                        txtMontoFactura = new TextView(getApplicationContext());
                        txtMontoFactura.setText("Monto");
                        txtMontoFactura.setGravity(Gravity.END);
                        txtMontoFactura.setBackgroundColor(Color.RED);
                        txtMontoFactura.setTextColor(Color.WHITE);
                        txtMontoFactura.setPadding(20, 20, 20, 20);
                        txtMontoFactura.setLayoutParams(layaoutDes);
                        fila.addView(txtMontoFactura);
                        tableLayout.addView(fila);
                    } else {
                        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date1 = sdformat.parse(fechaactual);
                            Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                            if (date1.after(date2)) {
                                fila.setId(i + 1);

                                txtFolio = new TextView(getApplicationContext());
                                txtFolio.setGravity(Gravity.START);
                                txtFolio.setBackgroundColor(Color.BLACK);
                                txtFolio.setText(listFacturas.get(i).getFolioFactura());
                                txtFolio.setPadding(20, 20, 20, 20);
                                txtFolio.setTextColor(Color.WHITE);
                                txtFolio.setLayoutParams(layaoutDes);
                                fila.addView(txtFolio);


                                txtPlazo = new TextView(getApplicationContext());
                                txtPlazo.setBackgroundColor(Color.WHITE);
                                txtPlazo.setGravity(Gravity.START);
                                txtPlazo.setText(listFacturas.get(i).getPlazoFactura());
                                txtPlazo.setPadding(20, 20, 20, 20);
                                txtPlazo.setTextColor(Color.BLACK);
                                txtPlazo.setLayoutParams(layaoutDes);
                                fila.addView(txtPlazo);

                                txtFechaVencimiento = new TextView(getApplicationContext());
                                txtFechaVencimiento.setBackgroundColor(Color.BLACK);
                                txtFechaVencimiento.setGravity(Gravity.START);
                                txtFechaVencimiento.setText(listFacturas.get(i).getFechadeNacimiento());
                                txtFechaVencimiento.setPadding(20, 20, 20, 20);
                                txtFechaVencimiento.setTextColor(Color.WHITE);
                                txtFechaVencimiento.setLayoutParams(layaoutDes);
                                fila.addView(txtFechaVencimiento);

                                txtSaldo = new TextView(getApplicationContext());



                                try {
                                     date1 = sdformat.parse(fechaactual);
                                     date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                                    if (date1.after(date2)) {
                                        txtSaldo.setBackgroundColor(Color.RED);
                                        txtSaldo.setTextColor(Color.WHITE);
                                    } else {
                                        txtSaldo.setBackgroundColor(Color.WHITE);
                                        txtSaldo.setTextColor(Color.BLACK);
                                    }


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                txtSaldo.setGravity(Gravity.END);
                                txtSaldo.setText((listFacturas.get(i).getSaldodeFactura().equals("0") ? Html.fromHtml("<font color='#F32121'> PAGADO </font>") : Html.fromHtml("<font color='#000000'> $</font>") + formatNumberCurrency(listFacturas.get(i).getSaldodeFactura())));
                                txtSaldo.setPadding(20, 20, 20, 20);
                                txtSaldo.setLayoutParams(layaoutDes);
                                fila.addView(txtSaldo);

                                txtMontoFactura = new TextView(getApplicationContext());
                                txtMontoFactura.setBackgroundColor(Color.WHITE);
                                txtMontoFactura.setGravity(Gravity.END);
                                txtMontoFactura.setText(Html.fromHtml("<font color=#000000>$</font> <font color ='#4CAF50'>" + formatNumberCurrency(listFacturas.get(i).getMontodeFactura()) + "</font>"));
                                txtMontoFactura.setPadding(20, 20, 20, 20);
                                txtMontoFactura.setTextColor(Color.BLACK);
                                txtMontoFactura.setLayoutParams(layaoutDes);
                                fila.addView(txtMontoFactura);
                                fila.setPadding(2, 2, 2, 2);


                                fila.setPadding(2, 2, 2, 2);
                                fila.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        int idfila = view.getId();
                                        for (int i = -1; i < listFacturas2vencidas.size(); i++) {
                                            int selector = tableLayout.getChildAt(i + 1).getId();
                                            if (idfila == selector) {

                                                for (int j = 0; j < fila.getChildCount(); j++) {

                                                    TableRow valorfolio = (TableRow) tableLayout.getChildAt(i + 1);
                                                    TableRow valorplazo = (TableRow) tableLayout.getChildAt(i + 1);
                                                    TableRow valorvence = (TableRow) tableLayout.getChildAt(i + 1);
                                                    TableRow valorsaldo = (TableRow) tableLayout.getChildAt(i + 1);
                                                    TableRow valormonto = (TableRow) tableLayout.getChildAt(i + 1);

                                                    valorfolio.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                                                    valorplazo.getChildAt(1).setBackgroundColor(Color.LTGRAY);
                                                    valorvence.getChildAt(2).setBackgroundColor(Color.LTGRAY);
                                                    valorsaldo.getChildAt(3).setBackgroundColor(Color.LTGRAY);
                                                    valormonto.getChildAt(4).setBackgroundColor(Color.LTGRAY);

                                                    TextView txfolio = (TextView) valorfolio.getChildAt(0);
                                                    TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                                    txsaldo.setTextColor(Color.BLACK);
                                                    strFolio = txfolio.getText().toString();
                                                }
                                            } else if (i == -1) {

                                            } else {


                                                TableRow valorfolio = (TableRow) tableLayout.getChildAt(i + 1);
                                                TableRow valorplazo = (TableRow) tableLayout.getChildAt(i + 1);
                                                TableRow valorvence = (TableRow) tableLayout.getChildAt(i + 1);
                                                TableRow valorsaldo = (TableRow) tableLayout.getChildAt(i + 1);
                                                TableRow valormonto = (TableRow) tableLayout.getChildAt(i + 1);

                                                valorfolio.getChildAt(0).setBackgroundColor(Color.BLACK);
                                                valorplazo.getChildAt(1).setBackgroundColor(Color.WHITE);
                                                valorvence.getChildAt(2).setBackgroundColor(Color.BLACK);
                                                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                                                try {
                                                    Date date1 = sdformat.parse(fechaactual);
                                                    Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                                                    if (date1.after(date2)) {
                                                        valorsaldo.getChildAt(3).setBackgroundColor(Color.RED);
                                                        TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                                        txsaldo.setTextColor(Color.WHITE);
                                                    } else {
                                                        valorsaldo.getChildAt(3).setBackgroundColor(Color.WHITE);
                                                        TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                                        txsaldo.setTextColor(Color.BLACK);
                                                    }


                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                valormonto.getChildAt(4).setBackgroundColor(Color.WHITE);

                                                TextView txplazo = (TextView) valorplazo.getChildAt(1);

                                                txplazo.setTextColor(Color.BLACK);

                                            }

                                        }

                                    }

                                });

                                tableLayout.addView(fila);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }






                    }
                }
                mDialog.dismiss();


            } else if (ban == 2) {

                tableLayout.setVisibility(View.VISIBLE);
                Cliente.setText(strnombreclientre);


                TableRow.LayoutParams layaoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                TableRow.LayoutParams layaoutDes = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                for (int i = -1; i < listFacturas.size(); i++) {


                    fila = new TableRow(getApplicationContext());
                    fila.setLayoutParams(layaoutFila);
                    if (i == -1) {
                        fila.setId(i + 1);
                        txtFolio = new TextView(getApplicationContext());
                        txtFolio.setText("Folio");
                        txtFolio.setGravity(Gravity.START);
                        txtFolio.setBackgroundColor(Color.RED);
                        txtFolio.setTextColor(Color.WHITE);
                        txtFolio.setPadding(20, 20, 20, 20);
                        txtFolio.setLayoutParams(layaoutDes);

                        fila.addView(txtFolio);

                        txtPlazo = new TextView(getApplicationContext());
                        txtPlazo.setText("Plazo");
                        txtPlazo.setGravity(Gravity.START);
                        txtPlazo.setBackgroundColor(Color.RED);
                        txtPlazo.setTextColor(Color.WHITE);
                        txtPlazo.setPadding(20, 20, 20, 20);
                        txtPlazo.setLayoutParams(layaoutDes);
                        fila.addView(txtPlazo);

                        txtFechaVencimiento = new TextView(getApplicationContext());
                        txtFechaVencimiento.setText("Vence");
                        txtFechaVencimiento.setGravity(Gravity.START);
                        txtFechaVencimiento.setBackgroundColor(Color.RED);
                        txtFechaVencimiento.setTextColor(Color.WHITE);
                        txtFechaVencimiento.setPadding(20, 20, 20, 20);
                        txtFechaVencimiento.setLayoutParams(layaoutDes);
                        fila.addView(txtFechaVencimiento);

                        txtSaldo = new TextView(getApplicationContext());
                        txtSaldo.setText("Saldo");
                        txtSaldo.setGravity(Gravity.END);
                        txtSaldo.setBackgroundColor(Color.RED);
                        txtSaldo.setTextColor(Color.WHITE);
                        txtSaldo.setPadding(20, 20, 20, 20);
                        txtSaldo.setLayoutParams(layaoutDes);
                        fila.addView(txtSaldo);

                        txtMontoFactura = new TextView(getApplicationContext());
                        txtMontoFactura.setText("Monto");
                        txtMontoFactura.setGravity(Gravity.END);
                        txtMontoFactura.setBackgroundColor(Color.RED);
                        txtMontoFactura.setTextColor(Color.WHITE);
                        txtMontoFactura.setPadding(20, 20, 20, 20);
                        txtMontoFactura.setLayoutParams(layaoutDes);
                        fila.addView(txtMontoFactura);
                        tableLayout.addView(fila);
                    } else {

                        fila.setId(i + 1);

                        txtFolio = new TextView(getApplicationContext());
                        txtFolio.setGravity(Gravity.START);
                        txtFolio.setBackgroundColor(Color.BLACK);
                        txtFolio.setText(listFacturas.get(i).getFolioFactura());
                        txtFolio.setPadding(20, 20, 20, 20);
                        txtFolio.setTextColor(Color.WHITE);
                        txtFolio.setLayoutParams(layaoutDes);
                        fila.addView(txtFolio);


                        txtPlazo = new TextView(getApplicationContext());
                        txtPlazo.setBackgroundColor(Color.WHITE);
                        txtPlazo.setGravity(Gravity.START);
                        txtPlazo.setText(listFacturas.get(i).getPlazoFactura());
                        txtPlazo.setPadding(20, 20, 20, 20);
                        txtPlazo.setTextColor(Color.BLACK);
                        txtPlazo.setLayoutParams(layaoutDes);
                        fila.addView(txtPlazo);

                        txtFechaVencimiento = new TextView(getApplicationContext());
                        txtFechaVencimiento.setBackgroundColor(Color.BLACK);
                        txtFechaVencimiento.setGravity(Gravity.START);
                        txtFechaVencimiento.setText(listFacturas.get(i).getFechadeNacimiento());
                        txtFechaVencimiento.setPadding(20, 20, 20, 20);
                        txtFechaVencimiento.setTextColor(Color.WHITE);
                        txtFechaVencimiento.setLayoutParams(layaoutDes);
                        fila.addView(txtFechaVencimiento);

                        txtSaldo = new TextView(getApplicationContext());


                        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date1 = sdformat.parse(fechaactual);
                            Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                            if (date1.after(date2)) {
                                txtSaldo.setBackgroundColor(Color.RED);
                                txtSaldo.setTextColor(Color.WHITE);
                            } else {
                                txtSaldo.setBackgroundColor(Color.WHITE);
                                txtSaldo.setTextColor(Color.BLACK);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        txtSaldo.setGravity(Gravity.END);
                        txtSaldo.setText((listFacturas.get(i).getSaldodeFactura().equals("0") ? Html.fromHtml("<font color='#F32121'> PAGADO </font>") : Html.fromHtml("<font color='#000000'> $</font>") + formatNumberCurrency(listFacturas.get(i).getSaldodeFactura())));
                        txtSaldo.setPadding(20, 20, 20, 20);
                        txtSaldo.setLayoutParams(layaoutDes);
                        fila.addView(txtSaldo);

                        txtMontoFactura = new TextView(getApplicationContext());
                        txtMontoFactura.setBackgroundColor(Color.WHITE);
                        txtMontoFactura.setGravity(Gravity.END);
                        txtMontoFactura.setText(Html.fromHtml("<font color=#000000>$</font> <font color ='#4CAF50'>" + formatNumberCurrency(listFacturas.get(i).getMontodeFactura()) + "</font>"));
                        txtMontoFactura.setPadding(20, 20, 20, 20);
                        txtMontoFactura.setTextColor(Color.BLACK);
                        txtMontoFactura.setLayoutParams(layaoutDes);
                        fila.addView(txtMontoFactura);
                        fila.setPadding(2, 2, 2, 2);


                        fila.setPadding(2, 2, 2, 2);
                        fila.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                int idfila = view.getId();
                                for (int i = -1; i < listFacturas.size(); i++) {
                                    int selector = tableLayout.getChildAt(i + 1).getId();
                                    if (idfila == selector) {

                                        for (int j = 0; j < fila.getChildCount(); j++) {

                                            TableRow valorfolio = (TableRow) tableLayout.getChildAt(i + 1);
                                            TableRow valorplazo = (TableRow) tableLayout.getChildAt(i + 1);
                                            TableRow valorvence = (TableRow) tableLayout.getChildAt(i + 1);
                                            TableRow valorsaldo = (TableRow) tableLayout.getChildAt(i + 1);
                                            TableRow valormonto = (TableRow) tableLayout.getChildAt(i + 1);

                                            valorfolio.getChildAt(0).setBackgroundColor(Color.LTGRAY);
                                            valorplazo.getChildAt(1).setBackgroundColor(Color.LTGRAY);
                                            valorvence.getChildAt(2).setBackgroundColor(Color.LTGRAY);
                                            valorsaldo.getChildAt(3).setBackgroundColor(Color.LTGRAY);
                                            valormonto.getChildAt(4).setBackgroundColor(Color.LTGRAY);

                                            TextView txfolio = (TextView) valorfolio.getChildAt(0);
                                            TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                            txsaldo.setTextColor(Color.BLACK);
                                            strFolio = txfolio.getText().toString();
                                        }
                                    } else if (i == -1) {

                                    } else {


                                        TableRow valorfolio = (TableRow) tableLayout.getChildAt(i + 1);
                                        TableRow valorplazo = (TableRow) tableLayout.getChildAt(i + 1);
                                        TableRow valorvence = (TableRow) tableLayout.getChildAt(i + 1);
                                        TableRow valorsaldo = (TableRow) tableLayout.getChildAt(i + 1);
                                        TableRow valormonto = (TableRow) tableLayout.getChildAt(i + 1);

                                        valorfolio.getChildAt(0).setBackgroundColor(Color.BLACK);
                                        valorplazo.getChildAt(1).setBackgroundColor(Color.WHITE);
                                        valorvence.getChildAt(2).setBackgroundColor(Color.BLACK);
                                        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                                        try {
                                            Date date1 = sdformat.parse(fechaactual);
                                            Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                                            if (date1.after(date2)) {
                                                valorsaldo.getChildAt(3).setBackgroundColor(Color.RED);
                                                TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                                txsaldo.setTextColor(Color.WHITE);
                                            } else {
                                                valorsaldo.getChildAt(3).setBackgroundColor(Color.WHITE);
                                                TextView txsaldo = (TextView) valorsaldo.getChildAt(3);

                                                txsaldo.setTextColor(Color.BLACK);
                                            }


                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        valormonto.getChildAt(4).setBackgroundColor(Color.WHITE);

                                        TextView txplazo = (TextView) valorplazo.getChildAt(1);

                                        txplazo.setTextColor(Color.BLACK);

                                    }

                                }

                            }

                        });

                        tableLayout.addView(fila);

                    }
                }
                mDialog.dismiss();


            }




            /*
            AdapterFacturas adapter = new AdapterFacturas(listFacturas);
            idlistFacturas.setAdapter(adapter);
            mDialog.dismiss();
        */
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
    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    private void WebServicePerfil() {
        String SOAP_ACTION = "UserConsulta";
        String METHOD_NAME = "UserConsulta";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlUsuarioConsulta soapEnvelope = new xmlUsuarioConsulta(SoapEnvelope.VER11);
            soapEnvelope.xmlUsuarioConsulta(strusr, strpass, strco, "1");
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
            response0 = (SoapObject) response0.getProperty("Item");
            response0 = (SoapObject) response0.getProperty("Datos");
            strnombreclientre = response0.getPropertyAsString("nombre");
            response0 = (SoapObject) response0.getProperty("estadodecuenta");
            for (int i = 0; i < response0.getPropertyCount(); i++) {

                SoapObject response = (SoapObject) soapEnvelope.bodyIn;
                response = (SoapObject) response.getProperty("Item");
                response = (SoapObject) response.getProperty("Datos");
                response = (SoapObject) response.getProperty("estadodecuenta");
                response = (SoapObject) response.getProperty(i);
                String folio_factura = response.getPropertyAsString("folio_factura");
                String plazo_factura = response.getPropertyAsString("plazo");
                String fechav = response.getPropertyAsString("fechav");
                String saldo_factura = response.getPropertyAsString("saldo_factura");
                String monto_factura = response.getPropertyAsString("monto_factura");


                listFacturas.add(new SetGetLisFacturas(folio_factura, plazo_factura, fechav, saldo_factura, monto_factura));

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
}