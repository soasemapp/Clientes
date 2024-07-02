package com.almacen.keplerclientesapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ProductosNuevosSANDG;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorProductosNuevos extends RecyclerView.Adapter<AdaptadorProductosNuevos.ViewHolderProductosNuevos> implements View.OnClickListener {

    ArrayList<ProductosNuevosSANDG> listaproductos;
    Context context;
    private View.OnClickListener listener;
    String Empresa;
    String EmpresaNuevaa="";


    public AdaptadorProductosNuevos(ArrayList<ProductosNuevosSANDG> listaProductosEagle, Context context,String empresa) {
        this.context=context;
        this.listaproductos=listaProductosEagle;
        this.Empresa=empresa;
        this.EmpresaNuevaa=empresa;
    }


    @NonNull
    @Override
    public ViewHolderProductosNuevos onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productosnuevoseagle, null, false);
        view.setOnClickListener(this);
        return new ViewHolderProductosNuevos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductosNuevos holder, int position) {

        if (!EmpresaNuevaa.equals("https://vazlo.com.mx/assets/img/productos/chica/jpg/")){
            Empresa="";
            Empresa=EmpresaNuevaa+listaproductos.get(position).getClave()+"/4.webp";
                 }else{
            Empresa="";
            Empresa=EmpresaNuevaa+listaproductos.get(position).getClave()+".jpg";

        }

        holder.Parte.setText(listaproductos.get(position).getClave());
        Picasso.with(context).
                load(Empresa)
                .error(R.drawable.noimage)
                .placeholder(R.drawable.loadingpro)
                .fit()
                .centerInside()
                .into(holder.imgPro);

    }



    @Override
    public int getItemCount() {
        return listaproductos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);

        }
    }

    public static class ViewHolderProductosNuevos extends RecyclerView.ViewHolder {
        TextView Parte;
        ImageView imgPro;


        public ViewHolderProductosNuevos(View itemView) {
            super(itemView);
            imgPro =  itemView.findViewById(R.id.productoImag);
            Parte =  itemView.findViewById(R.id.Parte);
             }
    }
}
