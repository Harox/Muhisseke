package com.firedevz.sistemadegestaofinanceira.modelo;



public class Contas {
    String nomeConta;
    float valorConta;
    String tipoConta;

    public Contas() {
    }

    public Contas(String nomeConta, float valorConta, String tipoConta) {
        this.nomeConta = nomeConta;
        this.valorConta = valorConta;
        this.tipoConta = tipoConta;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public float getValorConta() {
        return valorConta;
    }

    public void setValorConta(float valorConta) {
        this.valorConta = valorConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }
}
