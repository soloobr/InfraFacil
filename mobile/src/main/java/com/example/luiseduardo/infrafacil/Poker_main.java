package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.JSONParser.json;
import static com.example.luiseduardo.infrafacil.PecaFragment.Somavebdas;
import static com.example.luiseduardo.infrafacil.PecaFragment.lsvendas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private  int Pozi;
    static Boolean Delete = false;
    static Boolean Edit = false;

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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                idjogo = itens.get(i).getId();
                //Toast.makeText(mContext, "Long! " + idplayer, Toast.LENGTH_SHORT).show();
                descrijogo = itens.get(i).getDescricao();
                //Pozi = getAdapterPosition();
                myPopupMenu(view);

                return true;
            }
        });

        //newItemlist = new ArrayList<HashMap<String, String>>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DescriJodo = searchView.getQuery().toString();

                new GetDados_jogos_from_search().execute();

                return false;
            }
        });


    }


    private void myPopupMenu(View v) {

        PopupMenu popupMenu = new PopupMenu(Poker_main.this, v);

        /*  The below code in try catch is responsible to display icons*/
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());

        popupMenu.getMenu().findItem(R.id.delete).setTitle("Excluir Jogo");
        popupMenu.getMenu().findItem(R.id.edite).setTitle("Editar Jogo");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Toast. Maketext (mainactivity. This, "clicked" + item. Gettitle(), toast. Length_short). Show();

                switch (item.getItemId()) {
                    case R.id.delete:
                        //Toast.makeText(mContext, "clicked delete" + idplayer + " "+idjogo, Toast.LENGTH_SHORT).show();
                        showAlert(Poker_main.this, "Deletar", "Deletando Jogo");
                        //new DeletePlayer().execute();
                        return true;
                    case R.id.edite:
                        showAlert(Poker_main.this, "Editar", "Editando Jogo");

                        //Toast.makeText(mContext, "clicked edite" + idplayer, Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return true;
                }
            }
        });
        //Show menu
        popupMenu.show();
    }
    public void showAlert(Context context, String title, String message){


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.custom_alertnewplayer, null);



        if (title.equals("Deletar")){
            final ImageView img = promptView.findViewById(R.id.imgaddplayers);
            img.setImageResource(R.mipmap.usercircledelete);

            final TextView tvaction = promptView.findViewById(R.id.tvactionplayer);
            tvaction.setText("Excluir Jogo");

            final EditText ednome = promptView.findViewById(R.id.ednomePlayer);
            ednome.setText(descrijogo);
            //ednome.setEnabled(false);
            ednome.setFocusable(false);
            Delete = true;
        }

        if (title.equals("Editar")){

            final ImageView img = promptView.findViewById(R.id.imgaddplayers);
            img.setImageResource(R.mipmap.usercirclegear128);

            final TextView tvaction = promptView.findViewById(R.id.tvactionplayer);
            tvaction.setText("Editando Jogo");



            final EditText ednome = promptView.findViewById(R.id.ednomePlayer);
            ednome.setText(descrijogo);
            ednome.setSelection(ednome.getText().length());
            ednome.setEnabled(true);

            Edit = true;
        }

        alert.setView(promptView);
        alert.setCancelable(false);
        //Poker.this.setFinishOnTouchOutside(false);

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete = false;
                Edit = false;
                //Toast.makeText(context, "Edição cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (Delete){

                    //new customAdapter.DeletePlayer().execute();
                    //list.remove(Pozi);
                    //notifyItemRemoved(Pozi);
                    Delete = false;
                }
                if (Edit){
                    final EditText ednome = promptView.findViewById(R.id.ednomePlayer);
                    //sUsername = String.valueOf(ednome.getText());

                    //new customAdapter.UpdatePlayer().execute();

                    ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ednome.getWindowToken(), 0);
                    Edit = false;
                }


            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();

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
            pDialog = new ProgressDialog(Poker_main.this);
            pDialog.setMessage("Buscando Jogos...");
            pDialog.show();
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
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json.isEmpty()) {

            }else {
                adapterListView = new AdapterListViewPoker(Poker_main.this, itens);
                lv.setAdapter(adapterListView);
                lv.setCacheColorHint(Color.TRANSPARENT);
            }

        }
    }
    class GetDados_jogos_from_search extends AsyncTask<Void, Void, Void> {

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

    public void onStart(){
        super.onStart();
        //new Poker_main.GetDados_jogos().execute();
    }
    public void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //new Poker_main.GetDados_jogos().execute();
    }
}
