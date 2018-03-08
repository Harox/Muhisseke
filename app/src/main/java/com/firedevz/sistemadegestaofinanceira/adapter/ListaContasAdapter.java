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
import com.firedevz.sistemadegestaofinanceira.modelo.Contas;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;

/**
 * Created by PUDENTE on 2/15/2018.
 */

public class ListaContasAdapter extends RecyclerView.Adapter<ListaContasAdapter.ViewHolder> {

    private List<Contas> cotas;
   // DatabaseHelper db;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeConta, txtSaldoConta, txtTipoConta;
        private LinearLayout listaContas;



        public ViewHolder(View itemView) {
            super(itemView);

            txtNomeConta = (TextView) itemView.findViewById(R.id.txtNomeConta);
            txtSaldoConta = (TextView) itemView.findViewById(R.id.txtSaldoConta);
            txtTipoConta = (TextView) itemView.findViewById(R.id.txtTipoConta);
            listaContas = (LinearLayout) itemView.findViewById(R.id.listaContas);
        }

    }


    public ListaContasAdapter(Context context, List<Contas> contas) {
        this.cotas = contas;
        this.context = context;
    }

    @Override
    public ListaContasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_accounts, parent, false);
        return new ListaContasAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Contas listItem = cotas.get(position);
        holder.txtNomeConta.setText(listItem.getNomeConta());
        holder.txtSaldoConta.setText(listItem.getValorConta() + "MT");
        holder.txtTipoConta.setText(listItem.getTipoConta()+"");



        final String nome = listItem.getNomeConta();

        holder.listaContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"data", Toast.LENGTH_LONG).show();

                LayoutInflater li = LayoutInflater.from(context);
                View vi = li.inflate(R.layout.popup_contas, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(vi);

                final EditText nomeConta = (EditText) vi.findViewById(R.id.edtNomePopupConta);
                final EditText saldoConta = (EditText) vi.findViewById(R.id.edtSaldoPopupConta);

                Toast.makeText(context, listItem.getNomeConta(), Toast.LENGTH_SHORT).show();

                nomeConta.setText(listItem.getNomeConta() +"");
                saldoConta.setText(listItem.getValorConta() +"");


                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeAccount = nomeConta.getText().toString();
                        float saldoAccount = Float.parseFloat(saldoConta.getText().toString());

                        String nome_conf = listItem.getNomeConta();
                        if (db.actualizaConta(nome_conf, nomeAccount, saldoAccount)) {
                            Toast.makeText(context, "Conta Actualizada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // Criar O Alerta
                AlertDialog alertDialog = alertDialogBuilder.create();

                // Mostra o alerta
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cotas.size();
    }





    ///FIM//////
}
