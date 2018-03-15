package com.firedevz.sistemadegestaofinanceira.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.ContaCliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;
import com.firedevz.sistemadegestaofinanceira.modelo.Fornecedor;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.modelo.Usuario;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_muhisseke.db";

    private final Context context;

    // Table Names
    private static final String TABELA_USUARIO = " tb_usuario ";
    private static final String TABELA_CONTAS = "tb_contas";
    private static final String TABELA_DESPESAS = "tb_despesas";
    private static final String TABELA_PRODUTO = "tb_produto";
    private static final String TABELA_RENDIMENTO = "tb_rendimento";
    private static final String TABELA_VENDAS = "tb_vendas";
    private static final String TABELA_CLIENTES = "tb_clientes";
    private static final String TABELA_MOVIMENTOS = "tb_movimentos";
    private static final String TABELA_FORNECEDOR= "tb_fornecedor";
    private static final String TABELA_CONTA_CLIENTE= "tb_conta_cliente";

    ////*********************************************************************************************

    //TRIGGERS NAMES

    private static final String TRIGGER_MOVIMENTOSAIDA = "movimentoSaida";
    private static final String TRIGGER_MOVIMENTOENTRADA = "movimentoEntrada";
    private static final String TRIGGER_CRIAFORNECEDOR = "criaFornecedor";

    //************************************************************************************************

    // TABELA USUARIO - Nome de colunas
    private static String COLUNA_ID_DESPESA = "id_despesa";
    private static String COLUNA_DESCRICAO_DESPESA = "descricao_despesa";
    private static String COLUNA_VALOR_DESPESA= "valor_despesa";
    private static String COLUNA_TIPO_DESPESA  = "tipo_despesa";
    private static String COLUNA_CONTA_RETIRADA = "conta_retirada";

    // TABELA USUARIO - Nome de colunas
    private static String COLUNA_ID_USUARIO = "id_usuario";
    private static String COLUNA_NOME_USUARIO = "nome";
    private static String COLUNA_TELEFONE_USUARIO = "telefone";
    private static String COLUNA_EMAIL_USUARIO = "email";
    private static String COLUNA_ENDERECO_USUARIO = "endereço";
    private static String COLUNA_SENHA_USUARIO = "senha";

    // TABELA PRODUTOS - Nome de colunas
    public static String COLUNA_ID_PRODUTO = "id_produto";
    public static String COLUNA_NOME_PRODUTO = "descricao_produto";
    public static String COLUNA_DATA_PRODUTO= "dia_entrada";
    public static String COLUNA_CATEGORIA_PRODUTO= "categoria_produto";
    public static String COLUNA_VALIDADE_PRODUTO= "prazo_produto";
    public static String COLUNA_PRECO_COMPRA_PRODUTO = "preco_compra";
    public static String COLUNA_PRECO_VENDA_PRODUTO = "preco_venda";
    public static String COLUNA_QUANTIDADE_PRODUTO= "quantidade";
    public static String COLUNA_UNIDADE_PRODUTO= "unidade";
    public static String COLUNA_ESTOQUEMINIMO_PRODUTO= "estoque_minimo";
    public static String COLUNA_NOME_FORNECEDOR_PRODUTO= "nome_fornecedor";


    // TABELA RENDIMENTO - Nome de colunas
    private static String COLUNA_ID_RENDIMENTO = "id_rendimento";
    private static String COLUNA_DESCRICAO_RENDIMENTO = "descricao_rendimento";
    private static String COLUNA_VALOR_RENDIMETNO = "valor_rendimento";
    private static String COLUNA_TIPO_RENDIMENTO = "tipo_de_rendimento";
    private static String COLUNA_DATA_RENDIMENO = "data_rendimento";
    private static String COLUNA_CONTA_ADICIONADA = "conta_adicionada";

    // TABELA CONTA CLIENTE - Nome de colunas
    private static String COLUNA_ID_PRODUTO_CONTA = "id_produto_conta";
    private static String COLUNA_NOME_PRODUTO_CONTA = "nome_produto_conta";
    private static String COLUNA_PRECO_PRODUTO_CONTA = "preco_produto_conta";
    private static String COLUNA_QUANTIDADE_PRODUTO_CONTA = "quantidade_produto_conta";

    // TABELA CONTAS - Nome de colunas
    private static String COLUNA_ID_CONTA = "id_conta";
    private static String COLUNA_NOME_CONTA = "conta";
    private static String COLUNA_TIPO_CONTA= "tipo_conta";
    private static String COLUNA_SALDO_INICIAL_CONTA= "saldo_inicial";
    private static String COLUNA_SALDO_ACTUAL= "saldo_actual";

    // TABELA VENDAS - Nome de colunas
    private static String COLUNA_ID_VENDA = "id_vendas";
    private static String COLUNA_QUANTIDADE_PRODUTO_VENDA = "quantidade_produtos";
    private static String COLUNA_DESCONTO_VENDA= "desconto_venda";
    private static String COLUNA_PRECO_TOTAL_VENDA= "preco_total";
    private static String COLUNA_TIPO_VENDA= "tipo_venda";

    // TABELA CLIENTES - Nome de colunas
    private static String COLUNA_ID_CLIENTE = "id_clientes";
    private static String COLUNA_NOME_CLIENTE = "nome_cliente";
    private static String COLUNA_TELEFONE_CLIENTE= "contacto_cliente";
    private static String COLUNA_EMAIL_CLIENTE= "email_cliente";
    private static String COLUNA_MORADA_CLIENTE= "morada_cliente";
    private static String COLUNA_NUIT_CLIENTE= "nuit_cliente";
    private static String COLUNA_DIVIDA_CLIENTE= "divida_cliente";



    // TABELA FORNECEDORES - Nome de colunas
    private static String COLUNA_ID_FORNECEDOR = "id_fornecedor";
    private static String COLUNA_NOME_FORNECEDOR = "nome_fornecedor";
    private static String COLUNA_TELEFONE_FORNECEDOR= "telefone_fornecedor";
    private static String COLUNA_EMAIL_FORNECEDOR= "email_fornecedor";
    private static String COLUNA_ENDERECO_FORNECEDOR= "endereco_fornecedor";
    private static String COLUNA_TIPO_FORNECEDOR= "tipo_fornecedor";
    private static String COLUNA_TIPOPRODUTO_FORNECEDOR= "tipoproduto_fornecedor";


    // TABELA MOVIMENTOS - Nome de colunas
    private static String COLUNA_ID_MOVIMENTO = "id_movimento";
    private static String COLUNA_CONTA_MOVIMENTO = "conta_movimento";
    private static String COLUNA_VALOR_MOVIMENTO= "valor_movimento";
    private static String COLUNA_DATA_MOVIMENTO= "data_movimento";
    private static String COLUNA_TIPO_MOVIMENTO= "tipo_movimento";

    //*********************CRIACAO DE TABELAS*********************************

    //CRIAR TABELA USUARIO
    private String CREATE_USER_TABLE = " CREATE TABLE " + TABELA_USUARIO + "("
            + COLUNA_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NOME_USUARIO + " VARCHAR,"
            + COLUNA_TELEFONE_USUARIO + " VARCHAR, " + COLUNA_EMAIL_USUARIO + " VARCHAR, " + COLUNA_ENDERECO_USUARIO + " VARCHAR, " + COLUNA_SENHA_USUARIO + " VARCHAR" + ")";


    //CRIAR TABELA VENDAS
    private String CREATE_SALES_TABLE = "CREATE TABLE " + TABELA_VENDAS + "("
            + COLUNA_ID_VENDA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUNA_ID_PRODUTO + " REFERENCES tb_produto (id_produto) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, "
            + COLUNA_QUANTIDADE_PRODUTO_VENDA + " INTEGER NOT NULL, " + COLUNA_TIPO_VENDA + " VARCHAR NOT NULL, " + COLUNA_DESCONTO_VENDA + " REAL, " + COLUNA_PRECO_TOTAL_VENDA + " REAL NOT NULL, "
            + COLUNA_ID_USUARIO + " INTEGER REFERENCES " +TABELA_USUARIO +  "("+COLUNA_ID_USUARIO+") ON DELETE CASCADE ON UPDATE CASCADE, "
            + COLUNA_ID_CLIENTE + " INTEGER REFERENCES "+ TABELA_CLIENTES + "("+COLUNA_ID_CLIENTE+") ON DELETE CASCADE ON UPDATE CASCADE" + ")";

    //CRIAR TABELA CLIENTES
    private String CREATE_CLIENTES_TABLE = "CREATE TABLE " + TABELA_CLIENTES + "("
            + COLUNA_ID_CLIENTE  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUNA_NOME_CLIENTE + " VARCHAR NOT NULL, " + COLUNA_TELEFONE_CLIENTE + " INTEGER NOT NULL, "
            + COLUNA_EMAIL_CLIENTE + " VARCHAR, " + COLUNA_MORADA_CLIENTE +" VARCHAR, " + COLUNA_NUIT_CLIENTE +" INTEGER, "+ COLUNA_DIVIDA_CLIENTE + " VARCHAR" + ")";

    //CRIAR TABELA DESPESAS
    private String CREATE_DESPESAS_TABLE = "CREATE TABLE " + TABELA_DESPESAS + "("
            + COLUNA_ID_DESPESA  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUNA_DESCRICAO_DESPESA + " VARCHAR NOT NULL, " + COLUNA_VALOR_DESPESA + " REAL NOT NULL, "
            + COLUNA_TIPO_DESPESA  + " VARCHAR, " + COLUNA_CONTA_RETIRADA + " VARCHAR" + ")";


    //CRIAR TABELA CONTA CLIENTE
    private String CREATE_CONTA_CLIENTE_TABLE = "CREATE TABLE " + TABELA_CONTA_CLIENTE + "("
            + COLUNA_ID_PRODUTO_CONTA  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUNA_NOME_PRODUTO_CONTA + " VARCHAR NOT NULL, " + COLUNA_PRECO_PRODUTO_CONTA + " REAL NOT NULL, "
            + COLUNA_QUANTIDADE_PRODUTO_CONTA + " VARCHAR" + ")";


    //CRIAR TABELA CONTAS
   private String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABELA_CONTAS + "("
            + COLUNA_ID_CONTA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + COLUNA_NOME_CONTA + " VARCHAR NOT NULL, " + COLUNA_TIPO_CONTA + " VARCHAR NOT NULL, " + COLUNA_SALDO_INICIAL_CONTA + " REAL , "
            + COLUNA_SALDO_ACTUAL + " REAL NOT NULL, " + COLUNA_ID_USUARIO + " INTEGER REFERENCES "+ TABELA_USUARIO+ "("+COLUNA_ID_USUARIO +") ON DELETE CASCADE ON UPDATE CASCADE" + ")";


    //CRIAR TABELA PRODUTOS
    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABELA_PRODUTO + "("
            + COLUNA_ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NOME_PRODUTO + " VARCHAR, " + COLUNA_DATA_PRODUTO + " DATETIME, " + COLUNA_CATEGORIA_PRODUTO + " VARCHAR, " + COLUNA_VALIDADE_PRODUTO + " DATETIME, "
            + COLUNA_PRECO_COMPRA_PRODUTO + " REAL, " + COLUNA_PRECO_VENDA_PRODUTO + " REAL, " + COLUNA_QUANTIDADE_PRODUTO + " REAL, " + COLUNA_UNIDADE_PRODUTO + " VARCHAR, " + COLUNA_ESTOQUEMINIMO_PRODUTO + " INTEGER, " + COLUNA_NOME_FORNECEDOR_PRODUTO + " VARCHAR" +")";

    //CRIAR TABELA FORNECEDOR
    private String CREATE_FORNECEDOR_TABLE = "CREATE TABLE " + TABELA_FORNECEDOR + "("
            + COLUNA_ID_FORNECEDOR + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_NOME_FORNECEDOR + " VARCHAR, " + COLUNA_TELEFONE_FORNECEDOR + " INTEGER, " + COLUNA_EMAIL_FORNECEDOR + " VARCHAR, " + COLUNA_ENDERECO_FORNECEDOR + " VARCHAR, "
            + COLUNA_TIPO_FORNECEDOR + " VARCHAR, " + COLUNA_TIPOPRODUTO_FORNECEDOR + " VARCHAR" +")";

    //CRIAR TABELA RENDIMENTOS
    private String CREATE_RENDIMENTO_TABLE = "CREATE TABLE " + TABELA_RENDIMENTO + "("
            + COLUNA_ID_RENDIMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_DESCRICAO_RENDIMENTO + " VARCHAR, " + COLUNA_VALOR_RENDIMETNO + " REAL,"
            + COLUNA_TIPO_RENDIMENTO + " REAL, " + COLUNA_DATA_RENDIMENO + " DATETIME, "+COLUNA_CONTA_ADICIONADA +" VARCHAR" + ")";


    //CRIAR TABELA MOVIMENTOS
    private String CREATE_MOVIMENTOS_TABLE = "CREATE TABLE " + TABELA_MOVIMENTOS + "("
            + COLUNA_ID_MOVIMENTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUNA_CONTA_MOVIMENTO + " VARCHAR, " + COLUNA_VALOR_MOVIMENTO + " REAL,"
            + COLUNA_DATA_MOVIMENTO + " DATETIME, " + COLUNA_TIPO_MOVIMENTO + " REAL" + ")";


    //**********************************************************************************************

    /////Triggers

    //CRIAR TRIGGER SAIDAMOVIMENTO
    private String CREATE_SAIDAS_MOVI_TRIGGER = "CREATE TRIGGER " + TRIGGER_MOVIMENTOSAIDA + "AFTER INSERT ON "+TABELA_DESPESAS+
            " BEGIN " +
            "INSERT INTO "+TABELA_MOVIMENTOS+ "("+COLUNA_CONTA_MOVIMENTO+","+COLUNA_VALOR_MOVIMENTO+","+COLUNA_DATA_MOVIMENTO+","+COLUNA_TIPO_MOVIMENTO+")"+
            " VALUES(NEW."+COLUNA_CONTA_RETIRADA+",NEW."+COLUNA_VALOR_DESPESA+",datetime('now'),'Despesa');"+
            " END";

    //CRIAR TRIGGER ENTRADASMOVIMENTO
    private String CREATE_ENTRADAS_MOVI_TRIGGER = "CREATE TRIGGER " + TRIGGER_MOVIMENTOENTRADA + "AFTER INSERT ON "+TABELA_RENDIMENTO+
            " BEGIN " +
            "INSERT INTO "+TABELA_MOVIMENTOS+ "("+COLUNA_CONTA_MOVIMENTO+","+COLUNA_VALOR_MOVIMENTO+","+COLUNA_DATA_MOVIMENTO+","+COLUNA_TIPO_MOVIMENTO+")"+
            " VALUES(NEW."+COLUNA_CONTA_ADICIONADA+",NEW."+COLUNA_VALOR_RENDIMETNO+",datetime('now'),'Rendimento');"+
            " END";

    //CRIAR TRIGGER CRIAFORNECEDOR
    private String CREATE_CRIA_FORNECEDOR_TRIGGER = "CREATE TRIGGER " + TRIGGER_CRIAFORNECEDOR + "AFTER INSERT ON "+TABELA_PRODUTO+
            " BEGIN " +
            "INSERT INTO "+TABELA_FORNECEDOR+ "("+COLUNA_NOME_FORNECEDOR+","+COLUNA_TIPOPRODUTO_FORNECEDOR+")"+
            " VALUES(NEW."+COLUNA_NOME_FORNECEDOR_PRODUTO+",NEW."+COLUNA_NOME_PRODUTO+");"+
            " END";

    //**********************************************************************************************


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_RENDIMENTO_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_SALES_TABLE);
        db.execSQL(CREATE_CLIENTES_TABLE);
        db.execSQL(CREATE_DESPESAS_TABLE);
        db.execSQL(CREATE_MOVIMENTOS_TABLE);
        db.execSQL(CREATE_FORNECEDOR_TABLE);
        db.execSQL(CREATE_CONTA_CLIENTE_TABLE);

        db.execSQL(CREATE_SAIDAS_MOVI_TRIGGER);
        db.execSQL(CREATE_ENTRADAS_MOVI_TRIGGER);
        db.execSQL(CREATE_CRIA_FORNECEDOR_TRIGGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRODUTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_RENDIMENTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_VENDAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_DESPESAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_MOVIMENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_FORNECEDOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTA_CLIENTE);

        db.execSQL("DROP TRIGGER IF EXISTS " + TRIGGER_MOVIMENTOSAIDA);
        db.execSQL("DROP TRIGGER IF EXISTS " + TRIGGER_MOVIMENTOENTRADA);
        db.execSQL("DROP TRIGGER IF EXISTS " + TRIGGER_CRIAFORNECEDOR);

        // create new tables
        onCreate(db);
    }


    //*****************************CRUD****************************************************


    //Metodo Adiciona Usuario
    public boolean addUsuario(Usuario usuario) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(COLUNA_ID_USUARIO, usuario.getId());

        values.put(COLUNA_NOME_USUARIO, usuario.getNome());
        values.put(COLUNA_TELEFONE_USUARIO, usuario.getTelefone());
        values.put(COLUNA_EMAIL_USUARIO, usuario.getEmail());
        values.put(COLUNA_ENDERECO_USUARIO, usuario.getEndereco());
        values.put(COLUNA_SENHA_USUARIO, usuario.getSenha());
        long verficainsert = db.insert(TABELA_USUARIO, null, values);

        Cursor cursor = db.query(TABELA_USUARIO, new String[]{COLUNA_ID_USUARIO, COLUNA_NOME_USUARIO, COLUNA_TELEFONE_USUARIO, COLUNA_EMAIL_USUARIO, COLUNA_ENDERECO_USUARIO, COLUNA_SENHA_USUARIO}, COLUNA_ID_USUARIO +"="+verficainsert, null, null, null, null);
        cursor.moveToFirst();
        Usuario usuario1= new Usuario();
        usuario1.setId(cursor.getInt(0));
        usuario1.setNome(cursor.getString(1));
        usuario1.setTelefone(cursor.getString(2));
        usuario1.setEmail(cursor.getString(3));
        usuario1.setEndereco(cursor.getString(4));
        usuario1.setSenha(cursor.getString(5));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }

    //Metodo Adiciona Usuario
    public boolean addDespesa(Despesa despesa) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUNA_DESCRICAO_DESPESA, despesa.getDescricao_despesa());
        values.put(COLUNA_VALOR_DESPESA, despesa.getValor_despesa());
        values.put(COLUNA_TIPO_DESPESA, despesa.getTipo_despesa());
        values.put(COLUNA_CONTA_RETIRADA, despesa.getConta_Retirada());

        long verficainsert = db.insert(TABELA_DESPESAS, null, values);

        Cursor cursor = db.query(TABELA_DESPESAS, new String[]{COLUNA_ID_DESPESA, COLUNA_DESCRICAO_DESPESA, COLUNA_VALOR_DESPESA, COLUNA_TIPO_DESPESA,COLUNA_CONTA_RETIRADA}, COLUNA_ID_DESPESA +"="+verficainsert, null, null, null, null);
        cursor.moveToFirst();
        Despesa despesa1 = new Despesa();
        despesa1.setId_despesa(cursor.getInt(0));
        despesa1.setDescricao_despesa(cursor.getString(1));
        despesa1.setValor_despesa(Float.parseFloat(cursor.getString(2)));
        despesa1.setTipo_despesa(cursor.getString(3));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }


    //Metodo Adiciona Usuario
    public boolean addContaCliente(ContaCliente contaCliente) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUNA_NOME_PRODUTO_CONTA, contaCliente.getNome_produto());
        values.put(COLUNA_PRECO_PRODUTO_CONTA, contaCliente.getPreco_produto());
        values.put(COLUNA_QUANTIDADE_PRODUTO_CONTA, contaCliente.getQuant_produto());

        long verficainsert = db.insert(TABELA_CONTA_CLIENTE, null, values);

        Cursor cursor = db.query(TABELA_CONTA_CLIENTE, new String[]{COLUNA_ID_PRODUTO_CONTA, COLUNA_NOME_PRODUTO_CONTA, COLUNA_PRECO_PRODUTO_CONTA, COLUNA_QUANTIDADE_PRODUTO_CONTA}, COLUNA_ID_PRODUTO_CONTA +"="+verficainsert, null, null, null, null);
        cursor.moveToFirst();
        ContaCliente contaCliente1= new ContaCliente();
        contaCliente1.setId_produto_conta(cursor.getInt(0));
        contaCliente1.setNome_produto(cursor.getString(1));
        contaCliente1.setPreco_produto(Float.parseFloat(cursor.getString(2)));
        contaCliente1.setQuant_produto(cursor.getString(3));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }



    //Metodo Adiciona Cliente
    public boolean addCliente(Cliente cliente) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_CLIENTE, cliente.getNome());
        values.put(COLUNA_TELEFONE_CLIENTE, cliente.getTelefone());
        values.put(COLUNA_EMAIL_CLIENTE, cliente.getEmail());
        values.put(COLUNA_MORADA_CLIENTE, cliente.getMorada());
        values.put(COLUNA_DIVIDA_CLIENTE, cliente.getDivida());
        values.put(COLUNA_NUIT_CLIENTE, cliente.getNuitCliente());
        long verficainsert = db.insert(TABELA_CLIENTES, null, values);

        Cursor cursor = db.query(TABELA_CLIENTES, new String[]{COLUNA_ID_CLIENTE, COLUNA_NOME_CLIENTE, COLUNA_TELEFONE_CLIENTE, COLUNA_EMAIL_CLIENTE, COLUNA_MORADA_CLIENTE,COLUNA_NUIT_CLIENTE,COLUNA_DIVIDA_CLIENTE}, COLUNA_ID_CLIENTE +"="+verficainsert, null, null, null, null);
        cursor.moveToFirst();
        Cliente cliente1 = new Cliente();
        cliente1.setId(cursor.getInt(0));
        cliente1.setNome(cursor.getString(1));
        cliente1.setTelefone(cursor.getString(2));
        cliente1.setEmail(cursor.getString(3));
        cliente1.setMorada(cursor.getString(4));
        cliente1.setNuitCliente(Integer.parseInt(cursor.getString(5)));
        cliente1.setDivida(Float.parseFloat(cursor.getString(6)));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }


    //Metodo Adiciona ADD produto
    public boolean addProduto(Produto produto) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_PRODUTO, produto.getNome());
