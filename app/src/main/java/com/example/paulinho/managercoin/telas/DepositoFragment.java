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
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulinho.managercoin.R;
import com.example.paulinho.managercoin.adapters.AdapterDepositos;
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

import br.com.managercoin.dominio.Deposito;
import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Moeda;


public class DepositoFragment extends Fragment {

    private ListView lstDepositos;
    private Spinner spnClassificacaoDeposito;
    private ImageButton imgAdd;
    private ImageButton imgReflesh;
    private TextView txtTotalDepositado;

    private LayoutInflater inflater;

    private AlertDialog.Builder dialogDepositoBuilder;
    private AlertDialog dialog;
    private ProgressDialog dialogProgress;
    private AlertDialog alert;

    private ImageButton imgButtonEditCrud;
    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;

    private Deposito deposito;
    private List<EntidadeDominio> depositos = new ArrayList<>();
    private List<EntidadeDominio> moedas = new ArrayList<>();

    private IStrategy iStrategy;

    private EditText data;
    private EditText valorAplicado;

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

        imgAdd = (ImageButton) rootView.findViewById(R.id.imgAddDeposito);
        imgReflesh = (ImageButton) rootView.findViewById(R.id.imgRefleshDeposito);
        lstDepositos = (ListView) rootView.findViewById(R.id.lstDepositos);
        spnClassificacaoDeposito = (Spinner) rootView.findViewById(R.id.spnClassificacaoDeposito);
        txtTotalDepositado = (TextView) rootView.findViewById(R.id.txtTotalDepositado);

        List<String> classificacao = new ArrayList<>();
        classificacao.add("Data");
        classificacao.add("Moeda");
        classificacao.add("Valor Aplicado");
        classificacao.add("Comissão");
        classificacao.add("Total");

        ArrayAdapter adapterClassif = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classificacao);
        adapterClassif.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClassificacaoDeposito.setAdapter(adapterClassif);

        depositos = SessionUtil.getInstance().getDepositos();
        final AdapterDepositos adapterDepositos = new AdapterDepositos(getContext(), depositos);
        lstDepositos.setAdapter(adapterDepositos);

        atualizarTotalDeposito();

        lstDepositos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                deposito = new Deposito();
                deposito = (Deposito) lstDepositos.getItemAtPosition(i);

                showDialog();

                return true;
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deposito = new Deposito();
                criarDialogDeposito("Cadastrar");
            }
        });

        imgReflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SessionUtil.getInstance().getDepositos() != null) {
                    AdapterDepositos adapterDepositos = new AdapterDepositos(getContext(), SessionUtil.getInstance().getDepositos());
                    lstDepositos.setAdapter(adapterDepositos);
                }

                atualizarTotalDeposito();


            }
        });

        spnClassificacaoDeposito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                depositos = MovimentacaoComparator.ordenar((String) spnClassificacaoDeposito.getSelectedItem(), depositos);

                //Atualiza o ListView com a Ordenação escolhida
                AdapterDepositos adapterDepositos = new AdapterDepositos(getActivity().getApplicationContext(), depositos);
                lstDepositos.setAdapter(adapterDepositos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    private void atualizarTotalDeposito(){

        BigDecimal retorno = new BigDecimal("0.0");

        for(EntidadeDominio entidadeDominio : SessionUtil.getInstance().getDepositos()){

            if(entidadeDominio instanceof Deposito){
                Deposito deposito =  new Deposito();
                deposito = (Deposito) entidadeDominio;

                if(deposito.getMoeda().getNome().equals("REAL")){
                    retorno = retorno.add(deposito.getValorAplicado());
                }

            }

        }


        txtTotalDepositado.setText(retorno.toString());

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
                    criarDialogDeposito("Cadastrar");
                } else if (imgButtonDeleteCrud.getId() == view.getId()) {
                    criarDialogDeposito("Deletar");
                } else {
                    criarDialogDeposito("Editar");
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
    private void criarDialogDeposito(final String operacao) {

        dialogDepositoBuilder = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_deposito, null);
        dialogDepositoBuilder.setView(dialogView);
        dialogDepositoBuilder.setTitle("DEPÓSITOS" + " - " + operacao);

        data = (EditText) dialogView.findViewById(R.id.inpDataDialogDeposito);
        valorAplicado = (EditText) dialogView.findViewById(R.id.inpValorDialogDeposito);

        final Spinner spnMoeda = (Spinner) dialogView.findViewById(R.id.spnMoedaDialogDeposito);

        moedas = SessionUtil.getInstance().getMoedas();

        ArrayAdapter adapterMoedas = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moedas);
        adapterMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMoeda.setAdapter(adapterMoedas);

        //Se nao for cadastrar preenche os dados no dialog
        if (!operacao.equals("Cadastrar")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data.setText(sdf.format(deposito.getData()));
            valorAplicado.setText(deposito.getValorAplicado().toString());
            int posicaoPerfilSelecionado = 0;

            List<EntidadeDominio> moedas = new ArrayList<>();
            moedas = SessionUtil.getInstance().getMoedas();

            for (int iTemp = 0; iTemp < moedas.size(); iTemp++) {
                if (deposito.getMoeda().getId().equals(moedas.get(iTemp).getId())) {
                    posicaoPerfilSelecionado = iTemp;
                }
            }

            spnMoeda.setSelection(posicaoPerfilSelecionado);
        }


        dialogDepositoBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String retorno = validarDeposito();

                if (retorno.length() > 0) {
                    Toast.makeText(getContext(), retorno, Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (!operacao.equals("Deletar")) {
                    deposito = new Deposito();

                    try {
                        deposito.setData(sdf.parse(data.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    deposito.setValorAplicado(new BigDecimal(valorAplicado.getText().toString()));
                    deposito.setMoeda((Moeda) spnMoeda.getSelectedItem());

                    if (operacao.equals("Cadastrar")) {
                        if (salvar(deposito)) {
                            Toast.makeText(getContext(), "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Não foi Possível Cadastrar", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (editar(deposito)) {
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

                        TelaHelper.atualizarSession(deposito);
                    }
                });
                t.start();


                depositos = SessionUtil.getInstance().getDepositos();
                if (depositos != null) {
                    AdapterDepositos adapterDepositos = new AdapterDepositos(getContext(), depositos);
                    lstDepositos.setAdapter(adapterDepositos);
                }

                atualizarTotalDeposito();

            }
        })
                .

                        setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                return;

                            }
                        });

        dialog = dialogDepositoBuilder.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

    }

    /**
     * chama o stragegy para deletar
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
            if (iStrategy.executar(deposito)) {
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
     * Chama o Strategy para salvar
     *
     * @param deposito com os dados
     * @return Maior que 1 se tudo deu certo
     */
    private boolean salvar(final Deposito deposito) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            iStrategy = new SalvarStrategy();
            if (iStrategy.executar(deposito)) {
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
     * Chama o Strategy para salvar
     *
     * @param deposito com os dados
     * @return Maior que 1 se tudo deu certo
     */
    private boolean editar(final Deposito deposito) {

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.setTitle("ManagerCoin");
        dialogProgress.show();

        try {

            iStrategy = new EditarStrategy();
            if (iStrategy.executar(deposito)) {
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



    private String validarDeposito() {

        String retorno = "";

        if (data.getText().length() < 1) {
            retorno = "Data inválida";
        } else if (valorAplicado.getText().length() < 1) {
            retorno = "Valor Aplicado inválido";
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
