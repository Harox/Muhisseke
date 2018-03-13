package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;


public class ListaContaClienteAdapter extends RecyclerView.Adapter<ListaContaClienteAdapter.ViewHolder>{

    private List<Venda.ProdutoVenda> produtosNaVenda;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtNomeProdutoConta, txtValorProdutoConta, txtQuantidadeProdutoConta;
        LinearLayout linearLayConta;
        EfectuarVendasActivity efectuarVendasActivity;

        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);


            this.efectuarVendasActivity = efectuarVendasActivity;
            txtNomeProdutoConta = (TextView) itemView.findViewById(R.id.txtNomeProdutoConta);
            txtValorProdutoConta = (TextView) itemView.findViewById(R.id.txtValorProdutoConta);
            txtQuantidadeProdutoConta = (TextView) itemView.findViewById(R.id.txtQuantidadeProdutoConta);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_list_item_produto);
            linearLayConta = (LinearLayout) itemView.findViewById(R.id.linearLayConta);
            linearLayConta.setOnLongClickListener(efectuarVendasActivity);
        }
    }

    public ListaContaClienteAdapter( Context context, List<Venda.ProdutoVenda> produtosNaVenda) {
        this.produtosNaVenda = produtosNaVenda;
        this.context = context;
    }


    public ListaContaClienteAdapter(List<Venda.ProdutoVenda> produtosNaVenda) {
        this.produtosNaVenda = produtosNaVenda;
    }

    public ListaContaClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_conta_cliente, parent, false);
        return new ListaContaClienteAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Venda.ProdutoVenda listItem = produtosNaVenda.get(position);
        holder.txtNomeProdutoConta.setText(listItem.nome+"");
        holder.txtValorProdutoConta.setText(listItem.preco + "MT");
        holder.txtQuantidadeProdutoConta.setText(listItem.quantidade+"");
        if(!EfectuarVendasActivity.is_in_action_mode){
            holder.checkBox.setVisibility(View.GONE);
        }else{
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {

        if(produtosNaVenda == null) {
            return 0;
        }else {
            return produtosNaVenda.size();
        }
    }


}
