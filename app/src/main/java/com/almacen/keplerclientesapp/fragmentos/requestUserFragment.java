package com.almacen.keplerclientesapp.fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConsulCotiSANDG;
import com.almacen.keplerclientesapp.XMLS.xmlConsulCoti;
import com.almacen.keplerclientesapp.XMLS.xmlRegisterUser;
import com.almacen.keplerclientesapp.activity.Pedidos.ActivityConsulCoti;
import com.almacen.keplerclientesapp.adapter.AdaptadorConsulCoti;
import com.google.android.material.textfield.TextInputLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URLEncoder;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link requestUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class requestUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button  Register;
    EditText edNombre,edApellido,edPuesto,edRFC,edTelefono,edCorreo,edContraseña;
    TextInputLayout txtNombre,txtApellido,txtPuesto,txtRFC,txtTelefono,txtCorreo,txtContrase;
    String  strNombre="",strApellido="",strPuesto="",strRFC="",strTelefono="",strCorreo="",strContra="";
    View view;
    AlertDialog mDialog;

    public requestUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment requestUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static requestUserFragment newInstance(String param1, String param2) {
        requestUserFragment fragment = new requestUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    String strEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Espere un momento...").build();


        int Empresa = 0;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_request_user, container, false);

        Bundle datosRecuperados = getArguments();

        Register = view.findViewById(R.id.Register);
        edNombre = view.findViewById(R.id.edNombre);
        edApellido = view.findViewById(R.id.edApellido);
        edPuesto = view.findViewById(R.id.edPuesto);
        edRFC = view.findViewById(R.id.edRfc);
        edTelefono = view.findViewById(R.id.edTelefono);
        edCorreo = view.findViewById(R.id.edCorreo);
        edContraseña = view.findViewById(R.id.edPassword);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtApellido=view.findViewById(R.id.txtApellido);
        txtPuesto = view.findViewById(R.id.txtPuesto);
        txtRFC = view.findViewById(R.id.txtRFC);
        txtTelefono = view.findViewById(R.id.txtTelefono);
        txtCorreo=view.findViewById(R.id.txtCorreo);
        txtContrase = view.findViewById(R.id.txtContra);


        edContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String Contraseña="";

            Contraseña =edContraseña.getText().toString();

            if(Contraseña.length()<6){
                txtContrase.setError("Contraseña muy corta");
            }else if(Contraseña.length()>6 && Contraseña.length()<20){
                txtContrase.setErrorEnabled(false);
            }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                strNombre=edNombre.getText().toString();
                strApellido=edApellido.getText().toString();
                strPuesto=edPuesto.getText().toString();
                strRFC =edRFC.getText().toString();
                strTelefono  =edTelefono.getText().toString();
                strCorreo =edCorreo.getText().toString();
                strContra=edContraseña.getText().toString();
                if (!strNombre.equals("") ){
                    if (!strApellido.equals("")){
                        if (!strPuesto.equals("")){
                            if (!strRFC.equals("")){
                                if (!strTelefono.equals("")){
                                    if (!strCorreo.equals("")){
                                        if (!strContra.equals("")){

                                            requestUserFragment.AsyncCallWS task = new requestUserFragment.AsyncCallWS();
                                            task.execute();

                                        }else{
                                            txtContrase.setError("Ingrese una Contraseña");
                                        }
                                    }else{
                                        txtCorreo.setError("Ingrese el Correo");
                                    }
                                }else{
                                    txtTelefono.setError("Ingrese el Telefono");
                                }
                            }else{
                                txtRFC.setError("Ingrese el RFC  de Facturacion");
                            }
                        }else{
                            txtPuesto.setError("Ingrese un puesto");
                        }
                    }else{
                        txtApellido.setError("Ingrese un Apellido");
                    }
                }else{
                    txtNombre.setError("Ingrese el nombre");
                }




               /* PackageManager packageManager = getActivity().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);*/

                /*
                try {
                    String url = "https://api.whatsapp.com/send?phone=+524931020551&text=Hola yanni";
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        getActivity().startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        });


        if (datosRecuperados == null) {
            // No hay datos, manejar excepción

        }else{
            // Y ahora puedes recuperar usando get en lugar de put

             Empresa = datosRecuperados.getInt("Empresa");

        }

        if(Empresa==0){
        strEmpresa =  "jacve.dyndns.org:9085";
        }else if(Empresa==1){
            strEmpresa =  "autodis.ath.cx:9085";
        }else if(Empresa==2){
            strEmpresa =  "cecra.ath.cx:9085";
        }else if(Empresa==3){
            strEmpresa =  "guvi.ath.cx:9085";
        }else if(Empresa==4){
            strEmpresa =  "sprautomotive.servehttp.com:9085";
        }else if(Empresa==5){
            strEmpresa =  "cedistabasco.ddns.net:9085";
        }else if(Empresa==6){
            strEmpresa =  "sprautomotive.servehttp.com:9075";
        }

        return view;
    }
    Intent intent;



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


        }
    }


    private void conecta() {
        String SOAP_ACTION = "RegisterUserApp";
        String METHOD_NAME = "RegisterUserApp";
        String NAMESPACE = "http://" + strEmpresa + "/WSk75ClientesSOAP/";
        String URL = "http://" + strEmpresa + "/WSk75ClientesSOAP";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlRegisterUser soapEnvelope = new xmlRegisterUser(SoapEnvelope.VER11);
            soapEnvelope.xmlRegisterUser("BOTALEXA", "1454L3X4",strNombre,strApellido,strPuesto,strRFC,strTelefono,strCorreo,strContra);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;

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




    private void openWhatsApp() {

        String mPhoneNumber = "+5214931488937";
        mPhoneNumber = mPhoneNumber.replace("+", "").replace(" ", "");
        String mMessage = "";
        String mSendToWhatsApp = "https://wa.me/" + mPhoneNumber + "?text="+mMessage;
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(
                        mSendToWhatsApp
                )));

    }
}