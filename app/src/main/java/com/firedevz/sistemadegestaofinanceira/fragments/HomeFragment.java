package com.firedevz.sistemadegestaofinanceira.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firedevz.sistemadegestaofinanceira.ProducaoActivity;
import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityFundos;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaClientes;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityRelatorios;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityVendas;
import com.firedevz.sistemadegestaofinanceira.activity.activityListaProdutos;


public class HomeFragment extends Fragment {

    private View view;
    private Button btnFundos,btnVendas,btnProdutos,btnClientes,btnProducao,btnRelatorio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_menu_principal, container, false);

        inicializaComponentes();
        eventoClikes();

        return view;
    }

    private void inicializaComponentes() {
        btnFundos=(Button)view.findViewById(R.id.btnFundos);
        btnVendas=(Button)view.findViewById(R.id.btnVendas);
        btnProdutos=(Button)view.findViewById(R.id.btnProdutos);
        btnClientes=(Button)view.findViewById(R.id.btnClientes);
        btnProducao=(Button)view.findViewById(R.id.btnProducao);
        btnRelatorio=(Button)view.findViewById(R.id.btnRelatorio);

    }

    private void eventoClikes() {

        btnFundos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ActivityFundos.class);
                startActivity(i);
            }
        });
        btnVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ActivityVendas.class);
                startActivity(i);
            }
        });
        btnProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),activityListaProdutos.class);
                startActivity(i);
            }
        });
        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ActivityListaClientes.class);
                startActivity(i);
            }
        });
        btnProducao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.firedevz.sistemadegestaofinanceira.activities20.ProducaoActivity.class);
                startActivity(i);
            }
        });
        btnRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ActivityRelatorios.class);
                startActivity(i);
            }
        });
    }
}
