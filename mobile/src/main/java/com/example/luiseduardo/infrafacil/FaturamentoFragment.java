package com.example.luiseduardo.infrafacil;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaturamentoFragment extends Fragment {

    static View v;
    public static RecyclerView myrecyclerview;
    public static List<Faturamento> lsfaturamento;
    public static ImageButton btnadd;

    public static ArrayList itens = null;
    private static String IDORDEM = Status_Ordem.IDORDEM;

    JSONParser jsonParser = new JSONParser();
    private static String urlfaturamento = "http://futsexta.16mb.com/InfraFacil/Infra_Get_Faturamento.php";
    private static String urlupdatefaturamento = "http://futsexta.16mb.com/InfraFacil/Infra_Set_Parc_Faturamento.php";

    public static String idparcela, idfatura,  numeroparcela,  valorparcela,  datavencimento, datapagamento,  statusparcela,  idcliente, nomecliente;
    public static int Somavebdas, somatotal;
    private String idFaturasend ;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        // Log.i ("Activit", a);
        Log.i ("Activit", context.getClass().getName());
        if (context instanceof Activity){
            a=(Activity) context;
        }

    }

    public FaturamentoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (lsfaturamento != null && lsfaturamento.isEmpty()) {
       // }else {
            lsfaturamento = new ArrayList<>();
        //}
        new GetDadosFatura().execute();
    }

    public  void buildReclierView() {

        myrecyclerview = (RecyclerView) v.findViewById(R.id.recycleitemfaturamento);
        FaturamentoAdapter faturamentoAdapter  = new FaturamentoAdapter(getContext(),lsfaturamento);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(faturamentoAdapter);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    v =  inflater.inflate(R.layout.fragment_faturamento,container,false);
    myrecyclerview = (RecyclerView) v.findViewById(R.id.recycleitemfaturamento);
    FaturamentoAdapter faturamentoAdapter  = new FaturamentoAdapter(getContext(),lsfaturamento);
    myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    myrecyclerview.setAdapter(faturamentoAdapter);

    btnadd = (ImageButton) v.findViewById(R.id.itemadd);
    configureImageButton();


    return v;

    }

    private class GetDadosFatura extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            lsfaturamento.clear();
            String NUM_Ocor = Status_Ordem.IDORDEM;

            List params = new ArrayList<>();
            params.add(new BasicNameValuePair("IDOcor", NUM_Ocor));

            JSONObject jsonStr = jsonParser.makeHttpRequest(urlfaturamento, "POST", params);

            if (jsonStr != null) {
                try {
                    JSONArray contacts = jsonStr.getJSONArray("parcelas");

                    if (contacts.length() > 0) {
                        JSONObject firstParcel = contacts.getJSONObject(0);
                        idFaturasend = firstParcel.getString("idfatura"); // Supondo que idfatura está disponível

                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            String idparcela = c.getString("idparcela");
                            String numeroparcela = c.getString("numeroparcela");
                            String valorparcela = c.getString("valorparcela");
                            String datavencimento = c.getString("datavencimento");
                            String datapagamento = c.getString("datapagamento");
                            String statusparcela = c.getString("statusparcela");
                            String idcliente = c.getString("idcliente");
                            String nomecliente = c.getString("nomecliente");

                            lsfaturamento.add(new Faturamento(idparcela, idFaturasend, numeroparcela, valorparcela, datavencimento, datapagamento, statusparcela, idcliente, nomecliente));
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error : " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Atualiza o RecyclerView
            RecyclerView myrecyclerview = (RecyclerView) getView().findViewById(R.id.recycleitemfaturamento);
            FaturamentoAdapter faturamentoAdapter = new FaturamentoAdapter(getContext(), lsfaturamento);
            myrecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            myrecyclerview.setAdapter(faturamentoAdapter);

            // Configura o botão com idFatura
            configureImageButton();
        }
    }
    private class UpdateParcelasTask extends AsyncTask<Void, Void, Void> {

        private String idFatura;
        private String qtdParcelas;

        public UpdateParcelasTask(String idFatura, String qtdParcelas) {
            this.idFatura = idFatura;
            this.qtdParcelas = qtdParcelas;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Envie a solicitação para o servidor para atualizar as parcelas

            List params = new ArrayList<>();
            params.add(new BasicNameValuePair("idFatura", idFatura));
            params.add(new BasicNameValuePair("qtdparcela", qtdParcelas));

            JSONObject jsonStr = jsonParser.makeHttpRequest(urlupdatefaturamento, "POST", params);

            if (jsonStr != null) {
                try {
                    // Trate success como um inteiro
                    int success = jsonStr.getInt("success");
                    if (success == 1) {
                        // Atualize a lista após a atualização
                        new GetDadosFatura().execute();
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error : " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Atualize o RecyclerView na interface principal, se necessário
            // A chamada de 'new GetDadosFatura().execute()' já irá atualizar o RecyclerView
        }
    }




    private void configureImageButton() {
        // TODO Auto-generated method stub
        Button btn = (Button) v.findViewById(R.id.btnparc);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idFaturasend != null) { // Verifique se idFatura está disponível
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_parcelamento, null);
                    builder.setView(dialogView);

                    final EditText edtParcelas = dialogView.findViewById(R.id.edtParcelas);
                    ImageView imgCartao = dialogView.findViewById(R.id.imgCartao);
                    imgCartao.setImageResource(R.mipmap.visa52);  // Substitua pelo seu recurso de imagem

                    builder.setTitle("Definir Parcelamento")
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String qtdParcelas = edtParcelas.getText().toString();

                                    if (qtdParcelas.isEmpty()) {
                                        Toast.makeText(getContext(), "Por favor, insira a quantidade de parcelas", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    // Chamar a AsyncTask para atualizar as parcelas
                                    new UpdateParcelasTask(idFaturasend, qtdParcelas).execute();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Toast.makeText(getContext(), "ID da fatura não disponível", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onStart() {
        super.onStart();
        buildReclierView();
//
        Log.e(TAG, "Iniciou");
    }

    public void onStop() {
        super.onStop();
        Log.e(TAG, "Fechou");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static FaturamentoFragment newInstance(int page, String title) {
        FaturamentoFragment fragmentFirst = new FaturamentoFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }
}
