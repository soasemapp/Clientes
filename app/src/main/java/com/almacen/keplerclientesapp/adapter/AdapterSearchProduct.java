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
import com.almacen.keplerclientesapp.SetandGet.SetGetListProductos;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterSearchProduct extends RecyclerView.Adapter<AdapterSearchProduct.ViewHolderProdcuto> implements View.OnClickListener {

    ArrayList<SetGetListProductos> listProductos;
    private View.OnClickListener listener;
    Context context;

    public AdapterSearchProduct(ArrayList<SetGetListProductos> listProductos, Context context) {
        this.listProductos = listProductos;
        this.context = context;
    }

    @Override
    public AdapterSearchProduct.ViewHolderProdcuto onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, null, false);
        view.setOnClickListener(this);
        return new AdapterSearchProduct.ViewHolderProdcuto(view);
    }

    @Override
    public void onBindViewHolder(AdapterSearchProduct.ViewHolderProdcuto holder, int position) {
        holder.Clave.setText(listProductos.get(position).getProductos());
        holder.Descripcion.setText(listProductos.get(position).getDescripcion());
        holder.precio.setText((Double.valueOf(listProductos.get(position).getPrecioAjuste()) == 0 ?
                (Double.valueOf(listProductos.get(position).getPrecioBase()) == 0 ? Html.fromHtml("<font color = #E81414>No disponible</font>") : "$" + Html.fromHtml("<font color = #48E305>$" + formatNumberCurrency(listProductos.get(position).getPrecioBase()) + "</font>"))
                : (Double.valueOf(listProductos.get(position).getPrecioAjuste()) == 0 ? Html.fromHtml("<font color = #E81414>No disponible</font>") : Html.fromHtml("<font color = #48E305>$" + formatNumberCurrency(listProductos.get(position).getPrecioAjuste()) + "</font>"))));
        Picasso.with(context).
                load("https://www.pressa.mx/es-mx/img/products/xl/"+listProductos.get(position).getProductos()+"/4.webp")
                .error(R.drawable.ic_baseline_error_24)
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

    public class ViewHolderProdcuto extends RecyclerView.ViewHolder {
        TextView Clave, Descripcion, Marca, Modelo, year, precio;
        ImageView prodocuImag;

        public ViewHolderProdcuto(View itemView) {
            super(itemView);
            Clave = (TextView) itemView.findViewById(R.id.PartClave);
            Descripcion = (TextView) itemView.findViewById(R.id.Descr);
            precio = (TextView) itemView.findViewById(R.id.Precio);
            prodocuImag = (ImageView) itemView.findViewById(R.id.productoImag);
        }
    }
}