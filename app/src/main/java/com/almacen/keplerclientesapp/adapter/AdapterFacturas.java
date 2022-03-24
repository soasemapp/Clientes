package com.almacen.keplerclientesapp.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.keplerclientesapp.R;
import com.almacen.keplerclientesapp.SetandGet.SetGetLisFacturas;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterFacturas extends RecyclerView.Adapter<AdapterFacturas.ViewHolderFacturas> implements View.OnClickListener {

    ArrayList<SetGetLisFacturas> listFacturas;
    private View.OnClickListener listener;

    public AdapterFacturas(ArrayList<SetGetLisFacturas> listFacturas) {
        this.listFacturas = listFacturas;
    }

    @Override
    public AdapterFacturas.ViewHolderFacturas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_estadocuenta, null, false);
        view.setOnClickListener(this);
        return new ViewHolderFacturas(view);
    }

    @Override
    public void onBindViewHolder(AdapterFacturas.ViewHolderFacturas holder, int position) {
        holder.FolioFacturas.setText(listFacturas.get(position).getFolioFactura());
        holder.PlazodeFactura.setText(listFacturas.get(position).getPlazoFactura());
        holder.FechadeVencimiento.setText(listFacturas.get(position).getFechadeNacimiento());
        holder.SaldodeFactura.setText(Html.fromHtml((listFacturas.get(position).getSaldodeFactura().equals("0") ? "<font color='#F32121'> PAGADO </font>" : "$<font color='#4CAF50'>" + formatNumberCurrency(listFacturas.get(position).getSaldodeFactura())) + "</font>"));
        holder.MontodeFactura.setText(Html.fromHtml("<font color=#000000>$</font> <font color ='#4CAF50'>" + formatNumberCurrency(listFacturas.get(position).getMontodeFactura()) + "</font>"));


    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }
    @Override
    public int getItemCount() {
        return listFacturas.size();
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

    public static class ViewHolderFacturas extends RecyclerView.ViewHolder {
        TextView FolioFacturas;
        TextView PlazodeFactura;
        TextView FechadeVencimiento;
        TextView SaldodeFactura;
        TextView MontodeFactura;


        public ViewHolderFacturas(View itemView) {
            super(itemView);

            FolioFacturas = (TextView) itemView.findViewById(R.id.idFactura);
            PlazodeFactura = (TextView) itemView.findViewById(R.id.idplazodefactura);
            FechadeVencimiento = (TextView) itemView.findViewById(R.id.idfechanacimiento);
            SaldodeFactura = (TextView) itemView.findViewById(R.id.idsaldofactura);
            MontodeFactura = (TextView) itemView.findViewById(R.id.idmontofactura);

        }
    }
}