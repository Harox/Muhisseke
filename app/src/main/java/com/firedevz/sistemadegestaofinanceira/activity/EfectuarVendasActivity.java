package com.firedevz.sistemadegestaofinanceira.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.firedevz.sistemadegestaofinanceira.modelo.Clientes;
import com.firedevz.sistemadegestaofinanceira.modelo.ContaCliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Contas;
import com.firedevz.sistemadegestaofinanceira.modelo.Produtos;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EfectuarVendasActivity extends AppCompatActivity implements View.OnLongClickListener {

    private ListView lstConta,lstProdutos;
    private Button btnPagar,btnFacturar,btnAdicionarAconta;
    private TextView txtconta;
    private ArrayAdapter<String > adpMetodoPagamento;
    private EditText edtQuantidade;
    private AutoCompleteTextView edtNomeProdutoApgar;
    private int total;
    private TextView txtPrecoProduto;
    private float precoProduto;
    private int nuitCliente;
    Toolbar toolbar_vendas;
    public static boolean is_in_action_mode = false;
    TextView counter_text_view;

    private ProcuraProdutosAdapter procuraProdutosAdapter;
    private ProcuraClienteAdapter procuraClienteAdapter;
    private List<Clientes> listaClientes = new ArrayList<>();
    private List<Produtos> listaProdutos = new ArrayList<>();
    private List<ContaCliente> listaProdutosNaConta = new ArrayList<>();
    private ListaProdutosAdapter listaProdutosAdapter ;
    private ListaContaClienteAdapter listaContaClienteAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    ListView lstViewRendimentos;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efectuar_vendas);


        btnPagar = (Button) findViewById(R.id.btnPagar);
        btnFacturar = (Button) findViewById(R.id.btnFacturar);
        //lstConta = (ListView) findViewById(R.id.lstConta);
        txtconta = (TextView) findViewById(R.id.txtconta);
        btnAdicionarAconta = (Button) findViewById(R.id.btnAdicionarAconta);
        edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
        txtPrecoProduto = (TextView) findViewById(R.id.txtPrecoProduto);
        counter_text_view = (TextView) findViewById(R.id.counter_text);
        counter_text_view.setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_conta_cliente);
        toolbar_vendas = (Toolbar)findViewById(R.id.toolbar_vendas);
        setSupportActionBar(toolbar_vendas);

        edtNomeProdutoApgar = (AutoCompleteTextView)findViewById(R.id.edtNomeProdutoApgar);

        listaProdutos = procuraProdutos();
        edtNomeProdutoApgar.setThreshold(1);
        procuraProdutosAdapter = new ProcuraProdutosAdapter(EfectuarVendasActivity.this, R.layout.activity_lista_produtos, R.layout.linha_categoria, listaProdutos);
        edtNomeProdutoApgar.setAdapter(procuraProdutosAdapter);

        // listaTodaConta();


        ////Mostra quantidade depois de selecionar o produto
        edtNomeProdutoApgar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {
                precoProduto = listaProdutos.get(pos).getPreco();
                txtPrecoProduto.setText(precoProduto+"0 MZN");
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
                    db.addContaCliente(new ContaCliente(produto, precoProduto,quantidade));
                    Toast.makeText(EfectuarVendasActivity.this, "Produto adicionado a Lista", Toast.LENGTH_LONG).show();

                    //                    valorRendimentoConta=db.somaRendimentoConta(contaAdicionar);
//                    Toast.makeText(RendimentosActivity.this, "Adicionado a conta: "+contaAdicionar+" "+valorRendimentoConta+"0 MZN", Toast.LENGTH_SHORT).show();
//                    db.adicionaNaConta(contaAdicionar,valorRendimentoConta);
//                    listaRendimentos();
                  //



                    limpaCampos();
                    listaTodaConta();

                }


            }
        });





        listaContaClienteAdapter = new ListaContaClienteAdapter(listaProdutosNaConta);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(listaContaClienteAdapter);
        recyclerView.invalidate();



 /*      *//*listaProdutos();*//*

        lstProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conteudo = (String) lstProdutos.getItemAtPosition(position);

                Toast.makeText(EfectuarVendasActivity.this, "Adicionado a conta: " + conteudo, Toast.LENGTH_LONG).show();
                String codigo = conteudo.substring(0, conteudo.indexOf(":"));
                Produtos produtos = db.selecionarProduto((codigo));

                String nomePro=produtos.getNome();
                float preco=produtos.getPreco();

                total+=preco;

                listaConta(nomePro,preco);
                txtconta.setText("Total: "+total+".00 MNZ");
            }
        });*/

       // listaProdutos();



        btnPagar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                LayoutInflater li = LayoutInflater.from(EfectuarVendasActivity.this);
                View vi = li.inflate(R.layout.popup_pagar, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EfectuarVendasActivity.this);

                alertDialogBuilder.setView(vi);

                final AutoCompleteTextView edtNomeCliente = (AutoCompleteTextView) vi.findViewById(R.id.edtNomeCliente);
                final EditText edtNuitCliente = (EditText) vi.findViewById(R.id.edtNuitCliente);
                final EditText edtDesconto = (EditText) vi.findViewById(R.id.edtDesconto);
                final Spinner spnMetodoPagamento = (Spinner) vi.findViewById(R.id.spnMetodoPagamento);
                final Spinner spnContaEntrar = (Spinner) vi.findViewById(R.id.spnContaEntrar);

                String[] spinnerLists = db.getAllSpinnerAccounts();

                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(EfectuarVendasActivity.this,android.R.layout.simple_spinner_item, spinnerLists);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnContaEntrar.setAdapter(spinnerAdapter);

                adpMetodoPagamento = new ArrayAdapter<String>(EfectuarVendasActivity.this, android.R.layout.simple_spinner_item);
                adpMetodoPagamento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMetodoPagamento.setAdapter(adpMetodoPagamento);

                adpMetodoPagamento.add("Cash");
                adpMetodoPagamento.add("Cartão");
                adpMetodoPagamento.add("M-Pesa");
                adpMetodoPagamento.add("Outro") ;


                listaClientes = procuraCliente();
                edtNomeCliente.setThreshold(1);
                procuraClienteAdapter = new ProcuraClienteAdapter(EfectuarVendasActivity.this, R.layout.activity_lista_clientes, R.layout.linha_categoria, listaClientes);
                edtNomeCliente.setAdapter(procuraClienteAdapter);

                //Adiciona nuite depois de selecionar cliente
                edtNomeCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, int pos,long id) {
                        nuitCliente = listaClientes.get(pos).getNuitCliente();
                        Toast.makeText(EfectuarVendasActivity.this, nuitCliente+"", Toast.LENGTH_LONG).show();
                        edtNuitCliente.setText(nuitCliente+"");
                    }
                });


                alertDialogBuilder.setCancelable(false).setPositiveButton("Pagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String nomeCliente = edtNomeCliente.getText().toString();
                        float  nuitCliente = Float.parseFloat(edtNuitCliente.getText().toString());
                        String descontoCliente = edtDesconto.getText().toString();
                        String metodoPagamento = spnMetodoPagamento.getSelectedItem().toString();
                        String contaEntrar = spnContaEntrar.getSelectedItem().toString();



                        //String nome_conf = contas.getNomeConta()
                        if (db.addConta(new Contas(nomeCliente, nuitCliente, descontoCliente))) {
                            Toast.makeText(EfectuarVendasActivity.this, "Pagamento efectuado Com Sucesso! ", Toast.LENGTH_SHORT).show();

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

    public void listaProdutos() {

        Cursor dados = db.listaTodosProdutos();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Sem Produtoss", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nome = dados.getString(1);
                float preco = Float.parseFloat(dados.getString(6));
                int quantidade = Integer.parseInt(dados.getString(7));

                Produtos listaiten = new Produtos(nome, preco, quantidade);
                listaProdutos.add(listaiten);
            }
        }
    }



    public List<Produtos> procuraProdutos(){
        List<Produtos> listaPro = new ArrayList<>();
        db = new DatabaseHelper(this);
        Cursor dados = db.listaTodosProdutos();
        if(dados.getCount() == 0){
            Toast.makeText(this, "Nenhum Produto", Toast.LENGTH_LONG);
        }else{
            while(dados.moveToNext()){
                String nomeProduto = dados.getString(1);
                float precoProduto = Float.parseFloat(dados.getString(6));
                int quantidadeProdut = Integer.parseInt(dados.getString(7));

                Produtos listaiten = new Produtos(nomeProduto, precoProduto, quantidadeProdut);
                listaPro.add(listaiten);
            }
        }return listaPro;
    }



    public List<Clientes> procuraCliente(){
        List<Clientes> listaClient = new ArrayList<>();
        db = new DatabaseHelper(this);
        Cursor dados = db.listaTodosClientes();
        if(dados.getCount() == 0){
            Toast.makeText(this, "Nenhum Cliente", Toast.LENGTH_LONG);
        }else{
            while(dados.moveToNext()){
                String nomeCliente = dados.getString(1);


                Clientes listaiten = new Clientes(nomeCliente);
                listaClient.add(listaiten);
            }
        }return listaClient;
    }


    void limpaCampos() {
        edtNomeProdutoApgar.setText("");
        edtQuantidade.setText("");
        edtNomeProdutoApgar.requestFocus();
    }

/*    public void listaProdutos() {
        List<Produtos> produtos = db.listaTodosProdutos();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(EfectuarVendasActivity.this, android.R.layout.simple_list_item_1,arrayList);

        lstProdutos.setAdapter(adapter);

        for(Produtos c: produtos){
            // Log.d("Lista","\nID: " + c.getCodigo() + "Nome: "+c.getNome());
            arrayList.add(c.getId()+ ":            "+c.getNome()+ "             "+c.getPreco()+"MZN");
            adapter.notifyDataSetChanged();

        }
    }*/


    public void listaTodaConta() {

        Cursor dados = db.listaConta();

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Comece adicionando produtos a conta", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String nomeProduto = dados.getString(1);
                float valor = Float.parseFloat(dados.getString(2));
                String quantidade = dados.getString(3);

                ContaCliente listaiten = new ContaCliente(nomeProduto, valor, quantidade);
                listaProdutosNaConta.add(listaiten);
            }
        }


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


/////FIM
}
