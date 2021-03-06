package com.example.paulinho.managercoin.utils;


import java.util.ArrayList;
import java.util.List;

import br.com.managercoin.dominio.EntidadeDominio;
import br.com.managercoin.dominio.Investidor;

/**
 * Created by PauLinHo on 10/09/2017.
 */

public class SessionUtil {

    private static SessionUtil instance = new SessionUtil();
    private Investidor investidor;
    private List<EntidadeDominio> compras;
    private List<EntidadeDominio> moedas;
    private List<EntidadeDominio> depositos;
    private List<EntidadeDominio> investidores;
    private List<EntidadeDominio> saques;
    private List<EntidadeDominio> despesas;
    private List<EntidadeDominio> vendas;
    private List<EntidadeDominio> movimentacoes;

    private SessionUtil() {

        investidor = new Investidor();
        compras = new ArrayList<>();
        moedas = new ArrayList<>();
        depositos = new ArrayList<>();
        investidores = new ArrayList<>();
        saques = new ArrayList<>();
        despesas = new ArrayList<>();
        vendas = new ArrayList<>();
        movimentacoes = new ArrayList<>();

    }

    public static SessionUtil getInstance() {
        return instance;
    }


    public void clear() {
        if (investidor != null)
            investidor = new Investidor();
        if (compras != null)
            compras.clear();
        if (moedas != null)
            moedas.clear();
        if (investidores != null)
            investidores.clear();
        if (depositos != null)
            depositos.clear();
        if (saques != null)
            saques.clear();
        if (despesas != null)
            despesas.clear();
        if (vendas != null)
            vendas.clear();
    }

    public static void setInstance(SessionUtil instance) {
        SessionUtil.instance = instance;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }

    public List<EntidadeDominio> getCompras() {
        return compras;
    }

    public void setCompras(List<EntidadeDominio> compras) {
        this.compras = compras;
    }

    public List<EntidadeDominio> getMoedas() {
        return moedas;
    }

    public void setMoedas(List<EntidadeDominio> moedas) {
        this.moedas = moedas;
    }

    public List<EntidadeDominio> getDepositos() {
        return depositos;
    }

    public void setDepositos(List<EntidadeDominio> depositos) {
        this.depositos = depositos;
    }

    public List<EntidadeDominio> getInvestidores() {
        return investidores;
    }

    public void setInvestidores(List<EntidadeDominio> investidores) {
        this.investidores = investidores;
    }

    public List<EntidadeDominio> getSaques() {
        return saques;
    }

    public void setSaques(List<EntidadeDominio> saques) {
        this.saques = saques;
    }

    public List<EntidadeDominio> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<EntidadeDominio> despesas) {
        this.despesas = despesas;
    }

    public List<EntidadeDominio> getVendas() {
        return vendas;
    }

    public void setVendas(List<EntidadeDominio> vendas) {
        this.vendas = vendas;
    }

    public List<EntidadeDominio> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<EntidadeDominio> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }
}
