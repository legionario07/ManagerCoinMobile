package com.example.paulinho.managercoin.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.paulinho.managercoin.R;
import com.example.paulinho.managercoin.adapters.AdapterCompras;
import com.example.paulinho.managercoin.utils.SessionUtil;
import com.example.paulinho.managercoin.utils.WebServiceUtil;
import com.example.paulinho.managercoin.webservice.HttpClient;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Compra;
import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Moeda;

import static com.example.paulinho.managercoin.webservice.HttpClient.save;


public class CompraFragment extends Fragment {

    private ListView lstCompras;
    private Spinner spnClassificacaoCompra;
    private LayoutInflater inflater;

    private AlertDialog.Builder dialogCompraBuilder;
    private AlertDialog dialog;

    private ImageButton imgButtonEditCrud;
    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;

    private ProgressDialog dialogProgress;
    private List<EntidadeDominio> lista = new ArrayList<>();
    private List<EntidadeDominio> moedas = new ArrayList<>();
    private List<EntidadeDominio> compras = new ArrayList<>();

    private Compra compra;

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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_compras, container, false);


        lstCompras = (ListView) rootView.findViewById(R.id.lstCompras);
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

        moedas = SessionUtil.getInstance().getMoedas();

        lista = new ArrayList<>();

        compra = new Compra();

        compra.setData(new Date());

        Moeda moeda = new Moeda();
        moeda.setSigla("BTC");
        moeda.setTaxa(new BigDecimal("102.00"));

        compra.setTotaLiquido(new BigDecimal("100.00"));
        compra.setValorAplicado(new BigDecimal("200.00"));
        compra.setMoeda(moeda);

        for (int i = 0; i < 7; i++) {
            lista.add(compra);
        }

        AdapterCompras adapterCompras = new AdapterCompras(getActivity().getApplicationContext(), lista);
        lstCompras.setAdapter(adapterCompras);

//        compras = SessionUtil.getInstance().getCompras();
//        AdapterCompras adapterCompras = new AdapterCompras(getActivity().getApplicationContext(), compras);
//        lstCompras.setAdapter(adapterCompras);

        lstCompras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                compra = new Compra();
                compra = (Compra) lstCompras.getItemAtPosition(i);

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

        final AlertDialog alert;
        final AlertDialog.Builder dialogCrud = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_crud, null);
        dialogCrud.setView(dialogView);
        dialogCrud.setTitle("Escolha a Opção");

        View.OnClickListener criarDialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgButtonEditCrud.getId() == view.getId()) {
                    criarDialogCompra("Editar");
                } else if (imgButtonNewCrud.getId() == view.getId()) {
                    criarDialogCompra("Cadastrar");
                } else {
                    criarDialogCompra("Deletar");
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
     *
     * @param operacao Operação clicadoa {NOVO, EDITAR, DELETAR}
     */
    private void criarDialogCompra(final String operacao) {

        dialogCompraBuilder = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_compra, null);
        dialogCompraBuilder.setView(dialogView);
        dialogCompraBuilder.setTitle("COMPRAS" + " - " + operacao);

        final EditText data = (EditText) dialogView.findViewById(R.id.inpDataDialogCompra);
        final EditText valorAplicado = (EditText) dialogView.findViewById(R.id.inpValorAplicadoDialogCompra);
        final EditText taxa = (EditText) dialogView.findViewById(R.id.inpTaxaDialogCompra);

        final Spinner spnMoeda = (Spinner) dialogView.findViewById(R.id.spnMoedaDialogCompra);
        ArrayAdapter adapterMoedas = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moedas);
        adapterMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMoeda.setAdapter(adapterMoedas);

        if (!operacao.equals("Cadastrar")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data.setText(sdf.format(compra.getData()));
            valorAplicado.setText(compra.getValorAplicado().toString());
            taxa.setText(compra.getMoeda().getTaxa().toString());
            int posicaoPerfilSelecionado = 0;

            for (int iTemp = 0; iTemp < moedas.size(); iTemp++) {
                if (compra.getMoeda().getId().equals(moedas.get(iTemp).getId())) {
                    posicaoPerfilSelecionado = iTemp;
                }
            }

            spnMoeda.setSelection(posicaoPerfilSelecionado);
        }


        dialogCompraBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (operacao.equals("Cadastrar")) {
                    compra = new Compra();

                    try {
                        compra.setData(sdf.parse(data.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    compra.setValorAplicado(new BigDecimal(valorAplicado.getText().toString()));
                    compra.setMoeda((Moeda) spnMoeda.getSelectedItem());
                    compra.getMoeda().setTaxa(new BigDecimal(taxa.getText().toString()));

                    if (cadastrar(compra) > 0) {
                        Toast.makeText(getContext(), "Cadastrado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Cadastrar", Toast.LENGTH_LONG).show();
                    }
                } else if (operacao.equals("Editar")) {
                    if (editar()) {
                        Toast.makeText(getContext(), "Editado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Editar", Toast.LENGTH_LONG).show();
                    }
                } else if (operacao.equals("Deletar")) {
                    if (deletar()) {
                        Toast.makeText(getContext(), "Deletado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Deletado", Toast.LENGTH_LONG).show();
                    }
                }

                compras = SessionUtil.getInstance().getCompras();
                AdapterCompras adapterCompras = new AdapterCompras(getActivity().getApplicationContext(), compras);
                lstCompras.setAdapter(adapterCompras);

            }
        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;

                    }
                });

        dialog = dialogCompraBuilder.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

    }

    private boolean deletar() {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpClient.delete(WebServiceUtil.getUrlCompraDelete(), compra.getId());
                    SessionUtil.getInstance().setMoedas(HttpClient.findAll(WebServiceUtil.getUrlCompraFindall()));
                }
            });
        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
        return true;
    }

    private boolean editar() {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpClient.update(WebServiceUtil.getUrlCompraUpdate(), compra);
                    SessionUtil.getInstance().setMoedas(HttpClient.findAll(WebServiceUtil.getUrlCompraFindall()));
                }
            });
        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
        return true;
    }

    private Long cadastrar(final Compra compra) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    compra.setId(save(WebServiceUtil.getUrlCompraSave(), compra));
                    SessionUtil.getInstance().setMoedas(HttpClient.findAll(WebServiceUtil.getUrlCompraFindall()));
                }
            });


        } catch (Exception e) {
            return 0l;
        } finally {
            dialogProgress.dismiss();
        }
        return compra.getId();
    }

}
