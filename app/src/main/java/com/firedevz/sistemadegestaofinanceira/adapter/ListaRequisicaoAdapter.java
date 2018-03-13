package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Requisicao;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;


public class ListaRequisicaoAdapter extends RecyclerView.Adapter<ListaRequisicaoAdapter.ViewHolder>{

    private List<Requisicao> requisicoes;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtProduto, txtCliente, txtData;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            txtData = itemView.findViewById(R.id.txtData);
            txtCliente = itemView.findViewById(R.id.txtCliente);
            txtProduto = itemView.findViewById(R.id.txtProduto);
        }
    }

    public ListaRequisicaoAdapter(Context context, List<Requisicao> requisicoes) {
        this.requisicoes = requisicoes;
        this.context = context;
    }


    public ListaRequisicaoAdapter(List<Requisicao> requisicoes) {
        this.requisicoes = requisicoes;
    }

    public ListaRequisicaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requisicao, parent, false);
        return new ListaRequisicaoAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Requisicao listItem = requisicoes.get(position);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(listItem.data);
        Cliente cliente = Cliente.getById(listItem.idCliente);
        holder.txtCliente.setText(cliente.getNome()+"");
        holder.txtData.setText(date);
        holder.txtProduto.setText(listItem.produto+"");
    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }


}
