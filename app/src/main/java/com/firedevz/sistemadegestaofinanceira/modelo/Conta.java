package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class Conta {

    public static String PAPER_NAME = "contas";

    public static List<Conta> list(){
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        return contas;
    }

    public static boolean register(Conta conta){
        conta.Id = java.lang.System.identityHashCode(conta);
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        contas.add(conta);
        Paper.book().write(PAPER_NAME, contas);
        return true;
    }


    public static String[] listStringArray(){
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        String[] lista = new String[contas.size()];
        int i =0;
        for (Conta conta : contas){
            lista[i] = conta.nomeConta;
            i++;
        }
        return lista;
    }

    int Id;
    String nomeConta;
    float valorConta;
    String tipoConta;

    public Conta() {
    }

    public Conta(String nomeConta, float valorConta, String tipoConta) {
        this.nomeConta = nomeConta;
        this.valorConta = valorConta;
        this.tipoConta = tipoConta;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
