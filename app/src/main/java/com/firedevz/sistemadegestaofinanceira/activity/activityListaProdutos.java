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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ProcuraProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class activityListaProdutos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //DatabaseHelper db = new DatabaseHelper(this);
    Produto produto = new Produto();
    private List<Produto> listaProdutos = new ArrayList<>();
    private ListaProdutosAdapter listaProdutosAdapter;
    private ProcuraProdutosAdapter procuraProdutosAdapter;
    private ArrayAdapter<String> adpCategoria;
    private ArrayAdapter<String> adpUnidade;
    //*****************************


    //****************************
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private FloatingActionButton BtnAdicionarProdut, floatBDeleteProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);


        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        recyclerView = findViewById(R.id.recycle_listaPro);
        BtnAdicionarProdut = findViewById(R.id.BtnAdicionarProdut);
        floatBDeleteProduto = findViewById(R.id.floatBDeleteProduto);

        //listaProdutos();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        listaProdutos = Produto.list();
        listaProdutosAdapter = new ListaProdutosAdapter(this, listaProdutos);
        recyclerView.setAdapter(listaProdutosAdapter);

        BtnAdicionarProdut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(activityListaProdutos.this);
                View vi = li.inflate(R.layout.popup_add_produtos, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activityListaProdutos.this);


                alertDialogBuilder.setView(vi);

                final EditText edtData = vi.findViewById(R.id.edtData);
                final EditText edtPrazo = vi.findViewById(R.id.edtPrazo);
                final EditText edtPreco = vi.findViewById(R.id.edtPreco);
                final EditText edtPrecoVenda = vi.findViewById(R.id.edtPrecoVenda);
                final EditText edtQuantidade = vi.findViewById(R.id.edtQuantidade);
                final EditText edtNotificarEstoque = (EditText) vi.findViewById(R.id.edtNotificarEstoque);
                final EditText edtFornecedor = (EditText) vi.findViewById(R.id.edtFornecedor);
                final Spinner spnCategoria = (Spinner) vi.findViewById(R.id.spnCategoria);
                final Spinner spnUnidade = (Spinner) vi.findViewById(R.id.spnUnidade);
                final AutoCompleteTextView edtNomeProduto = (AutoCompleteTextView) vi.findViewById(R.id.edtNomeProduto);

                List<Produto> listaProdutoAuxes = Produto.list();
                edtNomeProduto.setThreshold(1);
                procuraProdutosAdapter = new ProcuraProdutosAdapter(activityListaProdutos.this, R.layout.activity_lista_produtos, R.layout.linha_categoria, listaProdutoAuxes);
                edtNomeProduto.setAdapter(procuraProdutosAdapter);


                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                //SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

                edtData.setText(dateFormat.format(new Date()));

                adpCategoria = new ArrayAdapter<>(activityListaProdutos.this, android.R.layout.simple_spinner_item);
                adpCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategoria.setAdapter(adpCategoria);

                adpCategoria.add("bebidas Alcoolicas");
                adpCategoria.add("bebidas Alcoolicas");
                adpCategoria.add("Fruta");
                adpCategoria.add("Sumos");
                adpCategoria.add("Mariscos");
                adpCategoria.add("Carnes");
                adpCategoria.add("Vegetais");
                adpCategoria.add("Cereais");
                adpCategoria.add("Doces e salgados");
                adpCategoria.add("Outro");

                adpUnidade = new ArrayAdapter<>(activityListaProdutos.this, android.R.layout.simple_spinner_item);
                adpUnidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnUnidade.setAdapter(adpUnidade);

                adpUnidade.add("kg");
                adpUnidade.add("unidade");
                adpUnidade.add("Caixa");
                adpUnidade.add("Litros");
                adpUnidade.add("doze");
                adpUnidade.add("chot");
                adpUnidade.add("Molho");
                adpUnidade.add("metros");
                adpUnidade.add("Não identificada");

                alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nome = edtNomeProduto.getText().toString();
                        String data = edtData.getText().toString();
                        String categoria = (String) spnCategoria.getSelectedItem();
                        String prazo = edtPrazo.getText().toString();
                        String preco = edtPreco.getText().toString();
                        String precoVenda = edtPrecoVenda.getText().toString();
                        String quantidade = edtQuantidade.getText().toString();
                        String Unidade = (String) spnUnidade.getSelectedItem();
                        String estoqueMinimo = edtNotificarEstoque.getText().toString();
                        String nomeFornecedor = edtFornecedor.getText().toString();

                        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(data) || TextUtils.isEmpty(preco) || TextUtils.isEmpty(precoVenda) || TextUtils.isEmpty(quantidade) || TextUtils.isEmpty(estoqueMinimo)) {
                            Toast.makeText(activityListaProdutos.this, "Ocorreu um erro! tente novamente ", Toast.LENGTH_LONG).show();
                            edtNomeProduto.setError("Campo Obrigatório..");
                            edtQuantidade.setError("Campo Obrigatório.");
                            edtPrecoVenda.setError("Campo Obrigatório..");
                            edtPreco.setError("Campo Obrigatório..");
                            edtNotificarEstoque.setError("Campo Obrigatório..");
                            Toast.makeText(activityListaProdutos.this, "preencha todos os campos Vazios ", Toast.LENGTH_LONG).show();

                        } else {
                            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date datePrazo = Calendar.getInstance().getTime();
                            Date dateEntrada = Calendar.getInstance().getTime();
                            try {
                                dateEntrada = sourceFormat.parse(data);
                                datePrazo = sourceFormat.parse(prazo);
                            } catch (ParseException e) {
//                                e.printStackTrace();
                            }
                            Produto produto = new Produto(nome, dateEntrada, categoria, datePrazo, Float.parseFloat(preco), Float.parseFloat(precoVenda), Integer.parseInt(quantidade), Unidade, Integer.parseInt(estoqueMinimo), nomeFornecedor);
                            Produto.register(produto);
                            Toast.makeText(activityListaProdutos.this, "ProdutoVenda adicionado com Sucesso", Toast.LENGTH_LONG).show();

                            listaProdutos = Produto.list();
                            listaProdutosAdapter = new ListaProdutosAdapter(activityListaProdutos.this, listaProdutos);
                            recyclerView.setAdapter(listaProdutosAdapter);

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


        floatBDeleteProduto.setOnClickListener(new View.OnClickListener() {
            Produto produto = new Produto();

            @Override
            public void onClick(View v) {

                int code = this.produto.getId();

                String codigo = String.valueOf(Integer.parseInt(String.valueOf(code)));
                Toast.makeText(activityListaProdutos.this, "Selecione um ProdutoVenda", Toast.LENGTH_LONG).show();

                Produto produto = new Produto();
                produto.setId(Integer.parseInt(codigo));
//                db.apagarProduto(produto.getId());

                Toast.makeText(activityListaProdutos.this, "ProdutoVenda Excluido com Sucesso", Toast.LENGTH_LONG).show();

                //listaProdutos();
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
        ArrayList<Produto> newList = new ArrayList<>();
        for (Produto produto : listaProdutos) {
            String name = produto.getNome().toLowerCase();
            String categoria = produto.getCategoria().toLowerCase();
            String preco = String.valueOf(produto.getPreco());
            if (name.contains(newText)
                            || categoria.contains(newText)
                            || preco.contains(newText)
                    ) {
                newList.add(produto);
            }
        }

        listaProdutosAdapter.setFilter(newList);
        return true;
    }

    /*public List<ProdutoVenda> listaProdutos() {
        listaProdutos = new ArrayList<>();
        Cursor dados = db.listaTodosProdutos();
        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não existem ProdutoVenda no estoque", Toast.LENGTH_LONG);
        } else {
            while (dados.moveToNext()) {
                int idProduto = dados.getInt(0);
                String nomeProduto = dados.getString(1);
                float precoProduto = Float.parseFloat(dados.getString(6));
                int quantidadeProdut = Integer.parseInt(dados.getString(7));
                String prazo = dados.getString(4);
                String categoria = dados.getString(3);

                ProdutoVenda listaiten = new ProdutoVenda(idProduto, nomeProduto, precoProduto, quantidadeProdut, prazo, categoria);
                listaProdutos.add(listaiten);
            }
        }
        return listaProdutos;
    }*/



}
