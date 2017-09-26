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
import br.com.managercoin.dominio.Saque;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterSaques extends ArrayAdapter<EntidadeDominio> {

    private Context context;
    private List<EntidadeDominio> lista;

    public AdapterSaques(Context context, List<EntidadeDominio> lista) {
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Saque saque = new Saque();
        saque = (Saque) this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_saque, null);

        TextView txtSaqueData = (TextView) convertView.findViewById(R.id.txtSaqueData);
        TextView txtSaqueMoeda = (TextView) convertView.findViewById(R.id.txtSaqueMoeda);
        TextView txtSaqueQTDE = (TextView) convertView.findViewById(R.id.txtSaqueQTDE);
        TextView txtSaqueComissao = (TextView) convertView.findViewById(R.id.txtSaqueComissao);
        TextView txtSaqueTotal = (TextView) convertView.findViewById(R.id.txtSaqueTotal);

        if (lista.size() > 0) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String data = sdf.format(saque.getData());

            txtSaqueData.setText(data);
            txtSaqueMoeda.setText(saque.getMoeda().getSigla());
            txtSaqueQTDE.setText(saque.getValorAplicado().toString());
            txtSaqueComissao.setText(saque.getComissao().toString());
            txtSaqueTotal.setText(saque.getTotalLiquido().toString());

        }
        return convertView;
    }


}
