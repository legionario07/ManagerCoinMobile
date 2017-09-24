package com.example.paulinho.managercoin.WebService;

import android.util.Log;

import com.example.paulinho.managercoin.Utils.DateDeserializer;
import com.example.paulinho.managercoin.Utils.WebServiceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.managercoin.dominio.Compra;
import br.com.managercoin.dominio.Deposito;
import br.com.managercoin.dominio.Despesa;
import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Investidor;
import br.com.managercoin.dominio.Moeda;
import br.com.managercoin.dominio.Saque;
import br.com.managercoin.dominio.Venda;


/**
 * Created by PauLinHo on 10/09/2017.
 */

public class HttpClient {

    private static Gson gson = null;
    private static StringBuilder retorno = null;


    public static Long save(String enderecoURL, EntidadeDominio entidadeDominio) {

        String output = null;
        StringBuffer retorno = null;
        Long id = 0l;

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            StringBuilder builder = new StringBuilder();

            request.setURI(new URI(enderecoURL));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer(new SimpleDateFormat("dd/MM/yyyy")));

            Gson gson = gsonBuilder.create();

            String dados = gson.toJson(entidadeDominio);

            HttpEntity entity = new StringEntity(dados);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            Type listaType = new TypeToken<Long>() {
            }.getType();

            id = gson.fromJson(retorno.toString(), listaType);
            content.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return id;


    }

    /**
     * @param enderecoURL - um endereço url
     * @param id          - um id da entidade a qual se deseja excluir
     * @return - um aviso de exclusão com sucesso ou mensagem de erro
     */
    public static Boolean delete(String enderecoURL, Integer id) {
        String output = null;
        Boolean isDelete = false;

        StringBuffer temp = new StringBuffer();
        temp.append(enderecoURL);
        temp.append("/");
        temp.append(id);

        try {
            URL url = new URL(temp.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : Http error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuffer retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            Type listaType = new TypeToken<Boolean>() {
            }.getType();

            isDelete = gson.fromJson(retorno.toString(), listaType);

            conn.disconnect();

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
            return false;
        }

        return isDelete;
    }

    public static Boolean update(String enderecoURL, EntidadeDominio entidadeDominio) {

        String output = null;

        Boolean isUpdate = false;

        try {

            Gson gson = new Gson();
            String dadosTemp = gson.toJson(entidadeDominio);

            URL url = new URL(enderecoURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            gson = new Gson();

            String dados = gson.toJson(entidadeDominio);

            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream());
            wr.writeBytes(dados);
            wr.flush();
            wr.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuffer retorno = new StringBuffer();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            Type listaType = new TypeToken<Boolean>() {
            }.getType();

            isUpdate = gson.fromJson(retorno.toString(), listaType);

            conn.disconnect();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        return isUpdate;
    }

    /**
     * @param enderecoURL
     * @return
     */
    public static List<EntidadeDominio> findAll(String enderecoURL) {

        String output = null;
        List<EntidadeDominio> lista = new ArrayList<>();

        try {
            URL url = new URL(enderecoURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : Http error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            retorno = new StringBuilder();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            gson = new Gson();

            lista = gson.fromJson(retorno.toString(), getTipoRetorno(enderecoURL));

            conn.disconnect();

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
            return null;
        }
        return lista;
    }

    /**
     * @param enderecoURL - um endereço URL
     * @param id          - o id da entidade procurada
     * @return - Um String Gson com a entidade encontrada ou NULL
     */
    public static String find(String enderecoURL, Integer id) {

        String output = null;

        StringBuffer temp = new StringBuffer();
        temp.append(enderecoURL);
        temp.append("/");
        temp.append(id);

        try {
            URL url = new URL(temp.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : Http error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            retorno = new StringBuilder();

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }


            conn.disconnect();

        } catch (Exception e) {
            Log.d("ERRO", e.getMessage());
            return null;
        }

        return retorno.toString();
    }



    /**
     * @param enderecoURL - Recebe uma url
     * @param entidade    - Recebe uma entidade Investidor
     * @return - um Investidor válido ou NULL se não for encontrado no Banco de Dados
     */
    public static Investidor login(String enderecoURL, EntidadeDominio entidade) {

        Investidor investidor = new Investidor();

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            StringBuilder builder = new StringBuilder();

            request.setURI(new URI(enderecoURL));

            gson = new Gson();

            String dados = gson.toJson(entidade);

            HttpEntity entity = new StringEntity(dados);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            StringBuffer retorno = new StringBuffer();
            String output;

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            gson = new Gson();
            Type tipo = new TypeToken<Investidor>() {
            }.getType();
            investidor = gson.fromJson(retorno.toString(), tipo);

            content.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return investidor;
    }

    /**
     * @param enderecoURL - Recebe uma url
     * @param entidade    - Recebe uma entidade Investidor
     * @return - um Investidor válido ou NULL se não for encontrado no Banco de Dados
     */
    public static Investidor findByLoginAndEmail(String enderecoURL, EntidadeDominio entidade) {

        Investidor investidor = new Investidor();

        org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost();
            request.setHeader("Content-Type", "application/json");

            StringBuilder builder = new StringBuilder();

            request.setURI(new URI(enderecoURL));

            gson = new Gson();

            String dados = gson.toJson(entidade);

            HttpEntity entity = new StringEntity(dados);
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);

            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content));

            StringBuffer retorno = new StringBuffer();
            String output;

            while ((output = br.readLine()) != null) {
                retorno.append(output);
            }

            gson = new Gson();
            Type tipo = new TypeToken<Investidor>() {
            }.getType();
            investidor = gson.fromJson(retorno.toString(), tipo);

            content.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return investidor;
    }

    /**
     * Verifica qual o tipo de List deve ser retornado
     *
     * @param URL
     * @return um Type Reflect
     */
    private static Type getTipoRetorno(String URL) {

        String urlMoedas = WebServiceUtil.getUrlMoedaFindall();
        String urlCompras = WebServiceUtil.getUrlCompraFindall();
        String urlDepositos = WebServiceUtil.getUrlDepositoFindall();
        String urlInvestidores = WebServiceUtil.getUrlInvestidorFindall();
        String urlSaques = WebServiceUtil.getUrlSaqueFindall();
        String urlDespesas = WebServiceUtil.getUrlDespesaFindall();
        String urlVendas = WebServiceUtil.getUrlVendaFindall();

        Type listaType = null;

        if (URL.equals(urlMoedas)) {
            listaType = new TypeToken<List<Moeda>>() {
            }.getType();
        } else if (URL.equals(urlCompras)) {
            listaType = new TypeToken<List<Compra>>() {
            }.getType();
        } else if (URL.equals(urlDepositos)) {
            listaType = new TypeToken<List<Deposito>>() {
            }.getType();
        } else if (URL.equals(urlInvestidores)) {
            listaType = new TypeToken<List<Investidor>>() {
            }.getType();
        } else if (URL.equals(urlSaques)) {

            listaType = new TypeToken<List<Saque>>() {
            }.getType();
        } else if (URL.equals(urlDespesas)) {

            listaType = new TypeToken<List<Despesa>>() {
            }.getType();
        } else if (URL.equals(urlVendas)) {

            listaType = new TypeToken<List<Venda>>() {
            }.getType();
        } else {

            listaType = new TypeToken<List<EntidadeDominio>>() {
            }.getType();
        }

        return listaType;
    }



}
