package com.example.paulinho.managercoin.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.paulinho.managercoin.adapters.AdapterVendas;
import com.example.paulinho.managercoin.comparadores.MovimentacaoComparator;
import com.example.paulinho.managercoin.strategy.DeletarStrategy;
import com.example.paulinho.managercoin.strategy.EditarStrategy;
import com.example.paulinho.managercoin.strategy.IStrategy;
import com.example.paulinho.managercoin.strategy.SalvarStrategy;
import com.example.paulinho.managercoin.utils.SessionUtil;
import com.example.paulinho.managercoin.utils.TelaHelper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Moeda;
import br.com.managercoin.dominio.Venda;


public class VendaFragment extends Fragment {

    private ListView lstVendas;
    private Spinner spnClassificacaoVenda;
    private ImageButton imgAdd;
    private ImageButton imgReflesh;

    private LayoutInflater inflater;

    private AlertDialog.Builder dialogVendaBuilder;
    private AlertDialog dialog;
    private AlertDialog alert;

    private ImageButton imgButtonEditCrud;
    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;

    private ProgressDialog dialogProgress;
    private List<EntidadeDominio> moedas = new ArrayList<>();
    private List<EntidadeDominio> vendas = new ArrayList<>();

    private Venda venda;

    private IStrategy iStrategy;

    private EditText data;
    private EditText quantidade;
    private EditText taxa;


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

        imgAdd = (ImageButton) rootView.findViewById(R.id.imgAddVenda);
        imgReflesh = (ImageButton) rootView.findViewById(R.id.imgRefleshVenda);
        lstVendas = (ListView) rootView.findViewById(R.id.lstVendas);
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

        vendas = SessionUtil.getInstance().getVendas();
        AdapterVendas adapterVendas = new AdapterVendas(getContext(), vendas);
        lstVendas.setAdapter(adapterVendas);

        lstVendas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                venda = new Venda();
                venda = (Venda) lstVendas.getItemAtPosition(i);

                showDialog();

