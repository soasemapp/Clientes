package com.almacen.keplerclientesapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.almacen.keplerclientesapp.fragmentos.loginFragment;
import com.almacen.keplerclientesapp.fragmentos.requestUserFragment;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.fragmentos.Fragment_SelectEmpresas;
import com.almacen.keplerclientesapp.includes.MyToolbar;

public class LoginActivity extends AppCompatActivity {

    int ban;
    Fragment framentLogin, fragmentReUser,fragmenSelect;
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();


        MyToolbar.show(this," ",true);

        ban = getIntent().getIntExtra("val", 0);
        framentLogin = new loginFragment();
        fragmentReUser = new requestUserFragment();
        fragmenSelect = new Fragment_SelectEmpresas();




        if (ban == 0) {

            if (preference.contains("Servidor")) {
                 //Crear bundle, que son los datos que pasaremos
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contenedorFragmentos, framentLogin);
                // Terminar transición y nos vemos en el fragmento de destino
                fragmentTransaction.commit();
            }else{
                //Crear bundle, que son los datos que pasaremos
                Bundle datosAEnviar = new Bundle();
                // Aquí pon todos los datos que quieras en formato clave, valor
                datosAEnviar.putInt("Pantalla", 0);
                //Crear bundle, que son los datos que pasaremos
               fragmenSelect.setArguments(datosAEnviar);
                FragmentManager fragmentManager = this.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.contenedorFragmentos, fragmenSelect);
                // Terminar transición y nos vemos en el fragmento de destino
                fragmentTransaction.commit();



            }
        }else if(ban == 1){
            //Crear bundle, que son los datos que pasaremos
            Bundle datosAEnviar = new Bundle();
            // Aquí pon todos los datos que quieras en formato clave, valor
            datosAEnviar.putInt("Pantalla", 1);
            //Crear bundle, que son los datos que pasaremos
            fragmenSelect.setArguments(datosAEnviar);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.contenedorFragmentos, fragmenSelect);
            // Terminar transición y nos vemos en el fragmento de destino
            fragmentTransaction.commit();

        }

    }
}