//        values.put(COLUNA_DATA_PRODUTO, produto.getDataEntrada());
        values.put(COLUNA_CATEGORIA_PRODUTO, produto.getCategoria());
//        values.put(COLUNA_VALIDADE_PRODUTO, produto.getPrazo()) ;
        values.put(COLUNA_PRECO_COMPRA_PRODUTO, produto.getPrecoCompra());
        values.put(COLUNA_PRECO_VENDA_PRODUTO, produto.getPreco());
        values.put(COLUNA_QUANTIDADE_PRODUTO, produto.getQuantidade());
        values.put(COLUNA_UNIDADE_PRODUTO, produto.getUnidade());
        values.put(COLUNA_ESTOQUEMINIMO_PRODUTO, produto.getEstoqueMinimo());
        values.put(COLUNA_NOME_FORNECEDOR_PRODUTO, produto.getNomeFornecedor());
        long verficainsert = db.insert(TABELA_PRODUTO, null, values);

        Cursor cursor = db.query(TABELA_PRODUTO, new String[]{COLUNA_ID_PRODUTO, COLUNA_NOME_PRODUTO,COLUNA_DATA_PRODUTO, COLUNA_CATEGORIA_PRODUTO,COLUNA_VALIDADE_PRODUTO,COLUNA_PRECO_COMPRA_PRODUTO, COLUNA_PRECO_VENDA_PRODUTO, COLUNA_QUANTIDADE_PRODUTO, COLUNA_UNIDADE_PRODUTO,COLUNA_ESTOQUEMINIMO_PRODUTO,COLUNA_NOME_FORNECEDOR_PRODUTO}, COLUNA_ID_PRODUTO +"="+verficainsert, null, null, null, null);

        cursor.moveToFirst();
        Produto produto1 = new Produto();
        produto1.setId(cursor.getInt(0));
        produto1.setNome(cursor.getString(1));
