package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firedevz.sistemadegestaofinanceira.R;

public class ActivityVendas extends AppCompatActivity {

    private Button btnEfectuarVendas, btnVendasPendentes, btnHistoricoVendas, btnRequisicoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        inicializaComponentes();
        eventoClikes();

    }

    private void eventoClikes() {

        btnEfectuarVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EfectuarVendasActivity.class);
                startActivity(i);
            }
        });

        btnVendasPendentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VendasPendentesActivity.class);
                i.putExtra("tipo", 1);
                startActivity(i);
            }
        });

        btnHistoricoVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VendasPendentesActivity.class);
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
