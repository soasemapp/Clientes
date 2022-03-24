package com.almacen.keplerclientesapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.almacen.keplerclientesapp.R;
import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {

    GifImageView imagegf;
    ImageView imgVi;
    String StrServer;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        imgVi = findViewById(R.id.imageSplash);
        imagegf = findViewById(R.id.gifImageView);
        StrServer = preference.getString("Servidor", "null");


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.jacve)
                        .error(R.drawable.mycedis)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "sprautomotive.servehttp.com:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.vipla)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "cecra.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.cecra)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "guvi.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.guvi)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "cedistabasco.ddns.net:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.pressa)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "autodis.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.autodis)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9075":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.spr_logotipo_bordes)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;

            default:
                Picasso.with(getApplicationContext()).
                        load(R.drawable.spr_logotipo_bordes)
                        .error(R.drawable.mycedis3)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent ScreenFir = new Intent(SplashActivity.this, inicioActivity.class);
                overridePendingTransition(0, 0);
                startActivity(ScreenFir);
                overridePendingTransition(0, 0);
                finish();

            }
        }, 5000);
    }
}
