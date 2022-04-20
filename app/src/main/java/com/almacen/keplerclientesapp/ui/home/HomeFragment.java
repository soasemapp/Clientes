package com.almacen.keplerclientesapp.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.BusquedaActivity;
import com.almacen.keplerclientesapp.DetalladoProductosActivity;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.SetGetListMarca;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos2;
import com.almacen.keplerclientesapp.SetterandGetter.ListLineaSANDG;
import com.almacen.keplerclientesapp.SetterandGetter.ProductosNuevosSANDG;
import com.almacen.keplerclientesapp.SliderAdapter;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueGeneral;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueProductos;
import com.almacen.keplerclientesapp.XMLS.xmlListLine;
import com.almacen.keplerclientesapp.XMLS.xmlListMarca;
import com.almacen.keplerclientesapp.XMLS.xmlListModelo;
import com.almacen.keplerclientesapp.XMLS.xmlProductoConsulta;
import com.almacen.keplerclientesapp.XMLS.xmlProductosNuevos;
import com.almacen.keplerclientesapp.activity.MainActivity;
import com.almacen.keplerclientesapp.activity.Pedidos.ActivityConsulCoti;
import com.almacen.keplerclientesapp.activity.Pedidos.ActivityConsulPedi;
import com.almacen.keplerclientesapp.activity.Pedidos.ActivityDetallPedi;
import com.almacen.keplerclientesapp.adapter.AdaptadorConsulCoti;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosNuevos;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosPartech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosRodatech;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductosShark;
import com.almacen.keplerclientesapp.adapter.AdaptadorProductostrackone;
import com.almacen.keplerclientesapp.adapter.AdapterSearchProduct;
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

public class HomeFragment extends Fragment {
    SliderView sliderView;
    RecyclerView recyclerViewEagle,recyclerViewTrackone,recyclerViewRodatech,recyclerViewPartech,recyclerViewShark;

    int[] images = {
            R.drawable.image2,
            R.drawable.image4,
            R.drawable.display_mx};
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    View view;

    AlertDialog mDialog;
    Context context = this.getActivity();

    ArrayList<ProductosNuevosSANDG> ListaProductosGeneral = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosEagle = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosTrackone = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosRodatech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosPartech = new ArrayList<>();
    ArrayList<ProductosNuevosSANDG> ListaProductosShark = new ArrayList<>();


    EditText BusquedaProducto;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Espere un momento...").build();


        //FindView
        recyclerViewEagle=view.findViewById(R.id.listProductosEagle);
        recyclerViewTrackone=view.findViewById(R.id.listProductTrackone);
        recyclerViewRodatech=view.findViewById(R.id.listProductosRodatech);
        recyclerViewPartech=view.findViewById(R.id.listProductosPartech);
        recyclerViewShark=view.findViewById(R.id.listProductosShark);
        BusquedaProducto =view.findViewById(R.id.idBusqueda);

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







        //SlideView
        sliderView = view.findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        ListaProductosEagle = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEagle .setLayoutManager(horizontalLayoutManagaer);

        ListaProductosTrackone = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTrackone .setLayoutManager(horizontalLayoutManagaer1);

        ListaProductosRodatech = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRodatech .setLayoutManager(horizontalLayoutManagaer2);

        ListaProductosPartech = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPartech .setLayoutManager(horizontalLayoutManagaer3);

        ListaProductosShark = new ArrayList<>();
        LinearLayoutManager horizontalLayoutManagaer4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewShark .setLayoutManager(horizontalLayoutManagaer4);


        HomeFragment.ProductosNuevos task = new HomeFragment.ProductosNuevos();
        task.execute();



        BusquedaProducto.setImeOptions(3);

        BusquedaProducto.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Check if no view has focus:
                view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String BusquedaProductoString="";
                BusquedaProductoString=BusquedaProducto.getText().toString();
                Intent BusquedaProdcuto = new Intent(getActivity(), BusquedaActivity.class);
                BusquedaProdcuto.putExtra("Producto", BusquedaProductoString);
                startActivity(BusquedaProdcuto);


                return false;
            }
        });



        return view;
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


            for (int i = 0; i < ListaProductosGeneral.size(); i++) {
                String Clave,Descripcion,Tipo;

                Clave=ListaProductosGeneral.get(i).getClave();
                Descripcion=ListaProductosGeneral.get(i).getDescripcion();
                Tipo=ListaProductosGeneral.get(i).getTipo();


                switch (Tipo){
                case"1":
                    ListaProductosEagle.add(new ProductosNuevosSANDG(Clave,Descripcion,Tipo));
                    break;
                case "2":
                    ListaProductosRodatech.add(new ProductosNuevosSANDG(Clave,Descripcion,Tipo));
                    break;
                case "3":
                    ListaProductosPartech.add(new ProductosNuevosSANDG(Clave,Descripcion,Tipo));
                    break;
                case "4":
                    ListaProductosShark.add(new ProductosNuevosSANDG(Clave,Descripcion,Tipo));
                    break;
                case "6":
                    ListaProductosTrackone.add(new ProductosNuevosSANDG(Clave,Descripcion,Tipo));
                    break;
                default:
                    break;
                }
            }

            AdaptadorProductosNuevos adapter = new AdaptadorProductosNuevos(ListaProductosEagle,context);
            recyclerViewEagle.setAdapter(adapter);
            AdaptadorProductostrackone adapter1 = new AdaptadorProductostrackone(ListaProductosTrackone,context);
            recyclerViewTrackone.setAdapter(adapter1);
            AdaptadorProductosRodatech adapter2 = new AdaptadorProductosRodatech(ListaProductosRodatech,context);
            recyclerViewRodatech.setAdapter(adapter2);
            AdaptadorProductosPartech adapter3 = new AdaptadorProductosPartech(ListaProductosPartech,context);
            recyclerViewPartech.setAdapter(adapter3);
            AdaptadorProductosShark adapter4 = new AdaptadorProductosShark(ListaProductosShark,context);
            recyclerViewShark.setAdapter(adapter4);

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