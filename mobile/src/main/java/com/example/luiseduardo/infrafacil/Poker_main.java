package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.JSONParser.json;
import static com.example.luiseduardo.infrafacil.PecaFragment.Somavebdas;
import static com.example.luiseduardo.infrafacil.PecaFragment.lsvendas;
import static com.example.luiseduardo.infrafacil.Poker_new.MoneyTextWatcher.getCurrencySymbol;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Poker_main extends AppCompatActivity implements AdapterView.OnItemClickListener,DatePickerDialog.OnDateSetListener {
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
    private static String GETINFO_URL = "http://futsexta.16mb.com/Poker/Poker_insert_Jogo.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private ImageView imgaddnew;
    private ImageButton btnDatePicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String Mes,Dia,Ano,sData;
    ImageButton btndata;
    TextView dateEditText;

    private View v;
    public static String IDORDEM = Status_Ordem.IDORDEM;
    public static String IDCLIENTE = Status_Ordem.idcliente;
    public VendasAdapter vendasAdapter;
    //public static RecyclerView myrecyclerview;
    //public static List<Vendas> lsvendas;
    private  int Pozi;
    static Boolean Delete = false;
    static Boolean Edit = false;
    String  sUsername, ssData, sVldentrada,sVldrebuy,sVldaddon,sQtdentrada,sQtdrebuy,sQtdaddon;
    Button btncanceljogo, btnsavejogo;
    //TextView dateEditText;
    //private EditText editvalorentrada,editvalorrebuy,editvaloraddon;
    private String datejogo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpoker);


        searchView = (SearchView) findViewById (R.id.searchjogo);
        imgaddnew = (ImageView) findViewById(R.id.imgaddnewjogo);

        btnDatePicker = (ImageButton) findViewById (R.id.btn_searchedata);
        /*btncanceljogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

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
    @SuppressLint("ResourceAsColor")
    public void onClickDATA(View v) {
        //Toast.makeText(this, "Date", Toast.LENGTH_SHORT).show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Poker_main.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        ;
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
                        sData = String.valueOf(year)+Mes+Dia;
                        //new Poker_main.GetDados_jogos_from_date().execute();
                        String ds1  = String.valueOf(year)+"-"+Mes+"-"+Dia;

                        SimpleDateFormat sdf1  = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            datejogo = sdf2.format(sdf1.parse(ds1));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                         TextView dateEditText = (TextView) findViewById(R.id.editTextDate);
                        dateEditText.setText(datejogo);  ;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
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
        View promptView = layoutInflater.inflate(R.layout.activity_newpoker, null);
//custom_alertnewplayer


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




            final ImageView img = promptView.findViewById(R.id.imgNewCliente);
            img.setImageResource(R.mipmap.usercirclegear128);

            final TextView tvaction = promptView.findViewById(R.id.tvactionplayer);
            //tvaction.setText("Editando Jogo");



            final EditText ednome = promptView.findViewById(R.id.ednomePlayer);
            //ednome.setText(descrijogo);
            //ednome.setSelection(ednome.getText().length());
            //ednome.setEnabled(true);


            final ImageButton btndata = promptView.findViewById(R.id.btn_date);

            btndata.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    //showDatePickerDialog();
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
                                    //searchidata = String.valueOf(year)+Mes+Dia;
                                    final AutoCompleteTextView usernameEditText = (AutoCompleteTextView) promptView.findViewById(R.id.namejogo);
                                    usernameEditText.setText(String.valueOf(year)+Mes+Dia);
                                    //new GetDados_jogos_from_date().execute();
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }
            });

            Edit = true;
        }


        alert.setView(promptView);
        alert.setCancelable(false);

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
/*
                switch (0) {
                    case 0:
                        break;
                   }
                if (Delete){

                    //new customAdapter.DeletePlayer().execute();
                    //list.remove(Pozi);
                    //notifyItemRemoved(Pozi);
                    Delete = false;

                }
                if (Edit){
                    final ImageButton btndata =  (ImageButton) promptView.findViewById(R.id.btn_date);
                    final Button btncanceljogo = (Button) promptView.findViewById(R.id.btnCancelarnjogo);
                    //final TextView dateEditText = (TextView) promptView.findViewById(R.id.editTextDate);
                    final EditText editvalorentrada = (EditText) promptView.findViewById(R.id.edvalorentrada);
                    final EditText editvalorrebuy = (EditText) promptView.findViewById(R.id.edvalorrebuy);
                    final EditText editvaloraddon = (EditText) promptView.findViewById(R.id.edvaloraddon);

                    //editvalorentrada.addTextChangedListener(new Poker_main.MoneyTextWatcher(editvalorentrada));
                    //editvalorrebuy.addTextChangedListener(new Poker_main.MoneyTextWatcher(editvalorrebuy));
                    //editvaloraddon.addTextChangedListener(new Poker_main.MoneyTextWatcher(editvaloraddon));


                    final TextView dateEditText = (TextView) promptView.findViewById(R.id.editTextDate);

                    ssData = dateEditText.getText().toString();
                    if (ssData.matches("")) {
                        Toast.makeText(Poker_main.this, "Favor Selecionar a data do Jogo", Toast.LENGTH_SHORT).show();
                        btndata.performClick();
                        return;
                    }

                    final AutoCompleteTextView usernameEditText = (AutoCompleteTextView) promptView.findViewById(R.id.namejogo);
                    sUsername = usernameEditText.getText().toString();
                    if (sUsername.matches("")) {
                        Toast.makeText(Poker_main.this, "Favor Preencher o nome do Jogo", Toast.LENGTH_SHORT).show();
                        usernameEditText.requestFocus();
                        return;
                    }


                    final TextView Vldentrada = (TextView) promptView.findViewById(R.id.edvalorentrada);
                    sVldentrada = Vldentrada.getText().toString();

                    String replaceable = String.format("[%s\\s]", getCurrencySymbol());
                    sVldentrada = sVldentrada.replaceAll(replaceable, "");
                    sVldentrada = sVldentrada.replaceAll(",", "");

                    if ( (sVldentrada.matches("")) || (sVldentrada.matches("000") )) {
                        Toast.makeText(Poker_main.this, "Favor preencher o valor da Entrada", Toast.LENGTH_SHORT).show();
                        Vldentrada.requestFocus();
                        return;
                    }
                    final TextView Qtdentrada = (TextView) promptView.findViewById(R.id.edqtdentrada);
                    sQtdentrada = Qtdentrada.getText().toString();
                    if ( (sQtdentrada.matches("")) || (sQtdentrada.matches("0") )) {
                        Toast.makeText(Poker_main.this, "Favor preencher a quantidade de ficha da Entrada", Toast.LENGTH_SHORT).show();
                        Qtdentrada.requestFocus();
                        return;
                    }

                    final TextView Vldrebuy = (TextView) promptView.findViewById(R.id.edvalorrebuy);
                    sVldrebuy = Vldrebuy.getText().toString();
                    sVldrebuy = sVldrebuy.replaceAll(replaceable, "");
                    sVldrebuy = sVldrebuy.replaceAll(",", "");
                    if ((sVldrebuy.matches("")) || (sVldrebuy.matches("000")) ) {
                        Toast.makeText(Poker_main.this, "Favor preencher o valor da Rebuy", Toast.LENGTH_SHORT).show();
                        Vldrebuy.requestFocus();
                        return;
                    }
                    final TextView Qtdrebuy = (TextView) promptView.findViewById(R.id.edqtdrebuy);
                    sQtdrebuy = Qtdrebuy.getText().toString();
                    if ( (sQtdrebuy.matches("")) || (sQtdrebuy.matches("0") )) {
                        Toast.makeText(Poker_main.this, "Favor preencher a quantidade de ficha do Rebuy", Toast.LENGTH_SHORT).show();
                        Qtdrebuy.requestFocus();
                        return;
                    }

                    final TextView Vldaddon = (TextView) promptView.findViewById(R.id.edvaloraddon);
                    sVldaddon = Vldaddon.getText().toString();
                    sVldaddon = sVldaddon.replaceAll(replaceable, "");
                    sVldaddon = sVldaddon.replaceAll(",", "");
                    if ((sVldaddon.matches("")) || (sVldaddon.matches("000")) ) {
                        Toast.makeText(Poker_main.this, "Favor preencher o valor da Addon", Toast.LENGTH_SHORT).show();
                        Vldaddon.requestFocus();
                        return;
                    }

                    final TextView Qtdaddon = (TextView) promptView.findViewById(R.id.edqtdaddon);
                    sQtdaddon = Qtdaddon.getText().toString();
                    if ( (sQtdaddon.matches("")) || (sQtdaddon.matches("0") )) {
                        Toast.makeText(Poker_main.this, "Favor preencher a quantidade de ficha do Addon", Toast.LENGTH_SHORT).show();
                        Qtdaddon.requestFocus();
                        return;
                    }

                    //--new Poker_main.UpdatetJogo().execute();

                    final EditText ednome = promptView.findViewById(R.id.ednomePlayer);
                    //sUsername = String.valueOf(ednome.getText());

                    //new customAdapter.UpdatePlayer().execute();

                    ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ednome.getWindowToken(), 0);
                    Edit = false;
                }

*/
            }
        });
        final AlertDialog dialog = alert.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView dateEditText = (TextView) promptView.findViewById(R.id.editTextDate);
                final ImageButton btndataa = (ImageButton) promptView.findViewById(R.id.btn_date);

                ssData = dateEditText.getText().toString();
                if (!ssData.matches("")) {
                    Toast.makeText(Poker_main.this, "foi", Toast.LENGTH_SHORT).show();
                    //dateEditText.requestFocus();
                    dialog.dismiss();
                }else{
                    Toast.makeText(Poker_main.this, "Favor Selecionar a data do Jogo", Toast.LENGTH_SHORT).show();
                    btndataa.performClick();
                }
            }
        });

    }

    @SuppressLint("ValidFragment")
    private static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }

    }

    //@Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
        // TODO Auto-generated method stub

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
    class UpdatetJogo extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Poker_main.this);
            pDialog.setMessage("Criando Jogo");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {




            int success;
            try {

                // Building Parameters
                List params = new ArrayList();

                params.add(new BasicNameValuePair("id", idjogo));
                params.add(new BasicNameValuePair("Descricao", sUsername));
                params.add(new BasicNameValuePair("Data", sData));
                params.add(new BasicNameValuePair("vlentrada", sVldentrada));
                params.add(new BasicNameValuePair("qtdfichaentrada", sQtdentrada));
                params.add(new BasicNameValuePair("vlrebuy", sVldrebuy));
                params.add(new BasicNameValuePair("qtdficharebuy", sQtdrebuy));
                params.add(new BasicNameValuePair("vladdon", sVldaddon));
                params.add(new BasicNameValuePair("qtdfichaaddon", sQtdaddon));

                //Log.d("Debug!", "starting");

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(GETINFO_URL, "POST",
                        params);


                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("successo!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);

                } else {
                    Log.d("Jogo não Atualizado", json.getString(TAG_MESSAGE));
                    finish();
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Poker_main.this, file_url, Toast.LENGTH_LONG).show();
            }
            //new Poker_main.GetDados_jogos();
        }


    }

    public static class MoneyTextWatcher implements TextWatcher {
        private WeakReference<EditText> editTextWeakReference;
        private final Locale locale = Locale.getDefault();

        public MoneyTextWatcher(EditText editText) {
            this.editTextWeakReference = new WeakReference<>(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            editText.removeTextChangedListener(this);

            BigDecimal parsed = parseToBigDecimal(editable.toString());
            String formatted;
            formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);

            //Remove o símbolo da moeda e espaçamento pra evitar bug
            //String replaceable = String.format("[%s\\s]", getCurrencySymbol());
            //String cleanString = formatted.replaceAll(replaceable, "");

            //editText.setText(cleanString);
            editText.setText(formatted);
            //editText.setSelection(cleanString.length());
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }


        public void afterTextChangeBanco(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            editText.removeTextChangedListener(this);

            BigDecimal parsed = parseToBigDecimal(editable.toString());
            String formatted;
            formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);

            //Remove o símbolo da moeda e espaçamento pra evitar bug
            String replaceable = String.format("[%s\\s]", getCurrencySymbol());
            String cleanString = formatted.replaceAll(replaceable, "");

            editText.setText(cleanString);
            //editText.setText(formatted);
            editText.setSelection(cleanString.length());
            //editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
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

        public static String formatPrice(String price) {
            //Ex - price = 2222
            //retorno = 2222.00
            DecimalFormat df = new DecimalFormat("0.00");
            return String.valueOf(df.format(Double.valueOf(price)));

        }

        public static String formatTextPrice(String price) {
            //Ex - price = 3333.30
            //retorna formato monetário em Br = 3.333,30
            //retorna formato monetário EUA: 3,333.30
            //retornar formato monetário de alguns países europeu: 3 333,30
            BigDecimal bD = new BigDecimal(formatPriceSave(formatPrice(price)));
            String newFormat = null;
            newFormat = String.valueOf(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(bD));
            String replaceable = String.format("[%s]", getCurrencySymbol());
            return newFormat.replaceAll(replaceable, "");

        }

        static String formatPriceSave(String price) {
            //Ex - price = $ 5555555
            //return = 55555.55 para salvar no banco de dados
            String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
            String cleanString = price.replaceAll(replaceable, "");
            StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

            return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));

        }

        static String formatPriceSaveBanco(String price) {
            //Ex - price = $ 5555555
            //return = 55555.55 para salvar no banco de dados
            String replaceable = String.format("[%s,.\\s]", getCurrencySymbol());
            String cleanString = price.replaceAll(replaceable, "");
            StringBuilder stringBuilder = new StringBuilder(cleanString.replaceAll(" ", ""));

            return String.valueOf(stringBuilder.insert(cleanString.length() - 2, '.'));



        }

        public static String getCurrencySymbol() {
            return NumberFormat.getCurrencyInstance(Locale.getDefault()).getCurrency().getSymbol();
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
