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
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;

public class ListaSaidasAdapter extends RecyclerView.Adapter<ListaSaidasAdapter.ViewHolder> {

    private List<Despesa> despesas;
    // DatabaseHelper db;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtDscricaoDespesa, txtValorDespesa, txtTipoDespesa;
        private View view;



        public ViewHolder(View itemView)      {
            super(itemView);

            txtDscricaoDespesa = (TextView) itemView.findViewById(R.id.txtDscricaoDespesa);
            txtValorDespesa = (TextView) itemView.findViewById(R.id.txtValorDespesa);
            txtTipoDespesa = (TextView) itemView.findViewById(R.id.txtTipoDespesa);
            view = itemView;
        }

    }

    public ListaSaidasAdapter( Context context, List<Despesa> despesas) {
        this.despesas = despesas;
        this.context = context;
    }

    public ListaSaidasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_despesas, parent, false);
        return new ListaSaidasAdapter.ViewHolder(v);
    }


    public void onBindViewHolder(ViewHolder holder, int position) {
        final Despesa listItem = despesas.get(position);
        holder.txtDscricaoDespesa.setText(listItem.getDescricao_despesa());
        holder.txtValorDespesa.setText(listItem.getValor_despesa() + "MT");
        holder.txtTipoDespesa.setText(listItem.getTipo_despesa()+"");



        final String nome = listItem.getDescricao_despesa();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"data", Toast.LENGTH_LONG).show();

                LayoutInflater li = LayoutInflater.from(context);
                View vi = li.inflate(R.layout.popup_despesas, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(vi);

                final EditText nomeDespesa = (EditText) vi.findViewById(R.id.edtNomeDespesaPopup);
                final EditText valorDespesa = (EditText) vi.findViewById(R.id.edtValorDespesaPopup);

                Toast.makeText(context, listItem.getDescricao_despesa(), Toast.LENGTH_SHORT).show();

                nomeDespesa.setText(listItem.getDescricao_despesa() +"");
                valorDespesa.setText(listItem.getValor_despesa() +"");


                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeDespes = nomeDespesa.getText().toString();
                        float valorDespes = Float.parseFloat(valorDespesa.getText().toString());

                        String nome_conf = listItem.getDescricao_despesa();
                        if (db.actualizaDespesa(nome_conf, nomeDespes, valorDespes)) {
                            Toast.makeText(context, "Despesa Actualizada", Toast.LENGTH_SHORT).show();
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
        return despesas.size();
    }

    ////FIM////
}
