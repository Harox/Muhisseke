//package com.firedevz.sistemadegestaofinanceira.activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.text.TextUtils;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firedevz.sistemadegestaofinanceira.R;
//import com.firedevz.sistemadegestaofinanceira.modelo.ProdutoVenda;
//import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;
//
//import java.util.ArrayList;
//
//public class ActivityAdicionarProdutos extends Activity implements View.OnClickListener {
//
//    private TextView mTextMessage;
//
//    public EditText edtID, edtNomeProduto, edtData,edtPrazo, edtPreco, edtPrecoVenda,edtQuantidade,edtNotificarEstoque;
//    private Button btnAdicionarPro;
//    public Spinner spnCategoria,spnUnidade;
//
//    private Context context;
//
//    DatabaseHelper db = new DatabaseHelper(this);
//
//    private ProdutoVenda produto;
//
//    private ArrayAdapter<String > adpUnidade;
//    private ArrayAdapter<String > adpCategoria;
//    ArrayList<String> arrayList;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_adicionar_produtos);
//
//        edtNomeProduto = (EditText)findViewById(R.id.edtNomeProduto);
//        edtData = (EditText)findViewById(R.id.edtData);
//        edtPrazo = (EditText)findViewById(R.id.edtPrazo);
//        edtPreco = (EditText)findViewById(R.id.edtPreco);
//        edtPrecoVenda = (EditText)findViewById(R.id.edtPrecoVenda);
//        edtQuantidade = (EditText)findViewById(R.id.edtQuantidade);
//        edtNotificarEstoque = (EditText)findViewById(R.id.edtNotificarEstoque);
//       // edtID = (EditText)findViewById(R.id.edtID);
//
//        btnAdicionarPro = (Button) findViewById(R.id.btnAdicionarPro);
//
//        spnCategoria = (Spinner) findViewById(R.id.spnCategoria);
//        spnUnidade = (Spinner) findViewById(R.id.spnUnidade);
//
//        btnAdicionarPro.setOnClickListener(this);
//
//
//
//
//        adpCategoria = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
//        adpCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnCategoria.setAdapter(adpCategoria);
//
//        adpCategoria.add("bebidas Alcoolicas");
//        adpCategoria.add("Bebidas nao alcoolicas");
//        adpCategoria.add("Mariscos");
//        adpCategoria.add("Carnes");
//        adpCategoria.add("Vegetais");
//        adpCategoria.add("Cereais");
//        adpCategoria.add("Doces e salgados");
//        adpCategoria.add("Outro");
//
//
//
//        adpUnidade = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
//        adpUnidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnUnidade.setAdapter(adpUnidade);
//
//        adpUnidade.add("uni");
//        adpUnidade.add("kg");
//        adpUnidade.add("Litros");
//        adpUnidade.add("chot");
//        adpUnidade.add("doze");
//        adpUnidade.add("Caixas");
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(v == btnAdicionarPro) {
//
//            //String codigo = edtID.getText().toString();
//            String nome = edtNomeProduto.getText().toString();
//            String data = edtData.getText().toString();
//            String categoria = (String) spnCategoria.getSelectedItem();
//            String prazo = edtPrazo.getText().toString();
//            String preco = edtPreco.getText().toString();
//            String precoVenda = edtPrecoVenda.getText().toString();
//            String quantidade = edtQuantidade.getText().toString();
//            String Unidade = (String) spnUnidade.getSelectedItem();
//            String estoqueMinimo = edtNotificarEstoque.getText().toString();
//
//
//
//
//            if (validarFormulario()) {
//                Toast.makeText(ActivityAdicionarProdutos.this, "ProdutoVenda adicionado com Sucesso", Toast.LENGTH_LONG).show();
//                db.addProduto(new ProdutoVenda(nome, data,categoria, prazo, Float.parseFloat(precoVenda), Float.parseFloat(preco), Integer.parseInt(quantidade), Unidade, Integer.parseInt(estoqueMinimo)));
//                limpaCampos();
//            }
//
//            else {
//                // inicio.hideProgressDialog();
//                Toast.makeText(ActivityAdicionarProdutos.this, "Preencha Todos os Campos obrigatorios", Toast.LENGTH_LONG).show();
//            }
//
//        }else{
//            // inicio.hideProgressDialog();
//            Toast.makeText(ActivityAdicionarProdutos.this, "Não é possivel Registar um produto existente! ", Toast.LENGTH_LONG).show();
//        }
//
//    }
//
//    public void limpaCampos() {
//
//       // edtID.setText("");
//        edtNomeProduto.setText("");
//        edtData.setText("");
//        edtPrazo.setText("");
//        edtPreco.setText("");
//        edtPrecoVenda.setText("");
//        edtQuantidade.setText("");
//        edtNotificarEstoque.setText("");
//
//        edtNomeProduto.requestFocus();
//    }
//
//    private boolean validarFormulario() {
//        boolean valid = true;
//
//        String quant = edtQuantidade.getText().toString();
//        if (TextUtils.isEmpty(quant)) {
//            edtQuantidade.setError("Campo Obrigatório.");
//            valid = false;
//        } else {
//            edtQuantidade.setError(null);
//        }
//
//
//        String preco = edtPreco.getText().toString();
//        if (TextUtils.isEmpty(preco)) {
//            edtPreco.setError("Campo Obrigatório..");
//            valid = false;
//        } else {
//            edtPreco.setError(null);
//        }
//
//        String nomeProduto = edtNomeProduto.getText().toString();
//        if (TextUtils.isEmpty(nomeProduto)) {
//            edtNomeProduto.setError("Campo Obrigatório..");
//            valid = false;
//        } else {
//            edtNomeProduto.setError(null);
//        }
//        String precoVenda = edtPrecoVenda.getText().toString();
//        if (TextUtils.isEmpty(precoVenda)) {
//            edtPrecoVenda.setError("Campo Obrigatório..");
//            valid = false;
//        } else {
//            edtPrecoVenda.setError(null);
//        }
//
//
//        return valid;
//    }
//}
