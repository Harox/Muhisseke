package com.firedevz.sistemadegestaofinanceira.modelo;

/**
 * Created by PUDENTE on 2/26/2018.
 */

public class ContaCliente {
    int id_produto_conta;
    String nome_produto;
    float preco_produto;
    String quant_produto;


    public ContaCliente() {
    }

    public ContaCliente(int id_produto_conta, String nome_produto, float preco_produto, String quant_produto) {
        this.id_produto_conta = id_produto_conta;
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
        this.quant_produto = quant_produto;
    }

    public ContaCliente(String nome_produto, float preco_produto, String quant_produto) {
        this.nome_produto = nome_produto;
        this.preco_produto = preco_produto;
        this.quant_produto = quant_produto;
    }

    public int getId_produto_conta() {
        return id_produto_conta;
    }

    public void setId_produto_conta(int id_produto_conta) {
        this.id_produto_conta = id_produto_conta;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public float getPreco_produto() {
        return preco_produto;
    }

    public void setPreco_produto(float preco_produto) {
        this.preco_produto = preco_produto;
    }

    public String getQuant_produto() {
        return quant_produto;
    }

    public void setQuant_produto(String quant_produto) {
        this.quant_produto = quant_produto;
    }

    @Override
    public String toString() {
        return "ContaCliente{" +
                "id_produto_conta=" + id_produto_conta +
                ", nome_produto='" + nome_produto + '\'' +
                ", preco_produto=" + preco_produto +
                ", quant_produto='" + quant_produto + '\'' +
                '}';
    }
}
