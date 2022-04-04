package com.almacen.keplerclientesapp.ui.slideshow;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.EstadodeCuentaActivity;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.SetGetLisFacturas;
import com.almacen.keplerclientesapp.SetandGet.SetGetListMarca;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.XMLS.xmlUsuarioConsulta;
import com.almacen.keplerclientesapp.activity.ActivityFactuDetall;
import com.almacen.keplerclientesapp.activity.LoginActivity;
import com.almacen.keplerclientesapp.activity.MainActivity;
import com.almacen.keplerclientesapp.adapter.AdapterFacturas;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
import com.almacen.keplerclientesapp.ui.gallery.GalleryFragment;
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
import java.util.List;

import dmax.dialog.SpotsDialog;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

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
    private TableLayout tableLayout;
    TableRow fila;
    String strFolio = "";
    Calendar c1 = Calendar.getInstance();
    SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
    String fechaactual = dateformatActually.format(c1.getTime());
    double Vencimiento = 0, montototal = 0;
    TextView Vencimientotxt,montotaltxt,grafvencidas,grafnovencidas;
    PieChartView pieChartView;
    Button totalvencidasbt,totalmontobt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Un momento...").build();

        Cliente = view.findViewById(R.id.idcliente);
        idlistFacturas = view.findViewById(R.id.idlistFacturas);
        preference = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
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
        listFacturas = new ArrayList<>();
        Vencimientotxt =view.findViewById(R.id.CantidadVencidatxt);
        montotaltxt = view.findViewById(R.id.totatltxt);
        grafvencidas =view.findViewById(R.id.grafvencidas);
        grafnovencidas = view.findViewById(R.id.grafnovencidas);
        totalvencidasbt =view.findViewById(R.id.totalvencidasver);
        totalmontobt = view.findViewById(R.id.totalmonto);


        pieChartView = view.findViewById(R.id.chart);


        totalvencidasbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Factura = new Intent(getActivity(), EstadodeCuentaActivity.class);
                Factura.putExtra("val", 1);
                startActivity(Factura);
            }
        });

        totalmontobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Facturar = new Intent(getActivity(), EstadodeCuentaActivity.class);
                Facturar.putExtra("val", 2);
                startActivity(Facturar);
            }
        });


        SlideshowFragment.Facturas task = new SlideshowFragment.Facturas();
        task.execute();



        return view;
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
            Cliente.setText(strnombreclientre);
            mDialog.dismiss();
int contVencidas=0,contnovencidas=0;
            for (int i = 0; i < listFacturas.size(); i++) {


                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1 = sdformat.parse(fechaactual);
                    Date date2 = sdformat.parse(listFacturas.get(i).getFechadeNacimiento());

                    if (date1.after(date2)) {
                        contVencidas++;
                        Vencimiento = Vencimiento + Double.parseDouble(listFacturas.get(i).getSaldodeFactura());
                    } else {
                        contnovencidas++;
                        montototal = montototal + Double.parseDouble(listFacturas.get(i).getSaldodeFactura());
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            Vencimientotxt.setText(Html.fromHtml("<font color=#000000>Facturas Vencidas <u>$</u></font><font color ='#4CAF50'><u>" + formatNumberCurrency(String.valueOf(Vencimiento))+ "</u></font>"));
            montotaltxt.setText(Html.fromHtml("<font color=#000000>Monto total <u>$</u></font><font color ='#4CAF50'><u>" + formatNumberCurrency(String.valueOf(montototal))+ "</u></font>"));
            List pieData = new ArrayList<>();
            pieData.add(new SliceValue(contVencidas, Color.RED).setLabel(String.valueOf(contVencidas)));
            pieData.add(new SliceValue(contnovencidas, Color.GRAY).setLabel(String.valueOf(contnovencidas)));


            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasLabels(true).setValueLabelTextSize(14);
            pieChartData.setHasCenterCircle(true).setCenterText1("Facturas Vencidas").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#000000"));
            pieChartView.setPieChartData(pieChartData);
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