//        produto1.setDataEntrada(cursor.getString(2));
        produto1.setCategoria(cursor.getString(3));
//        produto1.setPrazo(cursor.getString(4));
        produto1.setPrecoCompra(cursor.getInt(5));
        produto1.setPreco(cursor.getInt(6));
        produto1.setQuantidade(cursor.getInt(7));
        produto1.setUnidade(cursor.getString(8));
        produto1.setEstoqueMinimo(cursor.getInt(9));
        produto1.setNomeFornecedor(cursor.getString(10));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }


    //Metodo Adiciona ADD Fornecedor
    public boolean addFornecedor(Fornecedor fornecedor) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_FORNECEDOR, fornecedor.getNomeFornecedor());
        values.put(COLUNA_TELEFONE_FORNECEDOR, fornecedor.getContactoFornecedor());
        values.put(COLUNA_EMAIL_FORNECEDOR, fornecedor.getEmailFornecedro());
        values.put(COLUNA_ENDERECO_FORNECEDOR, fornecedor.getEnderecoFornecedro()) ;
        values.put(COLUNA_TIPO_FORNECEDOR, fornecedor.getTipoFornecedro());
        values.put(COLUNA_TIPOPRODUTO_FORNECEDOR, fornecedor.getTipoProdutoFornecedro());
        long verficainsert = db.insert(TABELA_FORNECEDOR, null, values);

        Cursor cursor = db.query(TABELA_FORNECEDOR, new String[]{COLUNA_ID_FORNECEDOR, COLUNA_NOME_FORNECEDOR,COLUNA_TELEFONE_FORNECEDOR, COLUNA_EMAIL_FORNECEDOR,COLUNA_ENDERECO_FORNECEDOR,COLUNA_TIPO_FORNECEDOR, COLUNA_TIPOPRODUTO_FORNECEDOR}, COLUNA_ID_FORNECEDOR +"="+verficainsert, null, null, null, null);

        cursor.moveToFirst();
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setIdFornecedor(cursor.getInt(0));
        fornecedor1.setNomeFornecedor(cursor.getString(1));
        fornecedor1.setContactoFornecedor(cursor.getString(2));
        fornecedor1.setEmailFornecedro(cursor.getString(3));
        fornecedor1.setEnderecoFornecedro(cursor.getString(4));
        fornecedor1.setTipoFornecedro(cursor.getString(5));
        fornecedor1.setTipoProdutoFornecedro(cursor.getString(6));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }


    //Metodo Adiciona vendas
    public boolean addVenda(Venda venda) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

