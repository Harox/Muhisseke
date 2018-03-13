package com.firedevz.sistemadegestaofinanceira.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Fornecedor;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ListaFornecedorAdapter extends RecyclerView.Adapter<ListaFornecedorAdapter.ViewHolder> {

    private List<Fornecedor> fornecedores;
    private ArrayAdapter<String> adpTipoFornecedor;
    DatabaseHelper db;

    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeFornecedor, txtContactoFornecedor,txtEmailFornecedor, txtEnderecoFornecedor,txtTipoFornecedor,txtTipoProdutoFornecedor;
        private CardView listaFornecedor;


        public ViewHolder(View itemView) {
            super(itemView);

            txtNomeFornecedor = (TextView) itemView.findViewById(R.id.txtNomeFornecedor);
            txtContactoFornecedor = (TextView) itemView.findViewById(R.id.txtContactoFornecedor);
            txtEmailFornecedor = (TextView) itemView.findViewById(R.id.txtEmailFornecedor);
            txtEnderecoFornecedor = (TextView) itemView.findViewById(R.id.txtEnderecoFornecedor);
            txtTipoFornecedor = (TextView) itemView.findViewById(R.id.txtTipoFornecedor);
            txtTipoProdutoFornecedor = (TextView) itemView.findViewById(R.id.txtTipoProdutoFornecedor);
            listaFornecedor = (CardView) itemView.findViewById(R.id.listaFornecedor);

        }

    }

    public ListaFornecedorAdapter(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public ListaFornecedorAdapter( Context context,List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_fornecedores, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Fornecedor listItem = fornecedores.get(position);
        holder.txtNomeFornecedor.setText(listItem.getNomeFornecedor());
        holder.txtContactoFornecedor.setText(listItem.getContactoFornecedor());
        holder.txtEmailFornecedor.setText(listItem.getEmailFornecedro());
        holder.txtEnderecoFornecedor.setText(listItem.getEnderecoFornecedro());
        holder.txtTipoFornecedor.setText(listItem.getTipoFornecedro());
        holder.txtTipoProdutoFornecedor.setText(listItem.getTipoProdutoFornecedro());

        final String nome = listItem.getNomeFornecedor();

        holder.listaFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Funcionalide em desenvolvimento", Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(context);
                View vi = li.inflate(R.layout.popup_edit_fornecedor, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(vi);

                final EditText nomeFornecedor = (EditText) vi.findViewById(R.id.edtNomeFornecedor);
                final EditText telefoneFornecedor = (EditText) vi.findViewById(R.id.edtTelefoneFornecedor);
                final EditText emailFornecedor = (EditText) vi.findViewById(R.id.edtEmailFornecedor);
                final EditText enderecoFornecedor = (EditText) vi.findViewById(R.id.edtEnderecoFornecedor);
                final Spinner tipoFornecedor = (Spinner) vi.findViewById(R.id.spnTipoFornecedor);
                final EditText tipoProdutoFornecedor = (EditText) vi.findViewById(R.id.edtTipoProdutoFornecedor);


                adpTipoFornecedor = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item);
                adpTipoFornecedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tipoFornecedor.setAdapter(adpTipoFornecedor);

                adpTipoFornecedor.add("Singular");
                adpTipoFornecedor.add("Empresa");

                nomeFornecedor.setText(listItem.getNomeFornecedor() +" ");
                telefoneFornecedor.setText(listItem.getContactoFornecedor() +" ");
                emailFornecedor.setText(listItem.getEmailFornecedro() +" ");
                enderecoFornecedor.setText(listItem.getEnderecoFornecedro() +" ");
                tipoProdutoFornecedor.setText(listItem.getTipoProdutoFornecedro() +" ");

                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeSupler = nomeFornecedor.getText().toString();
                        String telefoneSupler = telefoneFornecedor.getText().toString();
                        String emailSupler = emailFornecedor.getText().toString();
                        String enderecoSupler = enderecoFornecedor.getText().toString();
                        String tipoSupler = (String) tipoFornecedor.getSelectedItem();
                        String tipoProdutoSupler = tipoProdutoFornecedor.getText().toString();




                        String nome_conf = listItem.getNomeFornecedor();
                        if (db.actualizaFornecedor(nome_conf, nomeSupler, telefoneSupler,emailSupler,enderecoSupler,tipoSupler,tipoProdutoSupler)) {
                            Toast.makeText(context, "Fornecedor Actualizado com Sucesso! ", Toast.LENGTH_SHORT).show();
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
        if(fornecedores == null) {
            return 0;
        }else {
            return fornecedores.size();
        }
    }

    public void setFilter(ArrayList<Fornecedor> newList){
        fornecedores = new ArrayList<>();
        fornecedores.addAll(newList);
        notifyDataSetChanged();
    }




    ////FIM//////
}
