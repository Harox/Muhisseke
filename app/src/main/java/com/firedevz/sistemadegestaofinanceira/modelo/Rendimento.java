package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class Rendimento {

    public static String PAPER_NAME = "rendimentos";

    public static Rendimento getById(int id){
        List<Rendimento> rendimentos = Paper.book().read(PAPER_NAME, new ArrayList<Rendimento>());
        for (Rendimento rendimento : rendimentos){
            if(rendimento.id==id){
                return rendimento;
            }
        }
        return null;
    }

    public static double getAllIncome() {
        double income = 0;
        List<Rendimento> rendimentos = Paper.book().read(PAPER_NAME, new ArrayList<Rendimento>());
        for (Rendimento rendimento : rendimentos){
            income = income + rendimento.getValor();
        }
        return income;
    }

    public static boolean register(Rendimento rendimento){
        rendimento.id = java.lang.System.identityHashCode(rendimento);
        List<Rendimento> rendimentos = Paper.book().read(PAPER_NAME, new ArrayList<Rendimento>());
        rendimentos.add(rendimento);
        Paper.book().write(PAPER_NAME, rendimentos);
        return true;
    }

    public static List<Rendimento> list(){
        List<Rendimento> rendimentos = Paper.book().read(PAPER_NAME, new ArrayList<Rendimento>());
        return rendimentos;
    }

    public static List<Rendimento> listByInterval(Date dateInicio, Date dateFim) {
        List<Rendimento> rendimentos = Paper.book().read(PAPER_NAME, new ArrayList<Rendimento>());
        List<Rendimento> rendimentosAux = new ArrayList<>();
        for (Rendimento rendimento : rendimentos) {
            if(rendimento.data.after(dateInicio) && rendimento.data.before(dateFim))
                rendimentosAux.add(rendimento);
        }
        return rendimentosAux;
    }

    public int id;
    public int idConta;
    String descricao;
    double valor;
    String tipo;
    public Date data;
    String ContaAdicionada;

    public Rendimento(String descricao, int idConta, double valor, String tipo, Date data, String contaAdicionada) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.idConta = idConta;
        this.data = data;
        ContaAdicionada = contaAdicionada;
    }

    public Rendimento(String descricao, double valor, String tipo, Date data) {
        this.descricao = descricao;
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
    }


    public Rendimento() {

    }

    public Rendimento(String descricao, double valor, Date data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getContaAdicionada() {
        return ContaAdicionada;
    }

    public void setContaAdicionada(String contaAdicionada) {
        ContaAdicionada = contaAdicionada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Rendimento{" +
                "descricao='" + descricao + '\'' +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                '}';
    }
}
