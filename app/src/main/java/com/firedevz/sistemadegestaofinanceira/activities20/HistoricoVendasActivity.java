package com.firedevz.sistemadegestaofinanceira.activities20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.activity.RequisicoesActivity;
import com.firedevz.sistemadegestaofinanceira.activity.VendasPendentesActivity;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.List;

public class HistoricoVendasActivity extends AppCompatActivity {
    RecyclerView recycleView;
    ListaVendaAdapter listaVendaAdapter;
    List<Venda> vendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_pendentes2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.fab).setVisibility(View.GONE);

        int tipo = getIntent().getIntExtra("tipo", 0);
        vendas = Venda.listEfectuadas();

        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        listaVendaAdapter = new ListaVendaAdapter(this, vendas);
        recycleView.setAdapter(listaVendaAdapter);

    }
}
