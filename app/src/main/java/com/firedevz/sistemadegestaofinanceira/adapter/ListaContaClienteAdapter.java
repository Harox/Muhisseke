package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activities20.EfectuarVendaActivity;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;


public class ListaContaClienteAdapter extends RecyclerView.Adapter<ListaContaClienteAdapter.ViewHolder> {

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);
    private List<Venda.ProdutoVenda> produtosNaVenda;


    public ListaContaClienteAdapter(Context context, List<Venda.ProdutoVenda> produtosNaVenda) {
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

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Venda.ProdutoVenda listItem = produtosNaVenda.get(position);
        holder.txtNomeProdutoConta.setText(listItem.nome + "");
        holder.txtValorProdutoConta.setText(listItem.preco + "MT");
        holder.txtQuantidadeProdutoConta.setText(listItem.quantidade + "");

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog
                        .Builder(holder.view.getContext())
                        .setTitle("Deseja apagar este produto da lista?")
                        .setNegativeButton("Não", null)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeAt(position);
                                EfectuarVendaActivity.updatePreco();
                            }
                        }).show();
            }
        });

        if (!EfectuarVendasActivity.is_in_action_mode) {
            holder.checkBox.setVisibility(View.GONE);
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    public void removeAt(int position) {
        produtosNaVenda.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, produtosNaVenda.size());
    }

    @Override
    public int getItemCount() {

        if (produtosNaVenda == null) {
            return 0;
        } else {
            return produtosNaVenda.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeProdutoConta, txtValorProdutoConta, txtQuantidadeProdutoConta;
        public CheckBox checkBox;
        LinearLayout linearLayConta;
        EfectuarVendasActivity efectuarVendasActivity;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            this.efectuarVendasActivity = efectuarVendasActivity;
            txtNomeProdutoConta = (TextView) itemView.findViewById(R.id.txtNomeProdutoConta);
            txtValorProdutoConta = (TextView) itemView.findViewById(R.id.txtValorProdutoConta);
            txtQuantidadeProdutoConta = (TextView) itemView.findViewById(R.id.txtQuantidadeProdutoConta);
            checkBox = (CheckBox) itemView.findViewById(R.id.check_list_item_produto);
            linearLayConta = (LinearLayout) itemView.findViewById(R.id.linearLayConta);
            linearLayConta.setOnLongClickListener(efectuarVendasActivity);
        }
    }


}
