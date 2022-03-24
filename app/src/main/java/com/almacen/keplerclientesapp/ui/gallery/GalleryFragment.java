package com.almacen.keplerclientesapp.ui.gallery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.almacen.keplerclientesapp.XMLS.xmlBusqueProductos;
import com.almacen.keplerclientesapp.XMLS.xmlUsuarioConsulta;
import com.almacen.keplerclientesapp.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class GalleryFragment extends Fragment {

    View view;
    ImageView imageView;
    EditText idcliente, iddireccion, idtelefono, idcorreo;
    ImageView imgVi;
    String nombre;
    String direccion;
    String telefono;
    String correo;
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    AlertDialog mDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        imageView = (ImageView) view.findViewById(R.id.imageview);

        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Un momento...").build();


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
        StrServer = preference.getString("Servidor", "null");


        imgVi = view.findViewById(R.id.imageview);
        idcliente = view.findViewById(R.id.idcliente);
        iddireccion = view.findViewById(R.id.iddireccion);
        idtelefono = view.findViewById(R.id.idTelefono);
        idcorreo = view.findViewById(R.id.idCorreo);


        GalleryFragment.Perfil task = new GalleryFragment.Perfil();
        task.execute();


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.jacve)
                        .error(R.drawable.mycedis)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "sprautomotive.servehttp.com:9085":
                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.vipla)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "cecra.ath.cx:9085":

                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.cecra)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "guvi.ath.cx:9085":

                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.guvi)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "cedistabasco.ddns.net:9085":

                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.pressa)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "autodis.ath.cx:9085":

                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.autodis)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9075":

                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.spr_logotipo_bordes)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;

            default:
                Picasso.with(getActivity().getApplicationContext()).
                        load(R.drawable.spr_logotipo_bordes)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
        }


        return view;
    }


    private class Perfil extends AsyncTask<Void, Void, Void> {

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

            idcliente.setText(nombre);
            iddireccion.setText(direccion);
            idtelefono.setText(telefono);
            idcorreo.setText(correo);
            mDialog.dismiss();
        }

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
            nombre = response0.getPropertyAsString("nombre");
            direccion = response0.getPropertyAsString("direccion");
            telefono = response0.getPropertyAsString("telefono");
            correo = response0.getPropertyAsString("correo");
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