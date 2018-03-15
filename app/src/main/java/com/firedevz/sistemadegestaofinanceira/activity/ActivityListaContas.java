package com.firedevz.sistemadegestaofinanceira.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaContasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaContas extends AppCompatActivity {

    Conta conta = new Conta();
    private List<Conta> listaContas = new ArrayList<>();
    private ListaContasAdapter listaContasAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contas);



    }
}
