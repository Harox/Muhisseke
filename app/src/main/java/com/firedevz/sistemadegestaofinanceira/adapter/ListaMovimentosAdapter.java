package com.firedevz.sistemadegestaofinanceira.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Movimentos;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;

public class ListaMovimentosAdapter extends RecyclerView.Adapter<ListaMovimentosAdapter.ViewHolder>  {

    private List<Movimentos> movimentos;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);

    public ListaMovimentosAdapter(List<Movimentos> movimentos) {
        this.movimentos = movimentos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtContaMovimento, txtValorMovimento, txtdataMovimento,txtTipoMovimento;
        private LinearLayout listaMovimentos;



        public ViewHolder(View itemView) {
            super(itemView);

            txtContaMovimento = (TextView) itemView.findViewById(R.id.txtContaMovimento);
            txtValorMovimento = (TextView) itemView.findViewById(R.id.txtValorMovimento);
            txtdataMovimento = (TextView) itemView.findViewById(R.id.txtdataMovimento);
            txtTipoMovimento = (TextView) itemView.findViewById(R.id.txtTipoMovimento);
            listaMovimentos = (LinearLayout) itemView.findViewById(R.id.listaMovimentos);
        }

    }

    public ListaMovimentosAdapter( Context context, List<Movimentos> movimentos) {
        this.movimentos = movimentos;
        this.context = context;
    }

    public ListaMovimentosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movimentos, parent, false);
        return new ListaMovimentosAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movimentos listItem = movimentos.get(position);
        holder.txtContaMovimento.setText(listItem.getContaMovimento());
        holder.txtValorMovimento.setText(listItem.getValorMovimento() + "MT");
        holder.txtdataMovimento.setText(listItem.getDataMovimento()+"");
        holder.txtTipoMovimento.setText(listItem.getTipoMovimento()+"");


    }

    @Override
    public int getItemCount() {

        if(movimentos == null) {
            return 0;
        }else {
            return movimentos.size();
        }
    }



    ////FIM
}
