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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<CarritoBD> listaCarrito;
    Context context;
    private View.OnClickListener listener;

    public AdaptadorCarrito(ArrayList<CarritoBD> listaConsulCoti, Context context) {
        this.listaCarrito = listaConsulCoti;
        this.context = context;
    }

    @Override
    public ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_carrito, null, false);
        view.setOnClickListener(this);
        return new ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderCarrito holder, int position) {
        String NuevoPrecio="",Descuento="";
        double intNuePrecio=0,intnuevoDescuento=0,semiResultado=0,Resultado=0,DescuentoNivelDocumento=0,DescuentoRestar=0,PrecioNuevo=0,NuevoImporte=0;

        holder.Parte.setText(listaCarrito.get(position).getParte());
        holder.Descri.setText("Descripcion:\n" + listaCarrito.get(position).getDescr());
        holder.Cantidad.setText(listaCarrito.get(position).getCantidad());

        NuevoPrecio= listaCarrito.get(position).getPrecio();
        Descuento=listaCarrito.get(position).getDesc1();
        intNuePrecio=Double.parseDouble(NuevoPrecio);
        intnuevoDescuento=Double.parseDouble(Descuento)/100;

        semiResultado=intNuePrecio*intnuevoDescuento;
        Resultado = intNuePrecio-semiResultado  ;

        holder.Precio.setText(Html.fromHtml("Precio C/U:$<font color ='#4CAF50'>" +formatNumberCurrency(String.valueOf(Resultado))+"</font>"));
        holder.Monto.setText(Html.fromHtml("Total: $<font color ='#FF0000'>" +formatNumberCurrency(listaCarrito.get(position).getMonto())+"</font>"));
        holder.ID.setText(String.valueOf(listaCarrito.get(position).getID()));
        int Existencia = Integer.parseInt(listaCarrito.get(position).getExistencia());
        int Cantidad = Integer.parseInt((listaCarrito.get(position).getCantidad()));
        holder.Diponiblidad.setText(Html.fromHtml((Existencia<Cantidad)?"(<font color = #FF0000>SIN DISPONIBILIDAD EN SU SUCURSAL)</font>)":"(<font color = #4CAF50>HAY DISPONIBLES)</font>)"));
        Picasso.with(context).
        load("https://www.pressa.mx/es-mx/img/products/xl/"+listaCarrito.get(position).getParte()+"/4.webp")
                .error(R.drawable.ic_baseline_error_24)
                .fit()
                .centerInside()
                .into(holder.imgPro);

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listaCarrito.size();
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

    public class ViewHolderCarrito extends RecyclerView.ViewHolder {
        TextView Parte, Descri, Cantidad, Precio, Monto, ID,Diponiblidad;
        ImageView imgPro;


        public ViewHolderCarrito(View itemView) {
            super(itemView);
            ID = (TextView) itemView.findViewById(R.id.ID);
            imgPro = (ImageView) itemView.findViewById(R.id.productoImag);
            Parte = (TextView) itemView.findViewById(R.id.Parte);
            Descri = (TextView) itemView.findViewById(R.id.Descr);
            Cantidad = (TextView) itemView.findViewById(R.id.Cantidad);
            Precio = (TextView) itemView.findViewById(R.id.Precio);
            Monto = (TextView) itemView.findViewById(R.id.Monto);
            Diponiblidad=(TextView)itemView.findViewById(R.id.Disponi);
        }
    }
}
