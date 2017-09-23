package com.example.paulinho.managercoin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paulinho.managercoin.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.Compra;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterCompras extends ArrayAdapter<Compra> {

    private Context context;
    private List<Compra> lista;

    public AdapterCompras(Context context, List<Compra> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Compra compra = new Compra();
        compra = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_compra, null);

        TextView txtCompraData = (TextView) convertView.findViewById(R.id.txtCompraData);
        TextView txtCompraMoeda = (TextView) convertView.findViewById(R.id.txtCompraMoeda);
        TextView txtCompraQTDE = (TextView) convertView.findViewById(R.id.txtCompraQTDE);
        TextView txtCompraTaxa = (TextView) convertView.findViewById(R.id.txtCompraTaxa);
        TextView txtCompraTotal = (TextView) convertView.findViewById(R.id.txtCompraTotal);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(compra.getData());

        txtCompraData.setText(data);
        txtCompraMoeda.setText(compra.getMoeda().getSigla());
        txtCompraQTDE.setText(compra.getValorAplicado().toString());
        txtCompraTaxa.setText(compra.getMoeda().getTaxa().toString());
        txtCompraTotal.setText(compra.getMoeda().getTotal().toString());

        return convertView;
    }


}
