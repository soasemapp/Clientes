package com.almacen.keplerclientesapp.ui.home;

import android.annotation.SuppressLint;
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
import com.almacen.keplerclientesapp.SetterandGetter.BannersSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.ProductosNuevosSANDG;
import com.almacen.keplerclientesapp.SliderAdapter;
import com.almacen.keplerclientesapp.SliderData;
import com.almacen.keplerclientesapp.XMLS.xmlProductosNuevos;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosGSP;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosMechanic;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosNuevos;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosPartech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosRodatech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosShark;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductostrackone;
import com.almacen.keplerclientesapp.includes.HttpHandler;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
public class HomeFragment extends Fragment {

    RecyclerView recyclerViewEagle, recyclerViewTrackone, recyclerViewRodatech, recyclerViewPartech, recyclerViewShark,recyclerViewMechanic,recyclerViewGSP,recyclerViewVazlo;

    String strusr, strpass, strname, strlname, strtype, strbran, strma, strco, strcodBra, StrServer;

    String ProductosNuevos;
    String  strscliente = "",  strscliente3 = "";
    String K87;
    String Desc1fa;
    String mensaje = "";
    String Cliente = "";
    String Nombre;
    String rfc;
    String plazo;
    String Calle;
    String Colonia;
    String Poblacion;
    String Via;
    String DescPro;
    String Desc1;
    String Comentario1;
    String Comentario2;
    String Comentario3;
    int dato = 0;
    ConexionSQLiteHelper conn;
    int datos;

    private String version;
    int  Resultado=0;



    private SharedPreferences preferenceClie;
    private SharedPreferences.Editor editor2;
    View view;

    AlertDialog mDialog;
    Context context = this.getActivity();


    ArrayList<ProductosNuevosSANDG> ListaProductosGeneral = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosEagle = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosTrackone = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosRodatech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosPartech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosShark = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosMechanic = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosGSP = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosVazlo = new ArrayList<>();

    ArrayList<BannersSANDG> ListaBanners = new ArrayList<>();
    // Urls of our images.


    SliderView sliderView;
    String url;
    String Banners;
    EditText BusquedaProducto;
    String ProductosNuevosStr,Empresa;
    LinearLayout EagleOcultar, TrackOneOcultar, RodatechOcultar, PartechOcultar, SharkOcultar,MechanicOcultar,GspOcultar,VazloOcultar;


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
        recyclerViewMechanic = view.findViewById(R.id.listProductosMechanic);
        recyclerViewGSP = view.findViewById(R.id.listProductosGSP);
        recyclerViewVazlo = view.findViewById(R.id.listProductosVazlo);


        BusquedaProducto = view.findViewById(R.id.idBusqueda);
        EagleOcultar = view.findViewById(R.id.EagleOcultar);
        TrackOneOcultar = view.findViewById(R.id.TrackoneOcultar);
        RodatechOcultar = view.findViewById(R.id.RodatechOcultar);
        PartechOcultar = view.findViewById(R.id.PartechOcultar);
        SharkOcultar = view.findViewById(R.id.SharkOcultar);
        MechanicOcultar = view.findViewById(R.id.MechanicOcultar);
        GspOcultar = view.findViewById(R.id.GSPOcultar);
        VazloOcultar = view.findViewById(R.id.VazloOcultar);

        //Preference
        SharedPreferences preference = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();

        preferenceClie = requireActivity().getSharedPreferences("clienteCompra", Context.MODE_PRIVATE);
        editor2 = preferenceClie.edit();


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
        ProductosNuevosStr= preference.getString("Productosnuevos", "0");


