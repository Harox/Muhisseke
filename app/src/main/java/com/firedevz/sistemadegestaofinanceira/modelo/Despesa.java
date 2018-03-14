package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Despesa {

    public static String PAPER_NAME = "despesas";

    private int Id;
    private int id_despesa;
    private String descricao_despesa;
    private float valor_despesa;
    private String tipo_despesa;
    private int conta_Retirada;

    public static List<Despesa> list(){
        List<Despesa> despesas = Paper.book().read(PAPER_NAME, new ArrayList<Despesa>());
        return despesas;
    }

    public static double getAllIncome() {
        double income = 0;
        List<Despesa> despesas = Paper.book().read(PAPER_NAME, new ArrayList<Despesa>());
        for (Despesa despesa : despesas){
            income = income + despesa.getValor_despesa();
        }
        return income;
    }

    public static boolean register(Despesa despesa){
        despesa.Id = java.lang.System.identityHashCode(despesa);
        List<Despesa> despesas = Paper.book().read(PAPER_NAME, new ArrayList<Despesa>());
        despesas.add(despesa);
        Paper.book().write(PAPER_NAME, despesas);
        return true;
    }

    public Despesa() {
    }

    public Despesa(int id_despesa, String descricao_despesa, float valor_despesa, String tipo_despesa) {
        this.id_despesa = id_despesa;
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.tipo_despesa = tipo_despesa;
    }

    public Despesa(String descricao_despesa, float valor_despesa, String tipo_despesa) {
        this.descricao_despesa = descricao_despesa;
        this.valor_despesa = valor_despesa;
        this.tipo_despesa = tipo_despesa;
    }

    public Despesa(String descricao_despesa, float valor_despesa, String tipo_despesa, int contaRetirada) {
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

    public int getConta_Retirada() {
        return conta_Retirada;
    }

    public void setConta_Retirada(int conta_Retirada) {
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
        return "Despesa{" +
                "id_despesa=" + id_despesa +
                ", descricao_despesa='" + descricao_despesa + '\'' +
                ", valor_despesa=" + valor_despesa +
                ", tipo_despesa='" + tipo_despesa + '\'' +
                '}';
    }
}
