package com.example.paulinho.managercoin.Utils;

/**
 * Created by PauLinHo on 10/09/2017.
 */

public class WebServiceUtil {

    private static final String URL_USUARIO_SAVE = getUrl() + "usuario/save";
    private static final String URL_USUARIO_UPDATE = getUrl() + "usuario/update";
    private static final String URL_USUARIO_DELETE = getUrl() + "usuario/delete";
    private static final String URL_USUARIO_FIND = getUrl() + "usuario/find";
    private static final String URL_USUARIO_FINDALL = getUrl() + "usuario/findAll";


    private static final String URL_DEPOSITO_SAVE = getUrl() + "deposito/save";
    private static final String URL_DEPOSITO_UPDATE = getUrl() + "deposito/update";
    private static final String URL_DEPOSITO_DELETE = getUrl() + "deposito/delete";
    private static final String URL_DEPOSITO_FIND = getUrl() + "deposito/find";
    private static final String URL_DEPOSITO_FINDALL = getUrl() + "deposito/findAll";

    private static final String URL_COMPRA_SAVE = getUrl() + "compra/save";
    private static final String URL_COMPRA_UPDATE = getUrl() + "compra/update";
    private static final String URL_COMPRA_DELETE = getUrl() + "compra/delete";
    private static final String URL_COMPRA_FIND = getUrl() + "compra/find";
    private static final String URL_COMPRA_FINDALL = getUrl() + "compra/findAll";

    private static final String URL_DESPESA_SAVE = getUrl() + "despesa/save";
    private static final String URL_DESPESA_UPDATE = getUrl() + "despesa/update";
    private static final String URL_DESPESA_DELETE = getUrl() + "despesa/delete";
    private static final String URL_DESPESA_FIND = getUrl() + "despesa/find";
    private static final String URL_DESPESA_FINDALL = getUrl() + "despesa/findAll";

    private static final String URL_INVESTIDOR_SAVE = getUrl() + "investidor/save";
    private static final String URL_INVESTIDOR_UPDATE = getUrl() + "investidor/update";
    private static final String URL_INVESTIDOR_DELETE = getUrl() + "investidor/delete";
    private static final String URL_INVESTIDOR_FIND = getUrl() + "investidor/find";
    private static final String URL_INVESTIDOR_FINDALL = getUrl() + "investidor/findAll"; 
    private static final String URL_LOGIN = getUrl() + "investidor/login";
    private static final String URL_INVESTIDOR_FINDBYLOGIN_AND_EMAIL = getUrl() + "investidor/findByLoginAndSenha";

    private static final String URL_MOEDA_SAVE = getUrl() + "moeda/save";
    private static final String URL_MOEDA_UPDATE = getUrl() + "moeda/update";
    private static final String URL_MOEDA_DELETE = getUrl() + "moeda/delete";
    private static final String URL_MOEDA_FIND = getUrl() + "moeda/find";
    private static final String URL_MOEDA_FINDALL = getUrl() + "moeda/findAll";

    private static final String URL_SAQUE_SAVE = getUrl() + "saque/save";
    private static final String URL_SAQUE_UPDATE = getUrl() + "saque/update";
    private static final String URL_SAQUE_DELETE = getUrl() + "saque/delete";
    private static final String URL_SAQUE_FIND = getUrl() + "saque/find";
    private static final String URL_SAQUE_FINDALL = getUrl() + "saque/findAll";

    private static final String URL_VENDA_SAVE = getUrl() + "venda/save";
    private static final String URL_VENDA_UPDATE = getUrl() + "venda/update";
    private static final String URL_VENDA_DELETE = getUrl() + "venda/delete";
    private static final String URL_VENDA_FIND = getUrl() + "venda/find";
    private static final String URL_VENDA_FINDALL = getUrl() + "venda/findAll";


    /**
     * Method that returns the beginning of the webservice URL
     *
     * @return
     */
    private static String getUrl() {

        String URL = "http://omniatechnology.com.br/ManagerCoinWS/webresources/";

        return URL;

    }

    public static String getUrlUsuarioSave() {
        return URL_USUARIO_SAVE;
    }

