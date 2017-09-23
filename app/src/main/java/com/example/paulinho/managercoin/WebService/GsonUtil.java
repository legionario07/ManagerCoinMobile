package com.example.paulinho.managercoin.WebService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import br.com.managercoin.dominio.EntidadeDominio;


/**
 * Created by PauLinHo on 10/09/2017.
 */

public class GsonUtil {

    private static Map<String, EntidadeDominio> mapEntity;
    private static EntidadeDominio e;

    public GsonUtil() {


    }


    public static EntidadeDominio getEntidadeFromGson(String dados, EntidadeDominio entidade) {

        preencherMap();


        entidade = mapEntity.get(entidade.getClass().getSimpleName());

        Gson gson = new Gson();

        Type entityType = new TypeToken<EntidadeDominio>() {
        }.getType();

        e = gson.fromJson(dados, entityType);

        return e;

    }


    private static void preencherMap(){
        mapEntity = new HashMap<>();

//        mapEntity.put(Caixa.class.getSimpleName(), new Caixa());
//        mapEntity.put(Categoria.class.getSimpleName(), new Categoria());
//        mapEntity.put(Compra.class.getSimpleName(), new Compra());
//        mapEntity.put(Entrada.class.getSimpleName(), new Entrada());
//        mapEntity.put(ItemPedido.class.getSimpleName(), new ItemPedido());
//        mapEntity.put(Perfil.class.getSimpleName(), new Perfil());
//        mapEntity.put(Produto.class.getSimpleName(), new Produto());
//        mapEntity.put(Retirada.class.getSimpleName(), new Retirada());
//        mapEntity.put(Taxa.class.getSimpleName(), new Taxa());
//        mapEntity.put(Usuario.class.getSimpleName(), new Usuario());
//        mapEntity.put(Venda.class.getSimpleName(), new Venda());
    }
}
