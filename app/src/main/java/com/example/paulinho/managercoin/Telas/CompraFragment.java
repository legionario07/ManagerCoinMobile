package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.paulinho.managercoin.Adapters.AdapterCompras;
import com.example.paulinho.managercoin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Compra;
import br.com.managercoin.dominio.Moeda;


public class CompraFragment extends Fragment {

    private List<Compra> lista;
    private ListView lstCompras;
    private Spinner spnClassificacaoCompra;

    public CompraFragment() {
        // Required empty public constructor
    }

    public static CompraFragment newInstance(String param1, String param2) {
        CompraFragment fragment = new CompraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_compras, container, false);

        lstCompras = (ListView)rootView.findViewById(R.id.lstCompras);
        spnClassificacaoCompra = (Spinner) rootView.findViewById(R.id.spnClassificacaoCompra);
        List<String> classificacao = new ArrayList<>();
        classificacao.add("Data");
        classificacao.add("Moeda");
        classificacao.add("Valor Aplicado");
        classificacao.add("Taxa");
        classificacao.add("Total");

        ArrayAdapter adapterClassif = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classificacao);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassificacaoCompra.setAdapter(adapterClassif);

        lista = new ArrayList<>();

        Compra compra = new Compra();

        compra.setData(new Date());

        Moeda moeda = new Moeda();
        moeda.setSigla("BTC");
        moeda.setTaxa(new BigDecimal("102.00"));

        compra.setTotaLiquido(new BigDecimal("100.00"));
        compra.setValorAplicado(new BigDecimal("200.00"));
        compra.setMoeda(moeda);

        for(int i = 0; i <7; i++){
            lista.add(compra);
        }

        AdapterCompras adapterCompras = new AdapterCompras(getActivity().getApplicationContext(), lista);
        lstCompras.setAdapter(adapterCompras);

        return rootView;
    }

}
