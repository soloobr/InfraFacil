package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.Poker_new.MoneyTextWatcher.getCurrencySymbol;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Poker_new extends AppCompatActivity implements
        View.OnClickListener {
    Button btncanceljogo, btnsavejogo;
    ImageButton btndata;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static String Mes,Dia,Ano,sData,sSdata;
    TextView dateEditText;
    private String datejogo ;
    private EditText editvalorentrada,editvalorrebuy,editvaloraddon;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String GETINFO_URL = "http://futsexta.16mb.com/Poker/Poker_insert_Jogo.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String  sUsername, ssData, sVldentrada,sVldrebuy,sVldaddon,sQtdentrada,sQtdrebuy,sQtdaddon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpoker);
        btndata =  (ImageButton) findViewById(R.id.btn_date);
        btncanceljogo = (Button) findViewById(R.id.btnCancelarnjogo);
        dateEditText = (TextView) findViewById(R.id.editTextDate);
        editvalorentrada = (EditText) findViewById(R.id.edvalorentrada);
        editvalorrebuy = (EditText) findViewById(R.id.edvalorrebuy);
        editvaloraddon = (EditText) findViewById(R.id.edvaloraddon);

        editvalorentrada.addTextChangedListener(new Poker_new.MoneyTextWatcher(editvalorentrada));
        editvalorrebuy.addTextChangedListener(new Poker_new.MoneyTextWatcher(editvalorrebuy));
        editvaloraddon.addTextChangedListener(new Poker_new.MoneyTextWatcher(editvaloraddon));


        btncanceljogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @SuppressLint("ResourceAsColor")
    public void onClick(View view) {

    }
    @SuppressLint("ResourceAsColor")
    public void onClickDATA(View v) {
        //Toast.makeText(this, "Date", Toast.LENGTH_SHORT).show();
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Poker_new.this,
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

                        dateEditText.setText(datejogo);  ;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @SuppressLint("ResourceAsColor")
    public void onClickSAVE(View v) {


        TextView dateEditText = (TextView) findViewById(R.id.editTextDate);
        ssData = dateEditText.getText().toString();
        if (ssData.matches("")) {
            Toast.makeText(this, "Favor Selecionar a data do Jogo", Toast.LENGTH_SHORT).show();
            btndata.performClick();
            return;
        }

        AutoCompleteTextView usernameEditText = (AutoCompleteTextView) findViewById(R.id.namejogo);
        sUsername = usernameEditText.getText().toString();
        if (sUsername.matches("")) {
            Toast.makeText(this, "Favor Preencher o nome do Jogo", Toast.LENGTH_SHORT).show();
            usernameEditText.requestFocus();
            return;
        }


        TextView Vldentrada = (TextView) findViewById(R.id.edvalorentrada);
        sVldentrada = Vldentrada.getText().toString();

        String replaceable = String.format("[%s\\s]", getCurrencySymbol());
        sVldentrada = sVldentrada.replaceAll(replaceable, "");
        sVldentrada = sVldentrada.replaceAll(",", "");

        if ( (sVldentrada.matches("")) || (sVldentrada.matches("000") )) {
            Toast.makeText(this, "Favor preencher o valor da Entrada", Toast.LENGTH_SHORT).show();
            Vldentrada.requestFocus();
            return;
        }
        TextView Qtdentrada = (TextView) findViewById(R.id.edqtdentrada);
        sQtdentrada = Qtdentrada.getText().toString();
        if ( (sQtdentrada.matches("")) || (sQtdentrada.matches("0") )) {
            Toast.makeText(this, "Favor preencher a quantidade de ficha da Entrada", Toast.LENGTH_SHORT).show();
            Qtdentrada.requestFocus();
            return;
        }

        TextView Vldrebuy = (TextView) findViewById(R.id.edvalorrebuy);
        sVldrebuy = Vldrebuy.getText().toString();
        sVldrebuy = sVldrebuy.replaceAll(replaceable, "");
        sVldrebuy = sVldrebuy.replaceAll(",", "");
        if ((sVldrebuy.matches("")) || (sVldrebuy.matches("000")) ) {
            Toast.makeText(this, "Favor preencher o valor da Rebuy", Toast.LENGTH_SHORT).show();
            Vldrebuy.requestFocus();
            return;
        }
        TextView Qtdrebuy = (TextView) findViewById(R.id.edqtdrebuy);
        sQtdrebuy = Qtdrebuy.getText().toString();
        if ( (sQtdrebuy.matches("")) || (sQtdrebuy.matches("0") )) {
            Toast.makeText(this, "Favor preencher a quantidade de ficha do Rebuy", Toast.LENGTH_SHORT).show();
            Qtdrebuy.requestFocus();
            return;
        }

        TextView Vldaddon = (TextView) findViewById(R.id.edvaloraddon);
        sVldaddon = Vldaddon.getText().toString();
        sVldaddon = sVldaddon.replaceAll(replaceable, "");
        sVldaddon = sVldaddon.replaceAll(",", "");
        if ((sVldaddon.matches("")) || (sVldaddon.matches("000")) ) {
            Toast.makeText(this, "Favor preencher o valor da Addon", Toast.LENGTH_SHORT).show();
            Vldaddon.requestFocus();
            return;
        }

        TextView Qtdaddon = (TextView) findViewById(R.id.edqtdaddon);
        sQtdaddon = Qtdaddon.getText().toString();
        if ( (sQtdaddon.matches("")) || (sQtdaddon.matches("0") )) {
            Toast.makeText(this, "Favor preencher a quantidade de ficha do Addon", Toast.LENGTH_SHORT).show();
            Qtdaddon.requestFocus();
            return;
        }

        new InsertJogo().execute();

    }

    class InsertJogo extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Poker_new.this);
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

                //params.add(new BasicNameValuePair("id", Data_Local));
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
                    Log.d("Jogo não Criado", json.getString(TAG_MESSAGE));
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
                Toast.makeText(Poker_new.this, file_url, Toast.LENGTH_LONG).show();
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
}