                return true;
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                venda = new Venda();
                criarDialogVenda("Cadastrar");
            }
        });  
        
        imgReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SessionUtil.getInstance().getVendas() != null) {
                    AdapterVendas adapterVendas = new AdapterVendas(getContext(), SessionUtil.getInstance().getVendas());
                    lstVendas.setAdapter(adapterVendas);
                }

            }
        });

        spnClassificacaoVenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                vendas = MovimentacaoComparator.ordenar((String) spnClassificacaoVenda.getSelectedItem(), vendas);

                //Atualiza a ListView com a Ordenação adequada
                AdapterVendas adapterVendas = new AdapterVendas(getActivity().getApplicationContext(), vendas);
                lstVendas.setAdapter(adapterVendas);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    /**
     * Cria o Dialog com as opçoes Inserir, Editar e Excluir
     */
    private void showDialog() {

        AlertDialog.Builder dialogCrud = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_crud, null);
        dialogCrud.setView(dialogView);
        dialogCrud.setTitle("Escolha a Opção");

        View.OnClickListener criarDialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgButtonNewCrud.getId() == view.getId()) {
                    criarDialogVenda("Cadastrar");
                } else if (imgButtonDeleteCrud.getId() == view.getId()) {
                    criarDialogVenda("Deletar");
                } else {
                    criarDialogVenda("Editar");
                }
                alert.dismiss();
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
    private void criarDialogVenda(final String operacao) {

        dialogVendaBuilder = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_venda, null);
        dialogVendaBuilder.setView(dialogView);
        dialogVendaBuilder.setTitle("VENDAS" + " - " + operacao);

        data = (EditText) dialogView.findViewById(R.id.inpDataDialogVenda);
        quantidade = (EditText) dialogView.findViewById(R.id.inpQuantidadeDialogVenda);
        taxa = (EditText) dialogView.findViewById(R.id.inpTaxaDialogVenda);

        final Spinner spnMoeda = (Spinner) dialogView.findViewById(R.id.spnMoedaDialogVenda);

        moedas = SessionUtil.getInstance().getMoedas();

        ArrayAdapter adapterMoedas = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moedas);
        adapterMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMoeda.setAdapter(adapterMoedas);

        //se for diferente de Cadastrar preenche os dados na view
        if (!operacao.equals("Cadastrar")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data.setText(sdf.format(venda.getData()));
            quantidade.setText(venda.getValorAplicado().toString());
            taxa.setText(venda.getMoeda().getTaxa().toString());
            int posicaoPerfilSelecionado = 0;

            for (int iTemp = 0; iTemp < moedas.size(); iTemp++) {
                if (venda.getMoeda().getId().equals(moedas.get(iTemp).getId())) {
                    posicaoPerfilSelecionado = iTemp;
                }
            }

            spnMoeda.setSelection(posicaoPerfilSelecionado);
        }

        dialogVendaBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String retorno = validarVenda();

                if (retorno.length() > 0) {
                    Toast.makeText(getContext(), retorno, Toast.LENGTH_SHORT).show();
                    return;
                }


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (!operacao.equals("Deletar")) {
                    venda = new Venda();

                    try {
                        venda.setData(sdf.parse(data.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    venda.setValorAplicado(new BigDecimal(quantidade.getText().toString()));
                    venda.setMoeda((Moeda) spnMoeda.getSelectedItem());
                    venda.getMoeda().setTaxa(new BigDecimal(taxa.getText().toString()));

                    if (operacao.equals("Cadastrar")) {
                        if (salvar(venda)) {
                            Toast.makeText(getContext(), "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi Possível Cadastrar", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (editar(venda)) {
                            Toast.makeText(getContext(), "Editado com Sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi Possível Editar", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (operacao.equals("Deletar")) {
                    if (deletar()) {
                        Toast.makeText(getContext(), "Deletado com Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Deletado", Toast.LENGTH_SHORT).show();
                    }
                }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        TelaHelper.atualizarSession(venda);
                    }
                });
                t.start();

                vendas = SessionUtil.getInstance().getVendas();
                if (vendas != null) {
                    AdapterVendas adapterVendas = new AdapterVendas(getContext(), vendas);
                    lstVendas.setAdapter(adapterVendas);
                }

            }
        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        return;

                    }
                });

        dialog = dialogVendaBuilder.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

    }

    /**
     * chama o strategy para deletar
     *
     * @return return true se tudo deu certo
     */
    private boolean deletar() {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {
            iStrategy = new DeletarStrategy();
            if (iStrategy.executar(venda)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
    }


    /**
     * chama o strategy para salvar
     *
     * @return return true se tudo deu certo
     */
    private boolean salvar(final Venda venda) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.setMessage("Processando...");
        dialogProgress.show();

        try {

            iStrategy = new SalvarStrategy();
            if (iStrategy.executar(venda)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
    }

    /**
     * chama o strategy para editar
     *
     * @return return true se tudo deu certo
     */
    private boolean editar(final Venda venda) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.setMessage("Processando...");
        dialogProgress.show();

        try {

            iStrategy = new EditarStrategy();
            if (iStrategy.executar(venda)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
    }



    private String validarVenda() {

        String retorno = "";

        if (data.getText().length() < 1) {
            retorno = "Data inválida";
        } else if (quantidade.getText().length() < 1) {
            retorno = "Quantidade inválida";
        } else if (taxa.getText().length() < 1) {
            retorno = "Taxa inválida";
        }

        return retorno;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Deu erro?
        if (SessionUtil.getInstance().getInvestidor().getId() == null || SessionUtil.getInstance().getInvestidor().getId() < 1) {
            Intent intent = new Intent(getContext(), ActivityLogin.class);
            SessionUtil.getInstance().clear();
            startActivity(intent);
        }
    }


}
