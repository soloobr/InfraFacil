package com.example.luiseduardo.infrafacil;

public class Vendas {

    private String idvenda;
    private String idprod;
    private String qtd;
    private String idcliente;
    private String idocor;
    private String datavenda;
    private String idforne;
    private String valoruni;
    private String valorpago;
    private String valortotal;
    private String formadepagamento;
    private String status;
    private String qtdparcel;
    private String parcela;
    private String valorparcela;
    private String name;
    private int iconeRid;

    public int getIconeRid()
    {
        return iconeRid;
    }

    public String getParcela() {
        return parcela;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public void setIdvenda(String idvenda) {
        this.idvenda = idvenda;
    }

    public void setIdprod(String idprod) {
        this.idprod = idprod;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public void setIdocor(String idocor) {
        this.idocor = idocor;
    }

    public void setDatavenda(String datavenda) {
        this.datavenda = datavenda;
    }

    public void setIdforne(String idforne) {
        this.idforne = idforne;
    }

    public void setValoruni(String valoruni) { this.valoruni = valoruni; }

    public void setValorpago(String valorpago) {
        this.valorpago = valorpago;
    }

    public void setValortotal(String valortotal) {
        this.valortotal = valortotal;
    }

    public void setFormadepagamento(String formadepagamento) { this.formadepagamento = formadepagamento; }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQtdparcel(String qtdparcel) {
        this.qtdparcel = qtdparcel;
    }

    public void setValorparcela(String valorparcela) {
        this.valorparcela = valorparcela;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdvenda() {
        return idvenda;
    }

    public String getIdprod() {
        return idprod;
    }

    public String getQtd() {
        return qtd;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public String getIdocor() {
        return idocor;
    }

    public String getDatavenda() {
        return datavenda;
    }

    public String getIdforne() {
        return idforne;
    }

    public String getValoruni() { return valoruni; }

    public String getValorpago() {
        return valorpago;
    }

    public String getValortotal() {
        return valortotal;
    }

    public String getFormadepagamento() {
        return formadepagamento;
    }

    public String getStatus() {
        return status;
    }

    public String getQtdparcel() {
        return qtdparcel;
    }

    public String getValorparcela() {
        return valorparcela;
    }

    public String getName() {
        return name;
    }

    public Vendas() {

    }



    public Vendas(String idvenda,String idprod, String qtd, String idcliente, String idocor,String datavenda, String idforne, String valoruni, String valorpago, String valortotal, String formadepagamento, String status, String qtdparcel,String parcela, String valorparcela, String name) {
        this.idvenda = idvenda;
        this.idprod = idprod;
        this.qtd =  qtd;
        this.idcliente =  idcliente;
        this.idocor =  idocor;
        this.datavenda = datavenda;
        this.idforne =  idforne;
        this.valoruni =  valoruni;
        this.valorpago =  valorpago;
        this.valortotal =  valortotal;
        this.formadepagamento =  formadepagamento;
        this.status =  status;
        this.qtdparcel =  qtdparcel;
        this.parcela = parcela;
        this.valorparcela =  valorparcela;
        this.name =  name;
        this.iconeRid = iconeRid;

    }



}
