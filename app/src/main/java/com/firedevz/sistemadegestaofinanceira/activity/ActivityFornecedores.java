package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaFornecedorAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Fornecedor;

import java.util.ArrayList;
import java.util.List;

//import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;


public class ActivityFornecedores extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Fornecedor fornecedor = new Fornecedor();
    private List<Fornecedor> listaFornecedor = new ArrayList<>();
    private ListaFornecedorAdapter listaFornecedorAdapter;
    private ArrayAdapter<String> adpTipoFornecedor;
    private RecyclerView recyclerView;
    private Toolbar toolbarFonecedor;

//    DatabaseHelper db = new DatabaseHelper(this);
    private FloatingActionButton BtnAdicionarFornecedor, floatBDeleteFornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fornecedores);

        toolbarFonecedor = (Toolbar) findViewById(R.id.toolbarFonecedor);
        setSupportActionBar(toolbarFonecedor);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFornecedores);
        BtnAdicionarFornecedor = (FloatingActionButton) findViewById(R.id.BtnAdicionarFornecedor);
        floatBDeleteFornecedor = (FloatingActionButton) findViewById(R.id.floatBDeleteFornecedor);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listaFornecedor = Fornecedor.list();
        listaFornecedorAdapter = new ListaFornecedorAdapter(this, listaFornecedor);
        recyclerView.setAdapter(listaFornecedorAdapter);

        BtnAdicionarFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(ActivityFornecedores.this);
                View vi = li.inflate(R.layout.popup_add_fornecedor, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityFornecedores.this);


                alertDialogBuilder.setView(vi);


                final EditText edtNomeFornecedor = (EditText) vi.findViewById(R.id.edtNomeFornecedor);
                final EditText edtTelefoneFornecedor = (EditText) vi.findViewById(R.id.edtTelefoneFornecedor);
                final EditText edtEmailFornecedor = (EditText) vi.findViewById(R.id.edtEmailFornecedor);
                final EditText edtEnderecoFornecedor = (EditText) vi.findViewById(R.id.edtEnderecoFornecedor);
                final Spinner spnTipoFornecedor = (Spinner) vi.findViewById(R.id.spnTipoFornecedor);
                final EditText edtTipoProdutoFornecedor = (EditText) vi.findViewById(R.id.edtTipoProdutoFornecedor);


                adpTipoFornecedor = new ArrayAdapter<String>(ActivityFornecedores.this, android.R.layout.simple_spinner_item);
                adpTipoFornecedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTipoFornecedor.setAdapter(adpTipoFornecedor);

                adpTipoFornecedor.add("Singular");
                adpTipoFornecedor.add("Empresa");

                ///Alert Dialog Start

                alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nomeFornecedor = edtNomeFornecedor.getText().toString();
                        String telefoneFornecedor = edtTelefoneFornecedor.getText().toString();
                        String emailFornecedor = edtEmailFornecedor.getText().toString();
                        String enderecoFornecedor = edtEnderecoFornecedor.getText().toString();
                        String tipoFornecedor = (String) spnTipoFornecedor.getSelectedItem();
                        String tipoProdutoFornecedor = edtTipoProdutoFornecedor.getText().toString();
                        Fornecedor fornecedor = new Fornecedor(nomeFornecedor, telefoneFornecedor, emailFornecedor, enderecoFornecedor, tipoFornecedor, tipoProdutoFornecedor);

                        if (Fornecedor.register(fornecedor)) {
                            Toast.makeText(ActivityFornecedores.this, "Fornecedor adicionado com Sucesso", Toast.LENGTH_LONG).show();
                            listaFornecedor = Fornecedor.list();
                            listaFornecedorAdapter = new ListaFornecedorAdapter(ActivityFornecedores.this, listaFornecedor);
                            recyclerView.setAdapter(listaFornecedorAdapter);
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
        ArrayList<Fornecedor> newList = new ArrayList<>();
        for (Fornecedor fornecedor : listaFornecedor) {
            String name = fornecedor.getNomeFornecedor().toLowerCase();
            if (name.contains(newText)) {
                newList.add(fornecedor);
            }
        }

        listaFornecedorAdapter.setFilter(newList);
        return true;
    }

    public void listaFornecedores() {



        /*Cursor dados = db.listaTodosFornecedores();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não existem Fornecedor Registrados", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nomeFornecedor = dados.getString(1);
                String telefoneFornecedor = dados.getString(2);
                String emailFornecedor = dados.getString(3);
                String enderecoFornecedor = dados.getString(4);
                String tipoFornecedor = dados.getString(5);
                String tipoProdutoFornecedor = dados.getString(6);

                Fornecedor listaiten = new Fornecedor(nomeFornecedor, telefoneFornecedor, emailFornecedor,enderecoFornecedor,tipoFornecedor,tipoProdutoFornecedor);
                listaFornecedor.add(listaiten);
            }
        }*/
    }


///Fim//////
}
