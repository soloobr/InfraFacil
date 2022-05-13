package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.MoneyTextWatcher.getCurrencySymbol;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Windows 10 on 04/12/2017.
 */

public class AdapterListViewPoker extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<ItemListViewPoker> itens;
    private final Locale locale = Locale.getDefault();

    public AdapterListViewPoker(Context context, ArrayList<ItemListViewPoker> itens)
    {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Retorna a quantidade de itens
     *
     * @return
     */
    public int getCount()
    {
          return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public ItemListViewPoker getItem(int position)
    {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        //Pega o item de acordo com a posção.
        ItemListViewPoker item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.list_item_pecas_pecas, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.tvnome)).setText(item.getDescricao());
        ((TextView) view.findViewById(R.id.tvqtdpeca)).setText(item.getQtdfichaentrada());
        ((TextView) view.findViewById(R.id.tvnumero)).setText(item.getQtdficharebuy());

        String valor = item.getVlentrada();
        BigDecimal parsed = parseToBigDecimal(valor);
        String formatted;
        formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);

        ((TextView) view.findViewById(R.id.tvvalorvenda)).setText(formatted);
        //((TextView) view.findViewById(R.id.tvvalorvenda)).setText(format.format(item.getValor()));
        //((TextView) view.findViewById(R.id.lchamado)).setText("Nº Produto:");

        //((ImageView) view.findViewById(R.id._imagem_prod)).setImageResource(item.getIconeRid());

        return view;
    }
    private BigDecimal parseToBigDecimal(String value) {
        String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());

        String cleanString = value.replaceAll(replaceable, "");

        try {
            return new BigDecimal(cleanString).setScale(
                    2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        } catch (NumberFormatException e) {
            //ao apagar todos valores de uma só vez dava erro
            //Com a exception o valor retornado é 0.00
            return new BigDecimal(0);

        }
    }
}
