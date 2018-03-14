package com.firedevz.sistemadegestaofinanceira.modelo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class Venda {

    public static String PAPER_NAME = "vendas";

    List<ProdutoVenda> produtoVendas = new ArrayList<>();
    int idVenda, idCliente, conta;
    float quantidade, desconto;
    double precoTotal;
    String tipoVenda;
    String metodoPagamento;
    boolean pago;
    Date date;

    public Venda() {

    }


    public Venda(int idVenda, List<ProdutoVenda> produtoVendas, float quantidade, String tipoVenda, float precoTotal, float desconto, int idCliente) {
        this.idVenda = idVenda;
        this.produtoVendas = produtoVendas;
        this.idCliente = idCliente;
        this.quantidade = quantidade;
        this.desconto = desconto;
        this.precoTotal = precoTotal;
        this.tipoVenda = tipoVenda;
    }

    public static double getAllIncome() {
        double income = 0;
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        for (Venda venda : vendas) {
            income = income + venda.getPrecoTotal();
        }
        return income;
    }

    public static List<ProdutoVenda> getProdutosMaisVendidos() {
        List<ProdutoVenda> produtoVendas = new ArrayList<>();
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        for (Venda venda : vendas) {
            for (ProdutoVenda produto : venda.getProdutoVendas()) {
                Log.d("produtos", produto.nome);
                boolean wasAdded = false;
                int i = 0;
                for (ProdutoVenda produtoAux : produtoVendas) {
                    if(produtoAux.idProduto==produto.idProduto){
                        produtoAux.quantidade = produtoAux.quantidade + produto.quantidade;
                        produtoVendas.set(i, produtoAux);
                        wasAdded = true;
                        break;
                    }
                    i++;
                }
                if (!wasAdded)
                    produtoVendas.add(produto);
            }
        }
        return produtoVendas;
    }

    public static List<Venda> list() {
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        return vendas;
    }

    public static List<Venda> listPendentes() {
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        List<Venda> finalList = new ArrayList<>();
        for (Venda venda : vendas) {
            if (!venda.pago)
                finalList.add(venda);
        }
        return finalList;
    }

    public static List<Venda> listEfectuadas() {
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        List<Venda> finalList = new ArrayList<>();
        for (Venda venda : vendas) {
            if (!venda.pago)
                finalList.add(venda);
        }
        return vendas;
    }

    public static boolean register(Venda venda) {
        venda.idVenda = java.lang.System.identityHashCode(venda);
        List<Venda> vendas = Paper.book().read(PAPER_NAME, new ArrayList<Venda>());
        vendas.add(venda);
        Paper.book().write(PAPER_NAME, vendas);

        Log.d("debugger", venda.getProdutoVendas().size() + "Q");
        for (ProdutoVenda produtoVenda : venda.produtoVendas) {
            Produto produtoDB = Produto.getById(produtoVenda.idProduto);
            produtoDB.setQuantidade(produtoDB.getQuantidade() - produtoVenda.quantidade);
            Produto.updateById(produtoDB.id, produtoDB);
            Log.d("debugger", produtoDB.getQuantidade() + "Q");
        }

        return true;
    }

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public List<ProdutoVenda> getProdutoVendas() {
        return produtoVendas;
    }

    public void setProdutoVendas(List<ProdutoVenda> produtoVendas) {
        this.produtoVendas = produtoVendas;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public String getTipoVenda() {
        return tipoVenda;
    }

    public void setTipoVenda(String tipoVenda) {
        this.tipoVenda = tipoVenda;
    }

    public ProdutoVenda createProdutoObject() {
        return new ProdutoVenda();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", idProduto=" + produtoVendas.size() +
                ", idCliente=" + idCliente +
                ", quantidade=" + quantidade +
                ", desconto=" + desconto +
                ", precoTotal=" + precoTotal +
                ", tipoVenda='" + tipoVenda + '\'' +
                '}';
    }

    public class ProdutoVenda {
        public int idProduto;
        public double preco;
        public double quantidade;
        public String nome;
    }
}
