package com.example.luiseduardo.infrafacil;

import android.app.AlertDialog;
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
import android.widget.EditText;
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

import static com.example.luiseduardo.infrafacil.JSONParser.json;
import static com.example.luiseduardo.infrafacil.PecaFragment.Somavebdas;
import static com.example.luiseduardo.infrafacil.PecaFragment.lsvendas;

//import android.support.v7.app.AlertDialog;

public class Produtos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ProgressDialog pDialog;
    private static String urlAll = "http://futsexta.16mb.com/Poker/Infra_Get_produtos.php";
    private static String url = "http://futsexta.16mb.com/Poker/ordem_servicomobile.php";
    private static String urlvenda = "http://festabrinka.com.br/Infra_Get_produtosvendido.php";
    ArrayList<HashMap<String, String>> OcorList;
private static String urlFornecedor = "http://futsexta.16mb.com/Poker/Infra_Get_fornecedor.php";
    private AdapterListViewPecas adapterListView;
    //public static ArrayList itens = null;
    ArrayList<ItemListViewPecas> itens = new ArrayList<>();
    ArrayList<ItemListViewFornecedor> itensfornecedor = new ArrayList<>();
    ArrayList<HashMap<String, String>> newItemlist = new ArrayList<HashMap<String, String>>();
    //List<ItemListViewFornecedor> rowItems;
    private ListView lv;
    List<ItemListViewFornecedor> rowItems;
    String[] Itemtar1 = { "Adicionar Tarefa", "Formatação", "Visita Técnica", "Conf. Router", "Instalar Office", "Outro"};

    private String TAG = Produtos.class.getSimpleName();
    private String qtdvendalaste, qtdvendanow, somaqtdnew,qtdprodvend, idvenda, idprod,  qtd,    idocor, datavenda,  idforne,  valoruni, valorpago,  valortotal,  formadepagamento,  status,  parcela, qtdparcel,  valorparcela,  name, descri;
    public String DescriProd = "%",nome;
    private  String Origem;
	private  RadioButton buttonavista, buttonparcelado;

    private  ArrayList<ItemListViewFornecedor> mFornecedorList;
    private  AdapterSpinnerFornecedor mAdapter;
    private  Spinner spinnerteste;
    private String idf;
    SearchView searchView;
    JSONParser jsonParser = new JSONParser();
    JSONObject object =null;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> listfornecedor;
    ArrayAdapter<String > adapter;
    private static String IsertItem = "http://futsexta.16mb.com/Poker/IsertItem_OrdemMobile.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private ImageView imgaddnew;

    private View v;
    public static String IDORDEM = Status_Ordem.IDORDEM;
    public static String IDCLIENTE = Status_Ordem.idcliente;
    public VendasAdapter vendasAdapter;
    //public static RecyclerView myrecyclerview;
    //public static List<Vendas> lsvendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        searchView = (SearchView) findViewById (R.id.searchprod);
        imgaddnew = (ImageView) findViewById(R.id.imgaddnewprod);


        imgaddnew.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.v(TAG, " click");
                Intent it5 = new Intent(Produtos.this, Cadastro_Produtos.class);
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

        OcorList = new ArrayList<>();
        new GetDados1().execute();

        new GetDadosFornecedor().execute();




        lv = (ListView) findViewById(R.id.listviwerprod);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            if (Origem.equals("CONSULTA")){
                Intent i = new Intent(Produtos.this, Cadastro_Produtos.class);

                idprod = adapterListView.getItem(position).getNumero();
                descri = adapterListView.getItem(position).getTexto();
                valoruni = adapterListView.getItem(position).getNumero();

                i.putExtra("STRING_IDPROD", idprod);
                i.putExtra("STRING_DESCRICAO", descri);
                i.putExtra("STRING_QUANTIDADE", "1");
                i.putExtra("STRING_FORNECEDOR", "1");
                i.putExtra("STRING_VALOR", valoruni);

                startActivity(i);
            }else {
                // ListView Clicked item index
                qtdparcel = "0";
                descri = ((TextView) view.findViewById(R.id.tvnome)).getText().toString();
                idprod = adapterListView.getItem(position).getNumero();
				valoruni = adapterListView.getItem(position).getValor();



                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.custom_alertprod, null);
                final View alertLayoutparce = inflater.inflate(R.layout.custom_alertparcelamento, null);
                final View alertLayoutitemparce = inflater.inflate(R.layout.custom_alertatualizaitem, null);
                final EditText qtd = alertLayout.findViewById(R.id.edtquantidade);
                //final EditText idfornecedor = alertLayout.findViewById(R.id.edtidforne);
                final Spinner spinnerfornecedor = alertLayout.findViewById(R.id.spinnerfornecedor);

               // mAdapter = new AdapterSpinnerFornecedor(getApplicationContext(),mFornecedorList);
               // spinnerfornecedor.setAdapter(mAdapter);

                AdapterSpinnerFornecedor adapter = new AdapterSpinnerFornecedor(Produtos.this,
                        R.layout.spinner_fornecedor_layout, R.id.spinnerdescricao, mFornecedorList);
                spinnerfornecedor.setAdapter(adapter);

                spinnerfornecedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //first,  we have to retrieve the item position as a string
                        // then, we can change string value into integer
                        String item_position = String.valueOf(position);

                        //int positonInt = Integer.valueOf();
                         idf = spinnerfornecedor.getSelectedItem().toString();

                        //Toast.makeText(Produtos.this, "value is "+ idf, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //String idfornecedor = spinnerfornecedor.getPositionForView()

                // spinnerfornecedor.setAdapter(aa);
                //spinnerfornecedor.setSelection(0);

                
                
                
                final EditText valordevenda = alertLayout.findViewById(R.id.editvalorvendido);
                valordevenda.addTextChangedListener(new Status_Ordem.MoneyTextWatcher(valordevenda));

                final TextView tvdescriproduto = alertLayout.findViewById(R.id.tvdescriproduto);
                final TextView txnumeroid = alertLayout.findViewById(R.id.txnumeroid);
                final RadioButton buttonavista = alertLayout.findViewById(R.id.radioButtonAvista);

                tvdescriproduto.setText(descri);
                txnumeroid.setText(idprod);
                valordevenda.setText(valoruni);
                qtd.setText("1");
                //idfornecedor.setText("1");


                AlertDialog.Builder alert = new AlertDialog.Builder(Produtos.this);
                alert.setTitle("Adicionar item");
                alert.setView(alertLayout);
                alert.setCancelable(false);

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Adição cancelada", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idforne = idf;//idfornecedor.getText().toString();
                        valoruni = valordevenda.getText().toString();
                        String Vlr_Pexaold = String.format("[%s\\s]", Status_Ordem.MoneyTextWatcher.getCurrencySymbol());
                        valoruni = valoruni.replaceAll(Vlr_Pexaold, "");
                        valoruni = valoruni.replaceAll(",", "");

                        qtdprodvend = qtd.getText().toString();
                        //idcliente = Status_Ordem.idcliente;
                        datavenda = Status_Ordem.dataprev;
                        formadepagamento = "Avista - Dinheiro";
                        status = "Executando";


                        if(buttonavista.isChecked())
                        {
                            qtdparcel = "0";
                            valorparcela = "0";


                            final int position = getAdapterItemPosition(Integer.parseInt(idprod));

                            //Toast.makeText(getBaseContext(), "Position: "+ position , Toast.LENGTH_SHORT).show();

                           // String positionn = String.valueOf(position);
                            if (position == -1) {
                                     int iqtd = Integer.parseInt (qtdprodvend);
                                     int ivluni = Integer.parseInt (valoruni);
                                     int isoma = (iqtd*ivluni);
                                     valortotal = String.valueOf(isoma);
                                     formadepagamento = "Dinheiro - Avista";

                                     lsvendas.add(new Vendas(idvenda, idprod, qtdprodvend, IDCLIENTE, IDORDEM, datavenda, idforne, valoruni, valorpago, valortotal, formadepagamento, status, qtdparcel,parcela, valorparcela, descri));
                                    finish();
                            } else {
                                    int positiondel = getAdapterItemPositiondel(Integer.parseInt(idprod));

                                    if (positiondel == -1) {

                                        final TextView tvdescriproduto = alertLayoutitemparce.findViewById(R.id.tvdescriproduto);
                                        final EditText qtd = alertLayoutitemparce.findViewById(R.id.edtquantidade);
                                        final EditText addqtd = alertLayoutitemparce.findViewById(R.id.addedtquantidade);


                                        tvdescriproduto.setText(descri);
                                        //txnumeroid.setText(idprod);
                                        //valordevenda.setText(valoruni);
                                        qtd.setText(PecaFragment.lsvendas.get(position).getQtd());
                                        addqtd.setText(qtdprodvend);

                                        AlertDialog.Builder alertt = new AlertDialog.Builder(Produtos.this);
                                        alertt.setTitle("Item Encontrado na Lista:");
                                        alertt.setView(alertLayoutitemparce);
                                        alertt.setCancelable(false);

                                        alertt.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getBaseContext(), "Adição cancelada", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        alertt.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getBaseContext(), "Não encontarado parcelamento", Toast.LENGTH_SHORT).show();

                                                qtdvendalaste = PecaFragment.lsvendas.get(position).getQtd();

                                                int qtdvendalast = Integer.parseInt(qtdvendalaste);
                                                //int qtdvendasnow = Integer.parseInt(qtdprodvend);
                                                int qtdvendasnow = Integer.parseInt(addqtd.getText().toString());

                                                int somaqtd = (qtdvendalast + qtdvendasnow);
                                                somaqtdnew = String.valueOf(somaqtd);
                                                qtdprodvend = somaqtdnew;

                                                int iqtd = Integer.parseInt(qtdprodvend);
                                                int ivluni = Integer.parseInt(valoruni);
                                                int isoma = (iqtd * ivluni);

                                                valortotal = String.valueOf(isoma);

                                                lsvendas.remove(position);
                                                formadepagamento = "Dinheiro - Avista";

                                                lsvendas.add(new Vendas(idvenda, idprod, qtdprodvend, IDCLIENTE, IDORDEM, datavenda, idforne, valoruni,valorpago, valortotal, formadepagamento, status, qtdparcel, parcela, valorparcela, descri));
                                                finish();}



                                        });
                                        AlertDialog dialog1 = alertt.create();
                                        dialog1.show();


                                    }else {

                                        AlertDialog.Builder alertt = new AlertDialog.Builder(Produtos.this);
                                        alertt.setTitle("Item Parcelado encontrado");
                                        alertt.setView(alertLayoutparce);
                                        alertt.setCancelable(false);

                                        alertt.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getBaseContext(), "Adição cancelada", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        alertt.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getBaseContext(), "Parcelamento encontrado", Toast.LENGTH_SHORT).show();                    }



                                        });
                                        AlertDialog dialog1 = alertt.create();
                                        dialog1.show();

                                    }

                            }






                        }
                        else
                        {

                            LayoutInflater inflater = getLayoutInflater();
                            View alertLayoutParcela = inflater.inflate(R.layout.custom_alertparcelamento, null);

                            final EditText valorvendidoparcel = alertLayoutParcela.findViewById(R.id.editvalorvendidoparcel);
                            final EditText edtquantidadeparcela = alertLayoutParcela.findViewById(R.id.edtquantidadeparcela);

                            edtquantidadeparcela.setText("2");
                            valorvendidoparcel.setText(valoruni);

                            AlertDialog.Builder alertparcel = new AlertDialog.Builder(Produtos.this);
                            alertparcel.setTitle("Parcelamento");
                            alertparcel.setView(alertLayoutParcela);
                            alertparcel.setCancelable(false);

                            alertparcel.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getBaseContext(), "Adição cancelada", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });

                            alertparcel.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            qtdparcel = edtquantidadeparcela.getText().toString();

                                            int iqtd = Integer.parseInt (qtdprodvend);
                                            int ivluni = Integer.parseInt (valoruni);
                                            int isoma = (iqtd*ivluni);
                                            valortotal = String.valueOf(isoma);


                                            int ivltotal = Integer.parseInt(valortotal);
                                            int iparcela = Integer.parseInt (qtdparcel);
                                            int idivid = (ivltotal/iparcela);
                                            valorparcela =  String.valueOf(idivid);

                                            int p = Integer.parseInt (qtdparcel);

                                            String s = datavenda;
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                            Date dates = null;
                                            try {
                                                dates = sdf.parse(s);
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }

