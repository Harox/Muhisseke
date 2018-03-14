package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListaProdutosAdapter extends RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder> {

    public static final int PRODUTOS_SEM_STOCK = 1;
    public static final int PRODUTOS_COM_POUCO_STOCK = 2;
    public static final int PRODUTOS_FORA_PRAZO = 3;
    public static final int PRODUTOS_PRESTES_A_EXPIRAR = 4;
    //    DatabaseHelper db;
    Context context;
    View vi;
    LayoutInflater li;
    int type;
    private List<Produto> produtos;

    public ListaProdutosAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public ListaProdutosAdapter(Context context, List<Produto> produtos, int type) {
        this.produtos = produtos;
        this.context = context;
        this.type = type;
    }

    public static String dateDiff(Date startDate, Date endDate) {

        int diffInDays = (int)( (startDate.getTime() - endDate.getTime())
                / (1000 * 60 * 60 * 24));


        return "" + Math.abs(diffInDays)+ " dias";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Produto listItem = produtos.get(position);
        holder.txtNome.setText(listItem.getNome());
        holder.txtPreco.setText(listItem.getPreco() + "MT");
        holder.txtQuantidade.setText(listItem.getQuantidade() + "");
        holder.txtObs.setVisibility(View.GONE);
        if (type == PRODUTOS_PRESTES_A_EXPIRAR) {
            holder.txtObs.setVisibility(View.VISIBLE);
            String dateDiff = dateDiff(Calendar.getInstance().getTime(), listItem.getPrazo());
            holder.txtObs.setText(dateDiff);
        }
        if (type == PRODUTOS_COM_POUCO_STOCK) {
            holder.txtObs.setVisibility(View.VISIBLE);
            holder.txtObs.setText(listItem.getQuantidade() + " restantes");
        }
        if (type == PRODUTOS_FORA_PRAZO) {
            holder.txtObs.setVisibility(View.VISIBLE);
            holder.txtObs.setText("Expirou no dia "+ new SimpleDateFormat("dd/MM/yyyy").format(listItem.getPrazo()));
        }
        holder.lnLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                li = LayoutInflater.from(context);
                vi = li.inflate(R.layout.popup_escolha, null);

                final Button buttonEdit = vi.findViewById(R.id.buttonEdit);
                final Button buttonDelete = vi.findViewById(R.id.buttonDelete);

                final AlertDialog alertDialogPopUpEscolha = new AlertDialog
                        .Builder(holder.lnLay.getContext())
                        .setView(vi)
                        .create();

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Produto.exists(position)) {
                            Toast.makeText(holder.lnLay.getContext(), "Não pode apagar um produto com stock", Toast.LENGTH_LONG).show();
                            alertDialogPopUpEscolha.dismiss();
                        } else {
                            Produto.delete(position);
                            removeAt(position);
                            alertDialogPopUpEscolha.dismiss();
                        }
                    }
                });

                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialogPopUpEscolha.dismiss();

                        li = LayoutInflater.from(context);
                        vi = li.inflate(R.layout.popup_produto, null);
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(vi);

                        final EditText nomeProduto = vi.findViewById(R.id.edtNomePopup);
                        final EditText quantProduto = vi.findViewById(R.id.edtQuantidadePopup);
                        final EditText precoProduto = vi.findViewById(R.id.edtPrecoPopup);
                        final EditText prazoProduto = vi.findViewById(R.id.edtprazoPopup);

                        Toast.makeText(context, listItem.getNome(), Toast.LENGTH_SHORT).show();

                        nomeProduto.setText(listItem.getNome());
                        quantProduto.setText(listItem.getQuantidade() + "");
                        precoProduto.setText(listItem.getPreco() + "");
                        String prazo = new SimpleDateFormat("dd/MM/yyyy").format(listItem.getPrazo());
                        prazoProduto.setText(prazo + "");

                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String nomePro = nomeProduto.getText().toString();
                                int quantPro = Integer.parseInt(quantProduto.getText().toString());
                                float PrecoPro = Float.parseFloat(precoProduto.getText().toString());
                                String prazoPro = prazoProduto.getText().toString();

                                listItem.setNome(nomePro);
                                listItem.setQuantidade(quantPro);
                                listItem.setPreco(PrecoPro);

                                DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Date datePrazo = Calendar.getInstance().getTime();
                                try {
                                    datePrazo = sourceFormat.parse(prazoPro);
                                } catch (ParseException e) {
//                                e.printStackTrace();
                                }
                                listItem.setPrazo(datePrazo);

                                if (Produto.update(position, listItem)) {
                                    Toast.makeText(context, "ProdutoVenda Actualizado", Toast.LENGTH_SHORT).show();

                                    holder.txtNome.setText(nomePro);
                                    holder.txtPreco.setText(PrecoPro + "MT");
                                    holder.txtQuantidade.setText(quantPro + "");
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

                alertDialogPopUpEscolha.show();
                //Toast.makeText(context,"data", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void removeAt(int position) {
        produtos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, produtos.size());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setFilter(ArrayList<Produto> newList) {
        produtos = new ArrayList<>();
        produtos.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNome, txtPreco, txtQuantidade, txtObs;
        private LinearLayout lnLay;


        public ViewHolder(View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidade);
            txtPreco = itemView.findViewById(R.id.txtPreco);
            txtObs = itemView.findViewById(R.id.txtObs);
            lnLay = itemView.findViewById(R.id.lnLay);
        }

    }
}
