package com.almacen.keplerclientesapp.includes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.almacen.keplerclientesapp.R;


public class MyToolbar {

    public static void show(AppCompatActivity activity, String title, boolean upButton) {

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