//        values.put(COLUNA_ID_PRODUTO, venda.getIdProduto());
        values.put(COLUNA_QUANTIDADE_PRODUTO_VENDA, venda.getQuantidade());
        values.put(COLUNA_TIPO_VENDA,venda.getTipoVenda()) ;
        values.put(COLUNA_DESCONTO_VENDA, venda.getDesconto());
        values.put(COLUNA_PRECO_TOTAL_VENDA, venda.getPrecoTotal());
        //values.put(COLUNA_ID_USUARIO, venda.getQuantidade());
        values.put(COLUNA_ID_CLIENTE, venda.getIdCliente());

        long verficainsert = db.insert(TABELA_PRODUTO, null, values);

        Cursor cursor = db.query(TABELA_PRODUTO, new String[]{COLUNA_ID_PRODUTO, COLUNA_NOME_PRODUTO,COLUNA_DATA_PRODUTO, COLUNA_CATEGORIA_PRODUTO,COLUNA_VALIDADE_PRODUTO,COLUNA_PRECO_COMPRA_PRODUTO, COLUNA_PRECO_VENDA_PRODUTO, COLUNA_QUANTIDADE_PRODUTO, COLUNA_UNIDADE_PRODUTO,COLUNA_ESTOQUEMINIMO_PRODUTO}, COLUNA_ID_PRODUTO +"="+verficainsert, null, null, null, null);

        cursor.moveToFirst();
        Venda venda1= new Venda();
        venda1.setIdVenda(cursor.getInt(0));
