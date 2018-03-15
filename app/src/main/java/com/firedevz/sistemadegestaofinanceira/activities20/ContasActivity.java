package com.firedevz.sistemadegestaofinanceira.activities20;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaContas;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaContasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;

import java.util.List;

public class ContasActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Conta> listaContas;
    ListaContasAdapter listaContasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView =  findViewById(R.id.recycle_lista);;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listaContas = Conta.list();
        listaContasAdapter = new ListaContasAdapter(this, listaContas, true);
        recyclerView.setAdapter(listaContasAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(ContasActivity.this);
                View vi = li.inflate(R.layout.popup_add_conta, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ContasActivity.this);

                alertDialogBuilder.setView(vi);

                final EditText edtNomeDaConta = vi.findViewById(R.id.edtNomeDaConta);
                final EditText edtSaldoConta = vi.findViewById(R.id.edtSaldoConta);
                final Spinner spnTipoConta = vi.findViewById(R.id.spnTipoConta);


                ArrayAdapter adpTipoConta = new ArrayAdapter<>(ContasActivity.this, android.R.layout.simple_spinner_item);
                adpTipoConta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTipoConta.setAdapter(adpTipoConta);

                adpTipoConta.add("Banco");
                adpTipoConta.add("Conta Movel");
                adpTipoConta.add("Cofre");

                alertDialogBuilder
                        .setTitle("Adicionar conta")
                        .setCancelable(false)
                        .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeConta = edtNomeDaConta.getText().toString();
                        float saldoConta = Float.parseFloat(edtSaldoConta.getText().toString());
                        String tipoConta = spnTipoConta.getSelectedItem().toString();

                        Conta conta = new Conta(nomeConta, saldoConta, tipoConta);
                        if (Conta.register(conta)) {
                            listaContas = Conta.list();
                            listaContasAdapter = new ListaContasAdapter(ContasActivity.this, listaContas, true);
                            recyclerView.setAdapter(listaContasAdapter);

                            Toast.makeText(ContasActivity.this, "Conta Adicionada Com Sucesso! ", Toast.LENGTH_SHORT).show();
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

}
