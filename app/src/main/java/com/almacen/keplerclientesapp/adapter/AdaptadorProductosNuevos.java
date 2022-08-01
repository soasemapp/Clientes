package com.almacen.keplerclientesapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.CarritoBD;
import com.almacen.keplerclientesapp.SetterandGetter.ProductosNuevosSANDG;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorProductosNuevos extends RecyclerView.Adapter<AdaptadorProductosNuevos.ViewHolderProductosNuevos> implements View.OnClickListener {

    ArrayList<ProductosNuevosSANDG> listaproductos;
    Context context;
    private View.OnClickListener listener;

    public AdaptadorProductosNuevos(ArrayList<ProductosNuevosSANDG> listaConsulCoti, Context context) {
        this.listaproductos = listaConsulCoti;
        this.context = context;
    }

    @Override
    public ViewHolderProductosNuevos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productosnuevoseagle, null, false);
        view.setOnClickListener(this);
        return new ViewHolderProductosNuevos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderProductosNuevos holder, int position) {
        holder.Parte.setText(listaproductos.get(position).getClave());
        Picasso.with(context).
                load("https://www.pressa.mx/es-mx/img/products/xl/"+listaproductos.get(position).getClave()+"/4.webp")
                .error(R.drawable.ic_baseline_error_24)
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

    public class ViewHolderProductosNuevos extends RecyclerView.ViewHolder {
        TextView Parte;
        ImageView imgPro;


        public ViewHolderProductosNuevos(View itemView) {
            super(itemView);
            imgPro = (ImageView) itemView.findViewById(R.id.productoImag);
            Parte = (TextView) itemView.findViewById(R.id.Parte);
             }
    }
}
