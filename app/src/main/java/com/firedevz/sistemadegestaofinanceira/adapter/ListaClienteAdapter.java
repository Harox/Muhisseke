package com.firedevz.sistemadegestaofinanceira.adapter;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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

    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeCliente, txtContactoCliente,txtEmailCliente, txtEnderecoCliente,txtNuitCliente,txtDividaCliente;
        public View view;
        public View buttonCall;
        public View buttonMensagem;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            txtNomeCliente = (TextView) itemView.findViewById(R.id.txtNomeCliente);
            txtContactoCliente = (TextView) itemView.findViewById(R.id.txtContactoCliente);
            txtEmailCliente = (TextView) itemView.findViewById(R.id.txtEmailCliente);
            txtEnderecoCliente = (TextView) itemView.findViewById(R.id.txtEnderecoCliente);
            txtNuitCliente = (TextView) itemView.findViewById(R.id.txtNuitCliente);
            txtDividaCliente = (TextView) itemView.findViewById(R.id.txtDividaCliente);
            buttonCall = itemView.findViewById(R.id.buttonCall);
            buttonMensagem = itemView.findViewById(R.id.buttonMensagem);
        }

    }

    public ListaClienteAdapter(Activity activity, List<Clientes> clientes) {
        this.clientes = clientes;
        this.activity = activity;
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
        holder.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listItem.getTelefone()));
                        activity.startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + listItem.getTelefone()));
                    activity.startActivity(intent);
                }
            }
        });
        holder.buttonMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri smsUri = Uri.parse("tel:"+listItem.getTelefone());
                Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
                intent.putExtra("sms_body", "sms text");
                intent.setType("vnd.android-dir/mms-sms");
                activity.startActivity(intent);
            }
        });
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
}
