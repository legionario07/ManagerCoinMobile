package com.example.paulinho.managercoin.Telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paulinho.managercoin.R;
import com.example.paulinho.managercoin.Utils.WebServiceUtil;
import com.example.paulinho.managercoin.WebService.HttpClient;

import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.EntidadeDominio;


public class MainActivity extends AppCompatActivity implements Runnable{

    private TextView txtCategoria;
    private String retorno;
    private String url;
    private Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtCategoria = (TextView) findViewById(R.id.txtCategoria);

//        Thread t = new Thread(MainActivity.this);
//        t.start();

        criarDialog();

        Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
        startActivity(intent);

    }

    private void criarDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog;

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_compra, null);
        alert.setView(dialogView);
        alert.setTitle("COMPRA");


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })

                .setNegativeButton("SAIR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                return;
            }
        });

        dialog = alert.create();
        dialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);

                negativeButton.setBackgroundColor(Color.RED);
                positiveButton.setBackgroundColor(Color.GREEN);
            }
        });

    }

    private void testar(){

        List<EntidadeDominio> moedas = new ArrayList<>();

        moedas = HttpClient.findAll(WebServiceUtil.getUrlMoedaFindall());

        Log.i("TESTE", moedas.get(0).getId().toString());



    }

    @Override
    public void run() {
        testar();

        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, retorno, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
