package com.firedevz.sistemadegestaofinanceira;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaDespesa;
import com.firedevz.sistemadegestaofinanceira.activity.RendimentosActivity;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

public class ProducaoActivity extends AppCompatActivity {

    private Button btnEntradas,btnDespesas;
    private TextView txtRendimento,txtDespesa;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao);
        inicializaComponentes();
        eventoClikes();
        txtRendimento.setText(db.valorRendimento()+" MZN");
        txtDespesa.setText(db.valorDespesas()+" MZN");
    }

    private void eventoClikes() {
        btnEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RendimentosActivity.class);
                startActivity(i);
            }
        });

        btnDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ActivityListaDespesa.class);
                startActivity(i);
            }
        });
    }

    private void inicializaComponentes() {

        txtRendimento = (TextView) findViewById(R.id.txtRendimento);
        txtDespesa = (TextView) findViewById(R.id.txtDespesa);
        btnEntradas=(Button)findViewById(R.id.btnEntradas);
        btnDespesas=(Button)findViewById(R.id.btnDespesas);
    }

}
