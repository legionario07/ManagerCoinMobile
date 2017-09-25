package com.example.paulinho.managercoin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.paulinho.managercoin.R;

import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Moeda;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterMoedas extends ArrayAdapter<EntidadeDominio> {

    private Context context;
    private List<EntidadeDominio> lista;

    public AdapterMoedas(Context context, List<EntidadeDominio> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Moeda moeda = new Moeda();
        moeda = (Moeda) this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_moeda, null);


        TextView txtMoedaNome = (TextView) convertView.findViewById(R.id.txtMoedaNome);
        TextView txtMoedaSigla = (TextView) convertView.findViewById(R.id.txtMoedaSigla);
        TextView txtMoedaTotal = (TextView) convertView.findViewById(R.id.txtMoedaTotal);

        txtMoedaNome.setText(moeda.getNome());
        txtMoedaSigla.setText(moeda.getSigla());
        txtMoedaTotal.setText(moeda.getTotal().toString());

        return convertView;
    }


}
