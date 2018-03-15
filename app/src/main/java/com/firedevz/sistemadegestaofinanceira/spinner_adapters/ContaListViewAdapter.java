package com.firedevz.sistemadegestaofinanceira.spinner_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;

import java.util.List;

/**
 * Created by Matlhombe Junior on 06/11/2017.
 */

public class ContaListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Conta> items;


    public ContaListViewAdapter(Context context, List<Conta> items) {
        super();
        this.context = context;
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_accounts, null);
        }
        Conta conta = items.get(position);

        TextView txtNomeConta = convertView.findViewById(R.id.txtNomeConta);
        TextView txtSaldoConta = convertView.findViewById(R.id.txtSaldoConta);
        TextView txtTipoConta =  convertView.findViewById(R.id.txtTipoConta);

        txtNomeConta.setText(conta.getNomeConta());
        txtSaldoConta.setText(conta.getValorConta() + " MT");
        txtTipoConta.setText("Tipo: "+conta.getTipoConta()+"");
        txtSaldoConta.setVisibility(View.GONE);
        txtTipoConta.setVisibility(View.GONE);
//        TextView textViewNome = convertView.findViewById(R.id.textViewNome);

//        textViewNome.setText(disciplina.nome);

        return convertView;
    }
}
