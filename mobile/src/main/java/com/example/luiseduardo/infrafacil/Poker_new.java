package com.example.luiseduardo.infrafacil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Poker_new extends AppCompatActivity implements
        View.OnClickListener {
    Button btncanceljogo, btnsavejogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpoker);

        btncanceljogo = (Button) findViewById(R.id.btnCancelarnjogo);

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


}
