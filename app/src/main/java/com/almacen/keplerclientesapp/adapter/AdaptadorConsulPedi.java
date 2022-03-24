package com.almacen.keplerclientesapp.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetterandGetter.ConsulPediSANDG;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorConsulPedi extends RecyclerView.Adapter<AdaptadorConsulPedi.ViewHolderConsulPedi> implements View.OnClickListener {

    ArrayList<ConsulPediSANDG> listaConsulPedi;
    private View.OnClickListener listener;

    public AdaptadorConsulPedi(ArrayList<ConsulPediSANDG> listaConsulPedi) {
        this.listaConsulPedi = listaConsulPedi;
    }

    @Override
    public ViewHolderConsulPedi onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_consulpedi, null, false);
        view.setOnClickListener(this);
        return new ViewHolderConsulPedi(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderConsulPedi holder, int position) {


        if(listaConsulPedi.get(position).getPedido().equals("41")){

            if (listaConsulPedi.get(position).getFLiberacion().equals("43")){

                if (listaConsulPedi.get(position).getFAduana().equals("45")){

                    if (listaConsulPedi.get(position).getFFactura().equals("22")){
                        holder.imgstatus.setStartIconDrawable(R.drawable.icons8_check_mark_48);
                        holder.idStatus.setText(Html.fromHtml("<font color ='#5EFF02'>TERMINADO</font>"));
                    }else{
                        holder.imgstatus.setStartIconDrawable(R.drawable.entregar);
                        holder.idStatus.setText(Html.fromHtml("<font color ='#02C2FF'>PROCESO CASI TERMINADO</font>"));
                    }
                }else{

                    holder.imgstatus.setStartIconDrawable(R.drawable.icons8_mover_por_carretilla_128);
                    holder.idStatus.setText(Html.fromHtml("<font color ='#FFBE02'>PROCESANDO</font>"));
                }
            }else{
                holder.imgstatus.setStartIconDrawable(R.drawable.icons8_watch_48);
                holder.idStatus.setText(Html.fromHtml("<font color ='#FF0202'>Esperando</font>"));
            }
        }

        holder.Sucursal.setText(Html.fromHtml("Sucursal:<br> <font color ='#000000'>" +listaConsulPedi.get(position).getSucursal()+"</font>"));
        holder.Folio.setText((Html.fromHtml("Folio:<font color ='#F32121'>" +listaConsulPedi.get(position).getFolio()+"</font>")));
        holder.Fecha.setText(Html.fromHtml("Fecha: <font color ='#000000'>" +listaConsulPedi.get(position).getFecha()+"</font>"));
        holder.Nombre.setText(Html.fromHtml("Nombre:<br> <font color ='#000000'>" +listaConsulPedi.get(position).getNombre()+"</font>"));
        holder.Importe.setText((Html.fromHtml("Importe:<br>  <font color ='#000000'>$</font><font color ='#4CAF50'>" +formatNumberCurrency(listaConsulPedi.get(position).getImporte())+"</font>")));
        holder.Piezas.setText(Html.fromHtml("Piezas: <font color ='#000000'>" +listaConsulPedi.get(position).getPiezas()+"</font>"));


    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listaConsulPedi.size();
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

    public class ViewHolderConsulPedi extends RecyclerView.ViewHolder {
        TextView Sucursal, Folio, Fecha,  Nombre, Importe, Piezas;
EditText idStatus;
        TextInputLayout imgstatus;
        public ViewHolderConsulPedi(View itemView) {
            super(itemView);
            imgstatus= itemView.findViewById(R.id.statusimg);
            idStatus = (EditText) itemView.findViewById(R.id.idStatus);
            Sucursal = (TextView) itemView.findViewById(R.id.Sucursal);
            Folio = (TextView) itemView.findViewById(R.id.Folio);
            Fecha = (TextView) itemView.findViewById(R.id.Fecha);
            Nombre = (TextView) itemView.findViewById(R.id.NCliente);
            Importe = (TextView) itemView.findViewById(R.id.Importe);
            Piezas = (TextView) itemView.findViewById(R.id.piezas);
        }
    }
}