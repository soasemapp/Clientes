package com.almacen.keplerclientesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.almacen.keplerclientesapp.R;

public class MainActivity extends AppCompatActivity  {


    Button btnLogin,btnRequestUaser;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        btnLogin = findViewById(R.id.btnLogin);
        btnRequestUaser = findViewById(R.id.btnReUser);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Login = new Intent(MainActivity.this, LoginActivity.class);
                Login.putExtra("val", 0);
                startActivity(Login);
            }
        });



        btnRequestUaser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Login = new Intent(MainActivity.this, LoginActivity.class);
                Login.putExtra("val", 1);
                startActivity(Login);

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (preference.contains("user") && preference.contains("pass")) {

            Toast.makeText(this, "Entraste", Toast.LENGTH_SHORT).show();
            Intent perfil = new Intent(this, SplashActivity.class);
            startActivity(perfil);
            finish();
        }

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder mensaje=new AlertDialog.Builder(this);
        mensaje.setTitle("¿Desea salir de la aplicacion?").setIcon(R.drawable.icons8_exit_64);
        mensaje.setCancelable(false);
        mensaje.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);

            }
        });
        mensaje.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mensaje.show();
    }



}