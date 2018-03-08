package com.firedevz.sistemadegestaofinanceira.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Clientes;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ListaClienteAdapter extends RecyclerView.Adapter<ListaClienteAdapter.ViewHolder> {

    private List<Clientes> clientes;
    DatabaseHelper db;

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeCliente, txtContactoCliente,txtEmailCliente, txtEnderecoCliente,txtNuitCliente,txtDividaCliente;


        public ViewHolder(View itemView) {
            super(itemView);
            txtNomeCliente = (TextView) itemView.findViewById(R.id.txtNomeCliente);
            txtContactoCliente = (TextView) itemView.findViewById(R.id.txtContactoCliente);
            txtEmailCliente = (TextView) itemView.findViewById(R.id.txtEmailCliente);
            txtEnderecoCliente = (TextView) itemView.findViewById(R.id.txtEnderecoCliente);
            txtNuitCliente = (TextView) itemView.findViewById(R.id.txtNuitCliente);
            txtDividaCliente = (TextView) itemView.findViewById(R.id.txtDividaCliente);
        }

    }

    public ListaClienteAdapter(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    public ListaClienteAdapter( Context context,List<Clientes> clientes) {
        this.clientes = clientes;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_clientes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Clientes listItem = clientes.get(position);
        holder.txtNomeCliente.setText(listItem.getNome());
        holder.txtContactoCliente.setText(listItem.getTelefone());
        holder.txtEmailCliente.setText(listItem.getEmail());
        holder.txtEnderecoCliente.setText(listItem.getMorada());
        holder.txtNuitCliente.setText(listItem.getNuitCliente()+"");
        holder.txtDividaCliente.setText(listItem.getDivida()+"");
    }

    @Override
    public int getItemCount() {
        if(clientes == null) {
            return 0;
        }else {
            return clientes.size();
        }
    }

    public void setFilter(ArrayList<Clientes> newList){
        clientes = new ArrayList<>();
        clientes.addAll(newList);
        notifyDataSetChanged();
    }




    ////FIM

}