// somar 1 dia
                                            int cont = 1;
                                            if (p > 1){
                                                for (int counterr = 0; counterr < p; counterr++) {
                                                    Calendar cal = Calendar.getInstance();
                                                    cal.setTime(dates);
                                                    if (counterr == 0) {
                                                        cont = 0;
                                                    }else{
                                                        cal.add(Calendar.DAY_OF_MONTH, (cont*30));
                                                    }

                                                    dates = cal.getTime();

                                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                    String strDate = dateFormat.format(dates);
                                                    datavenda = strDate;
                                                    cont = cont +1;
                                                    parcela = cont+"-"+qtdparcel;
                                                    formadepagamento = "Parcelado";
                                                    lsvendas.add(new Vendas(idvenda, idprod,  qtdprodvend,  IDCLIENTE,  idocor, datavenda,  idforne,  valoruni,valorpago,  valortotal,  formadepagamento,  status,  qtdparcel, parcela,  valorparcela,  descri));
                                                }
                                            }



                                            Toast.makeText(getBaseContext(), "Parcelado", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                            });


                            AlertDialog parcel = alertparcel.create();
                            parcel.show();


                        }


                        //VendasAdapter.notifyItemChanged();

                        //finish();
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        }
    });

        newItemlist = new ArrayList<HashMap<String, String>>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                OcorList.clear();
                //adapterListView.notifyDataSetChanged();
                DescriProd = searchView.getQuery().toString();

                new GetDados1().execute();


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
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


    class GetDados1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (DescriProd != null) {
                DescriProd=DescriProd;
                //Log.i("Profile : ", "Not Null  "+DescriProd);
            } else{
                //Log.i("Profile : ", "Iss Null  "+DescriProd);
                DescriProd = "%";
            }

            List params = new ArrayList();
            params.add(new BasicNameValuePair("DescriProd",DescriProd));

            JSONObject json = jsonParser.makeHttpRequest(urlAll,"POST",
                    params);

            //Log.i("Profile JSON: ", json.toString());

            if (json != null) {
                try {
                    JSONObject parent = new JSONObject(String.valueOf(json));
                    JSONArray eventDetails = parent.getJSONArray("produtos");

                    itens = new ArrayList<ItemListViewPecas>();
                    newItemlist.clear();

                    for (int i = 0; i < eventDetails.length(); i++)
                    {
                        object = eventDetails.getJSONObject(i);
                        String id = object.getString("Id");
                        String descri = object.getString("descricao");
                        String idforn = object.getString("idfornecedor");
                        String quantidade = object.getString("quantidade");
                        String valorvendido = object.getString("valor");



                        idprod = id;
                        idforne =idforn;

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("numero","1");
                        newItemlist.add(map);

                        ItemListViewPecas item1 = new ItemListViewPecas(descri,quantidade,id, valorvendido, R.mipmap.trabalho100);
                        itens.add(item1);

                        HashMap<String, String> contact = new HashMap<>();
                        // adding each child node to HashMap key => value
                        contact.put("numero", "44");
                        contact.put("nome", "ok");
                        contact.put("descri", "ok");

                        //Log.e(TAG, "Descri:" + contact);
                        // adding contact to contact list
                        OcorList.add(contact);
                    }

                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Produto não encontrado." ,
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
                adapterListView = new AdapterListViewPecas(Produtos.this, itens);
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
}
