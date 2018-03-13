package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;


public class ListaVendaAdapter extends RecyclerView.Adapter<ListaVendaAdapter.ViewHolder>{

    private List<Venda> vendas;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtData, txtValorPreco, txtCliente;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            txtData = itemView.findViewById(R.id.txtData);
            txtValorPreco = itemView.findViewById(R.id.txtValorPreco);
            txtCliente = itemView.findViewById(R.id.txtCliente);
        }
    }

    public ListaVendaAdapter(Context context, List<Venda> vendas) {
        this.vendas = vendas;
        this.context = context;
    }


    public ListaVendaAdapter(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public ListaVendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_venda, parent, false);
        return new ListaVendaAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Venda listItem = vendas.get(position);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(listItem.getDate());
        Cliente cliente = Cliente.getById(listItem.getIdCliente());
        holder.txtCliente.setText(cliente.getNome()+"");
        holder.txtData.setText(date);
        holder.txtValorPreco.setText(listItem.getPrecoTotal()+"");
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }


}
