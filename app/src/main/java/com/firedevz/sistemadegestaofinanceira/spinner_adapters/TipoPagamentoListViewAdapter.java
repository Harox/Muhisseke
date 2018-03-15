package com.firedevz.sistemadegestaofinanceira.spinner_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;

import java.util.List;

/**
 * Created by Matlhombe Junior on 06/11/2017.
 */

public class TipoPagamentoListViewAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater inflater;
    List<String> items;


    public TipoPagamentoListViewAdapter(Activity activity, List<String> items) {
        super();
        this.activity = activity;
        this.items = items;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            convertView = inflater.inflate(R.layout.list_tipo_pagamento, null);
        }

        TextView txtPagamento = convertView.findViewById(R.id.txtPagamento);

        txtPagamento.setText(items.get(position));

        return convertView;
    }
}
