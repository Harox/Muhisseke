package com.firedevz.sistemadegestaofinanceira.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.widget.EditText;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaClienteAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaClientes extends AppCompatActivity implements SearchView.OnQueryTextListener {


    //    DatabaseHelper db = new DatabaseHelper(this);
    Cliente cliente = new Cliente();
    private List<Cliente> listaCliente = new ArrayList<>();
    private ListaClienteAdapter listaClienteAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbarCliente;
    private FloatingActionButton BtnAdicionarCliente, floatBDeleteCliente;
    private CursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        Contacts.initialize(this);

        toolbarCliente = (Toolbar) findViewById(R.id.toolbarCliente);
        setSupportActionBar(toolbarCliente);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewClientes);
        BtnAdicionarCliente = (FloatingActionButton) findViewById(R.id.BtnAdicionarCliente);
        floatBDeleteCliente = (FloatingActionButton) findViewById(R.id.floatBDeleteCliente);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

//        listaClientes();
        listaCliente = Cliente.list();
        listaClienteAdapter = new ListaClienteAdapter(this, listaCliente);
        recyclerView.setAdapter(listaClienteAdapter);

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
                        Cliente cliente = new Cliente(nomeCliente, telefoneCliente, emailCliente, enderecoCliente, nuitCliente);

                        if (Cliente.register(cliente)) {
                            Toast.makeText(ActivityListaClientes.this, "Cliente adicionado com Sucesso", Toast.LENGTH_LONG).show();

                            listaCliente = Cliente.list();
                            listaClienteAdapter = new ListaClienteAdapter(ActivityListaClientes.this, listaCliente);
                            recyclerView.setAdapter(listaClienteAdapter);
                        }

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        final String[] from = new String[]{"cityName", "cityBla"};
        final int[] to = new int[]{android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produtos_search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Query q = Contacts.getQuery();
                q.hasPhoneNumber();
                List<Contact> contacts = q.find();
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return true;
            }
        });
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
        ArrayList<Cliente> newList = new ArrayList<>();
        for (Cliente cliente : listaCliente) {
            String name = cliente.getNome().toLowerCase();
            if (name.contains(newText)) {
                newList.add(cliente);
            }
        }
        listaClienteAdapter.setFilter(newList);
        return true;
    }

//    public void listaClientes() {
//        listaCliente = new ArrayList<>();
//        Cursor dados = db.listaTodosClientes();
//
//        if (dados.getCount() == 0) {
//            Toast.makeText(this, "Não existem Cliente Registrados", Toast.LENGTH_LONG).show();
//        } else {
//            while (dados.moveToNext()) {
//                String nomeCliente = dados.getString(1);
//                String telefoneCliente = dados.getString(2);
//                String emailCliente = dados.getString(3);
//                String enderecoCliente = dados.getString(4);
//                int nuitClient = dados.getInt(5);
//                float estadoCliente = Float.parseFloat(dados.getString(6));
//
//                Cliente listaiten = new Cliente(nomeCliente, telefoneCliente, emailCliente, enderecoCliente, nuitClient, estadoCliente);
//                listaCliente.add(listaiten);
//            }
//        }
//    }
}
