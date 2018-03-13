package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRequisicaoAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ProcuraClienteAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ProcuraProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;
import com.firedevz.sistemadegestaofinanceira.modelo.Requisicao;

import java.util.ArrayList;
import java.util.List;

public class RequisicoesActivity extends AppCompatActivity {

    int idCliente = 0;
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Requisicao> listaRequisicao = new ArrayList<>();
    ListaRequisicaoAdapter listaRequisicaoAdapter;
    AlertDialog alertDialog;
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicoes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        listaRequisicao = Requisicao.list();

        recycleView = findViewById(R.id.recycleView);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        listaRequisicaoAdapter = new ListaRequisicaoAdapter(this, listaRequisicao);
        recycleView.setAdapter(listaRequisicaoAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(RequisicoesActivity.this);
                View vi = li.inflate(R.layout.popup_add_requisicao, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RequisicoesActivity.this);

                alertDialogBuilder.setView(vi);

                final AutoCompleteTextView edtNomeCliente = vi.findViewById(R.id.edtNomeCliente);
                final AutoCompleteTextView edtNomeProduto = vi.findViewById(R.id.edtNomeProduto);

                listaClientes = Cliente.list();

                edtNomeCliente.setThreshold(1);
                ProcuraClienteAdapter procuraClienteAdapter = new ProcuraClienteAdapter(RequisicoesActivity.this, R.layout.activity_lista_clientes, R.layout.linha_categoria, listaClientes);
                edtNomeCliente.setAdapter(procuraClienteAdapter);

                //Adiciona nuite depois de selecionar cliente
                edtNomeCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                        idCliente = listaClientes.get(pos).getId();
                        edtNomeCliente.setText(listaClientes.get(pos).getNome() + "");
                    }
                });


                listaProdutos = Produto.list();
                edtNomeProduto.setThreshold(1);
                ProcuraProdutosAdapter procuraProdutosAdapter = new ProcuraProdutosAdapter(RequisicoesActivity.this, R.layout.activity_lista_produtos, R.layout.linha_categoria, listaProdutos);
                edtNomeProduto.setAdapter(procuraProdutosAdapter);

                alertDialogBuilder.
                        setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Requisicao requisicao = new Requisicao();
                                requisicao.idCliente = idCliente;
                                requisicao.produto = edtNomeProduto.getText().toString();

                                Requisicao.register(requisicao);
                                alertDialog.dismiss();

                                listaRequisicao = Requisicao.list();
                                recycleView.setLayoutManager(new LinearLayoutManager(RequisicoesActivity.this));
                                listaRequisicaoAdapter = new ListaRequisicaoAdapter(RequisicoesActivity.this, listaRequisicao);
                                recycleView.setAdapter(listaRequisicaoAdapter);
                            }
                        }).setNegativeButton("Cancelar", null);

                // Criar O Alerta

                alertDialog = alertDialogBuilder.create();
                // Mostra o alerta
                alertDialog.show();

            }
        });
    }

}
