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
    String Empresa;
    String EmpresaAd="";
    String Desc1;
    String StrServer;
    private View.OnClickListener listener;

    public AdaptadorCarrito(ArrayList<CarritoBD> listaConsulCoti,String Desc1,String StrServer,Context context,String Empresa) {
        this.listaCarrito = listaConsulCoti;
        this.Desc1 =Desc1;
        this.StrServer=StrServer;
        this.EmpresaAd=Empresa;
        this.Empresa =Empresa;
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
        String Precio;
        String Descuento;
        double precio;
        double descuento;
        double descuento1;
        double cantidad;
        double monto;


        holder.Parte.setText(listaCarrito.get(position).getParte());
        holder.Descri.setText("Descripcion:\n" + listaCarrito.get(position).getDescr());
        holder.Cantidad.setText(listaCarrito.get(position).getCantidad());

        if(!StrServer.equals("vazlocolombia.dyndns.org:9085")){

            Precio = listaCarrito.get(position).getPrecio();
            Descuento=listaCarrito.get(position).getDesc1();
            precio= Double.parseDouble(Precio);
            descuento=Double.parseDouble(Descuento);
            descuento1=Double.parseDouble(Desc1);
            cantidad = Double.parseDouble(listaCarrito.get(position).getCantidad());
            precio = (precio-((precio*descuento)/100));
            precio = (precio-((precio*descuento1)/100));
            monto= precio* cantidad;
            holder.Precio.setText(Html.fromHtml("Precio C/U:$<font color ='#4CAF50'>" +formatNumberCurrency(String.valueOf(precio))+"</font>"));
            holder.Monto.setText(Html.fromHtml("Total: $<font color ='#FF0000'>" +formatNumberCurrency(String.valueOf(monto))+"</font>"));

        }else{
            holder.Precio.setText(Html.fromHtml("Precio C/U:$<font color ='#4CAF50'>" +formatNumberCurrency(listaCarrito.get(position).getPrecio())+"</font>"));
            holder.Monto.setText(Html.fromHtml("Total: $<font color ='#FF0000'>" +formatNumberCurrency(listaCarrito.get(position).getMonto())+"</font>"));

        }
        holder.ID.setText(String.valueOf(listaCarrito.get(position).getID()));
        int Existencia = Integer.parseInt(listaCarrito.get(position).getExistencia());
        int Cantidad = Integer.parseInt((listaCarrito.get(position).getCantidad()));



        if(Empresa.equals("https://www.jacve.mx/imagenes/")){
            EmpresaAd = "";
            EmpresaAd=Empresa+listaCarrito.get(position).getFotosTipo()+"/"+listaCarrito.get(position).getFotosLinea()+"/"+listaCarrito.get(position).getParte()+"/2.jpg";
        }else  if (!Empresa.equals("https://vazlo.com.mx/assets/img/productos/chica/jpg/")){
            EmpresaAd="";
            EmpresaAd=Empresa+listaCarrito.get(position).getParte()+"/4.webp";

        }else {
            EmpresaAd = "";
            EmpresaAd = Empresa + listaCarrito.get(position).getParte() + ".jpg";
        }

        holder.Diponiblidad.setText(Html.fromHtml((Existencia<Cantidad)?"(<font color = #FF0000>SIN DISPONIBILIDAD EN SU SUCURSAL)</font>)":"(<font color = #4CAF50>HAY DISPONIBLES)</font>)"));
        Picasso.with(context).
                load(EmpresaAd)
                .placeholder(R.drawable.loadingpro)
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
