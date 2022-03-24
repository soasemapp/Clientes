package com.almacen.keplerclientesapp.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.Login;
import com.almacen.keplerclientesapp.activity.SplashActivity;
import com.almacen.keplerclientesapp.XMLS.xmlLogin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SoapObject response;

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    AlertDialog mDialog;
    View view;
    TextView txt_forgetCon, txt_haveCon , selectempre;
    EditText editUsuario, editContraseña;
    Button btnEntrar;
    String strUsuario, strContraseña;
    String strEmpresa;
    int result1=0;
    String mensaje;
    Login loginSave = new Login();
    Fragment framentLogin, fragmentReUser, fragmenSelect;
    public loginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDialog = new SpotsDialog.Builder().setContext(getActivity()).setMessage("Espere un momento...").build();

        int Empresa = 0;
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        preference = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        txt_forgetCon = view.findViewById(R.id.forgetPass);
        txt_haveCon = view.findViewById(R.id.haveAccont);
        selectempre = view.findViewById(R.id.SelectEmpre);
        editUsuario = view.findViewById(R.id.ediUser);
        editContraseña = view.findViewById(R.id.editContraseña);
        btnEntrar = view.findViewById(R.id.btnEntrar);


        framentLogin = new loginFragment();
        fragmentReUser = new requestUserFragment();
        fragmenSelect = new Fragment_SelectEmpresas();

        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            // No hay datos, manejar excepción
        }else{
            // Y ahora puedes recuperar usando get en lugar de put
            if ( Empresa !=datosRecuperados.getInt("Empresa")){
                Empresa = datosRecuperados.getInt("Empresa");
            editor.remove("Servidor");
            editor.commit();
            }


        }

        if (preference.contains("Servidor")) {
            strEmpresa = preference.getString("Servidor", null);

        }else{
            if(Empresa==0){
                strEmpresa =  "jacve.dyndns.org:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==1){
                strEmpresa =  "autodis.ath.cx:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==2){
                strEmpresa =  "cecra.ath.cx:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==3){
                strEmpresa =  "guvi.ath.cx:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==4){
                strEmpresa =  "sprautomotive.servehttp.com:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==5){
                strEmpresa =  "cedistabasco.ddns.net:9085";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }else if(Empresa==6){
                strEmpresa =  "sprautomotive.servehttp.com:9075";
                editor.putString("Servidor", strEmpresa);
                editor.commit();
            }
        }





        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strUsuario = editUsuario.getText().toString();
                strContraseña = editContraseña.getText().toString();

                if (!strUsuario.equals("") && !strContraseña.equals("")) {



                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                    alerta.setMessage("Falta Ingresar Usuario o Contraseña").setIcon(R.drawable.icons8_error_52).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Faltan Datos");
                    titulo.show();
                }


            }
        });

        txt_forgetCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txt_haveCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        selectempre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
                // Aquí pon todos los datos que quieras en formato clave, valor
                datosAEnviar.putInt("Pantalla", 0);
                //Crear bundle, que son los datos que pasaremos
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragmentos, fragmenSelect);
                fragmentTransaction.addToBackStack(null);
                // Terminar transición y nos vemos en el fragmento de destino
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... params) {
            Conectar();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (result1 == 0) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                alerta.setMessage("Revise que esten correctos sus datos").setIcon(R.drawable.icons8_error_52).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Error");
                titulo.show();
            } else if (result1 == 1 && loginSave.getType().equals("CLIENTE")) {
                trasactiv();

            }

        }

    }

    private void guardarDatos() {
        editor.putString("user", strUsuario);
        editor.putString("pass", strContraseña);
        editor.putString("name", loginSave.getName());
        editor.putString("lname", loginSave.getLastname());
        editor.putString("type", loginSave.getType());
        editor.putString("branch", loginSave.getBranch());
        editor.putString("email", loginSave.getEmail());
        editor.putString("code", loginSave.getCode());
        editor.putString("codBra", loginSave.getCodeBranch());
        editor.putString("Servidor", strEmpresa);
        editor.putString("type2",null);
        //editor.putString("tokenId",token);
        editor.commit();
    }

    private void trasactiv() {
        guardarDatos();
        Intent perfil = new Intent(getActivity(), SplashActivity.class);
        startActivity(perfil);
        getActivity().finish();


    }


    private void Conectar() {

        String SOAP_ACTION = "login";
        String METHOD_NAME = "login";
        String NAMESPACE = "http://" + strEmpresa + "/WSlogin/";
        String URL = "http://" + strEmpresa + "/WSlogin";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlLogin soapEnvelope = new xmlLogin(SoapEnvelope.VER11);
            soapEnvelope.valoresLogin(strUsuario, strContraseña);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.call(SOAP_ACTION, soapEnvelope);
            Vector response0 = (Vector) soapEnvelope.getResponse();
            result1 = Integer.parseInt(response0.get(0).toString());

            if (response0 == null) {
                mensaje = "Null";

            } else {

                if (result1 == 0) {
                    mensaje = "Contraseña y/o Usuario Inconrrecto";
                } else if (result1 == 1) {
                    mensaje = "Correcto";
                    response = (SoapObject) soapEnvelope.bodyIn;
                    response = (SoapObject) response.getProperty("UserInfo");
                    loginSave = new Login();
                    loginSave.setUsers(response.getPropertyAsString("k_usr"));
                    loginSave.setName(response.getPropertyAsString("k_name"));
                    loginSave.setLastname(response.getPropertyAsString("k_lname"));
                    loginSave.setType(response.getPropertyAsString("k_type"));
                    loginSave.setBranch(response.getPropertyAsString("k_dscr"));
                    loginSave.setEmail(response.getPropertyAsString("k_mail1"));
                    loginSave.setCode(response.getPropertyAsString("k_kcode"));
                    loginSave.setCodeBranch(response.getPropertyAsString("k_codB"));

                }

            }


        } catch (SoapFault soapFault) {
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mensaje =e.getMessage()+ "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mensaje = "Error:" + ex.getMessage();
        }

    }



}





