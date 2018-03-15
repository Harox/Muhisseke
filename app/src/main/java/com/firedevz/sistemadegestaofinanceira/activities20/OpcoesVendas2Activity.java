package com.firedevz.sistemadegestaofinanceira.activities20;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.activity.RequisicoesActivity;
import com.firedevz.sistemadegestaofinanceira.activity.VendasPendentesActivity;

public class OpcoesVendas2Activity extends AppCompatActivity {

    private Button btnEfectuarVendas, btnVendasPendentes, btnHistoricoVendas, btnRequisicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_vendas2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fab.setVisibility(View.GONE);
        inicializaComponentes();
        eventoClikes();
    }

    private void eventoClikes() {

        btnEfectuarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EfectuarVendaActivity.class);
                startActivity(i);
            }
        });

        btnVendasPendentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VendasPendentes2Activity.class);
                i.putExtra("tipo", 1);
                startActivity(i);
            }
        });

        btnHistoricoVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HistoricoVendasActivity.class);
                i.putExtra("tipo", 2);
                startActivity(i);
            }
        });

        btnRequisicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequisicoesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inicializaComponentes() {

        btnEfectuarVendas = (Button) findViewById(R.id.btnEfectuarVendas);
        btnVendasPendentes = (Button) findViewById(R.id.btnVendasPendentes);
        btnHistoricoVendas = (Button) findViewById(R.id.btnHistoricoVendas);
        btnRequisicoes = (Button) findViewById(R.id.btnRequisicoes);
    }

}