        sliderView = view.findViewById(R.id.image_slider);


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Empresa = "https://www.jacve.mx/imagenes/";
                url="https://www.jacve.mx/img/banners/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "autodis.ath.cx:9085":
                Empresa = "https://www.autodis.mx/es-mx/img/products/xl/";
                url="https://www.autodis.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "cecra.ath.cx:9085":
                Empresa = "https://www.cecra.mx/es-mx/img/products/xl/";
                url="https://www.cecra.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "guvi.ath.cx:9085":
                Empresa = "https://www.guvi.mx/es-mx/img/products/xl/";
                url="https://www.guvi.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "cedistabasco.ddns.net:9085":
                Empresa = "https://www.pressa.mx/es-mx/img/products/xl/";
                url="https://www.pressa.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "sprautomotive.servehttp.com:9090":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                url="https://www.spr.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "sprautomotive.servehttp.com:9095":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                url="https://www.spr.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "sprautomotive.servehttp.com:9080":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                url="https://www.spr.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "sprautomotive.servehttp.com:9085":
                Empresa = "https://www.vipla.mx/es-mx/img/products/xl/";
                url="https://www.vipla.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            case "vazlocolombia.dyndns.org:9085":
                Empresa = "https://www.pressa.mx/es-mx/img/products/xl/";
                url="https://www.colombia.mx/images/index/display/";
                Banners="https://jacve.mx/api/banners";
                break;
            default:
                Empresa = "https://www.pressa.mx/es-mx/img/products/xl/";
                Banners="https://jacve.mx/api/banners";
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

        ListaProductosMechanic = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer5 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMechanic.setLayoutManager(horizontalLayoutManagaer5);

        ListaProductosGSP = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer6 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewGSP.setLayoutManager(horizontalLayoutManagaer6);


        ListaProductosVazlo = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer7 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewVazlo.setLayoutManager(horizontalLayoutManagaer7);



        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        ConsultaComprobacion();

        Versiones task1 = new Versiones();
        task1.execute();

        if(datos>0){
            if( day==15 || day==1){
                if (ProductosNuevosStr.equals("0")) {
                    editor.putString("Productosnuevos", "1");
                    editor.apply();
                    ProductosNuevosAscy();
                } else {
                    Consulta();
                }

            }else{

                editor.putString("Productosnuevos", "0");
                editor.apply();
                Consulta();

            }


        }else{
            ProductosNuevosAscy();

        }



