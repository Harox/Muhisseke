package com.firedevz.sistemadegestaofinanceira.modelo;



public class Despesas {

    private int id_despesa;
    private String descricao_despesa;
    private float valor_despesa;
    private String tipo_despesa;
    private String conta_Retirada;

    public Despesas() {
    }

    public Despesas(int id_despesa, String descricao_despesa, float valor_despesa, String tipo_despesa) {
        this.id_despesa = id_despesa;
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.tipo_despesa = tipo_despesa;
    }

    public Despesas(String descricao_despesa, float valor_despesa, String tipo_despesa) {
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.tipo_despesa = tipo_despesa;
    }

    public Despesas(String descricao_despesa, float valor_despesa, String tipo_despesa, String contaRetirada) {
        this.id_despesa = id_despesa;
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.tipo_despesa = tipo_despesa;
        this.conta_Retirada = contaRetirada;
    }

    public int getId_despesa() {
        return id_despesa;
    }


    public void setId_despesa(int id_despesa) {
        this.id_despesa = id_despesa;
    }

    public String getConta_Retirada() {
        return conta_Retirada;
    }

    public void setConta_Retirada(String conta_Retirada) {
        this.conta_Retirada = conta_Retirada;
    }

    public String getDescricao_despesa() {
        return descricao_despesa;
    }

    public void setDescricao_despesa(String descricao_despesa) {
        this.descricao_despesa = descricao_despesa;
    }

    public float getValor_despesa() {
        return valor_despesa;
    }

    public void setValor_despesa(float valor_despesa) {
        this.valor_despesa = valor_despesa;
    }

    public String getTipo_despesa() {
        return tipo_despesa;
    }

    public void setTipo_despesa(String tipo_despesa) {
        this.tipo_despesa = tipo_despesa;
    }

    @Override
    public String toString() {
        return "Despesas{" +
                "id_despesa=" + id_despesa +
                ", descricao_despesa='" + descricao_despesa + '\'' +
                ", valor_despesa=" + valor_despesa +
                ", tipo_despesa='" + tipo_despesa + '\'' +
                '}';
    }
}