//        venda1.setIdProduto(cursor.getInt(1));
        venda1.setQuantidade(Float.parseFloat(cursor.getString(1)));
        venda1.setTipoVenda(cursor.getString(2));
        venda1.setDesconto(Float.parseFloat(cursor.getString(3)));
        venda1.setPrecoTotal(Float.parseFloat(cursor.getString(4)));
        venda1.setIdCliente(cursor.getInt(5));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }

    //valor da ultima Despesa na conta
    public int somaDespesaConta(String conta){
        //String sqlQuery = "SELECT ("+COLUNA_VALOR_DESPESA+") FROM "+TABELA_DESPESAS+" WHERE "+COLUNA_CONTA_RETIRADA+"='"+conta+"'";
        String sqlQuery = "SELECT ("+COLUNA_VALOR_DESPESA+") FROM "+TABELA_DESPESAS+" WHERE "+COLUNA_ID_DESPESA+"=(SELECT MAX("+COLUNA_ID_DESPESA+") FROM "+TABELA_DESPESAS+" WHERE "+COLUNA_CONTA_RETIRADA+"='"+conta+"')";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery,null);

        int total=0;

        cursor.moveToFirst();
        //cursor.moveToLast();

        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

    //valor da ultima Despesa na conta
    public int somaRendimentoConta(String conta){
        //String sqlQuery = "SELECT ("+COLUNA_VALOR_DESPESA+") FROM "+TABELA_DESPESAS+" WHERE "+COLUNA_CONTA_RETIRADA+"='"+conta+"'";
        String sqlQuery = "SELECT ("+COLUNA_VALOR_RENDIMETNO+") FROM "+TABELA_RENDIMENTO+" WHERE "+COLUNA_ID_RENDIMENTO+"=(SELECT MAX("+COLUNA_ID_RENDIMENTO+") FROM "+TABELA_RENDIMENTO+" WHERE "+COLUNA_CONTA_ADICIONADA+"='"+conta+"')";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery,null);

        int total=0;

        cursor.moveToFirst();
        //cursor.moveToLast();

        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

    //Metodo retira valor da conta
    public void retiraNaConta(String conta, float valor) {
        SQLiteDatabase db = this.getWritableDatabase();
        float novoSaldo;

        String sqlQuery = "SELECT ("+COLUNA_SALDO_ACTUAL+") FROM "+TABELA_CONTAS+" WHERE "+COLUNA_NOME_CONTA+"='"+conta+"'";

        Cursor cursor = db.rawQuery(sqlQuery,null);

        float totalNaConta =0;

        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            totalNaConta = cursor.getFloat(0);
        }

        novoSaldo = totalNaConta-valor;

        ContentValues values = new ContentValues();

        values.put(COLUNA_SALDO_ACTUAL, novoSaldo);



        db.update(TABELA_CONTAS, values, COLUNA_NOME_CONTA + " = '"+conta+"'", null);

        //        return true;

    }

    //Metodo adiciona valor da conta
    public void adicionaNaConta(String conta, float valor) {
        SQLiteDatabase db = this.getWritableDatabase();
        float novoSaldo;

        String sqlQuery = "SELECT ("+COLUNA_SALDO_ACTUAL+") FROM "+TABELA_CONTAS+" WHERE "+COLUNA_NOME_CONTA+"='"+conta+"'";

        Cursor cursor = db.rawQuery(sqlQuery,null);

        float totalNaConta =0;

        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            totalNaConta = cursor.getFloat(0);
        }

        novoSaldo = totalNaConta+valor;

        ContentValues values = new ContentValues();

        values.put(COLUNA_SALDO_ACTUAL, novoSaldo);



        db.update(TABELA_CONTAS, values, COLUNA_NOME_CONTA + " = '"+conta+"'", null);

        //        return true;

    }

    //Saldo Disponivel no caixa
    public float saldoCaixa(){
        String sqlQuery = "SELECT "+COLUNA_SALDO_ACTUAL+" FROM "+TABELA_CONTAS+"  WHERE "+COLUNA_NOME_CONTA+"='Caixa'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery,null);

        float saldo=0;

        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            saldo = cursor.getFloat(0);
        }
        return saldo;
    }


    //Valor total Rendimento
    public int valorRendimento(){
        String sqlQuery = "SELECT SUM("+COLUNA_VALOR_RENDIMETNO+") FROM "+TABELA_RENDIMENTO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        int total=0;
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

    //Valor total Despesa
    public int valorDespesas(){
        String sqlQuery = "SELECT SUM("+COLUNA_VALOR_DESPESA+") FROM "+TABELA_DESPESAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);
        int total=0;
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

    //Dinheiro Investido
    public int dinheiroInvestido(){
        String sqlQuery = "SELECT SUM(("+COLUNA_PRECO_COMPRA_PRODUTO+")*("+COLUNA_QUANTIDADE_PRODUTO+")) FROM "+TABELA_PRODUTO;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery,null);

        int total=0;

        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            total = cursor.getInt(0);
        }
        return total;
    }

