package com.example.paulinho.managercoin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.paulinho.managercoin.R;

import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.Moeda;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterMoedas extends ArrayAdapter<Moeda> {

    private Context context;
    private List<Moeda> lista;

    public AdapterMoedas(Context context, List<Moeda> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Moeda moeda = new Moeda();
        moeda = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_moeda, null);

        /**
         * Criacao  dos view aki
         */


        return convertView;
    }


}
