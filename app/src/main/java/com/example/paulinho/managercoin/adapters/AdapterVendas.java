package com.example.paulinho.managercoin.adapters;

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

import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Venda;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterVendas extends ArrayAdapter<EntidadeDominio> {

    private Context context;
    private List<EntidadeDominio> lista;

    public AdapterVendas(Context context, List<EntidadeDominio> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Venda venda = new Venda();
        venda = (Venda) this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_venda, null);

        TextView txtVendaData = (TextView) convertView.findViewById(R.id.txtVendaData);
        TextView txtVendaMoeda = (TextView) convertView.findViewById(R.id.txtVendaMoeda);
        TextView txtVendaQTDE = (TextView) convertView.findViewById(R.id.txtVendaQTDE);
        TextView txtVendaTaxa = (TextView) convertView.findViewById(R.id.txtVendaTaxa);
        TextView txtVendaTotal = (TextView) convertView.findViewById(R.id.txtVendaTotal);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(venda.getData());

        txtVendaData.setText(data);
        txtVendaMoeda.setText(venda.getMoeda().getSigla());
        txtVendaQTDE.setText(venda.getValorAplicado().toString());
        txtVendaTaxa.setText(venda.getMoeda().getTaxa().toString());
        txtVendaTotal.setText(venda.getTotalLiquido().toString());


        return convertView;
    }


}
