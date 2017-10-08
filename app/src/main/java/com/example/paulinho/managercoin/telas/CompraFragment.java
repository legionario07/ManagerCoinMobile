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
import com.example.paulinho.managercoin.adapters.AdapterCompras;
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

import br.com.managercoin.dominio.Compra;
import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Moeda;


public class CompraFragment extends Fragment {

    private ListView lstCompras;
    private Spinner spnClassificacaoCompra;
    private LayoutInflater inflater;
    private ImageButton imgAdd;
    private ImageButton imgReflesh;

    private AdapterCompras adapterCompras;
    private ArrayAdapter adapterMoedas;

    private AlertDialog.Builder dialogCompraBuilder;
    private AlertDialog dialog;
    private AlertDialog alert;

    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;
    private ImageButton imgButtonEditCrud;

    private ProgressDialog dialogProgress;
    private List<EntidadeDominio> moedas = new ArrayList<>();
    private List<EntidadeDominio> compras = new ArrayList<>();

    private Compra compra;
    private IStrategy iStrategy;

    private EditText data;
    private EditText valorAplicado;
    private EditText taxa;

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


        imgAdd = (ImageButton) rootView.findViewById(R.id.imgAddCompra);
        imgReflesh = (ImageButton) rootView.findViewById(R.id.imgRefleshCompra);
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
        compras = SessionUtil.getInstance().getCompras();

        adapterCompras = new AdapterCompras(getContext(), compras);
        lstCompras.setAdapter(adapterCompras);

        lstCompras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                compra = new Compra();
                compra = (Compra) lstCompras.getItemAtPosition(i);

                showDialog();

                return true;
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compra = new Compra();
                criarDialogCompra("Cadastrar");
            }
        });

        imgReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SessionUtil.getInstance().getCompras() != null) {
                    AdapterCompras adapterCompras = new AdapterCompras(getContext(), SessionUtil.getInstance().getCompras());
                    lstCompras.setAdapter(adapterCompras);
                }
            }
        });

        spnClassificacaoCompra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                compras = MovimentacaoComparator.ordenar((String) spnClassificacaoCompra.getSelectedItem(), compras);

                //Atualiza o ListView com a Ordenação escolhida
                adapterCompras = new AdapterCompras(getContext(), compras);
                lstCompras.setAdapter(adapterCompras);

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


        final AlertDialog.Builder dialogCrud = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_crud, null);
        dialogCrud.setView(dialogView);
        dialogCrud.setTitle("Escolha a Opção");

        final View.OnClickListener criarDialog = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imgButtonNewCrud.getId() == view.getId()) {
                    criarDialogCompra("Cadastrar");
                } else if (imgButtonDeleteCrud.getId() == view.getId()) {
                    criarDialogCompra("Deletar");
                } else {
                    criarDialogCompra("Editar");
                }

                alert.dismiss();
            }

        };

        imgButtonNewCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonNewCrud);
        imgButtonDeleteCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonDeleteCrud);
        imgButtonEditCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonEditCrud);

        imgButtonNewCrud.setOnClickListener(criarDialog);
        imgButtonDeleteCrud.setOnClickListener(criarDialog);
        imgButtonEditCrud.setOnClickListener(criarDialog);

        //Existe algum item?
        if (SessionUtil.getInstance().getCompras().size() < 0) {
            imgButtonDeleteCrud.setEnabled(false);
            imgButtonEditCrud.setEnabled(false);
        }

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

        data = (EditText) dialogView.findViewById(R.id.inpDataDialogCompra);
        valorAplicado = (EditText) dialogView.findViewById(R.id.inpValorAplicadoDialogCompra);
        taxa = (EditText) dialogView.findViewById(R.id.inpTaxaDialogCompra);

        final Spinner spnMoeda = (Spinner) dialogView.findViewById(R.id.spnMoedaDialogCompra);

        adapterMoedas = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moedas);
        adapterMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnMoeda.setAdapter(adapterMoedas);

        //Se não for excluir preenche  o Dialog
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

                String retorno = validarCompra();

                if (retorno.length() > 0) {
                    Toast.makeText(getContext(), retorno, Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (!operacao.equals("Deletar")) {
                    compra = new Compra();

                    try {
                        compra.setData(sdf.parse(data.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    compra.setValorAplicado(new BigDecimal(valorAplicado.getText().toString()));
                    compra.setMoeda((Moeda) spnMoeda.getSelectedItem());
                    compra.getMoeda().setTaxa(new BigDecimal(taxa.getText().toString()));

                    if (operacao.equals("Cadastrar")) {
                        if (salvar(compra)) {
                            Toast.makeText(getContext(), "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi Possível Cadastrar", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        if (editar(compra)) {
                            Toast.makeText(getContext(), "Editado com Sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi Possível Editar", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (operacao.equals("Deletar")) {
                    if (deletar()) {
                        Toast.makeText(getContext(), "Deletado com Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Deletar", Toast.LENGTH_SHORT).show();
                    }
                }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        TelaHelper.atualizarSession(compra);
                    }
                });
                t.start();


                compras = SessionUtil.getInstance().getCompras();
                if (compras != null) {
                    AdapterCompras adapterCompras = new AdapterCompras(getContext(), compras);
                    lstCompras.setAdapter(adapterCompras);
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

        dialog = dialogCompraBuilder.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

    }

    /**
     * Chama o strategy para deletar
     *
     * @return true se nao houve erro
     */

    private boolean deletar() {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            iStrategy = new DeletarStrategy();
            if (iStrategy.executar(compra)) {
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
     * Chama o strategy para Editar
     *
     * @param compra
     * @return um long > 1 se tudo ocorreu bem
     */
    private boolean editar(final Compra compra) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            iStrategy = new EditarStrategy();
            if (iStrategy.executar(compra)) {
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
     * Chama o strategy para Salvar
     *
     * @param compra
     * @return um long > 1 se tudo ocorreu bem
     */
    private boolean salvar(final Compra compra) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            iStrategy = new SalvarStrategy();
            if (iStrategy.executar(compra)) {
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

    private String validarCompra() {

        String retorno = "";

        if (data.getText().length() < 1) {
            retorno = "Data inválida";
        } else if (valorAplicado.getText().length() < 1) {
            retorno = "Valor Aplicado inválido";
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
