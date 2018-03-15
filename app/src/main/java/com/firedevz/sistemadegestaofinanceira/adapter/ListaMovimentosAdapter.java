package com.firedevz.sistemadegestaofinanceira.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Movimento;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListaMovimentosAdapter extends RecyclerView.Adapter<ListaMovimentosAdapter.ViewHolder>  {

    private List<Movimento> movimentos;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);

    public ListaMovimentosAdapter(List<Movimento> movimentos) {
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

    public ListaMovimentosAdapter( Context context, List<Movimento> movimentos) {
        this.movimentos = movimentos;
        this.context = context;
    }

    public ListaMovimentosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movimentos, parent, false);
        return new ListaMovimentosAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movimento listItem = movimentos.get(position);
        holder.txtContaMovimento.setText(listItem.contaMovimento);
        holder.txtValorMovimento.setText(listItem.valorMovimento + "MT");
        holder.txtdataMovimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(listItem.dataMovimento)+"");
        holder.txtTipoMovimento.setText(listItem.tipoMovimento+"");


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
