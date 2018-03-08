package com.firedevz.sistemadegestaofinanceira.modelo;

/**
 * Created by PUDENTE on 2/1/2018.
 */

public class Fornecedores {
    int idFornecedor;
    String nomeFornecedor;
    String contactoFornecedor;
    String emailFornecedro;
    String enderecoFornecedro;
    String tipoFornecedro;
    String tipoProdutoFornecedro;


    public Fornecedores() {

    }

    public Fornecedores(String nomeFornecedor, String contactoFornecedor, String emailFornecedro, String enderecoFornecedro, String tipoFornecedro, String tipoProdutoFornecedro) {
        this.nomeFornecedor = nomeFornecedor;
        this.contactoFornecedor = contactoFornecedor;
        this.emailFornecedro = emailFornecedro;
        this.enderecoFornecedro = enderecoFornecedro;
        this.tipoFornecedro = tipoFornecedro;
        this.tipoProdutoFornecedro = tipoProdutoFornecedro;
    }

    public Fornecedores(String nome,String nomeFornecedor, String contactoFornecedor, String emailFornecedro, String enderecoFornecedro, String tipoFornecedro, String tipoProdutoFornecedro) {
        this.nomeFornecedor = nome;
        this.nomeFornecedor = nomeFornecedor;
        this.contactoFornecedor = contactoFornecedor;
        this.emailFornecedro = emailFornecedro;
        this.enderecoFornecedro = enderecoFornecedro;
        this.tipoFornecedro = tipoFornecedro;
        this.tipoProdutoFornecedro = tipoProdutoFornecedro;
    }

    public Fornecedores(int idFornecedor, String nomeFornecedor, String contactoFornecedor, String emailFornecedro, String enderecoFornecedro, String tipoFornecedro, String tipoProdutoFornecedro) {
        this.idFornecedor = idFornecedor;
        this.nomeFornecedor = nomeFornecedor;
        this.contactoFornecedor = contactoFornecedor;
        this.emailFornecedro = emailFornecedro;
        this.enderecoFornecedro = enderecoFornecedro;
        this.tipoFornecedro = tipoFornecedro;
        this.tipoProdutoFornecedro = tipoProdutoFornecedro;
    }



    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getContactoFornecedor() {
        return contactoFornecedor;
    }

    public void setContactoFornecedor(String contactoFornecedor) {
        this.contactoFornecedor = contactoFornecedor;
    }

    public String getEmailFornecedro() {
        return emailFornecedro;
    }

    public void setEmailFornecedro(String emailFornecedro) {
        this.emailFornecedro = emailFornecedro;
    }

    public String getEnderecoFornecedro() {
        return enderecoFornecedro;
    }

    public void setEnderecoFornecedro(String enderecoFornecedro) {
        this.enderecoFornecedro = enderecoFornecedro;
    }

    public String getTipoFornecedro() {
        return tipoFornecedro;
    }

    public void setTipoFornecedro(String tipoFornecedro) {
        this.tipoFornecedro = tipoFornecedro;
    }

    public String getTipoProdutoFornecedro() {
        return tipoProdutoFornecedro;
    }

    public void setTipoProdutoFornecedro(String tipoProdutoFornecedro) {
        this.tipoProdutoFornecedro = tipoProdutoFornecedro;
    }

    @Override
    public String toString() {
        return "Fornecedores{" +
                "idFornecedor=" + idFornecedor +
                ", nomeFornecedor='" + nomeFornecedor + '\'' +
                ", contactoFornecedor='" + contactoFornecedor + '\'' +
                ", emailFornecedro='" + emailFornecedro + '\'' +
                ", enderecoFornecedro='" + enderecoFornecedro + '\'' +
                ", tipoFornecedro='" + tipoFornecedro + '\'' +
                ", tipoProdutoFornecedro='" + tipoProdutoFornecedro + '\'' +
                '}';
    }
}
