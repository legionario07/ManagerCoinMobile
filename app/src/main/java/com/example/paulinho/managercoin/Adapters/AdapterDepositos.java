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

import br.com.managercoin.dominio.Deposito;

/**
 * Created by PauLinHo on 23/09/2017.
 */

public class AdapterDepositos extends ArrayAdapter<Deposito> {

    private Context context;
    private List<Deposito> lista;

    public AdapterDepositos(Context context, List<Deposito> lista){
        super(context, 0, lista);
        this.context = context;
        this.lista = new ArrayList<>();
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Deposito deposito = new Deposito();
        deposito = this.lista.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_item_deposito, null);

        TextView txtDepositoData = (TextView) convertView.findViewById(R.id.txtDepositoData);
        TextView txtDepositoMoeda = (TextView) convertView.findViewById(R.id.txtDepositoMoeda);
        TextView txtDepositoQTDE = (TextView) convertView.findViewById(R.id.txtDepositoQTDE);
        TextView txtDepositoComissao = (TextView) convertView.findViewById(R.id.txtDepositoComissao);
        TextView txtDepositoTotal = (TextView) convertView.findViewById(R.id.txtDepositoTotal);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(deposito.getData());

        txtDepositoData.setText(data);
        txtDepositoMoeda.setText(deposito.getMoeda().getSigla());
        txtDepositoQTDE.setText(deposito.getValorAplicado().toString());
        txtDepositoComissao.setText(deposito.getComissao().toString());
        txtDepositoTotal.setText(deposito.getTotalLiquido().toString());



        return convertView;
    }


}
