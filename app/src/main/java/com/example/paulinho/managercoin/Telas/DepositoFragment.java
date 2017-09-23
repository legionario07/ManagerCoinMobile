package com.example.paulinho.managercoin.Telas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.paulinho.managercoin.Adapters.AdapterDepositos;
import com.example.paulinho.managercoin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Deposito;
import br.com.managercoin.dominio.Moeda;


public class DepositoFragment extends Fragment {

    private List<Deposito> lista;
    private ListView lstDepositos;
    private Spinner spnClassificacaoDeposito;

    public DepositoFragment() {
        // Required empty public constructor
    }

    public static DepositoFragment newInstance(String param1, String param2) {
        DepositoFragment fragment = new DepositoFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_depositos, container, false);

        lstDepositos = (ListView)rootView.findViewById(R.id.lstDepositos);
        spnClassificacaoDeposito = (Spinner) rootView.findViewById(R.id.spnClassificacaoDeposito);
        List<String> classificacao = new ArrayList<>();
        classificacao.add("Data");
        classificacao.add("Moeda");
        classificacao.add("Valor Aplicado");
        classificacao.add("Comissão");
        classificacao.add("Total");

        ArrayAdapter adapterClassif = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classificacao);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassificacaoDeposito.setAdapter(adapterClassif);

        lista = new ArrayList<>();

        Deposito deposito = new Deposito();

        deposito.setData(new Date());

        Moeda moeda = new Moeda();
        moeda.setSigla("BTC");
        moeda.setTaxa(new BigDecimal("102.00"));

        deposito.setTotaLiquido(new BigDecimal("100.00"));
        deposito.setValorAplicado(new BigDecimal("200.00"));
        deposito.setComissao(new BigDecimal("0.21"));
        deposito.setMoeda(moeda);

        for(int i = 0; i <7; i++){
            lista.add(deposito);
        }

        AdapterDepositos adapterDepositos = new AdapterDepositos(getActivity().getApplicationContext(), lista);
        lstDepositos.setAdapter(adapterDepositos);

        return rootView;
    }

}
