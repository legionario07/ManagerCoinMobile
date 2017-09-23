package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.paulinho.managercoin.Adapters.AdapterSaques;
import com.example.paulinho.managercoin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Saque;
import br.com.managercoin.dominio.Moeda;


public class SaqueFragment extends Fragment {

    private List<Saque> lista;
    private ListView lstSaques;
    private Spinner spnClassificacaoSaque;

    public SaqueFragment() {
        // Required empty public constructor
    }

    public static SaqueFragment newInstance(String param1, String param2) {
        SaqueFragment fragment = new SaqueFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_saques, container, false);

        lstSaques = (ListView)rootView.findViewById(R.id.lstSaques);
        spnClassificacaoSaque = (Spinner) rootView.findViewById(R.id.spnClassificacaoSaque);
        List<String> classificacao = new ArrayList<>();
        classificacao.add("Data");
        classificacao.add("Moeda");
        classificacao.add("Valor Aplicado");
        classificacao.add("Comissão");
        classificacao.add("Total");

        ArrayAdapter adapterClassif = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classificacao);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassificacaoSaque.setAdapter(adapterClassif);

        lista = new ArrayList<>();

        Saque saque = new Saque();

        saque.setData(new Date());

        Moeda moeda = new Moeda();
        moeda.setSigla("BTC");
        moeda.setTaxa(new BigDecimal("102.00"));

        saque.setTotaLiquido(new BigDecimal("100.00"));
        saque.setValorAplicado(new BigDecimal("200.00"));
        saque.setComissao(new BigDecimal("0.21"));
        saque.setMoeda(moeda);

        for(int i = 0; i <7; i++){
            lista.add(saque);
        }

        AdapterSaques adapterSaques = new AdapterSaques(getActivity().getApplicationContext(), lista);
        lstSaques.setAdapter(adapterSaques);

        return rootView;
    }

}
