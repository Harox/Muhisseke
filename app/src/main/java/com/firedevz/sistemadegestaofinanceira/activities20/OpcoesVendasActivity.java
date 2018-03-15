package com.firedevz.sistemadegestaofinanceira.activities20;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.EfectuarVendasActivity;
import com.firedevz.sistemadegestaofinanceira.activity.RequisicoesActivity;
import com.firedevz.sistemadegestaofinanceira.activity.VendasPendentesActivity;


public class OpcoesVendasActivity extends AppCompatActivity {

    private Button btnEfectuarVendas, btnVendasPendentes, btnHistoricoVendas, btnRequisicoes;
    TextView counter_text;
    TextView txtconta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_vendas);

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
