package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.LoginActivity;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Requisicao;
import com.firedevz.sistemadegestaofinanceira.modelo.Usuario;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.List;

import io.paperdb.Paper;


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

    public ListaRequisicaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requisicao, parent, false);
        return new ListaRequisicaoAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Requisicao listItem = requisicoes.get(position);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(listItem.data);
        final Cliente cliente = Cliente.getById(listItem.idCliente);
        if(cliente!=null){
            holder.txtCliente.setText(cliente.getNome()+"");
            holder.txtData.setText(date);
            holder.txtProduto.setText(listItem.produto+"");

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog
                            .Builder(holder.view.getContext())
                            .setTitle("Notificar cliente?")
                            .setMessage("Deseja notificar o cliente da existencia do produto no stock?")
                            .setNegativeButton("Não", null)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Usuario usuario = Paper.book().read(LoginActivity.LOGGED_USER, new Usuario());

                                    Uri smsUri = Uri.parse("tel:"+cliente.getTelefone());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                                    intent.putExtra("sms_body", "Saudações, "+cliente.getNome()+", o produto "+listItem.produto+" já se encontra disponível. \n\n\n "+usuario.getNome()+".");
                                    intent.setType("vnd.android-dir/mms-sms");
                                    holder.view.getContext().startActivity(intent);
                                }
                            })
                            .show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return requisicoes.size();
    }


}
