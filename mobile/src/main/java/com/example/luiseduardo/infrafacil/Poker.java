package com.example.luiseduardo.infrafacil;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Poker extends Activity implements ItemClickListener{

    private AdapterListViewPlayers adapterListViewPlayers;
    private static String url = "http://futsexta.16mb.com/Poker/poker_get_jogo.php";


    //ArrayList<HashMap<String, String>> PlayersList;
    //public static ArrayList<PlayersListView> itens;
    public static List<PlayersListView> lsplayer;
    //ArrayList<HashMap<String, String>> newItemlist = new ArrayList<HashMap<String, String>>();
    private ListView lv;
    private ProgressDialog pDialog;
    public static RecyclerView myrecyclerview;

    private static RecyclerView recyclerView;
    private customAdapter  mAdapter;
    //ArrayList<PlayersListView> list = new ArrayList<>();
    private List<PlayersListView> cities;

    ImageButton btnRebuy;

    static View v;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public static String  idplayer, rebuy, addon;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_poker);

        //lsplayer = new ArrayList<>();

       // lv = (ListView) findViewById(R.id.listviwerplayers);
       // lv.setOnItemClickListener(this);

        //new GetDados().execute();

        //lsplayer.add(new PlayersListView("20", "1", "Luis Eduardo", "0",  "0", "200",1));


        //recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        //list.add("Luis Eduardo");

        btnRebuy = (ImageButton) findViewById(R.id.bntaddon);
        //btnRebuy.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
                //list.add("Mammahe");
              //  mAdapter.notifyDataSetChanged();

            //}
        //});



       // myrecyclerview = (RecyclerView) findViewById(R.id.listviwerplayers);

       // myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

       // AdapterListViewPlayers adapterListViewPlayers  = new AdapterListViewPlayers(Poker.this,lsplayer);

       // myrecyclerview.setAdapter(adapterListViewPlayers);

    }

    @Override
    public void onClick(View view, int position) {
        //mAdapter.notifyDataSetChanged();
        final PlayersListView city = lsplayer.get(position);
        btnRebuy = (ImageButton) findViewById(R.id.bntaddon);
        btnRebuy.setOnClickListener(new View.OnClickListener() {
          @Override
        public void onClick(View view) {
        //list.add("Mammahe");
          mAdapter.notifyDataSetChanged();
              Toast.makeText(getApplicationContext(),
                      "Atualizou " + city.getNome(),
                      Toast.LENGTH_LONG)
                      .show();
        }
        });

        btnRebuy = (ImageButton) findViewById(R.id.bntaddon);
        Toast.makeText(getApplicationContext(),
                "Atualizou " + view.getId(),
                Toast.LENGTH_LONG)
                .show();
        if (view.getId() == R.id.bntaddon) {

        }

        /*btnRebuy.setOnClickListener(new View.OnClickListener() {



          @Override
        public void onClick(View view) {
        //list.add("Mammahe");
          //mAdapter.notifyDataSetChanged();
              Toast.makeText(getApplicationContext(),
                      "Atualizou " + city.getNome(),
                      Toast.LENGTH_LONG)
                      .show();
        }
        });
*/
    }

    //@Override
    //public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    //}
    public static void methodOnBtnClick(int position)
    {
        //lsplayer.remove(position);
        //recyclerView = (RecyclerView) findViewById(R.id.listviwerplayers);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    class GetDados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Poker.this);
            pDialog.setMessage("Buscando Jogadores...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("jogo");

                    lsplayer = new ArrayList<PlayersListView>();

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String idjogo = c.getString("idjogo");
                        String id = c.getString("id");
                        String name = c.getString("Nome_Player");
                        String rebuy = c.getString("rebuy");
                        String addon = c.getString("addon");
                        String valor = c.getString("Valor");

                        lsplayer.add(new PlayersListView(id, idjogo, name,  rebuy,  addon, valor,1));

                    }

                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            //tx.setText(contador);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            //myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

            //myrecyclerview = (RecyclerView) findViewById(R.id.listviwerplayers);
            //AdapterListViewPlayers adapterListViewPlayers  = new AdapterListViewPlayers(Poker.this,lsplayer);
            //myrecyclerview.setAdapter(adapterListViewPlayers);

             //myrecyclerview = (RecyclerView) findViewById(R.id.listviwerplayers);

             //myrecyclerview.setLayoutManager(new LinearLayoutManager(Poker.this));

             //AdapterListViewPlayers adapterListViewPlayers  = new AdapterListViewPlayers(Poker.this,lsplayer);

             //myrecyclerview.setAdapter(adapterListViewPlayers);
            recyclerView = (RecyclerView) findViewById(R.id.listviwerplayers);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new customAdapter (lsplayer, R.layout.item_players, Poker.this);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setClickListener(Poker.this);

        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        new Poker.GetDados().execute();

    }


}