        BusquedaProducto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) { //Do Something } return false; } });

                    // Check if no view has focus:


                    String BusquedaProductoString;
                    BusquedaProductoString = BusquedaProducto.getText().toString();
                    Intent BusquedaProdcuto = new Intent(getActivity(), BusquedaActivity.class);
                    BusquedaProdcuto.putExtra("Producto", BusquedaProductoString);
                    startActivity(BusquedaProdcuto);



                }
                return false;
            }
        });

        switch (StrServer) {
            case "sprautomotive.servehttp.com:9090":
                EagleOcultar.setVisibility(View.GONE);
                TrackOneOcultar.setVisibility(View.GONE);
                RodatechOcultar.setVisibility(View.VISIBLE);
                PartechOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.GONE);
                VazloOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);

                break;
            case "sprautomotive.servehttp.com:9095":
                EagleOcultar.setVisibility(View.GONE);
                TrackOneOcultar.setVisibility(View.GONE);
                RodatechOcultar.setVisibility(View.GONE);
                PartechOcultar.setVisibility(View.VISIBLE);
                VazloOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);
                break;
            case "sprautomotive.servehttp.com:9080":
                EagleOcultar.setVisibility(View.GONE);
                TrackOneOcultar.setVisibility(View.GONE);
                RodatechOcultar.setVisibility(View.GONE);
                PartechOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.VISIBLE);
                VazloOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);
                break;
            case "vazlocolombia.dyndns.org:9085":
                EagleOcultar.setVisibility(View.VISIBLE);
                TrackOneOcultar.setVisibility(View.GONE);
                RodatechOcultar.setVisibility(View.GONE);
                PartechOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.GONE);
                VazloOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);
                break;
            case "autodis.ath.cx:9085":
                EagleOcultar.setVisibility(View.VISIBLE);
                TrackOneOcultar.setVisibility(View.VISIBLE);
                RodatechOcultar.setVisibility(View.GONE);
                PartechOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.GONE);
                VazloOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);
                break;
            case "jacve.dyndns.org:9085":
                EagleOcultar.setVisibility(View.GONE);
                TrackOneOcultar.setVisibility(View.GONE);
                RodatechOcultar.setVisibility(View.GONE);
                PartechOcultar.setVisibility(View.GONE);
                SharkOcultar.setVisibility(View.GONE);
                VazloOcultar.setVisibility(View.GONE);
                MechanicOcultar.setVisibility(View.VISIBLE);
                GspOcultar.setVisibility(View.VISIBLE);

                break;
            default:
                EagleOcultar.setVisibility(View.VISIBLE);
                TrackOneOcultar.setVisibility(View.VISIBLE);
                RodatechOcultar.setVisibility(View.VISIBLE);
                PartechOcultar.setVisibility(View.VISIBLE);
                SharkOcultar.setVisibility(View.VISIBLE);
                VazloOcultar.setVisibility(View.VISIBLE);
                MechanicOcultar.setVisibility(View.GONE);
                GspOcultar.setVisibility(View.GONE);

                break;
        }

        BannersAsy();


        return view;
    }


    private void ConsultaComprobacion() {

        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("SELECT COUNT(*) FROM productos", null);
        if (fila != null && fila.moveToFirst()) {
            do {
                datos=fila.getInt(0);
            } while (fila.moveToNext());
        }
        db.close();

    }
    private void BorrarCarrito() {
        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        db.delete("productos", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='productos'");
        db.close();
    }




    private void Consulta() {
        ListaProductosEagle = new ArrayList<>();
        ListaProductosRodatech = new ArrayList<>();
        ListaProductosPartech = new ArrayList<>();
        ListaProductosShark = new ArrayList<>();
        ListaProductosTrackone = new ArrayList<>();
        ListaProductosMechanic = new ArrayList<>();
        ListaProductosGSP = new ArrayList<>();
        ListaProductosVazlo = new ArrayList<>();
        conn = new ConexionSQLiteHelper(getActivity(), "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from productos ", null);
        if (fila != null && fila.moveToFirst()) {
            do {

                ListaProductosGeneral.add(new ProductosNuevosSANDG(fila.getString(1),
                        fila.getString(2),
                        fila.getString(3),
                        fila.getString(4),
                        fila.getString(5)));

                switch (fila.getString(3)) {
                    case "1":
                        ListaProductosEagle.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;
                    case "2":
                        ListaProductosRodatech.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                    case "3":
                        ListaProductosPartech.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;
                    case "4":
                        ListaProductosShark.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;
                    case "6":
                        ListaProductosTrackone.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;

                    case "8":
                    case "9":
                    case "10":
                    case "11":
                    case "12":
                        ListaProductosGSP.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;
                    case "13":
                        ListaProductosMechanic.add(new ProductosNuevosSANDG(fila.getString(1),
                                fila.getString(2),
                                fila.getString(3),
                                fila.getString(4),
                                fila.getString(5)));
                        break;
                    default:
                        break;
                }

            } while (fila.moveToNext());

            if (ListaProductosGeneral.size() > 0) {

                AdaptadorProductosNuevos adapter = new AdaptadorProductosNuevos(ListaProductosEagle, context,Empresa);
                recyclerViewEagle.setAdapter(adapter);
                AdaptadorProductostrackone adapter1 = new AdaptadorProductostrackone(ListaProductosTrackone, context,Empresa);
                recyclerViewTrackone.setAdapter(adapter1);
                AdaptadorProductosRodatech adapter2 = new AdaptadorProductosRodatech(ListaProductosRodatech, context,Empresa);
                recyclerViewRodatech.setAdapter(adapter2);
                AdaptadorProductosPartech adapter3 = new AdaptadorProductosPartech(ListaProductosPartech, context,Empresa);
                recyclerViewPartech.setAdapter(adapter3);
                AdaptadorProductosShark adapter4 = new AdaptadorProductosShark(ListaProductosShark, context,Empresa);
                recyclerViewShark.setAdapter(adapter4);
                AdaptadorProductosMechanic adapter5 = new AdaptadorProductosMechanic(ListaProductosMechanic, context,Empresa);
                recyclerViewMechanic.setAdapter(adapter5);
                AdaptadorProductosGSP adapter6 = new AdaptadorProductosGSP(ListaProductosGSP, context,Empresa);
                recyclerViewGSP.setAdapter(adapter6);



                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        int position = recyclerViewEagle.getChildAdapterPosition(Objects.requireNonNull(recyclerViewEagle.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosEagle.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);



                    }
                });

                adapter1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int position = recyclerViewTrackone.getChildAdapterPosition(Objects.requireNonNull(recyclerViewTrackone.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosTrackone.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);



                    }
                });

                adapter2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        int position = recyclerViewRodatech.getChildAdapterPosition(Objects.requireNonNull(recyclerViewRodatech.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosRodatech.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);

                    }
                });

                adapter3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int position = recyclerViewPartech.getChildAdapterPosition(Objects.requireNonNull(recyclerViewPartech.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosPartech.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);


                    }
                });

                adapter4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewShark.getChildAdapterPosition(Objects.requireNonNull(recyclerViewShark.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosShark.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);


                    }
                });

                adapter5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewMechanic.getChildAdapterPosition(Objects.requireNonNull(recyclerViewMechanic.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosMechanic.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);


                    }
                });

                adapter6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = recyclerViewGSP.getChildAdapterPosition(Objects.requireNonNull(recyclerViewGSP.findContainingItemView(view)));
                        Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                        String Producto = ListaProductosGSP.get(position).getClave();
                        ProductosDetallados.putExtra("Producto", Producto);
                        ProductosDetallados.putExtra("claveVentana", "1");
                        startActivity(ProductosDetallados);


                    }
                });

            }

        }

        db.close();

    }







    public void BannersAsy() {
        new HomeFragment.Banners().execute();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class Banners extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = Banners;
            String jsonStr = sh.makeServiceCall(url,"","");
            if (jsonStr != null) {
                try {



                    JSONObject jitems, Numero;
                    JSONArray jsonObject = new JSONArray(jsonStr);
                    int entero=jsonObject.length();
                    if(entero!=0) {
                        for (int i = 0; i < entero; i++) {
                        String Imagen = jsonObject.getJSONObject(i).getString("image");

                            ListaBanners.add(new BannersSANDG(Imagen));
                        }

                    }
                } catch (final JSONException e) {
                    String mensaje =e.getMessage().toString();
                }//catch JSON EXCEPTION
            } else {

            }//else
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
            // adding the urls inside array list
            for (int i = 0; i < ListaBanners.size(); i++) {
                sliderDataArrayList.add(new SliderData(url+"/"+ListaBanners.get(i).getNombre()));
            }


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

            mDialog.dismiss();
        }


    }






    public void ProductosNuevosAscy() {
        new HomeFragment.ProductosNuevosa().execute();
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class ProductosNuevosa extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = "http://" + StrServer + "/listapronuevosapp";
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
                            ListaProductosGeneral.add(new ProductosNuevosSANDG((Numero.getString("k_Producto").equals("") ? "" : Numero.getString("k_Producto")),
                                    (Numero.getString("k_Descripcion").equals("") ? "" : Numero.getString("k_Descripcion")),
                                    (Numero.getString("k_Tipo").equals("") ? "" : Numero.getString("k_Tipo")),
                                    (Numero.getString("k_FotosTipo").equals("") ? "" : Numero.getString("k_FotosTipo")),
                                    (Numero.getString("k_FotosLinea").equals("") ? "" : Numero.getString("k_FotosLinea"))));
                        }
                    }
                } catch (final JSONException e) {
                    String mensaje =e.getMessage().toString();
                }//catch JSON EXCEPTION
            } else {

            }//else
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
                String TipoFotos = ListaProductosGeneral.get(i).getFotoTipo();
                String LineaFotos = ListaProductosGeneral.get(i).getFotoLinea();

                db.execSQL("INSERT INTO  productos (Clave,Descripcion,Tipo,FotoTipo,FotoLinea) values ('" + Clave + "','" + Descripcion + "','" + Tipo + "','" + TipoFotos + "','" + LineaFotos + "')");
            }

            db.close();

            for (int i = 0; i < ListaProductosGeneral.size(); i++) {
                String Clave, Descripcion, Tipo,TipoFotos,LineaFotos;
                Clave = ListaProductosGeneral.get(i).getClave();
                Descripcion = ListaProductosGeneral.get(i).getDescripcion();
                Tipo = ListaProductosGeneral.get(i).getTipo();
                TipoFotos =ListaProductosGeneral.get(i).getFotoTipo();
                LineaFotos=ListaProductosGeneral.get(i).getFotoLinea();
                switch (Tipo) {
                    case "1":
                        ListaProductosEagle.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "2":
                        ListaProductosRodatech.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "3":
                        ListaProductosPartech.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "4":
                        ListaProductosShark.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "6":
                        ListaProductosTrackone.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "8":
                    case "9":
                    case "10":
                    case "11":
                    case "12":
                        ListaProductosGSP.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    case "13":
                        ListaProductosMechanic.add(new ProductosNuevosSANDG(Clave, Descripcion, Tipo,TipoFotos,LineaFotos));
                        break;
                    default:
                        break;
                }
            }


            AdaptadorProductosNuevos adapter = new AdaptadorProductosNuevos(ListaProductosEagle, context,Empresa);
            recyclerViewEagle.setAdapter(adapter);
            AdaptadorProductostrackone adapter1 = new AdaptadorProductostrackone(ListaProductosTrackone, context,Empresa);
            recyclerViewTrackone.setAdapter(adapter1);
            AdaptadorProductosRodatech adapter2 = new AdaptadorProductosRodatech(ListaProductosRodatech, context,Empresa);
            recyclerViewRodatech.setAdapter(adapter2);
            AdaptadorProductosPartech adapter3 = new AdaptadorProductosPartech(ListaProductosPartech, context,Empresa);
            recyclerViewPartech.setAdapter(adapter3);
            AdaptadorProductosShark adapter4 = new AdaptadorProductosShark(ListaProductosShark, context,Empresa);
            recyclerViewShark.setAdapter(adapter4);
            AdaptadorProductosMechanic adapter5 = new AdaptadorProductosMechanic(ListaProductosMechanic, context,Empresa);
            recyclerViewMechanic.setAdapter(adapter5);
            AdaptadorProductosGSP adapter6 = new AdaptadorProductosGSP(ListaProductosGSP, context,Empresa);
            recyclerViewGSP.setAdapter(adapter6);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int position = recyclerViewEagle.getChildAdapterPosition(Objects.requireNonNull(recyclerViewEagle.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosEagle.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);


                }
            });

            adapter1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewTrackone.getChildAdapterPosition(Objects.requireNonNull(recyclerViewTrackone.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosTrackone.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);


                }
            });

            adapter2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = recyclerViewRodatech.getChildAdapterPosition(Objects.requireNonNull(recyclerViewRodatech.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosRodatech.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);

                }
            });

            adapter3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewPartech.getChildAdapterPosition(Objects.requireNonNull(recyclerViewPartech.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosPartech.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);


                }
            });

            adapter4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewShark.getChildAdapterPosition(Objects.requireNonNull(recyclerViewShark.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosShark.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);



                }
            });

            adapter5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewMechanic.getChildAdapterPosition(Objects.requireNonNull(recyclerViewMechanic.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosMechanic.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);



                }
            });

            adapter6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = recyclerViewGSP.getChildAdapterPosition(Objects.requireNonNull(recyclerViewGSP.findContainingItemView(view)));
                    Intent ProductosDetallados = new Intent(getActivity(), DetalladoProductosActivity.class);
                    String Producto = ListaProductosGSP.get(position).getClave();
                    ProductosDetallados.putExtra("Producto", Producto);
                    startActivity(ProductosDetallados);



                }
            });


            mDialog.dismiss();

        }


    }





    @SuppressWarnings("deprecation")
    @SuppressLint("StaticFieldLeak")
    private class Versiones extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String url = "http://jacve.dyndns.org:9085/versionesapp?Clave=2";
            String jsonStr = sh.makeServiceCall(url, "WEBPETI", "W3B3P3T1");
            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    if(jsonObject.length()!=0) {
                        version = jsonObject.getString("Version");


                        Resultado = 1;
                    }
                } catch (final JSONException e) {

                }//catch JSON EXCEPTION
            } else {

            }//else
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (Resultado==1){
                if (version.equals("1.0")) {

                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                    alerta.setMessage("La versión instalada no está actualizada por favor comuníquese con su proveedor para actualizar.").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            System.exit(0);
                            getActivity().finish();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Version desactualizada");
                    titulo.show();
                }
            }else{

            }

        }

    }

}