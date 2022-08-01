package com.almacen.keplerclientesapp.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConsulCotiSANDG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorConsulCoti extends RecyclerView.Adapter<AdaptadorConsulCoti.ViewHolderConsulCoti> implements View.OnClickListener {

    ArrayList<ConsulCotiSANDG> listaConsulCoti;
    private View.OnClickListener listener;

    public AdaptadorConsulCoti(ArrayList<ConsulCotiSANDG> listaConsulCoti) {
        this.listaConsulCoti = listaConsulCoti;
    }

    @Override
    public ViewHolderConsulCoti onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_consulcoti, null, false);
        view.setOnClickListener(this);
        return new ViewHolderConsulCoti(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderConsulCoti holder, int position) {
        holder.Sucursal.setText(Html.fromHtml("Sucursal:<br> <font color ='#000000'>" +listaConsulCoti.get(position).getNomSucursal()+"</font>"));
        holder.Folio.setText(Html.fromHtml("Folio:<font color ='#F32121'>" +listaConsulCoti.get(position).getFolio()+"</font>"));
        holder.Fecha.setText(Html.fromHtml("Fecha:<br> <font color ='#000000'>" +listaConsulCoti.get(position).getFecha()+"</font>"));
        holder.Nombre.setText(Html.fromHtml("Nombre:<br> <font color ='#000000'>" +listaConsulCoti.get(position).getNombre()+"</font>"));
        holder.Importe.setText(Html.fromHtml("Importe:<br>  <font color ='#000000'>$</font><font color ='#4CAF50'>" +formatNumberCurrency(listaConsulCoti.get(position).getImporte())+"</font>"));
        holder.Piezas.setText(Html.fromHtml("Piezas:<br> <font color ='#000000'>" +listaConsulCoti.get(position).getPiezas()+"</font>"));
        holder.Comentario.setText(Html.fromHtml("Comentario:<br> <font color ='#000000'>" +listaConsulCoti.get(position).getComentario()+"</font>"));


    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listaConsulCoti.size();
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

    public class ViewHolderConsulCoti extends RecyclerView.ViewHolder {
        TextView Sucursal, Folio, Fecha, Nombre, Importe, Piezas,Comentario;

        public ViewHolderConsulCoti(View itemView) {
            super(itemView);
            Sucursal = (TextView) itemView.findViewById(R.id.Sucursal);
            Folio = (TextView) itemView.findViewById(R.id.Folio);
            Fecha = (TextView) itemView.findViewById(R.id.Fecha);
            Nombre = (TextView) itemView.findViewById(R.id.NCliente);
            Importe = (TextView) itemView.findViewById(R.id.Importe);
            Piezas = (TextView) itemView.findViewById(R.id.piezas);
            Comentario = (TextView) itemView.findViewById(R.id.Comentario);

        }
    }
}
