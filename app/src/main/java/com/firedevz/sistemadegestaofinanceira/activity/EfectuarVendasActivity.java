package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaContaClienteAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ProcuraClienteAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ProcuraProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.ContaCliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EfectuarVendasActivity extends AppCompatActivity implements View.OnLongClickListener {

    public static boolean is_in_action_mode = false;
    Toolbar toolbar_vendas;
    TextView counter_text_view;
    double preco = 0.0;
    //    DatabaseHelper db = new DatabaseHelper(this);
    private ListView lstConta, lstProdutos;
    private Button btnPagar, btnFacturar, btnAdicionarAconta;
    private ArrayAdapter<String> adpMetodoPagamento;
    private EditText edtQuantidade;
    private AutoCompleteTextView edtNomeProdutoApgar;
    private int total;
    private TextView txtPrecoProduto;
    private TextView counter_text;
    private TextView textViewTotal;
    private float precoProduto;
    private int idProduto;
    private int nuitCliente;
    private int idCliente;
    private ProcuraProdutosAdapter procuraProdutosAdapter;
    private ProcuraClienteAdapter procuraClienteAdapter;
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<ContaCliente> listaProdutosNaConta = new ArrayList<>();
    private ListaProdutosAdapter listaProdutosAdapter;
    private ListaContaClienteAdapter listaContaClienteAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Venda venda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efectuar_vendas);

        venda = new Venda();
        venda.setDate(Calendar.getInstance().getTime());

        btnPagar = findViewById(R.id.btnPagar);
        btnFacturar = findViewById(R.id.btnFacturar);
        counter_text = findViewById(R.id.counter_text);
        textViewTotal = findViewById(R.id.textViewTotal);

        btnAdicionarAconta = findViewById(R.id.btnAdicionarAconta);
        edtQuantidade = findViewById(R.id.edtQuantidade);
        txtPrecoProduto = findViewById(R.id.txtPrecoProduto);
        counter_text_view = findViewById(R.id.counter_text);
        counter_text_view.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recycle_lista_conta_cliente);
        toolbar_vendas = findViewById(R.id.toolbar_vendas);
        setSupportActionBar(toolbar_vendas);

        edtNomeProdutoApgar = findViewById(R.id.edtNomeProdutoApgar);

        counter_text.setText("Efectuar venda");


        listaProdutos = Produto.list();
        edtNomeProdutoApgar.setThreshold(1);
        procuraProdutosAdapter = new ProcuraProdutosAdapter(EfectuarVendasActivity.this, R.layout.activity_lista_produtos, R.layout.linha_categoria, listaProdutos);
        edtNomeProdutoApgar.setAdapter(procuraProdutosAdapter);

        ////Mostra quantidade depois de selecionar o produto
        edtNomeProdutoApgar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                precoProduto = listaProdutos.get(pos).getPreco();
                idProduto = listaProdutos.get(pos).getId();
                txtPrecoProduto.setText(precoProduto + "0 MZN");