//    //Dinheiro do caixa
    public void addCaixaAccount(){

//        String sqlQuery =
//                "CREATE TRIGGER movimentoSaida AFTER INSERT ON "+TABELA_DESPESAS+
//                " BEGIN " +
//                        "INSERT INTO "+TABELA_MOVIMENTOS+ "("+COLUNA_CONTA_MOVIMENTO+","+COLUNA_VALOR_MOVIMENTO+","+COLUNA_DATA_MOVIMENTO+","+COLUNA_TIPO_MOVIMENTO+")"+
//                " VALUES(NEW."+COLUNA_CONTA_RETIRADA+",NEW."+COLUNA_VALOR_DESPESA+",datetime('now'),'Despesa');"+
//                        " END";
        String sqlQuery = "INSERT INTO "+TABELA_CONTAS+ "("+
                COLUNA_NOME_CONTA+","+ COLUNA_TIPO_CONTA+","+ COLUNA_SALDO_INICIAL_CONTA+","+ COLUNA_SALDO_ACTUAL+") "+
                "VALUES ('Caixa','Caixa',0,0);";


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery,null);

        cursor.moveToFirst();

    }


    //Metodo Add Rendimentos
    public boolean addRendimetno(Rendimento rendimento) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO_RENDIMENTO, rendimento.getDescricao());
        values.put(COLUNA_VALOR_RENDIMETNO, rendimento.getValor());
        values.put(COLUNA_TIPO_RENDIMENTO,rendimento.getTipo()) ;
//        values.put(COLUNA_DATA_RENDIMENO,rendimento.getData()) ;
        values.put(COLUNA_CONTA_ADICIONADA,rendimento.getContaAdicionada()) ;

        long verficainsert = db.insert(TABELA_RENDIMENTO, null, values);

        Cursor cursor = db.query(TABELA_RENDIMENTO, new String[]{COLUNA_ID_RENDIMENTO,COLUNA_DESCRICAO_RENDIMENTO, COLUNA_VALOR_RENDIMETNO, COLUNA_TIPO_RENDIMENTO,COLUNA_DATA_RENDIMENO,COLUNA_CONTA_ADICIONADA}, COLUNA_ID_RENDIMENTO+"="+verficainsert, null, null, null, null);

        cursor.moveToFirst();
        Rendimento rendimento1= new Rendimento();
        rendimento1.setDescricao(cursor.getString(0));
        rendimento1.setValor(cursor.getDouble(1));
        rendimento1.setTipo(cursor.getString(2));
