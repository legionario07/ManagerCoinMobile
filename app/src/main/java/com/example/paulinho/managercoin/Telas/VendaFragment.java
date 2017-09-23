package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.paulinho.managercoin.Adapters.AdapterVendas;
import com.example.paulinho.managercoin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Moeda;
import br.com.managercoin.dominio.Venda;


public class VendaFragment extends Fragment {

    private List<Venda> lista;
    private ListView lstVendas;
    private Spinner spnClassificacaoVenda;

    public VendaFragment() {
        // Required empty public constructor
    }

    public static VendaFragment newInstance(String param1, String param2) {
        VendaFragment fragment = new VendaFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_vendas, container, false);

        lstVendas = (ListView)rootView.findViewById(R.id.lstVendas);
        spnClassificacaoVenda = (Spinner) rootView.findViewById(R.id.spnClassificacaoVenda);
        List<String> classificacao = new ArrayList<>();
        classificacao.add("Data");
        classificacao.add("Moeda");
        classificacao.add("Valor Aplicado");
        classificacao.add("Taxa");
        classificacao.add("Total");

        ArrayAdapter adapterClassif = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classificacao);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassificacaoVenda.setAdapter(adapterClassif);

        lista = new ArrayList<>();

        Venda venda = new Venda();

        venda.setData(new Date());

        Moeda moeda = new Moeda();
        moeda.setSigla("BTC");
        moeda.setTaxa(new BigDecimal("102.00"));

        venda.setTotaLiquido(new BigDecimal("100.00"));
        venda.setValorAplicado(new BigDecimal("200.00"));
        venda.setMoeda(moeda);

        for(int i = 0; i <7; i++){
            lista.add(venda);
        }

        AdapterVendas adapterVendas = new AdapterVendas(getActivity().getApplicationContext(), lista);
        lstVendas.setAdapter(adapterVendas);

        return rootView;
    }

}
