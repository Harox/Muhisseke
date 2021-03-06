package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.List;

/**
 * Created by PUDENTE on 2/15/2018.
 */

public class ListaContasAdapter extends RecyclerView.Adapter<ListaContasAdapter.ViewHolder> {

    private List<Conta> cotas;
   // DatabaseHelper db;

    Context context;
    DatabaseHelper db = new DatabaseHelper(context);
    boolean canEdit;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeConta, txtSaldoConta, txtTipoConta;
//        private LinearLayout listaContas;
        ImageView imageConta;
        View view;



        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            imageConta = itemView.findViewById(R.id.imageConta);
            txtNomeConta = (TextView) itemView.findViewById(R.id.txtNomeConta);
            txtSaldoConta = (TextView) itemView.findViewById(R.id.txtSaldoConta);
            txtTipoConta = (TextView) itemView.findViewById(R.id.txtTipoConta);
//            listaContas = (LinearLayout) itemView.findViewById(R.id.listaContas);
        }

    }


    public ListaContasAdapter(Context context, List<Conta> contas, boolean canEdit) {
        this.cotas = contas;
        this.context = context;
        this.canEdit = canEdit;
    }

    @Override
    public ListaContasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_accounts, parent, false);
        return new ListaContasAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Conta listItem = cotas.get(position);
        holder.txtNomeConta.setText(listItem.getNomeConta());
        holder.txtSaldoConta.setText(listItem.getValorConta() + " MT");
        holder.txtTipoConta.setText("Tipo: "+listItem.getTipoConta()+"");

        if(listItem.getNomeConta().equals("Caixa")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_caixa));
        }else if(listItem.getNomeConta().equalsIgnoreCase("BCI")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_bci));
        }else if(listItem.getNomeConta().equalsIgnoreCase("MOZA")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_moza));
        }else if(listItem.getNomeConta().equalsIgnoreCase("FNB")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_fnb));
        }else if(listItem.getNomeConta().equalsIgnoreCase("BARCLAYS")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_barclays));
        }else if(listItem.getNomeConta().equalsIgnoreCase("M-PESA")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_mpesa));
        }else if(listItem.getNomeConta().equalsIgnoreCase("M-KESH")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_mkesh));
        }else if(listItem.getNomeConta().equalsIgnoreCase("e-MOLA")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_emola));
        }else if(listItem.getNomeConta().equalsIgnoreCase("millennium bim")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_bim));
        }else if(listItem.getNomeConta().equalsIgnoreCase("STANDARD BANK")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_standardbank));
        }else if(listItem.getNomeConta().equalsIgnoreCase("PONTO 24")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_ponto24));
        }else if(listItem.getNomeConta().equalsIgnoreCase("unico")){
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_unico));
        }else {
            holder.imageConta.setImageDrawable(holder.imageConta.getResources().getDrawable(R.mipmap.ic_conta));
        }




        final String nome = listItem.getNomeConta();

        if(canEdit){
            holder.view.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    public int getItemCount() {
        return cotas.size();
    }





    ///FIM//////
}
