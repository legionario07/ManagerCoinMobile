package com.example.paulinho.managercoin.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.paulinho.managercoin.R;

import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.Despesa;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterDespesas extends ArrayAdapter<Despesa> {

    private Context context;
    private List<Despesa> lista;

    public AdapterDespesas(Context context, List<Despesa> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Despesa despesa = new Despesa();
        despesa = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_despesa, null);

        /**
         * Criacao  dos view aki
         */


        return convertView;
    }


}
