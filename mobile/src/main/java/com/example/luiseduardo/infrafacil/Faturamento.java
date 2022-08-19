package com.example.luiseduardo.infrafacil;

public class Faturamento {

    private String idparcela;
    private String idfatura;
    private String numeroparcela;
    private String valorparcela;
    private String datavencimento;
    private String datapagamento;
    private String statusparcela;
    private String idcliente;
    private String nomecliente;

    public String getIdparcela() {
        return idparcela;
    }

    public String getIdfatura() {
        return idfatura;
    }

    public String getNumeroparcela() {
        return numeroparcela;
    }

    public String getValorparcela() {
        return valorparcela;
    }

    public String getDatavencimento() {
        return datavencimento;
    }

    public String getDatapagamento() {
        return datapagamento;
    }

    public String getStatusparcela() {
        return statusparcela;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public String getNomecliente() {
        return nomecliente;
    }

    public void setIdparcela(String idparcela) {
        this.idparcela = idparcela;
    }

    public void setIdfatura(String idfatura) {
        this.idfatura = idfatura;
    }

    public void setNumeroparcela(String numeroparcela) {
        this.numeroparcela = numeroparcela;
    }

    public void setValorparcela(String valorparcela) {
        this.valorparcela = valorparcela;
    }

    public void setDatavencimento(String datavencimento) {
        this.datavencimento = datavencimento;
    }

    public void setDatapagamento(String datapagamento) {
        this.datapagamento = datapagamento;
    }

    public void setStatusparcela(String statusparcela) {
        this.statusparcela = statusparcela;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public Faturamento() {

    }

    public Faturamento(String idparcela,String  idfatura,String numeroparcela, String valorparcela,String datavencimento,String datapagamento,String statusparcela,String idcliente,String nomecliente) {
        this.idparcela = idparcela;
        this.idfatura = idfatura;
        this.numeroparcela =  numeroparcela;
        this.valorparcela =  valorparcela;
        this.datavencimento =  datavencimento;
        this.datapagamento = datapagamento;
        this.statusparcela =  statusparcela;
        this.idcliente =  idcliente;
        this.nomecliente =  nomecliente;

    }



}
