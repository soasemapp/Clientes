package com.almacen.keplerclientesapp.ui.home;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.BusquedaActivity;
import com.almacen.keplerclientesapp.ConexionSQLiteHelper;
import com.almacen.keplerclientesapp.DetalladoProductosActivity;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ProductosNuevosSANDG;
import com.almacen.keplerclientesapp.SliderAdapter;
import com.almacen.keplerclientesapp.SliderData;
import com.almacen.keplerclientesapp.XMLS.xmlProductosNuevos;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosNuevos;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosPartech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosRodatech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosShark;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductostrackone;
import com.smarteist.autoimageslider.SliderView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment {

    RecyclerView recyclerViewEagle, recyclerViewTrackone, recyclerViewRodatech, recyclerViewPartech, recyclerViewShark;

    int  datos;
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    View view;

    ConexionSQLiteHelper conn;
    AlertDialog mDialog;
    Context context = this.getActivity();

    ArrayList<ProductosNuevosSANDG> ListaProductosGeneral = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosEagle = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosTrackone = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosRodatech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosPartech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosShark = new ArrayList<>();

    LinearLayout EagleOcultar, TrackOneOcultar, RodatechOcultar, PartechOcultar, SharkOcultar;

    EditText BusquedaProducto;
    // Urls of our images.
    String url1 = "https://www.jacve.mx/images/index/display/display_mx.webp";
    String url2 = "https://www.jacve.mx/images/index/display/trkdir%20y%20susp2.webp";
    String url3 = "https://www.jacve.mx/images/index/display/eaglesopcubr3.webp";

    String ProductosNuevosStr ;
    SliderView sliderView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Espere un momento...").build();


        //FindView
        recyclerViewEagle = view.findViewById(R.id.listProductosEagle);
        recyclerViewTrackone = view.findViewById(R.id.listProductTrackone);
        recyclerViewRodatech = view.findViewById(R.id.listProductosRodatech);
        recyclerViewPartech = view.findViewById(R.id.listProductosPartech);
        recyclerViewShark = view.findViewById(R.id.listProductosShark);
        BusquedaProducto = view.findViewById(R.id.idBusqueda);
        EagleOcultar = view.findViewById(R.id.EagleOcultar);
        TrackOneOcultar = view.findViewById(R.id.TrackoneOcultar);
        RodatechOcultar = view.findViewById(R.id.RodatechOcultar);
        PartechOcultar = view.findViewById(R.id.PartechOcultar);
        SharkOcultar = view.findViewById(R.id.SharkOcultar);


        sliderView = view.findViewById(R.id.image_slider);


        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        // adding the urls inside array list
        sliderDataArrayList.add(new SliderData(url1));
        sliderDataArrayList.add(new SliderData(url2));
        sliderDataArrayList.add(new SliderData(url3));

        // passing this array list inside our adapter class.
        SliderAdapter adapter = new SliderAdapter(getActivity(), sliderDataArrayList);

        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);

        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(3);

        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);

        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        //Preference
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
        ProductosNuevosStr=preference.getString("Productosnuevos", "0");





        ListaProductosEagle = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEagle.setLayoutManager(horizontalLayoutManagaer);

        ListaProductosTrackone = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrackone.setLayoutManager(horizontalLayoutManagaer1);

        ListaProductosRodatech = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRodatech.setLayoutManager(horizontalLayoutManagaer2);

        ListaProductosPartech = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPartech.setLayoutManager(horizontalLayoutManagaer3);

        ListaProductosShark = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewShark.setLayoutManager(horizontalLayoutManagaer4);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        ConsultaComprobacion();


        if(datos>0){
            if( day==15 || day==1){
                if (ProductosNuevosStr.equals("0")) {
                    editor.putString("Productosnuevos", "1");
                    editor.commit();
                    HomeFragment.ProductosNuevos task = new HomeFragment.ProductosNuevos();
                    task.execute();
                } else {
                    Consulta();
                }

            }else{

                editor.putString("Productosnuevos", "0");
                editor.commit();
                Consulta();

            }


        }else{
            HomeFragment.ProductosNuevos task = new HomeFragment.ProductosNuevos();
            task.execute();

        }




        BusquedaProducto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) { //Do Something } return false; } });

                    // Check if no view has focus:
                    String BusquedaProductoString = "";
                    BusquedaProductoString = BusquedaProducto.getText().toString();
                    Intent BusquedaProdcuto = new Intent(getActivity(), BusquedaActivity.class);
                    BusquedaProdcuto.putExtra("Producto", BusquedaProductoString);
                    startActivity(BusquedaProdcuto);


                }
                return false;
            }
        });

        if (StrServer.equals("sprautomotive.servehttp.com:9090")) {
            EagleOcultar.setVisibility(View.GONE);
            TrackOneOcultar.setVisibility(View.GONE);
            RodatechOcultar.setVisibility(View.VISIBLE);
            PartechOcultar.setVisibility(View.GONE);
            SharkOcultar.setVisibility(View.GONE);


        } else if (StrServer.equals("sprautomotive.servehttp.com:9095")) {
            EagleOcultar.setVisibility(View.GONE);
            TrackOneOcultar.setVisibility(View.GONE);
            RodatechOcultar.setVisibility(View.GONE);
            PartechOcultar.setVisibility(View.VISIBLE);
            SharkOcultar.setVisibility(View.GONE);
        } else if (StrServer.equals("sprautomotive.servehttp.com:9080")) {
            EagleOcultar.setVisibility(View.GONE);
            TrackOneOcultar.setVisibility(View.GONE);
            RodatechOcultar.setVisibility(View.GONE);
            PartechOcultar.setVisibility(View.GONE);
            SharkOcultar.setVisibility(View.VISIBLE);
        } else if (StrServer.equals("vazlocolombia.dyndns.org:9085")) {
            EagleOcultar.setVisibility(View.VISIBLE);
            TrackOneOcultar.setVisibility(View.GONE);
            RodatechOcultar.setVisibility(View.GONE);
            PartechOcultar.setVisibility(View.GONE);
            SharkOcultar.setVisibility(View.GONE);
        } else {
            EagleOcultar.setVisibility(View.VISIBLE);
            TrackOneOcultar.setVisibility(View.VISIBLE);
            RodatechOcultar.setVisibility(View.VISIBLE);
            PartechOcultar.setVisibility(View.VISIBLE);
            SharkOcultar.setVisibility(View.VISIBLE);

        }


        return view;
    }
    private void BorrarCarrito() {
        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        db.delete("productos", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='productos'");
        db.close();
    }
    private void ConsultaComprobacion() {

        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor fila = db.rawQuery("SELECT COUNT(*) FROM productos", null);
        if (fila != null && fila.moveToFirst()) {
            do {
               datos=fila.getInt(0);
            } while (fila.moveToNext());
        }
        db.close();

    }


    private void Consulta() {
        ListaProductosEagle = new ArrayList<>();
        ListaProductosRodatech = new ArrayList<>();
        ListaProductosPartech = new ArrayList<>();
        ListaProductosShark = new ArrayList<>();
        ListaProductosTrackone = new ArrayList<>();
        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor fila = db.rawQuery("select * from productos", null);
        if (fila != null && fila.moveToFirst()) {
            do {

                ListaProductosGeneral.add(new ProductosNuevosSANDG(fila.getString(1),
                        fila.getString(2),
                        fila.getString(3)));

                switch (fila.getString(3)) {
                    case "1":
                        ListaProductosEagle.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)));
                        break;
                    case "2":
                        ListaProductosRodatech.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)));
                        break;
                    case "3":
                        ListaProductosPartech.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)));
                        break;
                    case "4":
                        ListaProductosShark.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)));
                        break;
                    case "6":
                        ListaProductosTrackone.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3)));
                        break;
                    default:
                        break;
                }

            } while (fila.moveToNext());

            if (ListaProductosGeneral.size() > 0) {

                AdaptadorProductosNuevos adapter = new AdaptadorProductosNuevos(ListaProductosEagle, context);
                recyclerViewEagle.setAdapter(adapter);
                AdaptadorProductostrackone adapter1 = new AdaptadorProductostrackone(ListaProductosTrackone, context);
                recyclerViewTrackone.setAdapter(adapter1);
                AdaptadorProductosRodatech adapter2 = new AdaptadorProductosRodatech(ListaProductosRodatech, context);
                recyclerViewRodatech.setAdapter(adapter2);
                AdaptadorProductosPartech adapter3 = new AdaptadorProductosPartech(ListaProductosPartech, context);
                recyclerViewPartech.setAdapter(adapter3);
                AdaptadorProductosShark adapter4 = new AdaptadorProductosShark(ListaProductosShark, context);
                recyclerViewShark.setAdapter(adapter4);


                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int position = recyclerViewEagle.getChildAdapterPosition(recyclerViewEagle.findContainingItemView(view));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosEagle.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        startActivity(ProductosDetallados);


                    }
                });

                adapter1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int position = recyclerViewTrackone.getChildAdapterPosition(recyclerViewTrackone.findContainingItemView(view));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosTrackone.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        startActivity(ProductosDetallados);

                    }
                });

                adapter2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewRodatech.getChildAdapterPosition(recyclerViewRodatech.findContainingItemView(view));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosRodatech.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        startActivity(ProductosDetallados);
                    }
                });

                adapter3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewPartech.getChildAdapterPosition(recyclerViewPartech.findContainingItemView(view));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosPartech.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        startActivity(ProductosDetallados);
                    }
                });

                adapter4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewShark.getChildAdapterPosition(recyclerViewShark.findContainingItemView(view));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosShark.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        startActivity(ProductosDetallados);


                    }
                });

            }

        }

        db.close();

    }


    private class ProductosNuevos extends AsyncTask<Void, Void, Void> {

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
            BorrarCarrito();

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            for (int i = 0; i < ListaProductosGeneral.size(); i++) {

                String Clave = ListaProductosGeneral.get(i).getClave();
                String Descripcion = ListaProductosGeneral.get(i).getDescripcion();
                String Tipo = ListaProductosGeneral.get(i).getTipo();

                db.execSQL("INSERT INTO  productos (Clave,Descripcion,Tipo) values ('" + Clave + "','" + Descripcion + "','" + Tipo + "')");
            }

            db.close();

            for (int i = 0; i < ListaProductosGeneral.size(); i++) {
                String Clave, Descripcion, Tipo;
                Clave = ListaProductosGeneral.get(i).getClave();
                Descripcion = ListaProductosGeneral.get(i).getDescripcion();
                Tipo = ListaProductosGeneral.get(i).getTipo();

                switch (Tipo) {
                    case "1":
                        ListaProductosEagle.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo));
                        break;
                    case "2":
                        ListaProductosRodatech.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo));
                        break;
                    case "3":
                        ListaProductosPartech.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo));
                        break;
                    case "4":
                        ListaProductosShark.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo));
                        break;
                    case "6":
                        ListaProductosTrackone.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo));
                        break;
                    default:
                        break;
                }
            }


            AdaptadorProductosNuevos adapter = new AdaptadorProductosNuevos(ListaProductosEagle, context);
            recyclerViewEagle.setAdapter(adapter);
            AdaptadorProductostrackone adapter1 = new AdaptadorProductostrackone(ListaProductosTrackone, context);
            recyclerViewTrackone.setAdapter(adapter1);
            AdaptadorProductosRodatech adapter2 = new AdaptadorProductosRodatech(ListaProductosRodatech, context);
            recyclerViewRodatech.setAdapter(adapter2);
            AdaptadorProductosPartech adapter3 = new AdaptadorProductosPartech(ListaProductosPartech, context);
            recyclerViewPartech.setAdapter(adapter3);
            AdaptadorProductosShark adapter4 = new AdaptadorProductosShark(ListaProductosShark, context);
            recyclerViewShark.setAdapter(adapter4);


            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewEagle.getChildAdapterPosition(recyclerViewEagle.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosEagle.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);
                }
            });

            adapter1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewTrackone.getChildAdapterPosition(recyclerViewTrackone.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosTrackone.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);
                }
            });

            adapter2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewRodatech.getChildAdapterPosition(recyclerViewRodatech.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosRodatech.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);
                }
            });

            adapter3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewPartech.getChildAdapterPosition(recyclerViewPartech.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosPartech.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);
                }
            });

            adapter4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewShark.getChildAdapterPosition(recyclerViewShark.findContainingItemView(view));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosShark.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);
                }
            });

            mDialog.dismiss();

        }


    }


    private void conectar3() {
        String SOAP_ACTION = "ProdcutosNuevos";
        String METHOD_NAME = "ProdcutosNuevos";
        String NAMESPACE = "http://" + StrServer + "/WSk75ClientesSOAP/";
        String URL = "http://" + StrServer + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlProductosNuevos soapEnvelope = new xmlProductosNuevos(SoapEnvelope.VER11);
            soapEnvelope.xmlProductosNuevos(strusr, strpass);
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

                ListaProductosGeneral.add(new ProductosNuevosSANDG((response0.getPropertyAsString("k_Producto").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Producto")),
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_Tipo").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Tipo"))));

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


}