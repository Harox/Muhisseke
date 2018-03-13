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
import com.firedevz.sistemadegestaofinanceira.adapter.ListaSaidasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaDespesa extends AppCompatActivity {

    Despesa despesa = new Despesa();
    private List<Despesa> listaDespesas = new ArrayList<>();
    private ListaSaidasAdapter listaSaidasAdapter;
    private ArrayAdapter<String> adpTipoDespesa;
    private ArrayAdapter<String> listAdapter;
    private RecyclerView recyclerView;

//    DatabaseHelper db = new DatabaseHelper(this);
    private FloatingActionButton BtnAdicionarDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_despesas);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_desp);
        BtnAdicionarDespesa = (FloatingActionButton) findViewById(R.id.BtnAdicionarDespesa);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listaDespesas = Despesa.list();
        listaSaidasAdapter = new ListaSaidasAdapter(this, listaDespesas);
        recyclerView.setAdapter(listaSaidasAdapter);

        BtnAdicionarDespesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ActivityListaDespesa.this);
                View vi = li.inflate(R.layout.popup_add_despesas, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityListaDespesa.this);

                alertDialogBuilder.setView(vi);

                final EditText edtNomeDespesa = (EditText) vi.findViewById(R.id.edtNomeDespesa);
                final EditText edtValorDespesa = (EditText) vi.findViewById(R.id.edtValorDespesa);
                final Spinner spnTipoDespesa = (Spinner) vi.findViewById(R.id.spnTipoDespesa);
                final Spinner spnContaRetirada = (Spinner) vi.findViewById(R.id.spnContaRetirada);

                String[] spinnerLists = Conta.listStringArray();//db.getAllSpinnerAccounts();

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ActivityListaDespesa.this, android.R.layout.simple_spinner_item, spinnerLists);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnContaRetirada.setAdapter(spinnerAdapter);

                adpTipoDespesa = new ArrayAdapter<String>(ActivityListaDespesa.this, android.R.layout.simple_spinner_item);
                adpTipoDespesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTipoDespesa.setAdapter(adpTipoDespesa);

                adpTipoDespesa.add("Pessoal");
                adpTipoDespesa.add("Comercial");
                adpTipoDespesa.add("Imposto");
                adpTipoDespesa.add("Outro");


                alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeDespesa = edtNomeDespesa.getText().toString();
                        float valorDespesa = Float.parseFloat(edtValorDespesa.getText().toString());
                        String tipoDespesa = spnTipoDespesa.getSelectedItem().toString();
                        int contaRetirada = spnContaRetirada.getSelectedItemPosition();
                        float totalDespesaCOnta = 0;

                        if (Despesa.register(new Despesa(nomeDespesa, valorDespesa, tipoDespesa, contaRetirada))) {
                            Toast.makeText(ActivityListaDespesa.this, "Despesa Adicionada Com Sucesso! ", Toast.LENGTH_SHORT).show();
//                            totalDespesaCOnta = db.somaDespesaConta(contaRetirada);
                            Toast.makeText(ActivityListaDespesa.this, "Retirado da conta: " + contaRetirada + " " + totalDespesaCOnta + "0 MZN", Toast.LENGTH_SHORT).show();
//                            db.retiraNaConta(contaRetirada, totalDespesaCOnta);
                            listaDespesas = Despesa.list();
                            listaSaidasAdapter = new ListaSaidasAdapter(ActivityListaDespesa.this, listaDespesas);
                            recyclerView.setAdapter(listaSaidasAdapter);
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


    /*public void listaDespesas() {

        Cursor dados = db.listaTodasDespesas();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não existem Despesa Registradas", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nomeDespesa = dados.getString(1);
                float valorDespea = Float.parseFloat(dados.getString(2));
                String tipoDespesa = dados.getString(3);

                Despesa listaitem = new Despesa(nomeDespesa, valorDespea, tipoDespesa);
                listaDespesas.add(listaitem);
            }
        }
    }*/


///FIM/////

}
