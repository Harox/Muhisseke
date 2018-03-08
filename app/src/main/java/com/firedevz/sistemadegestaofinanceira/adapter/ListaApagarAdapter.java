package com.firedevz.sistemadegestaofinanceira.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

public class ListaApagarAdapter extends AppCompatActivity {

    DatabaseHelper db;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_conta_a_pagar);
    }

    ////Fim
}
