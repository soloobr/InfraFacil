package com.example.luiseduardo.infrafacil;

import static com.example.luiseduardo.infrafacil.Poker.lsplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {

    private  List<PlayersListView> list;
    private int rowLayout;
    private Context mContext;
    private ItemClickListener clickListener;

    public customAdapter( List<PlayersListView> list,int rowLayout,Context context) {
        this.list = list;
        this.rowLayout = rowLayout;
        this.mContext = context;

    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder myViewHolder, int position) {
        final PlayersListView city = list.get(position);

        myViewHolder.title.setText( city.getNome());
        //myViewHolder.title.setText( list.get(position));

    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
        //return list.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageButton btnaddon;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.main_line_nome);
            btnaddon = (ImageButton) view.findViewById(R.id.bntaddon);

            view.setTag(view);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition());
            //if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }





}