    public static String getUrlUsuarioUpdate() {
        return URL_USUARIO_UPDATE;
    }

    public static String getUrlUsuarioDelete() {
        return URL_USUARIO_DELETE;
    }

    public static String getUrlUsuarioFind() {
        return URL_USUARIO_FIND;
    }

    public static String getUrlUsuarioFindall() {
        return URL_USUARIO_FINDALL;
    }

    public static String getUrlDepositoSave() {
        return URL_DEPOSITO_SAVE;
    }

    public static String getUrlDepositoUpdate() {
        return URL_DEPOSITO_UPDATE;
    }

    public static String getUrlDepositoDelete() {
        return URL_DEPOSITO_DELETE;
    }

    public static String getUrlDepositoFind() {
        return URL_DEPOSITO_FIND;
    }

    public static String getUrlDepositoFindall() {
        return URL_DEPOSITO_FINDALL;
    }

    public static String getUrlCompraSave() {
        return URL_COMPRA_SAVE;
    }

    public static String getUrlCompraUpdate() {
        return URL_COMPRA_UPDATE;
    }

    public static String getUrlCompraDelete() {
        return URL_COMPRA_DELETE;
    }

    public static String getUrlCompraFind() {
        return URL_COMPRA_FIND;
    }

    public static String getUrlCompraFindall() {
        return URL_COMPRA_FINDALL;
    }

    public static String getUrlDespesaSave() {
        return URL_DESPESA_SAVE;
    }

    public static String getUrlDespesaUpdate() {
        return URL_DESPESA_UPDATE;
    }

    public static String getUrlDespesaDelete() {
        return URL_DESPESA_DELETE;
    }

    public static String getUrlDespesaFind() {
        return URL_DESPESA_FIND;
    }

    public static String getUrlDespesaFindall() {
        return URL_DESPESA_FINDALL;
    }

    public static String getUrlInvestidorSave() {
        return URL_INVESTIDOR_SAVE;
    }

    public static String getUrlInvestidorUpdate() {
        return URL_INVESTIDOR_UPDATE;
    }

    public static String getUrlInvestidorDelete() {
        return URL_INVESTIDOR_DELETE;
    }

    public static String getUrlInvestidorFind() {
        return URL_INVESTIDOR_FIND;
    }

    public static String getUrlInvestidorFindall() {
        return URL_INVESTIDOR_FINDALL;
    }

    public static String getUrlMoedaSave() {
        return URL_MOEDA_SAVE;
    }

    public static String getUrlMoedaUpdate() {
        return URL_MOEDA_UPDATE;
    }

    public static String getUrlMoedaDelete() {
        return URL_MOEDA_DELETE;
    }

    public static String getUrlMoedaFind() {
        return URL_MOEDA_FIND;
    }

    public static String getUrlMoedaFindall() {
        return URL_MOEDA_FINDALL;
    }

    public static String getUrlSaqueSave() {
        return URL_SAQUE_SAVE;
    }

    public static String getUrlSaqueUpdate() {
        return URL_SAQUE_UPDATE;
    }

    public static String getUrlSaqueDelete() {
        return URL_SAQUE_DELETE;
    }

    public static String getUrlSaqueFind() {
        return URL_SAQUE_FIND;
    }

    public static String getUrlSaqueFindall() {
        return URL_SAQUE_FINDALL;
    }

    public static String getUrlVendaSave() {
        return URL_VENDA_SAVE;
    }

    public static String getUrlVendaUpdate() {
        return URL_VENDA_UPDATE;
    }

    public static String getUrlVendaDelete() {
        return URL_VENDA_DELETE;
    }

    public static String getUrlVendaFind() {
        return URL_VENDA_FIND;
    }

    public static String getUrlVendaFindall() {
        return URL_VENDA_FINDALL;
    }

    public static String getUrlLogin() {
        return URL_LOGIN;
    }

    public static String getUrlInvestidorFindbyloginAndEmail() {
        return URL_INVESTIDOR_FINDBYLOGIN_AND_EMAIL;
    }
}