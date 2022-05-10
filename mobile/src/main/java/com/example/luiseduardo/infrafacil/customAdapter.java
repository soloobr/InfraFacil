package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.MoneyTextWatcher.getCurrencySymbol;
import static com.example.luiseduardo.infrafacil.Poker.lsplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {

    private  List<PlayersListView> list;
    private int rowLayout;
    private Context mContext;
    private ItemClickListener clickListener;
    public static String  idplayer,rebuy, addon, valor, action;
    JSONParser jsonParser = new JSONParser();
    private static String URLUP = "http://futsexta.16mb.com/Poker/Poker_Edit_Players.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public customAdapter( List<PlayersListView> list,int rowLayout,Context context) {
        this.list = list;
        this.rowLayout = rowLayout;
        this.mContext = context;

    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, int position) {
        final PlayersListView city = list.get(position);

        myViewHolder.title.setText( city.getNome());
        //myViewHolder.title.setText( list.get(position));
        myViewHolder.tv_idplayer.setText(city.getId());
        myViewHolder.tv_nome.setText(city.getNome());
        myViewHolder.tv_qtdrebuy.setText(city.getRebuy());
        myViewHolder.tv_qtdaddon.setText(city.getAddon());

        valor = city.getValor();



    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
        //return list.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageButton btnaddon;
        public ImageButton btnrebuy;
        private TextView tv_idplayer;
        private TextView tv_nome;
        private TextView tv_qtdrebuy;
        private TextView tv_qtdaddon;
        private TextView tv_valortotal;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.main_line_nome);
            //btnaddon = (ImageButton) view.findViewById(R.id.bntaddon);
            //btnrebuy = (ImageButton) view.findViewById(R.id.btnrebuy);

            tv_idplayer = (TextView) itemView.findViewById(R.id.idplayer);
            tv_nome = (TextView) itemView.findViewById(R.id.main_line_nome);
            tv_qtdrebuy = (TextView)itemView.findViewById(R.id.main_line_valorrebuy);
            tv_qtdaddon = (TextView)itemView.findViewById(R.id.main_line_valoraddon);
            tv_valortotal = (TextView)itemView.findViewById(R.id.main_line_valortotal);

            view.setTag(view);

            //view.setOnClickListener(this);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    View promptView = layoutInflater.inflate(R.layout.custom_alertplayers, null);

                    //LayoutInflater inflater = this.getLayoutInflater();
                    //LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //View alertLayout = inflater.inflate(R.layout.custom_alertplayers, null);
                    //--View alertLayout = .getLayoutInflater().inflate(R.layout.custom_alertplayers, null);
                    //final View alertLayoutparce = inflater.inflate(R.layout.custom_alertparcelamento, null);
                    //final View alertLayoutitemparce = inflater.inflate(R.layout.custom_alertatualizaitem, null);
                    //final EditText qtd = alertLayout.findViewById(R.id.edtquantidade);
                    //final EditText idfornecedor = alertLayout.findViewById(R.id.edtidforne);
                    //final ImageButton spinnerfornecedor = findViewById(R.id.spinnerfornecedor);


                    //final EditText valordevenda = alertLayout.findViewById(R.id.editvalorvendido);
                    //valordevenda.addTextChangedListener(new Status_Ordem.MoneyTextWatcher(valordevenda));

                    //final EditText valordecompra = alertLayout.findViewById(R.id.editvalorpago);
                    //valordecompra.addTextChangedListener(new Status_Ordem.MoneyTextWatcher(valordecompra));

                    //final TextView tvdescriproduto = alertLayout.findViewById(R.id.tvdescriproduto);
                    //final TextView txnumeroid = alertLayout.findViewById(R.id.txnumeroid);
                    //final RadioButton buttonavista = alertLayout.findViewById(R.id.radioButtonAvista);

                    //idfornecedor.setText("1");


                    //AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    //alert.setTitle("Adicionar item");
                    alert.setView(promptView);
                    alert.setCancelable(false);
                    ImageButton btn_1= (ImageButton) promptView.findViewById(R.id.btndeleterebuy);
                    btn_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //do required function
                            // don't forget to call alertD.dismiss()
                            Toast.makeText(mContext, "DELETE", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setCancelable(false);

                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "Adição cancelada", Toast.LENGTH_SHORT).show();
                        }
                    });

                    alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    final AlertDialog dialog = alert.create();
                    dialog.show();
                }
                            /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(title.getText());
                    //builder.setTitle("Adicionar  Addon:");
                    builder.setMessage("Rebuy:");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            //idvenda = (String) holder.idvenda;
                            rebuy = String.valueOf(tv_qtdrebuy.getText());

                            valor = String.valueOf(tv_valortotal.getText());
                            addon = (String) tv_qtdaddon.getText();
                            //String Vlr_venda = String.valueOf(holder.tv_valor.getText());
                            //String Vlr_venda = String.valueOf(holder.tv_valor.getText());
                            //new ExcluiDadosVenda().execute();
                            //removeAt(position);
                            int number1 = (int)Double.parseDouble(rebuy);
                            //int number2 = (int)Double.parseDouble(addon);
                            int number3 = (int)Double.parseDouble(valor);

                            int res = number1 + 1;
                            int restota = res * 20;
                            int restotal = restota + number3;
                            // Status_Ordem.editValorpca.setText(String.valueOf(res));
                            // int res1 = res + number2;

                            tv_qtdrebuy.setText(String.valueOf(res));
                            //holder.tv_valortotal.setText(String.valueOf(restotal));

                            idplayer = String.valueOf(tv_idplayer.getText());
                            rebuy = String.valueOf(res);
                            addon = String.valueOf(tv_qtdaddon.getText());
                            valor = String.valueOf(restotal);
                            action = "rebuy";

                            new UpdatePlayer().execute();
                            //notifyDataSetChanged();
                            //Poker.methodOnBtnClick(position);
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    //cria o AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();




                    //Poker.methodOnBtnClick(position);
                }
            });*//*
            btnrebuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Adicionar  Rebuy:");
                    builder.setMessage(title.getText());
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            //idvenda = (String) holder.idvenda;
                            rebuy = String.valueOf(tv_qtdrebuy.getText());

                            valor = String.valueOf(tv_valortotal.getText());
                            addon = (String) tv_qtdaddon.getText();
                            //String Vlr_venda = String.valueOf(holder.tv_valor.getText());
                            //String Vlr_venda = String.valueOf(holder.tv_valor.getText());
                            //new ExcluiDadosVenda().execute();
                            //removeAt(position);
                            int number1 = (int)Double.parseDouble(rebuy);
                            //int number2 = (int)Double.parseDouble(addon);
                            int number3 = (int)Double.parseDouble(valor);

                            int res = number1 + 1;
                            int restota = res * 20;
                            int restotal = restota + number3;
                            // Status_Ordem.editValorpca.setText(String.valueOf(res));
                            // int res1 = res + number2;

                            tv_qtdrebuy.setText(String.valueOf(res));
                            //holder.tv_valortotal.setText(String.valueOf(restotal));
                            title.setText(String.valueOf(300));


                            idplayer = String.valueOf(tv_idplayer.getText());
                            rebuy = String.valueOf(res);
                            addon = String.valueOf(tv_qtdaddon.getText());
                            valor = String.valueOf(restotal);
                            action = "rebuy";

                            new UpdatePlayer().execute();
                            //notifyDataSetChanged();
                            //Poker.methodOnBtnClick(position);
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    //cria o AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();



                }*/
            });

       }

        @Override
        public void onClick(View view) {

        }
    }
    class UpdatePlayer extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(Context.this);
            //pDialog.setMessage("Atualizando Dados");
            //pDialog.setIndeterminate(false);
            //pDialog.setCancelable(true);
            //pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {



            int success;
            try {


                // Building Parameters
                List params = new ArrayList();

                if (action == "rebuy" ){
                    params.add(new BasicNameValuePair("id", idplayer));
                    params.add(new BasicNameValuePair("rebuy", rebuy));
                    params.add(new BasicNameValuePair("action", action));

                }
                if (action == "addon" ){
                    params.add(new BasicNameValuePair("id", idplayer));
                    params.add(new BasicNameValuePair("addon", addon));
                    params.add(new BasicNameValuePair("action", action));
                }



                Log.d("Debug!", "starting");

                // getting product details by making HTTP request
                JSONObject newjson = jsonParser.makeHttpRequest(URLUP, "POST",
                        params);

                //Id_Comp = "0";
                // json success tag
                success = newjson.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Editar successo!", newjson.toString());
                    //finish();
                    return newjson.getString(TAG_MESSAGE);

                } else {
                    Log.d("Não Atualizada", newjson.getString(TAG_MESSAGE));
                    //finish();
                    return newjson.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            //new Poker.GetDados().execute();
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

}