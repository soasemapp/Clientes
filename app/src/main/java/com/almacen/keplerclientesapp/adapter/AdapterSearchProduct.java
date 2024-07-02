package com.almacen.keplerclientesapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSearchProduct extends RecyclerView.Adapter<AdapterSearchProduct.ViewHolderProdcuto> implements View.OnClickListener {


    ArrayList<SetGetListProductos> listProductos;
    private View.OnClickListener listener;
    Context context;
    String Empresa;
    String EmpresaNuevaa="";
    public AdapterSearchProduct(ArrayList<SetGetListProductos> listProductos, Context context,String empresa) {
        this.listProductos = listProductos;
        this.context = context;
        this.Empresa = empresa;
        this.EmpresaNuevaa=empresa;
    }

    @NonNull
    @Override
    public ViewHolderProdcuto onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, null, false);
        view.setOnClickListener(this);
        return new ViewHolderProdcuto(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderProdcuto holder, int position) {
        holder.Clave.setText(listProductos.get(position).getProductos());
        holder.Descripcion.setText(listProductos.get(position).getDescripcion());
        holder.Linea.setText(listProductos.get(position).getLinea());
        holder.precio.setText((Double.parseDouble(listProductos.get(position).getPrecioAjuste()) == 0 ?
                (Double.parseDouble(listProductos.get(position).getPrecioBase()) == 0 ? Html.fromHtml("<font color = #E81414>No disponible</font>") : "$" + Html.fromHtml("<font color = #48E305>$" + formatNumberCurrency(listProductos.get(position).getPrecioBase()) + "</font>"))
                : (Double.parseDouble(listProductos.get(position).getPrecioAjuste()) == 0 ? Html.fromHtml("<font color = #E81414>No disponible</font>") : Html.fromHtml("<font color = #48E305>$" + formatNumberCurrency(listProductos.get(position).getPrecioAjuste()) + "</font>"))));




        if (!EmpresaNuevaa.equals("https://vazlo.com.mx/assets/img/productos/chica/jpg/")){
            Empresa="";
            Empresa=EmpresaNuevaa+listProductos.get(position).getProductos()+"/4.webp";
        }else{
            Empresa="";
            Empresa=EmpresaNuevaa+listProductos.get(position).getProductos()+".jpg";

        }

        Picasso.with(context).
                load(Empresa)
                .error(R.drawable.noimage)
                .placeholder(R.drawable.loadingpro)
                .fit()
                .centerInside()
                .into(holder.prodocuImag);

    }


    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listProductos.size();
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

    public static class ViewHolderProdcuto extends RecyclerView.ViewHolder {
        TextView Clave, Descripcion,Linea, precio;
        ImageView prodocuImag;

        public ViewHolderProdcuto(View itemView) {
            super(itemView);
            Clave =  itemView.findViewById(R.id.PartClave);
            Descripcion =  itemView.findViewById(R.id.Descr);
            Linea =itemView.findViewById(R.id.Linea);
            precio =  itemView.findViewById(R.id.Precio);
            prodocuImag =  itemView.findViewById(R.id.productoImag);
        }
    }
}