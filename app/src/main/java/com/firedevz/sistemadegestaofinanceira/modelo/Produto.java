package com.firedevz.sistemadegestaofinanceira.modelo;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by PUDENTE on 2/1/2018.
 */

public class Produto {

    public static String PAPER_NAME = "produtoVendas";

    int id;
    String nome;
    Date dataEntrada;
    String categoria;
    Date prazo;
    float precoCompra;
    float preco;
    double Quantidade;
    String unidade;
    int estoqueMinimo;
    String nomeFornecedor;


    public static Produto getById(int id){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        Produto produto = null;
        for (Produto produtoAux : produtos){
            if(produtoAux.getId()==id){
                produto = produtoAux;
            }
        }
        return produto;
    }

    public static List<Produto> list(){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        return produtos;
    }

    public static boolean register(Produto produto){
        produto.id = java.lang.System.identityHashCode(produto);
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        produtos.add(produto);
        Paper.book().write(PAPER_NAME, produtos);
        return true;
    }

    public static boolean updateById(int id, Produto produto){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        int i = 0;
        for (Produto produtoAux : produtos){
            if(produtoAux.getId()==id){
                produtos.set(i, produto);
                Paper.book().write(PAPER_NAME, produtos);
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean update(int position, Produto produto){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        produtos.set(position, produto);
        Paper.book().write(PAPER_NAME, produtos);
        return true;
    }

    public static boolean exists(int position){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        int  i = 0;
        for (Produto produto: produtos) {
            if(i == position && produto.getQuantidade() > 0){
                return true;
            }
            i++;
        }
        return false;
    }

    public static boolean delete(int position){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        int  i = 0;
        for (Produto produto: produtos) {
            if(i == position){
                produtos.remove(position);
                Paper.book().write(PAPER_NAME, produtos);
                return true;
            }
            i++;
        }
        return false;
    }

    public static String[] listStringArray(){
        List<Produto> produtos = Paper.book().read(PAPER_NAME, new ArrayList<Produto>());
        String[] lista = new String[produtos.size()];
        int i =0;
        for (Produto produto : produtos){
            lista[i] = produto.nome;
            i++;
        }
        return lista;
    }


    public Produto() {
    }

    public Produto(int id, String nome, float preco, int quantidade, Date prazo, String categoria) {
        this.id = id;
        this.nome = nome;
        this.Quantidade = quantidade;
        this.preco = preco;
        this.prazo = prazo;
        this.categoria = categoria;
    }

    public Produto(String nome, Date dataEntrada, String categoria, Date prazo, float precoCompra, float preco, int quantidade, String unidade, int estoqueMinimo, String nomeFornecedor) {
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.categoria = categoria;
        this.prazo = prazo;
        this.precoCompra = precoCompra;
        this.preco = preco;
        Quantidade = quantidade;
        this.unidade = unidade;
        this.estoqueMinimo = estoqueMinimo;
        this.nomeFornecedor = nomeFornecedor;
    }

    public Produto(int id, String nome, Date dataEntrada, String categoria, Date prazo, float precoCompra, float preco, int quantidade, String unidade, int estoqueMinimo, String nomeFornecedor) {
        this.id = id;
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.categoria = categoria;
        this.prazo = prazo;
        this.precoCompra = precoCompra;
        this.preco = preco;
        this.Quantidade = quantidade;
        this.unidade = unidade;
        this.estoqueMinimo = estoqueMinimo;
        this.nomeFornecedor = nomeFornecedor;
    }

    public Produto(int id, String nome, Date dataEntrada, String categoria, Date prazo, float precoCompra, float preco, int quantidade, String unidade, int estoqueMinimo) {
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.prazo = prazo;
        this.categoria = categoria;
        this.unidade = unidade;
        this.Quantidade = quantidade;
        this.id = id;
        this.estoqueMinimo = estoqueMinimo;
        this.preco = preco;
        this.precoCompra = precoCompra;
    }

    public Produto(String nome, Date dataEntrada, String categoria, Date prazo, float precoCompra, float preco, int quantidade, String unidade, int estoqueMinimo) {
        this.nome = nome;
        this.dataEntrada = dataEntrada;
        this.prazo = prazo;
        this.categoria = categoria;
        this.unidade = unidade;
        this.Quantidade = quantidade;
        this.estoqueMinimo = estoqueMinimo;
        this.preco = preco;
        this.precoCompra = precoCompra;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public double getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(double quantidade) {
        Quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public void setEstoqueMinimo(int estoqueMinimo) {
        this.estoqueMinimo = estoqueMinimo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(float precoCompra) {
        this.precoCompra = precoCompra;
    }

    @Override
    public String toString() {
        return "ProdutoVenda{" +
                "nome='" + nome + '\'' +
                ", dataEntrada='" + dataEntrada + '\'' +
                ", prazo='" + prazo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", unidade='" + unidade + '\'' +
                ", Quantidade=" + Quantidade +
                ", id=" + id +
                ", estoqueMinimo=" + estoqueMinimo +
                ", preco=" + preco +
                ", precoCompra=" + precoCompra +
                '}';
    }
}
