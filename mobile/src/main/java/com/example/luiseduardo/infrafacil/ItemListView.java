package com.example.luiseduardo.infrafacil;

/**
 * Created by Windows 10 on 04/12/2017.
 */

public class ItemListView {
    private String texto;
    private String descri;
    private String numero;
    private int iconeRid;


    public ItemListView(String status, int trabalho100)
    {
    }

    public ItemListView(String texto, String descri, String numero,  int iconeRid)

    {
        this.texto = texto;
        this.descri = descri;
        this.numero = numero;
        this.iconeRid = iconeRid;
    }


    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getIconeRid()
    {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid)
    {
        this.iconeRid = iconeRid;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }
}

