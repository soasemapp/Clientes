package com.almacen.keplerclientesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_CARRITO = "CREATE TABLE carrito (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Cliente VARCHAR (20)," +
            "Parte VARCHAR (20)," +
            "Existencia VARCHAR (10)," +
            "Cantidad VARCHAR (10)," +
            "Unidad VARCHAR (10)," +
            "Precio VARCHAR (20)," +
            "Desc1 VARCHAR (10)," +
            "Desc2 VARCHAR (10)," +
            "Desc3 VARCHAR (10), " +
            "Monto VARCHAR (20)," +
            "Descri VARCHAR (50)," +
            "FotosTipo VARCHAR (50),"+
            "FotosLinea VARCHAR (50))";

    final String CREAR_TABLA_PRODUCTOS = "CREATE TABLE productos (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Clave VARCHAR (20)," +
            "Descripcion VARCHAR (50)," +
            "Tipo VARCHAR (20)," +
            "FotoTipo VARCHAR (20)," +
            "FotoLinea VARCHAR (20))";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_CARRITO);
        db.execSQL(CREAR_TABLA_PRODUCTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS carrito");
        db.execSQL("DROP TABLE IF EXISTS productos");
        onCreate(db);
    }
}
