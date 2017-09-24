package com.example.paulinho.managercoin.Telas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.paulinho.managercoin.Adapters.AdapterSaques;
import com.example.paulinho.managercoin.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Moeda;
import br.com.managercoin.dominio.Saque;


public class SaqueFragment extends Fragment {

    private List<Saque> lista;
    private ListView lstSaques;
    private Spinner spnClassificacaoSaque;

    private LayoutInflater inflater;

    private AlertDialog.Builder dialogSaqueBuilder;
    private AlertDialog dialog;

    private ImageButton imgButtonEditCrud;
    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;

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
        lstSaques.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showDialog();

                return true;
            }
        });

        return rootView;
    }

    /**
     * Cria o Dialog com as opçoes Inserir, Editar e Excluir
     */
    private void showDialog() {

        AlertDialog alert;
        AlertDialog.Builder dialogCrud = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_crud, null);
        dialogCrud.setView(dialogView);
        dialogCrud.setTitle("Escolha a Opção");

        View.OnClickListener criarDialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgButtonEditCrud.getId() == view.getId()) {
                    criarDialogSaque("Editar");
                } else if (imgButtonNewCrud.getId() == view.getId()) {
                    criarDialogSaque("Novo");
                } else {
                    criarDialogSaque("Deletar");
                }
            }

        };

        imgButtonEditCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonEditCrud);
        imgButtonNewCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonNewCrud);
        imgButtonDeleteCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonDeleteCrud);

        imgButtonEditCrud.setOnClickListener(criarDialog);
        imgButtonNewCrud.setOnClickListener(criarDialog);
        imgButtonDeleteCrud.setOnClickListener(criarDialog);


        dialogCrud.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()

        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                return;
            }
        });

        alert = dialogCrud.create();
        alert.show();
    }

    /**
     * Cria o dialog para realizar a operação solicitada
     * @param operacao    Operação clicadoa {NOVO, EDITAR, DELETAR}
     */
    private void criarDialogSaque(String operacao) {

        dialogSaqueBuilder = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_compra, null);
        dialogSaqueBuilder.setView(dialogView);
        dialogSaqueBuilder.setTitle("COMPRAS" + " - "+ operacao);

        dialogSaqueBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;

                    }
                });

        dialog = dialogSaqueBuilder.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

    }

}
