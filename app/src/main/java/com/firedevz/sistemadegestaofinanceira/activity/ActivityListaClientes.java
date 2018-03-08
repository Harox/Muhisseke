package com.firedevz.sistemadegestaofinanceira.activity;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaClienteAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Clientes;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaClientes extends AppCompatActivity implements SearchView.OnQueryTextListener{


    private List<Clientes> listaCliente = new ArrayList<>();
    private ListaClienteAdapter listaClienteAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbarCliente;
    private FloatingActionButton BtnAdicionarCliente,floatBDeleteCliente;

    DatabaseHelper db = new DatabaseHelper(this);

    Clientes clientes = new Clientes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        toolbarCliente = (Toolbar) findViewById(R.id.toolbarCliente);
        setSupportActionBar(toolbarCliente);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewClientes);
        BtnAdicionarCliente = (FloatingActionButton) findViewById(R.id.BtnAdicionarCliente);
        floatBDeleteCliente= (FloatingActionButton) findViewById(R.id.floatBDeleteCliente);

        listaClienteAdapter = new ListaClienteAdapter(this,listaCliente);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listaClienteAdapter);

        listaClientes();


        BtnAdicionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ActivityListaClientes.this);
                View vi = li.inflate(R.layout.popup_add_cliente, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityListaClientes.this);


                alertDialogBuilder.setView(vi);


                final EditText edtNomeCliente = (EditText) vi.findViewById(R.id.edtNomeCliente);
                final EditText edtTelefoneCliente = (EditText) vi.findViewById(R.id.edtTelefoneCliente);
                final EditText edtEmailCliente = (EditText) vi.findViewById(R.id.edtEmailCliente);
                final EditText edtEnderecoCliente = (EditText) vi.findViewById(R.id.edtEnderecoCliente);
                final EditText edtNuitCliente = (EditText) vi.findViewById(R.id.edtNuitCliente);

                ///Alert Dialog Start

                alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeCliente = edtNomeCliente.getText().toString();
                        String telefoneCliente = edtTelefoneCliente.getText().toString();
                        String emailCliente = edtEmailCliente.getText().toString();
                        String enderecoCliente = edtEnderecoCliente.getText().toString();
                        int nuitCliente = Integer.parseInt(edtNuitCliente.getText().toString());
                        ;

                        //String nome_conf = contas.getNomeConta()
                        // if (validarFormulario()) {
                        // Toast.makeText(activityListaProdutos.this, "Produto adicionado com Sucesso", Toast.LENGTH_LONG).show();
                        if (db.addCliente(new Clientes(nomeCliente, telefoneCliente, emailCliente, enderecoCliente,nuitCliente))) {
                            Toast.makeText(ActivityListaClientes.this, "Cliente adicionado com Sucesso", Toast.LENGTH_LONG).show();
                            listaClientes();
                        }
//                        else {
//                            // inicio.hideProgressDialog();
//                            Toast.makeText(activityListaProdutos.this, "Preencha Todos os Campos obrigatorios", Toast.LENGTH_LONG).show();
//                        }

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


                //Alert Dialog End

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produtos_search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        ArrayList<Clientes> newList = new ArrayList<>();
        for (Clientes clientes : listaCliente)
        {
            String name = clientes.getNome().toLowerCase();
            if(name.contains(newText)){
                newList.add(clientes);
            }
        }

        listaClienteAdapter.setFilter(newList);
        return true;
    }

    public void listaClientes() {

        Cursor dados = db.listaTodosClientes();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não existem Clientes Registrados", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nomeCliente = dados.getString(1);
                String telefoneCliente = dados.getString(2);
                String emailCliente = dados.getString(3);
                String enderecoCliente = dados.getString(4);
                int nuitClient = dados.getInt(5);
                float estadoCliente = Float.parseFloat(dados.getString(6));

                Clientes listaiten = new Clientes(nomeCliente, telefoneCliente, emailCliente,enderecoCliente,nuitClient,estadoCliente);
                listaCliente.add(listaiten);
            }
        }
    }




///Fim//////
}
