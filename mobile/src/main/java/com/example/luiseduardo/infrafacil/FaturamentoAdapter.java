package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.FaturamentoFragment.lsfaturamento;
import static com.example.luiseduardo.infrafacil.PecaFragment.lsvendas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import androidx.recyclerview.widget.RecyclerView;

public class FaturamentoAdapter extends RecyclerView.Adapter<FaturamentoAdapter.MyViwerHolder> {

    Context mContext;
    List<Faturamento> mData;
    public static String idocor = Status_Ordem.IDORDEM;
    public static String  idparcela, idfatura,  numeroparcela,  valorparcela,  datavencimento, datapagamento,  statusparcela,  idcliente, nomecliente;
    private static String urldelvenda = "http://futsexta.16mb.com/Poker/Infra_Delete_produtosvendido.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String TAG = Ordem.class.getSimpleName();
    JSONParser jsonParser = new JSONParser();
    private final Locale locale = Locale.getDefault();

    public FaturamentoAdapter(Context mContext, List<Faturamento> mData) {
        this.mContext = mContext;
        this.mData = lsfaturamento;
    }


    public static void notifyItemChanged() {
    }

    @Override
    public MyViwerHolder onCreateViewHolder(ViewGroup parent, int viwType) {
        //mData = Status_Ordem.
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_faturamento,parent,false);
        MyViwerHolder vHolder = new MyViwerHolder(v);
        return vHolder;
    }
    public static String getCurrencySymbol() {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
    }
    @Override
    public void onBindViewHolder(final MyViwerHolder holder, @SuppressLint("RecyclerView") final int position) {



        //holder.tv_idparcela.setText(mData.get(position).getIdparcela());
        holder.tv_idfatura.setText(mData.get(position).getIdfatura());
        holder.tv_datavencimento.setText(mData.get(position).getDatavencimento());
        holder.tv_numeroparcela.setText(mData.get(position).getNumeroparcela());

        String valor = mData.get(position).getValorparcela();
        BigDecimal parsed = parseToBigDecimal(valor);
        String formatted;
        formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);
        holder.tv_valorparcela.setText(formatted);
        holder.tv_statusparcela.setText(mData.get(position).getStatusparcela());

        //if(!mData.get(position).getQtdparcel().equals("0")) {
        //    holder.imgparce.setImageResource(R.mipmap.parce100);
        //}

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Excluir item:");
                builder.setMessage(holder.tv_idfatura.getText());
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //idvenda = (String) holder.idvenda;
                        //idvenda = PecaFragment.lsvendas.get(counter).getIdvenda();
                        //idprod = (String) holder.idproduto;

                        //String sVlr_venda = String.valueOf(holder.tv_valor.getText());
                        //String sVlr_vendaold = String.format("[%s\\s]", Status_Ordem.MoneyTextWatcher.getCurrencySymbol());
                        //sVlr_venda = sVlr_venda.replaceAll(sVlr_vendaold, "");
                        //sVlr_venda = sVlr_venda.replaceAll("[.]", "");
                        //sVlr_venda = sVlr_venda.replaceAll("[,]", "");
                        //int iVlr_venda = (int)Double.parseDouble(sVlr_venda);

                        //new ExcluiDadosVenda().execute();
                        //removeAt(position);
                        //Status_Ordem.somatValue.SubitraiVendas(iVlr_venda);
                        //int number1 = (int)Double.parseDouble(Status_Ordem.Vlr_Peca);
                        //int number2 = (int)Double.parseDouble(Status_Ordem.Vlr_MO);
                       // int number3 = (int)Double.parseDouble(Vlr_venda);

                       // int res = number1 - number3;
                       // Status_Ordem.editValorpca.setText(String.valueOf(res));
                       // int res1 = res + number2;

                        //Status_Ordem.editValorTotal.setText(String.valueOf(res1));

                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                //cria o AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();



            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, holder.tv_idfatura.getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void removeAt(int position) {
        lsvendas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lsvendas.size());
    }



    public class MyViwerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_idparcela;
        private TextView tv_idfatura;
        private TextView tv_valorparcela;
        private TextView tv_numeroparcela;
        private TextView tv_datavencimento;
        private TextView tv_datapagamento;
        private TextView tv_statusparcela;
        private TextView tv_idcliente;
        private TextView tv_nomecliente;

        private ImageButton img;
        //private ImageButton imgparce;
        private String idparcela;
        private String idfatura;
        private String idocor;



        public MyViwerHolder(@NonNull View itemView) {
            super(itemView);

            //tv_idparcela = (TextView) itemView.findViewById(R.id.main_line_id);
            tv_idfatura = (TextView)itemView.findViewById(R.id.main_line_id);
            tv_datavencimento = (TextView)itemView.findViewById(R.id.main_line_venc);
            tv_numeroparcela = (TextView)itemView.findViewById(R.id.main_line_parc);
            tv_valorparcela = (TextView)itemView.findViewById(R.id.main_line_valorparc);
            tv_statusparcela = (TextView)itemView.findViewById(R.id.main_line_status);
            img = (ImageButton) itemView.findViewById(R.id.main_next);

            //tv_datapagamento = (TextView)itemView.findViewById(R.id.main_line_valor);
            //tv_idcliente = (TextView)itemView.findViewById(R.id.main_line_valor);
            //tv_nomecliente = (TextView)itemView.findViewById(R.id.main_line_valor);




        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Toast.makeText(mContext, tv_valorparcela.getText(), Toast.LENGTH_SHORT).show();
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                //User user = users.get(position);
                // We can access the data within the views
                Toast.makeText(mContext, tv_valorparcela.getText(), Toast.LENGTH_SHORT).show();
            }
        }

    }

     class ExcluiDadosVenda extends AsyncTask<String, String, String> {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
         }

         @Override
         protected String doInBackground(String... strings) {
             int success;
             try {

             List params = new ArrayList();
             params.add(new BasicNameValuePair("idocorrencia",idocor));
             params.add(new BasicNameValuePair("idparcela",idparcela));
             params.add(new BasicNameValuePair("idfatura",idfatura));

             JSONObject newjson = jsonParser.makeHttpRequest(urldelvenda,"POST",
                     params);
                 success = newjson.getInt(TAG_SUCCESS);
                 if (success == 1) {
                     Log.d("Insert Item successo!", newjson.toString());

                     return newjson.getString(TAG_MESSAGE);

                 } else {
                     Log.d("Item não Atualizado", newjson.getString(TAG_MESSAGE));

                     return newjson.getString(TAG_MESSAGE);
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
             return null;

         }

        @Override
        protected void onPostExecute(String file_url) {

            if (file_url != null) {
                //Toast.makeText(Context.this,  file_url, Toast.LENGTH_LONG).show();
            }
            FaturamentoAdapter.notifyItemChanged();
        }
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
    public void updateList(List<Faturamento> newList) {
        lsfaturamento.clear();
        lsfaturamento.addAll(newList);
        notifyDataSetChanged();
    }
}
