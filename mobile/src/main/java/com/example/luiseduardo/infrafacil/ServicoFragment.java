package com.example.luiseduardo.infrafacil;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicoFragment extends Fragment {

    /**
     * @param context
     */

    public static EditText editServRealizado2 = Status_Ordem.editServRealizado2;

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

    public ServicoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment

       // editServRealizado2 = (EditText) findViewById(R.id.editServRealizado2);

        View view = inflater.inflate(R.layout.fragment_servico,null);
        editServRealizado2 = (EditText) view.findViewById(R.id.editServRealizado2);
        editServRealizado2.setText(editServRealizado2.getText());
        //return inflater.inflate(R.layout.fragment_servico, container, false);
        return (view);
    }

}
