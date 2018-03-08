package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaSaidasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesas;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaDespesa extends AppCompatActivity {

    private List<Despesas> listaDespesas = new ArrayList<>();
    private ListaSaidasAdapter listaSaidasAdapter ;
    private ArrayAdapter<String > adpTipoDespesa;
    private ArrayAdapter<String> listAdapter;
    private RecyclerView recyclerView;

    private FloatingActionButton BtnAdicionarDespesa;

    DatabaseHelper db = new DatabaseHelper(this);

    Despesas despesas = new Despesas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_despesas);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_desp);
        BtnAdicionarDespesa = (FloatingActionButton) findViewById(R.id.BtnAdicionarDespesa);

        listaSaidasAdapter = new ListaSaidasAdapter(this, listaDespesas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listaSaidasAdapter);


        listaDespesas();


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

                String[] spinnerLists = db.getAllSpinnerAccounts();

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(ActivityListaDespesa.this,android.R.layout.simple_spinner_item, spinnerLists);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnContaRetirada.setAdapter(spinnerAdapter);
//                spnContaRetirada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        return;
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });



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
                        String contaRetirada = spnContaRetirada.getSelectedItem().toString();
                        float totalDespesaCOnta=0;

                        if (db.addDespesa(new Despesas(nomeDespesa, valorDespesa, tipoDespesa,contaRetirada))) {
                            Toast.makeText(ActivityListaDespesa.this, "Despesa Adicionada Com Sucesso! ", Toast.LENGTH_SHORT).show();
                            totalDespesaCOnta=db.somaDespesaConta(contaRetirada);
                            Toast.makeText(ActivityListaDespesa.this, "Retirado da conta: "+contaRetirada+" "+totalDespesaCOnta+"0 MZN", Toast.LENGTH_SHORT).show();
                            db.retiraNaConta(contaRetirada,totalDespesaCOnta);
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


    public void listaDespesas() {

        Cursor dados = db.listaTodasDespesas();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não existem Despesas Registradas", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nomeDespesa = dados.getString(1);
                float valorDespea = Float.parseFloat(dados.getString(2));
                String tipoDespesa = dados.getString(3);

                Despesas listaitem = new Despesas(nomeDespesa, valorDespea, tipoDespesa);
                listaDespesas.add(listaitem);
            }
        }
    }










///FIM/////

}
