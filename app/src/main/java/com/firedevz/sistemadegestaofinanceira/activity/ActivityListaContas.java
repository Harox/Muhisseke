package com.firedevz.sistemadegestaofinanceira.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.firedevz.sistemadegestaofinanceira.modelo.Contas;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaContas extends AppCompatActivity {

    private List<Contas> listaContas = new ArrayList<>();
    private ListaContasAdapter listaContasAdapter ;
    private ArrayAdapter<String > adpTipoConta;
    private RecyclerView recyclerView;

    private FloatingActionButton BtnAdicionarConta;


    DatabaseHelper  db = new DatabaseHelper(this);

    Contas contas = new Contas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contas);



        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista);
        BtnAdicionarConta = (FloatingActionButton) findViewById(R.id.BtnAdicionarConta);

        listaContasAdapter = new ListaContasAdapter(this,listaContas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listaContasAdapter);


        listaConta();


        BtnAdicionarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ActivityListaContas.this);
                View vi = li.inflate(R.layout.popup_add_conta, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityListaContas.this);

                alertDialogBuilder.setView(vi);

                final EditText edtNomeDaConta = (EditText) vi.findViewById(R.id.edtNomeDaConta);
                final EditText edtSaldoConta = (EditText) vi.findViewById(R.id.edtSaldoConta);
                final Spinner spnTipoConta = (Spinner) vi.findViewById(R.id.spnTipoConta);


                adpTipoConta = new ArrayAdapter<String>(ActivityListaContas.this,android.R.layout.simple_spinner_item);
                adpTipoConta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTipoConta.setAdapter(adpTipoConta);

                adpTipoConta.add("Banco");
                adpTipoConta.add("Conta Movel");
                adpTipoConta.add("Cofre");


                alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeConta = edtNomeDaConta.getText().toString();
                        float saldoConta = Float.parseFloat(edtSaldoConta.getText().toString());
                        String tipoConta = spnTipoConta.getSelectedItem().toString();


                        //String nome_conf = contas.getNomeConta()
                        if (db.addConta(new Contas(nomeConta, saldoConta, tipoConta))) {
                            Toast.makeText(ActivityListaContas.this, "Conta Adicionada Com Sucesso! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // Criar O Alerta
                AlertDialog alertDialog = alertDialogBuilder.create();

                // Mostra o alerta
                alertDialog.show();

            }
        });

    }




    public void listaConta() {

        Cursor dados = db.listaTodasContas();

        if (dados.getCount() == 0) {
            db.addCaixaAccount();
            listaConta();

        } else {
            while (dados.moveToNext()) {
                String nomeConta = dados.getString(1);
                float ValorConta = Float.parseFloat(dados.getString(4));
                String tipoConta = dados.getString(2);

                Contas listaiten = new Contas(nomeConta, ValorConta, tipoConta);
                listaContas.add(listaiten);
            }
        }
    }





    /////FIM /////
}
