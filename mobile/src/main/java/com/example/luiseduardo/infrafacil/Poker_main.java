package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.JSONParser.json;
import static com.example.luiseduardo.infrafacil.PecaFragment.Somavebdas;
import static com.example.luiseduardo.infrafacil.PecaFragment.lsvendas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Poker_main extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ProgressDialog pDialog;
    private static String urlAll = "http://futsexta.16mb.com/Poker/poker_get_jogo.php";
    private static String urlGetJogoFordate = "http://futsexta.16mb.com/Poker/poker_get_jogo_date.php";
    private static String url = "http://futsexta.16mb.com/Poker/ordem_servicomobile.php";
    private static String urlvenda = "http://festabrinka.com.br/Infra_Get_produtosvendido.php";
    ArrayList<HashMap<String, String>> OcorList;
    private static String urlFornecedor = "http://futsexta.16mb.com/Poker/Infra_Get_fornecedor.php";
    private AdapterListViewPoker adapterListView;
    //public static ArrayList itens = null;
    ArrayList<ItemListViewPoker> itens = new ArrayList<>();
    ArrayList<ItemListViewFornecedor> itensfornecedor = new ArrayList<>();
    //ArrayList<HashMap<String, String>> newItemlist = new ArrayList<HashMap<String, String>>();
    //List<ItemListViewFornecedor> rowItems;
    private ListView lv;
    List<ItemListViewFornecedor> rowItems;
    String[] Itemtar1 = { "Adicionar Tarefa", "Formatação", "Visita Técnica", "Conf. Router", "Instalar Office", "Outro"};

    private String TAG = Produtos.class.getSimpleName();
    private String qtdvendalaste, qtdvendanow, somaqtdnew,qtdprodvend, idvenda, idprod,  qtd,    idocor, datavenda,  idforne,  valoruni, valorpago,  valortotal,  formadepagamento,  status,  parcela, qtdparcel,  valorparcela,  name, descri;
    public String DescriJodo, searchidata = "0";
    private  String Origem, idjogo,descrijogo,qtdplayers;
    private RadioButton buttonavista, buttonparcelado;

    private  ArrayList<ItemListViewFornecedor> mFornecedorList;
    private  AdapterSpinnerFornecedor mAdapter;
    private Spinner spinnerteste;
    private String idf;
    SearchView searchView;
    JSONParser jsonParser = new JSONParser();
    JSONObject object =null;
    JSONObject objectP =null;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> listfornecedor;
    ArrayAdapter<String > adapter;
    private static String IsertItem = "http://futsexta.16mb.com/Poker/IsertItem_OrdemMobile.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private ImageView imgaddnew;
    private ImageButton btnDatePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String Mes,Dia,Ano;

    private View v;
    public static String IDORDEM = Status_Ordem.IDORDEM;
    public static String IDCLIENTE = Status_Ordem.idcliente;
    public VendasAdapter vendasAdapter;
    //public static RecyclerView myrecyclerview;
    //public static List<Vendas> lsvendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpoker);

        searchView = (SearchView) findViewById (R.id.searchjogo);
        imgaddnew = (ImageView) findViewById(R.id.imgaddnewjogo);

        btnDatePicker = (ImageButton) findViewById (R.id.btn_searchedata);

        btnDatePicker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Poker_main.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if(dayOfMonth < 10){
                                    Dia = "0"+ (dayOfMonth);
                                }else {
                                    Dia = String.valueOf(dayOfMonth);
                                }
                                if(monthOfYear < 10){
                                    Mes = "0"+ (monthOfYear + 1);
                                }else{
                                    Mes = String.valueOf(monthOfYear + 1);
                                }
                                //searchView.setQuery( year + "-" + Mes + "-" + Dia,false);
                                searchidata = String.valueOf(year)+Mes+Dia;

                                new GetDados_jogos_from_date().execute();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        imgaddnew.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.v(TAG, " click");
                Intent it5 = new Intent(Poker_main.this, Poker_new.class);
                startActivity(it5);
            }
        });

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            Origem =(String) b.get("STRING_ORIGEM");
        }else{
            Origem = "ADDITEM";
        }
        idjogo = "0";
        OcorList = new ArrayList<>();
        new Poker_main.GetDados_jogos().execute();

       // new Poker_main.GetDadosFornecedor().execute();




        lv = (ListView) findViewById(R.id.listviwerjogos);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Intent intent = new Intent(Poker_main.this, Poker.class);
                idjogo = itens.get(position).getId();
                descrijogo = itens.get(position).getDescricao();
                intent.putExtra("id", idjogo);
                intent.putExtra("Drisc", descrijogo);
                startActivity(intent);
            }
        });

        //newItemlist = new ArrayList<HashMap<String, String>>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //OcorList.clear();
                //adapterListView.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DescriJodo = searchView.getQuery().toString();

                new GetDados_jogos().execute();

                return false;
            }
        });


    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }
    public int getAdapterItemPosition(long id)
    {

        for (int position = 0; position < PecaFragment.lsvendas.size(); position++)
            if (Integer.parseInt(PecaFragment.lsvendas.get(position).getIdprod()) == id)
                return position;
        return -1;
    }

    public int getAdapterItemPositiondel(int id) {

        for (int position = 0; position < PecaFragment.lsvendas.size(); position++){
            if (Integer.parseInt(PecaFragment.lsvendas.get(position).getIdprod()) == id)
            {
                String sdtd = PecaFragment.lsvendas.get(position).getQtdparcel();
                // if (sdtd.equals("2")){
                if (Integer.parseInt(sdtd) > 1){

                    return 0;
                }
                return -1;
            }
        }
        return -1;

    }


    class GetDados_jogos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List params = new ArrayList();
            params.add(new BasicNameValuePair("idjogo",idjogo));
            params.add(new BasicNameValuePair("descri",DescriJodo));
            params.add(new BasicNameValuePair("searchedata",searchidata));

            JSONObject json = new JSONObject();
             json = jsonParser.makeHttpRequest(urlAll,"POST",
                    params);

            if (json != null) {
                try {
                    JSONObject parent = new JSONObject(String.valueOf(json));
                    JSONArray eventDetails = parent.getJSONArray("jogo");

                    itens = new ArrayList<ItemListViewPoker>();
                    //newItemlist.clear();

                    for (int i = 0; i < eventDetails.length(); i++)
                    {
                        object = eventDetails.getJSONObject(i);
                        String id  = object.getString("id");
                        String Descricao = object.getString("Descricao");
                        String Data = object.getString("Data");
                        String vlentrada = object.getString("vlentrada");
                        String qtdfichaentrada = object.getString("qtdfichaentrada");
                        String vlrebuy = object.getString("vlrebuy");
                        String qtdficharebuy = object.getString("qtdficharebuy");
                        String vladdon = object.getString("vladdon");
                        String qtdfichaaddon = object.getString("qtdfichaaddon");
                        String ttplayers = object.getString("ttplayers");

                        ItemListViewPoker item1 = new ItemListViewPoker(id,Descricao, Data, vlentrada,qtdfichaentrada,vlrebuy,qtdficharebuy,vladdon,qtdfichaaddon,ttplayers);
                        itens.add(item1);
                        descrijogo = object.getString("Descricao");
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Jogos não encontrado." ,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else{
                Toast.makeText(getApplicationContext(),
                        "Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (json.isEmpty()) {

            }else {
                adapterListView = new AdapterListViewPoker(Poker_main.this, itens);
                lv.setAdapter(adapterListView);
                lv.setCacheColorHint(Color.TRANSPARENT);
            }

        }
    }

    class GetDados_jogos_from_date extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List params = new ArrayList();
            params.add(new BasicNameValuePair("idjogo",idjogo));
            //params.add(new BasicNameValuePair("descri",DescriJodo));
            params.add(new BasicNameValuePair("searchedata",searchidata));

            JSONObject jsonD = new JSONObject();
            jsonD = jsonParser.makeHttpRequest(urlGetJogoFordate,"POST",
                    params);

            if (json != null) {
                try {
                    JSONObject parent = new JSONObject(String.valueOf(json));
                    JSONArray eventDetails = parent.getJSONArray("jogo");

                    itens = new ArrayList<ItemListViewPoker>();
                    //newItemlist.clear();

                    for (int i = 0; i < eventDetails.length(); i++)
                    {
                        object = eventDetails.getJSONObject(i);
                        String id  = object.getString("id");
                        String Descricao = object.getString("Descricao");
                        String Data = object.getString("Data");
                        String vlentrada = object.getString("vlentrada");
                        String qtdfichaentrada = object.getString("qtdfichaentrada");
                        String vlrebuy = object.getString("vlrebuy");
                        String qtdficharebuy = object.getString("qtdficharebuy");
                        String vladdon = object.getString("vladdon");
                        String qtdfichaaddon = object.getString("qtdfichaaddon");
                        String ttplayers = object.getString("ttplayers");

                        ItemListViewPoker item1 = new ItemListViewPoker(id,Descricao, Data, vlentrada,qtdfichaentrada,vlrebuy,qtdficharebuy,vladdon,qtdfichaaddon,ttplayers);
                        itens.add(item1);
                        descrijogo = object.getString("Descricao");
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                            "Jogos não encontrado nesta data." ,
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else{
                Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (json.isEmpty()) {

            }else {
                adapterListView = new AdapterListViewPoker(Poker_main.this, itens);
                lv.setAdapter(adapterListView);
                lv.setCacheColorHint(Color.TRANSPARENT);
            }

        }
    }
    class InserirPeca extends AsyncTask<String, String, String>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;
            try {
                String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                String NUM_Ocor = IDORDEM;
                List params = new ArrayList();
                params.add(new BasicNameValuePair("idvenda", idvenda));
                params.add(new BasicNameValuePair("NUM_Ocor", NUM_Ocor));
                params.add(new BasicNameValuePair("idprod", idprod));
                params.add(new BasicNameValuePair("quantidadeproduto", qtdprodvend));
                params.add(new BasicNameValuePair("idfornecedor", idforne));
                params.add(new BasicNameValuePair("volorvendido", valoruni));
                params.add(new BasicNameValuePair("formadepagamento", "Dinheiro - Avista"));
                params.add(new BasicNameValuePair("datavenda", date));


                Log.e("Debug!", "starting");

                // getting product details by making HTTP request
                JSONObject newjson = jsonParser.makeHttpRequest(IsertItem, "POST",
                        params);
                // json success tag
                success = newjson.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Insert Item successo!", newjson.toString());
                    //finish();
                    return newjson.getString(TAG_MESSAGE);

                } else {
                    Log.d("Item não Atualizado", newjson.getString(TAG_MESSAGE));
                    //finish();
                    return newjson.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
        }
    }


    private class GetDadosVenda extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            IDORDEM = Status_Ordem.IDORDEM;
            String NUM_Ocor = IDORDEM;
            Log.e("Profile IDOCOR: ", IDORDEM);

            List params = new ArrayList();
            params.add(new BasicNameValuePair("IDOcor",NUM_Ocor));

            JSONObject jsonStr = jsonParser.makeHttpRequest(urlvenda,"POST",
                    params);

            Log.i("Profile JSON: ", jsonStr.toString());

            if (jsonStr != null) {
                try {
                    Log.e(TAG, "Não nulo");
                    JSONObject jsonObj = new JSONObject(String.valueOf(jsonStr));

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("produtos");
                    Log.e(TAG, "Count : " + contacts.length());

                    if (PecaFragment.itens == null){
                        PecaFragment.itens = new ArrayList<ItemListViewVendas>();
                    }

                    Somavebdas = 0;
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String idvend = c.getString("idvenda");
                        String idpro = c.getString("idproduto");
                        String quantidade = c.getString("quantidadeproduto");
                        String idcli = c.getString("idcliente");
                        String sidocorrencia = c.getString("idocorrencia");
                        String sdatavenda = c.getString("datavenda");
                        String idfornecedor = c.getString("idforne");
                        String valorunitario = c.getString("valoruni");
                        String svalortotal = c.getString("valortotal");
                        String sformapag = c.getString("formadepagamento");
                        String sstatus = c.getString("status");
                        String qtdparcela = c.getString("qtdparcela");
                        String svalorparcela = c.getString("valorparcela");
                        String sdescricao = c.getString("descricao");


                        idvenda = idvend;
                        idprod = idpro;
                        qtd =  quantidade;
                        IDCLIENTE =  idcli;
                        idocor =  sidocorrencia;
                        datavenda = sdatavenda;
                        idforne =  idfornecedor;
                        valoruni =  valorunitario;
                        valortotal =  svalortotal;
                        formadepagamento =  sformapag;
                        status =  sstatus;
                        qtdparcel =  qtdparcela;
                        valorparcela =  svalorparcela;
                        name =  sdescricao;

                        lsvendas.add(new Vendas(idvenda, idprod,  qtd,  IDCLIENTE,  idocor, datavenda,  idforne,  valoruni,valorpago,  valortotal,  formadepagamento,  status,  qtdparcel,parcela,  valorparcela,  name));

                        Somavebdas = Somavebdas + (int)Double.parseDouble(valortotal);

                        Log.e(TAG, String.valueOf(lsvendas));

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // VendasAdapter.notifyItemChanged();

            //myrecyclerview = (RecyclerView) v.findViewById(R.id.recycleitemendas);
            //VendasAdapter vendasAdapter  = new VendasAdapter(Produtos.this,lsvendas);
            // myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
            //myrecyclerview.setAdapter(vendasAdapter);

            // editValorTotal.setText(res);
            //Status_Ordem.editValorpca.setText(String.valueOf(Somavebdas));
            //Somavebdas=0;
        }
    }
    class GetDadosFornecedor extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List params = new ArrayList();
            params.add(new BasicNameValuePair("Id","%"));

            JSONObject json = jsonParser.makeHttpRequest(urlFornecedor,"GET",params);

            Log.i("Profile Fornecedor: ", json.toString());

            if (json != null) {
                try {
                    JSONObject parent = new JSONObject(String.valueOf(json));
                    JSONArray eventDetails = parent.getJSONArray("fornecedor");

                    mFornecedorList = new ArrayList<>();


                    for (int i = 0; i < eventDetails.length(); i++)
                    {
                        object = eventDetails.getJSONObject(i);
                        String id = object.getString("Id");
                        String descricao = object.getString("descricao");
                        String cnpj = object.getString("cnpj");
                        String endereco = object.getString("endereco");


                        ItemListViewFornecedor itemforne = new ItemListViewFornecedor(id, descricao, cnpj, endereco);
                        itensfornecedor.add(itemforne);
                        mFornecedorList.add(new ItemListViewFornecedor(id,descricao,cnpj,endereco));


                    }

                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Fornecedor não encontrado." ,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else{
                Toast.makeText(getApplicationContext(),
                        "Couldn't get json from server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (json.isEmpty()) {

            }else {
                //adapterListView = new AdapterListViewPecas(Produtos.this, itens);
                //lv.setAdapter(adapterListView);
                //lv.setCacheColorHint(Color.TRANSPARENT);


                //for (int counter = 0; counter < itensfornecedor.size(); counter++) {
                //System.out.println(itensfornecedor.get(counter));
                //   listfornecedor.add("Luis");
                // }

                //AdapterSpinnerFornecedor adapter = new AdapterSpinnerFornecedor(Produtos.this,
                //        R.layout.spinner_fornecedor_layout, R.id.spinnerdescricao, mFornecedorList);
                //spinnerteste.setAdapter(adapter);

            }

        }


    }
    public void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        new Poker_main.GetDados_jogos().execute();
    }
}