//                txtPrecoProduto[0] = produStockModels.get(pos).getProduto_preco_venda();
            }
        });

        btnAdicionarAconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String produto = edtNomeProdutoApgar.getText().toString();
                String quantidade = edtQuantidade.getText().toString();
                if (produto.isEmpty() || quantidade.isEmpty()) {
                    edtNomeProdutoApgar.setError("campo Obrigatorio");
                    edtQuantidade.setError("campo Obrigatorio");
                } else {
                    //insert
                    //db.addContaCliente(new ContaCliente(produto, precoProduto, quantidade));
                    Venda.ProdutoVenda produtoVendaAux = venda.createProdutoObject();
                    produtoVendaAux.nome = produto;
                    produtoVendaAux.quantidade = Double.parseDouble(quantidade);
                    produtoVendaAux.preco = precoProduto;
                    produtoVendaAux.idProduto = idProduto;

                    int i = 0;
                    boolean added = true;
                    for (Venda.ProdutoVenda vendaProdutoVenda : venda.getProdutoVendas()) {
                        if (vendaProdutoVenda.idProduto == produtoVendaAux.idProduto) {
                            vendaProdutoVenda.quantidade = vendaProdutoVenda.quantidade + produtoVendaAux.quantidade;
                            venda.getProdutoVendas().set(i, vendaProdutoVenda);
                            added = false;
                            break;
                        }
                        i++;
                    }
                    if (added) {
                        venda.getProdutoVendas().add(produtoVendaAux);
                    }

                    if (venda.getProdutoVendas().size() == 0) {
                        venda.getProdutoVendas().add(produtoVendaAux);
                    }

                    listaProdutosNaConta.add(new ContaCliente(produto, precoProduto, quantidade));
                    listaContaClienteAdapter = new ListaContaClienteAdapter(venda.getProdutoVendas());
                    recyclerView.setAdapter(listaContaClienteAdapter);

                    double total = 0;
                    for (Venda.ProdutoVenda produtoVenda : venda.getProdutoVendas()) {
                        total = total + (produtoVenda.quantidade * produtoVenda.preco);
                    }
                    Log.d("DEBUG", total+"Mts");
                    textViewTotal.setText(total+" Mts");

                    Toast.makeText(EfectuarVendasActivity.this, "ProdutoVenda adicionado a Lista", Toast.LENGTH_LONG).show();

                    limpaCampos();
                }


            }
        });


        listaContaClienteAdapter = new ListaContaClienteAdapter(venda.getProdutoVendas());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listaContaClienteAdapter);
        recyclerView.invalidate();

        btnPagar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                fechar(true);
            }
        });

        btnFacturar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                fechar(false);
            }
        });
    }

    void fechar(final boolean pagar) {
        preco = 0;

        LayoutInflater li = LayoutInflater.from(EfectuarVendasActivity.this);
        View vi = li.inflate(R.layout.popup_pagar, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EfectuarVendasActivity.this);

        alertDialogBuilder.setView(vi);

        final AutoCompleteTextView edtNomeCliente = vi.findViewById(R.id.edtNomeCliente);
        final EditText edtNuitCliente = vi.findViewById(R.id.edtNuitCliente);
        final EditText edtDesconto = vi.findViewById(R.id.edtDesconto);
        final TextView txtConta = vi.findViewById(R.id.txtConta);
        final TextView txtValorIva = vi.findViewById(R.id.txtValorIva);
        final Spinner spnMetodoPagamento = vi.findViewById(R.id.spnMetodoPagamento);
        final Spinner spnContaEntrar = vi.findViewById(R.id.spnContaEntrar);

        String[] spinnerLists = Conta.listStringArray();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EfectuarVendasActivity.this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnContaEntrar.setAdapter(spinnerAdapter);

        adpMetodoPagamento = new ArrayAdapter<>(EfectuarVendasActivity.this, android.R.layout.simple_spinner_item);
        adpMetodoPagamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMetodoPagamento.setAdapter(adpMetodoPagamento);

        adpMetodoPagamento.add("Cash");
        adpMetodoPagamento.add("Cartão");
        adpMetodoPagamento.add("M-Pesa");
        adpMetodoPagamento.add("Outro");


        for (Venda.ProdutoVenda produtoVenda : venda.getProdutoVendas()) {
            Log.d("debugger", produtoVenda.quantidade + "Q");
            preco = preco + (produtoVenda.preco * produtoVenda.quantidade);
        }

        if (!edtDesconto.getText().toString().equals("")) {
            final double desconto = Double.parseDouble(edtDesconto.getText().toString());
            preco = (preco * (desconto / 100));
        }
        final double iva = preco * 0.17;
        preco = preco + iva;
        txtValorIva.setText((iva) + " Mt");
        txtConta.setText(preco + " Mt");

        listaClientes = Cliente.list();

        edtNomeCliente.setThreshold(1);
        procuraClienteAdapter = new ProcuraClienteAdapter(EfectuarVendasActivity.this, R.layout.activity_lista_clientes, R.layout.linha_categoria, listaClientes);
        edtNomeCliente.setAdapter(procuraClienteAdapter);

        //Adiciona nuite depois de selecionar cliente
        edtNomeCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                nuitCliente = listaClientes.get(pos).getNuitCliente();
                idCliente = listaClientes.get(pos).getId();
                Toast.makeText(EfectuarVendasActivity.this, nuitCliente + "", Toast.LENGTH_LONG).show();
                edtNuitCliente.setText(nuitCliente + "");
            }
        });


        alertDialogBuilder.
                setPositiveButton("Pagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String nomeCliente = edtNomeCliente.getText().toString();
                        float nuitCliente = Float.parseFloat(edtNuitCliente.getText().toString());
                        String descontoCliente = edtDesconto.getText().toString();
                        String metodoPagamento = spnMetodoPagamento.getSelectedItem().toString();
                        String contaEntrar = spnContaEntrar.getSelectedItem().toString();

                        //String nome_conf = conta.getNomeConta()
                        if (pagar) {
                            venda.setConta(Conta.list().get(spnContaEntrar.getSelectedItemPosition()).getId());
                            venda.setMetodoPagamento(metodoPagamento);
                        }

                        venda.setPrecoTotal(preco);
                        venda.setPago(pagar);
                        venda.setIdCliente(idCliente);
                        if (Venda.register(venda)) {
                            Toast.makeText(EfectuarVendasActivity.this, "Pagamento efectuado Com Sucesso! ", Toast.LENGTH_SHORT).show();
                            EfectuarVendasActivity.this.finish();
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

    void limpaCampos() {
        edtNomeProdutoApgar.setText("");
        txtPrecoProduto.setText("");
        edtQuantidade.setText("");
        edtNomeProdutoApgar.requestFocus();
    }

    @Override
    public boolean onLongClick(View v) {
        toolbar_vendas.getMenu().clear();
        toolbar_vendas.inflateMenu(R.menu.menu_efectua_vendas_action_mode);
        counter_text_view.setVisibility(View.VISIBLE);
        is_in_action_mode = true;
        adapter.notifyDataSetChanged();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

}
