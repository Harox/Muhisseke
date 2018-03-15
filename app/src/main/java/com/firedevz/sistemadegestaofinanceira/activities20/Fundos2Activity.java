package com.firedevz.sistemadegestaofinanceira.activities20;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaContas;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaMovimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.Movimento;

import java.util.ArrayList;
import java.util.List;

public class Fundos2Activity extends AppCompatActivity {

    private Button btnOutrasContas;
    private TextView txSaldoCaixa;

    private List<Movimento> listaMovimentos = new ArrayList<>();
    private ListaMovimentosAdapter listaMovimentosAdapter ;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.fab).setVisibility(View.GONE);

        btnOutrasContas = (Button) findViewById(R.id.btnOutrasContas);
        txSaldoCaixa = (TextView) findViewById(R.id.txSaldoCaixa);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_mov);

        listaMovimentos = Movimento.getAllMovimentos();
        listaMovimentosAdapter = new ListaMovimentosAdapter(listaMovimentos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listaMovimentosAdapter);

        Conta caixa = Conta.list().get(0);
        txSaldoCaixa.setText(caixa.getValorConta()+"0 MZN");

        btnOutrasContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContasActivity.class);
                startActivity(i);
            }
        });


    }


}
