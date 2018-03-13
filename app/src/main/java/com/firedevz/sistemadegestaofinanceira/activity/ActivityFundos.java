package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaMovimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Movimentos;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityFundos extends AppCompatActivity {

    private Button btnOutrasContas;
    private TextView txSaldoCaixa;
    private ListView lstMovimentos;

    private List<Movimentos> listaMovimentos = new ArrayList<>();
    private ListaMovimentosAdapter listaMovimentosAdapter ;
    private RecyclerView recyclerView;

   DatabaseHelper db = new DatabaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundos);


        btnOutrasContas = (Button) findViewById(R.id.btnOutrasContas);
        txSaldoCaixa = (TextView) findViewById(R.id.txSaldoCaixa);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_mov);

        listaMovimentosAdapter = new ListaMovimentosAdapter(listaMovimentos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listaMovimentosAdapter);

        txSaldoCaixa.setText(db.saldoCaixa()+"0 MZN");

        btnOutrasContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityListaContas.class);
                startActivity(i);
            }
        });



        listaMovimentos();

    }

    public void listaMovimentos() {

        Cursor dados = db.listaTodosMovimentos();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Nenhum Movimento foi efectuado", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String conta = dados.getString(1);
                float valor = Float.parseFloat(dados.getString(2));
                String data = dados.getString(3);
                String tipo = dados.getString(4);

                Movimentos listaiten = new Movimentos(conta, valor, data,tipo);
                listaMovimentos.add(listaiten);
            }
        }
    }

}
