package com.almacen.keplerclientesapp.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.almacen.keplerclientesapp.ConexionSQLiteHelper;
import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.activity.Carrito.CarritoComprasActivity;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class inicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    String strusr, strpass, strname, strlname, strtype , strbran, strma, strco, strcodBra, StrServer;
    String Empresa;
    NavigationView navigationView;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.activityConsulPedi2)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mensaje = new AlertDialog.Builder(this);
        mensaje.setTitle("¿Desea salir de la aplicacion?").setIcon(R.drawable.icons8_fire_exit_100);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        if(StrServer.equals("sprautomotive.servehttp.com:9090")){
            MenuItem item = menu.findItem(R.id.MenuSPR);
            MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
            MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
            MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
            itemRod.setVisible(false);
            itemPartech.setVisible(true);
            itemSharck.setVisible(true);
            item.setVisible(true);
        }else if(StrServer.equals("sprautomotive.servehttp.com:9095")){
            MenuItem item = menu.findItem(R.id.MenuSPR);

            MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
            MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
            MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
            itemRod.setVisible(true);
            itemPartech.setVisible(false);
            itemSharck.setVisible(true);

            item.setVisible(true);
        }else if(StrServer.equals("sprautomotive.servehttp.com:9080")){
            MenuItem item = menu.findItem(R.id.MenuSPR);


            MenuItem itemRod = menu.findItem(R.id.RodatechMenu);
            MenuItem itemPartech = menu.findItem(R.id.PartechMenu);
            MenuItem itemSharck = menu.findItem(R.id.SharkMenu);
            itemRod.setVisible(true);
            itemPartech.setVisible(true);
            itemSharck.setVisible(false);


            item.setVisible(true);
        }else{
            MenuItem item = menu.findItem(R.id.MenuSPR);
            item.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (id == R.id.cerrarSe) {
                editor.clear().commit();
                editor.apply();
                BorrarCarrito();
                BorrarProductos();
                Intent cerrar = new Intent(this, MainActivity.class);
                startActivity(cerrar);
                System.exit(0);
                finish();
            }else if (id == R.id.CarrComp){
                Intent Shoping = new Intent(this, CarritoComprasActivity.class);
                 startActivity(Shoping);

            }else if (id == R.id.RodatechMenu){
                StrServer = "sprautomotive.servehttp.com:9090";
                editor.putString("Server", StrServer);
                editor.commit();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                finish();
            }else if (id == R.id.PartechMenu){
                StrServer = "sprautomotive.servehttp.com:9095";
                editor.putString("Server", StrServer);
                editor.commit();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                finish();
            }else if (id == R.id.SharkMenu){
                StrServer = "sprautomotive.servehttp.com:9080";
                editor.putString("Server", StrServer);
                editor.commit();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                finish();
            }




        return super.onOptionsItemSelected(item);
    }

    private void BorrarCarrito() {
        conn = new ConexionSQLiteHelper(inicioActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        db.delete("carrito", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='carrito'");
        db.close();
    }
    private void BorrarProductos() {
        conn = new ConexionSQLiteHelper(inicioActivity.this, "bd_Carrito", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        db.delete("productos", null, null);
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='productos'");
        db.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private void loadData() {


        final View vistaHeader = navigationView.getHeaderView(0);
        final ImageView imageViewdrawer = vistaHeader.findViewById(R.id.imageViewdrawer);
        final TextView tvEmpresa = vistaHeader.findViewById(R.id.idEmpresa),
                tvNombre = vistaHeader.findViewById(R.id.idnombre);

        if (StrServer.equals("jacve.dyndns.org:9085")) {
            Empresa = strbran;
            Picasso.with(getApplicationContext()).load(R.drawable.jacve)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);

        } else if (StrServer.equals("autodis.ath.cx:9085")) {
            Empresa = strbran;

            Picasso.with(getApplicationContext()).load(R.drawable.autodis)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);

        }else if (StrServer.equals("cecra.ath.cx:9085")){
            Empresa = strbran;
            Picasso.with(getApplicationContext()).load(R.drawable.cecra)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
        }else if (StrServer.equals("guvi.ath.cx:9085")) {
            Empresa = strbran;
            Picasso.with(getApplicationContext()).load(R.drawable.guvi)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
        }else if (StrServer.equals("sprautomotive.servehttp.com:9085")) {
            Empresa = strbran;
            Picasso.with(getApplicationContext()).load(R.drawable.vipla)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
        }else if (StrServer.equals("cedistabasco.ddns.net:9085")) {
            Empresa = strbran;
            Picasso.with(getApplicationContext()).load(R.drawable.pressa)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
        }else if (StrServer.equals("sprautomotive.servehttp.com:9090")) {
            Picasso.with(getApplicationContext()).load(R.drawable.sprimage)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
            Empresa = strbran;

        }else if (StrServer.equals("sprautomotive.servehttp.com:9095")) {
            Picasso.with(getApplicationContext()).load(R.drawable.sprimage)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
            Empresa = strbran;
        }else if (StrServer.equals("sprautomotive.servehttp.com:9080")) {
            Picasso.with(getApplicationContext()).load(R.drawable.sprimage)
                    .error(R.drawable.ic_baseline_error_24)
                    .fit()
                    .centerInside()
                    .into(imageViewdrawer);
            Empresa = strbran;
        }
        tvEmpresa.setText(Empresa);
        tvNombre.setText(strname + " " + strlname);











    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}