package com.example.luiseduardo.infrafacil;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import static java.security.AccessController.getContext;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

public class Poker extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private AdapterListViewPlayers adapterListViewPlayers;
    private static String url = "http://futsexta.16mb.com/Poker/poker_get_jogo.php";


    //ArrayList<HashMap<String, String>> PlayersList;
    //public static ArrayList<PlayersListView> itens;
    public static List<PlayersListView> lsplayer;
    //ArrayList<HashMap<String, String>> newItemlist = new ArrayList<HashMap<String, String>>();
    private ListView lv;
    private ProgressDialog pDialog;
    public static RecyclerView myrecyclerview;
    static View v;

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public static String  idplayer, rebuy, addon;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_poker);

        lsplayer = new ArrayList<>();

       // lv = (ListView) findViewById(R.id.listviwerplayers);
       // lv.setOnItemClickListener(this);



        myrecyclerview = (RecyclerView) findViewById(R.id.listviwerplayers);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(this));

        AdapterListViewPlayers adapterListViewPlayers  = new AdapterListViewPlayers(Poker.this,lsplayer);

        myrecyclerview.setAdapter(adapterListViewPlayers);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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


            // Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //Log.e(TAG, "Não nulo");
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("jogo");

                    //Log.e(TAG, "Count : " + contacts.length());

                    //contador = String.valueOf(contacts.length()+1);
                    //PlayersList.clear();

                    lsplayer = new ArrayList<PlayersListView>();
                    //newItemlist.clear();
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String idjogo = c.getString("idjogo");
                        String id = c.getString("id");
                        String name = c.getString("Nome_Player");
                        String rebuy = c.getString("rebuy");
                        String addon = c.getString("addon");
                        String valor = c.getString("Valor");

                        //HashMap<String, String> map = new HashMap<String, String>();
                        //map.put("numero", id);
                        //newItemlist.add(map);

                        //Log.e(TAG, "Status = " + status);

                        lsplayer.add(new PlayersListView(id, idjogo, name,  rebuy,  addon, valor,1));
                        //PlayersListView item1 = new PlayersListView(id, idjogo, name, rebuy, addon, R.mipmap.trabalho100);

                        //itens.add(item1);


                        // tmp hash map for single contact
                        //HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        //contact.put("id", id);
                        //contact.put("idjogo", idjogo);
                        //c/ontact.put("nome", name);
                        //contact.put("rebuy", rebuy);
                        //contact.put("addon", addon);

                        // adding contact to contact list
                        //PlayersList.add(contact);

                    }
                   // Log.e(TAG, String.valueOf(lsplayer));
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

            myrecyclerview = (RecyclerView) findViewById(R.id.listviwerplayers);
            AdapterListViewPlayers adapterListViewPlayers  = new AdapterListViewPlayers(Poker.this,lsplayer);
            myrecyclerview.setAdapter(adapterListViewPlayers);

            //AdapterListViewPlayers adapterListViewPlayers = new AdapterListViewPlayers(Poker.this, lsplayer);
            //adapterListViewPlayers = new AdapterListViewPlayers(Poker.this, lsplayer);
            //lv.setAdapter(adapterListViewPlayers);
            //lv.setCacheColorHint(Color.TRANSPARENT);

        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        new Poker.GetDados().execute();
    }


}

