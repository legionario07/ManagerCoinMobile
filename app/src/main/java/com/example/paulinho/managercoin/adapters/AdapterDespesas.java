package com.example.paulinho.managercoin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.paulinho.managercoin.R;

import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.Despesa;
import br.com.managercoin.dominio.EntidadeDominio;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterDespesas extends ArrayAdapter<EntidadeDominio> {

    private Context context;
    private List<EntidadeDominio> lista;

    public AdapterDespesas(Context context, List<EntidadeDominio> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Despesa despesa = new Despesa();
        despesa = (Despesa) this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_despesa, null);

        /**
         * Criacao  dos view aki
         */


        return convertView;
    }


}
