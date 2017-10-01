package com.example.paulinho.managercoin.telas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.paulinho.managercoin.adapters.AdapterSaques;
import com.example.paulinho.managercoin.strategy.DeletarStrategy;
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
import br.com.managercoin.dominio.Saque;


public class SaqueFragment extends Fragment {

    private ListView lstSaques;
    private Spinner spnClassificacaoSaque;
    private ImageButton imgAdd;

    private LayoutInflater inflater;

    private AlertDialog.Builder dialogSaqueBuilder;
    private AlertDialog dialog;
    private AlertDialog alert;

    //private ImageButton imgButtonEditCrud;
    private ImageButton imgButtonNewCrud;
    private ImageButton imgButtonDeleteCrud;

    private ProgressDialog dialogProgress;
    private List<EntidadeDominio> lista = new ArrayList<>();
    private List<EntidadeDominio> moedas = new ArrayList<>();
    private List<EntidadeDominio> saques = new ArrayList<>();

    private Saque saque;

    private IStrategy iStrategy;
    private Handler handler = new Handler();

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

        imgAdd = (ImageButton) rootView.findViewById(R.id.imgAddSaque);
        lstSaques = (ListView) rootView.findViewById(R.id.lstSaques);
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

        saques = SessionUtil.getInstance().getSaques();
        AdapterSaques adapterSaques = new AdapterSaques(getActivity().getApplicationContext(), saques);
        lstSaques.setAdapter(adapterSaques);


        lstSaques.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                saque = new Saque();
                saque = (Saque) lstSaques.getItemAtPosition(i);
                
                showDialog();

                return true;
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
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
                    criarDialogSaque("Cadastrar");
                } else {
                    criarDialogSaque("Deletar");
                }

                alert.dismiss();
            }

        };

        //imgButtonEditCrud = (ImageButton) dialogView.findViewById(imgButtonEditCrud);
        imgButtonNewCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonNewCrud);
        imgButtonDeleteCrud = (ImageButton) dialogView.findViewById(R.id.imgButtonDeleteCrud);

        //imgButtonEditCrud.setOnClickListener(criarDialog);
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
    private void criarDialogSaque(final String operacao) {

        dialogSaqueBuilder = new AlertDialog.Builder(getContext());

        inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_compra, null);
        dialogSaqueBuilder.setView(dialogView);
        dialogSaqueBuilder.setTitle("SAQUES" + " - " + operacao);

        final EditText data = (EditText) dialogView.findViewById(R.id.inpDataDialogSaque);
        final EditText valor = (EditText) dialogView.findViewById(R.id.inpValorDialogSaque);

        final Spinner spnMoeda = (Spinner) dialogView.findViewById(R.id.spnMoedaDialogSaque);

        moedas = SessionUtil.getInstance().getMoedas();

        ArrayAdapter adapterMoedas = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, moedas);
        adapterMoedas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMoeda.setAdapter(adapterMoedas);

        //Se for diferente de Cadastrar preenche os dados no dialog
        if (!operacao.equals("Cadastrar")) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            data.setText(sdf.format(saque.getData()));
            valor.setText(saque.getValorAplicado().toString());
            int posicaoPerfilSelecionado = 0;

            for (int iTemp = 0; iTemp < moedas.size(); iTemp++) {
                if (saque.getMoeda().getId().equals(moedas.get(iTemp).getId())) {
                    posicaoPerfilSelecionado = iTemp;
                }
            }

            spnMoeda.setSelection(posicaoPerfilSelecionado);
        }


        dialogSaqueBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (operacao.equals("Cadastrar")) {
                    saque = new Saque();

                    try {
                        saque.setData(sdf.parse(data.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    saque.setValorAplicado(new BigDecimal(valor.getText().toString()));
                    saque.setMoeda((Moeda) spnMoeda.getSelectedItem());

                    if (salvar(saque)) {
                        Toast.makeText(getContext(), "Cadastrado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Cadastrar", Toast.LENGTH_LONG).show();
                    }
                } else if (operacao.equals("Deletar")) {
                    if (deletar()) {
                        Toast.makeText(getContext(), "Deletado com Sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Não foi Possível Deletar", Toast.LENGTH_LONG).show();
                    }
                }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TelaHelper.atualizarSession(saque);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                saques = SessionUtil.getInstance().getSaques();
                                if (saques != null) {
                                    AdapterSaques adapterSaques = new AdapterSaques(getContext(), saques);
                                    lstSaques.setAdapter(adapterSaques);


                                }

                            }
                        });

                    }
                });
                t.start();

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
            if(iStrategy.executar(saque)){
                return true;
            }else{
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
     * @param saque com os dados
     * @return Maior que 1 se tudo deu certo
     */
    private boolean salvar(final Saque saque) {
        dialogProgress.setTitle("ManagerCoin");

        dialogProgress = new ProgressDialog(getContext());
        dialogProgress.setMessage("Processando...");
        dialogProgress.show();

        try {

            iStrategy = new SalvarStrategy();
            if(iStrategy.executar(saque)){
                return true;
            }else{
                return false;
            }


        } catch (Exception e) {
            return false;
        } finally {
            dialogProgress.dismiss();
        }
    }

}
