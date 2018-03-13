package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.List;

public class VendasPendentesActivity extends AppCompatActivity {

    RecyclerView recycleView;
    ListaVendaAdapter listaVendaAdapter;
    List<Venda> vendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas_pendentes);

        int tipo = getIntent().getIntExtra("tipo", 0);
        if(tipo==1){
            vendas = Venda.listPendentes();
        } else {
            vendas = Venda.listEfectuadas();
        }

        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        listaVendaAdapter = new ListaVendaAdapter(this, vendas);
        recycleView.setAdapter(listaVendaAdapter);

    }
}