//        rendimento1.setData(cursor.getString(3));
        rendimento1.setContaAdicionada(cursor.getString(4));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }




    //***********************************************************************************************

    //Metodo Verifica Usuario
    public boolean verificaUsuario(String telefone) {
        String[] columns = {
                COLUNA_ID_USUARIO
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_TELEFONE_USUARIO + " = ?";
        String[] selectionArgs = {telefone};

        Cursor cursor = db.query(TABELA_USUARIO, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }



    //Metodo Verifica Usuario e Password
    public boolean verificaUsuario(String telefone, String senha) {
        String[] columns = {
                COLUNA_ID_USUARIO
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_TELEFONE_USUARIO + " = ?" + " AND " + COLUNA_SENHA_USUARIO + " =?";
        String[] selectionArgs = {telefone, senha};

        Cursor cursor = db.query(TABELA_USUARIO, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }




    //Metodo Verifica Cliente
    public boolean verificaCliente(String telefone) {
        String[] columns = {
                COLUNA_ID_CLIENTE
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_TELEFONE_CLIENTE + " = ?";
        String[] selectionArgs = {telefone};

        Cursor cursor = db.query(TABELA_CLIENTES, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //Metodo Verifica Cliente
    public boolean verificaFornecedor(String nomeFornecedor) {
        String[] columns = {
                COLUNA_ID_FORNECEDOR
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_NOME_FORNECEDOR + " = ?";
        String[] selectionArgs = {nomeFornecedor};

        Cursor cursor = db.query(TABELA_FORNECEDOR, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    //Metodo Verifica ProdutoVenda
    public boolean verificaProduto(String nomeProduto) {
        String[] columns = {
                COLUNA_ID_PRODUTO
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_NOME_PRODUTO + " = ?";
        String[] selectionArgs = {nomeProduto};

        Cursor cursor = db.query(TABELA_PRODUTO, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //**********************************************************************************************

    //Metodo apagar usuario
    public void apagarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_USUARIO, COLUNA_TELEFONE_USUARIO + " = ?", new String[]{String.valueOf(usuario.getTelefone())});
        db.close();
    }


    //Metodo apagar produto
    public void apagarProduto(int idProduto) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_PRODUTO, COLUNA_ID_PRODUTO + " = ?", new String[]{String.valueOf(idProduto)});
        db.close();
    }

    //Metodo apagar rendimeto
    public void apagarRendimento(Rendimento rendimento) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_RENDIMENTO, COLUNA_DESCRICAO_RENDIMENTO + " = ?", new String[]{String.valueOf(rendimento.getDescricao())});
        db.close();
    }

    //Metodo Seleciona Usuario
    public Usuario selecionarUsuario(int telefone) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_USUARIO, new String[]{COLUNA_ID_USUARIO, COLUNA_NOME_USUARIO, COLUNA_TELEFONE_USUARIO, COLUNA_EMAIL_USUARIO, COLUNA_ENDERECO_USUARIO, COLUNA_SENHA_USUARIO}, COLUNA_TELEFONE_USUARIO + "  = ?",
                new String[]{String.valueOf(telefone)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Usuario usuario1 = new Usuario(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

        return usuario1;
    }

    //Metodo Seleciona produto
    public Produto selecionarProduto(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_PRODUTO, new String[]{COLUNA_ID_PRODUTO, COLUNA_NOME_PRODUTO, COLUNA_DATA_PRODUTO,COLUNA_CATEGORIA_PRODUTO,COLUNA_VALIDADE_PRODUTO,COLUNA_PRECO_COMPRA_PRODUTO, COLUNA_PRECO_VENDA_PRODUTO, COLUNA_QUANTIDADE_PRODUTO, COLUNA_UNIDADE_PRODUTO,COLUNA_ESTOQUEMINIMO_PRODUTO}, COLUNA_ID_PRODUTO + "  = ?",
                new String[]{String.valueOf(nome)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
//        ProdutoVenda produto = new ProdutoVenda(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), Float.parseFloat(cursor.getString(5)), Float.parseFloat(cursor.getString(6)),Integer.parseInt(cursor.getString(7)), cursor.getString(8), Integer.parseInt(cursor.getString(9)));

        return null;
    }


    public Cursor readItem(long itemId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                COLUNA_ID_PRODUTO,
                COLUNA_NOME_PRODUTO,
                COLUNA_DATA_PRODUTO,
                COLUNA_CATEGORIA_PRODUTO,
                COLUNA_VALIDADE_PRODUTO,
                COLUNA_PRECO_COMPRA_PRODUTO,
                COLUNA_PRECO_VENDA_PRODUTO,
                COLUNA_QUANTIDADE_PRODUTO,
                COLUNA_UNIDADE_PRODUTO,
                COLUNA_ESTOQUEMINIMO_PRODUTO

        };
        String selection = COLUNA_ID_PRODUTO + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };

        Cursor cursor = db.query(
                TABELA_PRODUTO,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor;
    }





    //Metodo Seleciona Rendimento
    public Rendimento selecionarRendimento(String descricao) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_RENDIMENTO, new String[]{COLUNA_ID_PRODUTO,COLUNA_DESCRICAO_RENDIMENTO, COLUNA_VALOR_RENDIMETNO, COLUNA_TIPO_RENDIMENTO}, COLUNA_DESCRICAO_RENDIMENTO + "  = ?",
                new String[]{String.valueOf(descricao)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
//        Rendimento rendimento = new Rendimento(cursor.getInt(0),cursor.getString(1),
//                Double.parseDouble(cursor.getString(2)), cursor.getString(3),cursor.getString(4));

        return null;
    }


    //Metodo Actualiza Usuario
    public void actualizaCliente(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_USUARIO, usuario.getNome());
        values.put(COLUNA_EMAIL_USUARIO, usuario.getEmail());
        values.put(COLUNA_ENDERECO_USUARIO, usuario.getEndereco());
        values.put(COLUNA_SENHA_USUARIO, usuario.getSenha());


        db.update(TABELA_USUARIO, values, COLUNA_TELEFONE_USUARIO + " = ?", new String[]{String.valueOf(usuario.getTelefone())});

    }

    //Metodo Actualiza ProdutoVenda
    public Boolean actualizaProduto(String nome_conf,String nomePro,int quantPro,float PrecoPro, String prazoPro) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_PRODUTO, nomePro);
        values.put(COLUNA_VALIDADE_PRODUTO,prazoPro) ;
        values.put(COLUNA_PRECO_VENDA_PRODUTO, PrecoPro);
        values.put(COLUNA_QUANTIDADE_PRODUTO, quantPro);

        db.update(TABELA_PRODUTO, values, COLUNA_NOME_PRODUTO + " = '" + nome_conf + "'", null);
        db.close();
        return true;

    }

    //Metodo Add Rendimentos
    public boolean addConta(Conta conta) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_CONTA, conta.getNomeConta());
        values.put(COLUNA_SALDO_ACTUAL, conta.getValorConta());
        values.put(COLUNA_TIPO_CONTA, conta.getTipoConta()) ;
        long verficainsert = db.insert(TABELA_CONTAS, null, values);

        Cursor cursor = db.query(TABELA_CONTAS, new String[]{COLUNA_ID_CONTA,COLUNA_NOME_CONTA, COLUNA_SALDO_ACTUAL, COLUNA_TIPO_CONTA}, COLUNA_ID_CONTA+"="+verficainsert, null, null, null, null);

        cursor.moveToFirst();
        Conta conta1 = new Conta();
        conta1.setNomeConta(cursor.getString(0));
        conta1.setValorConta(cursor.getFloat(1));
        conta1.setTipoConta(cursor.getString(2));
        cursor.close();
        db.close();
        if(verficainsert != -1){
            return true;
        }

        return false;
    }

    public Boolean actualizaConta(String nome_conf,String nomeAccount,float saldoAccount) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_CONTA, nomeAccount);
        values.put(COLUNA_SALDO_ACTUAL,saldoAccount) ;
        
        db.update(TABELA_CONTAS, values, COLUNA_NOME_CONTA + " = '" + nome_conf + "'", null);
        db.close();
        return true;
    }


    //Actualiza Fornecedor
    public Boolean actualizaFornecedor(String nome_Fornecedor,String nomeSupler,String telefoneSupler,String emailSupler,String enderecoSupler,String tipoSupler,String tipoProdutoSupler) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME_FORNECEDOR, nomeSupler);
        values.put(COLUNA_TELEFONE_FORNECEDOR,telefoneSupler) ;
        values.put(COLUNA_EMAIL_FORNECEDOR, emailSupler);
        values.put(COLUNA_ENDERECO_FORNECEDOR,enderecoSupler) ;
        values.put(COLUNA_TIPO_FORNECEDOR, tipoSupler);
        values.put(COLUNA_TIPOPRODUTO_FORNECEDOR,tipoProdutoSupler) ;

        db.update(TABELA_FORNECEDOR, values, COLUNA_NOME_FORNECEDOR + " = '" + nome_Fornecedor + "'", null);
        db.close();
        return true;
    }


    public Boolean actualizaDespesa(String nome_conf,String nomeDespesa,float valorDespesa) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_DESCRICAO_DESPESA, nomeDespesa);
        values.put(COLUNA_VALOR_DESPESA,valorDespesa) ;

        db.update(TABELA_DESPESAS, values, COLUNA_DESCRICAO_DESPESA + " = '" + nome_conf + "'", null);
        db.close();
        return true;
    }

    public String[] getAllSpinnerAccounts(){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABELA_CONTAS;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> spinnerContent = new ArrayList<String>();
        if(c.moveToFirst()){
            do{
                String word = c.getString(c.getColumnIndexOrThrow("conta"));
                spinnerContent.add(word);
            }while(c.moveToNext());
        }
        c.close();

        String[] allSpinner = new String[spinnerContent.size()];
        allSpinner = spinnerContent.toArray(allSpinner);

        return allSpinner;
    }

    public boolean produtoHaveStock(int produtoId){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABELA_PRODUTO + " WHERE " + COLUNA_QUANTIDADE_PRODUTO+">0 AND " + COLUNA_ID_PRODUTO +" = "+produtoId;
        Log.d("tester__", query);
        Cursor c = db.rawQuery(query, null);
        return c.moveToFirst();
    }


    //Metodo Lista Usuarios
    public List<Usuario> listaTodosUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();

        String query = "SELECT * FROM " + TABELA_USUARIO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Usuario usuario = new Usuario();
                usuario.setTelefone(c.getString(0));
                usuario.setNome(c.getString(1));
                usuario.setEmail(c.getString(2));
                usuario.setEndereco(c.getString(3));

                listaUsuarios.add(usuario);
            } while ((c.moveToNext()));
        }
        return listaUsuarios;

    }


    //Metodo Lista ProdutoVenda
    public Cursor listaTodosProdutos() {
        String query = "SELECT * FROM " + TABELA_PRODUTO;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista Fornecedore
    public Cursor listaTodosFornecedores() {
        String query = "SELECT * FROM " + TABELA_FORNECEDOR;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista Fornecedore
    public Cursor listaTodosClientes() {
        String query = "SELECT * FROM " + TABELA_CLIENTES;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista Movimento
    public Cursor listaTodosMovimentos() {
        String query = "SELECT * FROM " + TABELA_MOVIMENTOS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista Rendimentos
    public Cursor listaTodosRendimentos() {
        String query = "SELECT * FROM " + TABELA_RENDIMENTO;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }


    //Metodo Lista Rendimentos Relatorio
    public Cursor listaRendimentosMarcados(String dataInicio,String dataFim) {
        String query;
        if(dataInicio==null && dataFim==null){
             query = "SELECT * FROM " + TABELA_RENDIMENTO;
        }else if(dataInicio==null){
             query = "SELECT * FROM " + TABELA_RENDIMENTO+ " WHERE "+COLUNA_DATA_RENDIMENO+"<='"+dataFim+"';";
        }else if(dataFim==null){
             query = "SELECT * FROM " + TABELA_RENDIMENTO+ " WHERE "+COLUNA_DATA_RENDIMENO+">='"+dataInicio+"';";
        }else {
             query = "SELECT * FROM " + TABELA_RENDIMENTO + " WHERE " + COLUNA_DATA_RENDIMENO + ">='" + dataInicio + "' AND " + COLUNA_DATA_RENDIMENO + "<='" + dataFim + "';";
        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista Rendimentos
    public Cursor listaConta() {
        String query = "SELECT * FROM " + TABELA_CONTA_CLIENTE;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }

    //Metodo Lista ProdutoVenda
    public Cursor listaTodasContas() {
        String query = "SELECT * FROM " + TABELA_CONTAS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        return c;
    }


    //Metodo Lista Despesa
    public Cursor listaTodasDespesas() {
        String query = "SELECT * FROM " + TABELA_DESPESAS;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cur = db.rawQuery(query, null);

        return cur;
    }



//    public List<filtroProdutosCategoria> (String categoria){
//        String sqlQuery = "SELECT * FROM "+TABELA_PRODUTO+ " WHERE "+COLUNA_CATEGORIA_PRODUTO+" ="+categoria;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(sqlQuery,null);
//
//
//        cursor.moveToFirst();
//
//        if (cursor.getCount()>0) {
//            total = cursor.getInt(0);
//        }
//        return total;
//    }



}
