package com.example.luiseduardo.infrafacil;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;



public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1888;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();

        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.custom_alertnewplayer, container, false);


        EditText text=(EditText) root.findViewById(R.id.ednomePlayer);
        text.setOnClickListener(this);

        CircleImageView img=(CircleImageView)root.findViewById(R.id.imgaddplayers);
        img.setOnClickListener(this);


        return root;
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgaddplayers: {
                getDialog().dismiss();

                startActivityForResult(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_REQUEST);
                Toast.makeText(getActivity(), "You have selected camera", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }



}