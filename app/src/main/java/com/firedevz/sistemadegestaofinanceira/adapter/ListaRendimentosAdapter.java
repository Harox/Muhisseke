package com.firedevz.sistemadegestaofinanceira.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;

public class ListaRendimentosAdapter extends RecyclerView.Adapter<ListaRendimentosAdapter.ViewHolder>{

    private List<Rendimento> rendimentos;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtDesccricaoRendimenti, txtValorRendimento, txtDataRendimento;


        public ViewHolder(View itemView) {
            super(itemView);

            txtDesccricaoRendimenti = (TextView) itemView.findViewById(R.id.txtDesccricaoRendimenti);
            txtValorRendimento = (TextView) itemView.findViewById(R.id.txtValorRendimento);
            txtDataRendimento = (TextView) itemView.findViewById(R.id.txtDataRendimento);
        }

    }

    public ListaRendimentosAdapter( Context context, List<Rendimento> rendimentos) {
        this.rendimentos = rendimentos;
        this.context = context;
    }

    public ListaRendimentosAdapter(List<Rendimento> rendimentos) {
        this.rendimentos = rendimentos;
    }

    public ListaRendimentosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rendimentos, parent, false);
        return new ListaRendimentosAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Rendimento listItem = rendimentos.get(position);
        holder.txtDesccricaoRendimenti.setText(listItem.getDescricao()+"");
        holder.txtValorRendimento.setText(listItem.getValor() + "MT");
        holder.txtDataRendimento.setText(listItem.getData()+"");
    }

    @Override
    public int getItemCount() {

        if(rendimentos == null) {
            return 0;
        }else {
            return rendimentos.size();
        }
    }



    //FIM///////

}
