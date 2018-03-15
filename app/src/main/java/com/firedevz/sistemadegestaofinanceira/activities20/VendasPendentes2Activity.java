package com.firedevz.sistemadegestaofinanceira.activities20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.List;

public class VendasPendentes2Activity extends AppCompatActivity {

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

        vendas = Venda.listPendentes();

        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        listaVendaAdapter = new ListaVendaAdapter(this, vendas);
        recycleView.setAdapter(listaVendaAdapter);

    }

}
