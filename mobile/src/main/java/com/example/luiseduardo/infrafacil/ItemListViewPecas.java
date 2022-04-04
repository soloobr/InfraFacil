package com.example.luiseduardo.infrafacil;

public class ItemListViewPecas{
    private String texto;
    private String qtd;
    private String numero;
    private String valor;
    private int iconeRid;



    public ItemListViewPecas(String texto, String qtd, String numero, String valor, int iconeRid)

    {
        this.texto = texto;
        this.qtd = qtd;
        this.numero = numero;
        this.valor = valor;
        this.iconeRid = iconeRid;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getIconeRid() {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid) {
        this.iconeRid = iconeRid;
    }
}
