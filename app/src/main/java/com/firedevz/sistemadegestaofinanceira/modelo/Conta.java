package com.firedevz.sistemadegestaofinanceira.modelo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;

public class Conta {

    public static String PAPER_NAME = "contas";
    int Id;
    String nomeConta;
    double valorConta;
    String tipoConta;
    public List<Movimento> movimentos = new ArrayList<>();

    public Conta() {
    }

    public Conta(String nomeConta, float valorConta, String tipoConta) {
        this.nomeConta = nomeConta;
        this.valorConta = valorConta;
        this.tipoConta = tipoConta;
    }

    public static List<Conta> list() {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        return contas;
    }

        public static Conta getById(int id) {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        for (Conta conta : contas){
            if(conta.getId()==id){
                return conta;
            }
        }
        return null;
    }

    public static boolean register(Conta conta) {
        conta.Id = java.lang.System.identityHashCode(conta);
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        contas.add(conta);
        Paper.book().write(PAPER_NAME, contas);
        return true;
    }

    public static String[] listStringArray() {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        String[] lista = new String[contas.size()];
        int i = 0;
        for (Conta conta : contas) {
            lista[i] = conta.nomeConta;
            i++;
        }
        return lista;
    }


    public static boolean addMovRendimento(Conta conta, Rendimento rendimento) {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        List<Conta> contasAux = new ArrayList<>();
        for (Conta contaAux : contas) {
            if (conta.getId() == contaAux.getId()) {
                Movimento movimento = new Movimento();
                movimento.contaMovimento = conta.getNomeConta();
                movimento.dataMovimento = Calendar.getInstance().getTime();
                movimento.idMovimento = java.lang.System.identityHashCode(movimento);
                movimento.tipoMovimento = "Rendimento";
                movimento.valorMovimento = rendimento.getValor();
                conta.movimentos.add(movimento);
                conta.valorConta = conta.getValorConta() + rendimento.getValor();
            }
            contasAux.add(conta);
        }
        Paper.book().write(PAPER_NAME, contasAux);
        return true;
    }


    public static boolean addMovDespesa(Conta conta, Despesa despesa) {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        List<Conta> contasAux = new ArrayList<>();
        for (Conta contaAux : contas) {
            if (conta.getId() == contaAux.getId()) {
                Movimento movimento = new Movimento();
                movimento.contaMovimento = conta.getNomeConta();
                movimento.dataMovimento = Calendar.getInstance().getTime();
                movimento.idMovimento = java.lang.System.identityHashCode(movimento);
                movimento.tipoMovimento = "Despesa";
                movimento.valorMovimento = despesa.getValor_despesa();
                conta.movimentos.add(movimento);
                conta.valorConta = conta.getValorConta() - despesa.getValor_despesa();
            }
            contasAux.add(conta);
        }
        Paper.book().write(PAPER_NAME, contasAux);
        return true;
    }


    public static boolean addMovVenda(Conta conta, Venda venda) {
        List<Conta> contas = Paper.book().read(PAPER_NAME, new ArrayList<Conta>());
        List<Conta> contasAux = new ArrayList<>();
        for (Conta contaAux : contas) {
            if (conta.getId() == contaAux.getId()) {
                Movimento movimento = new Movimento();
                movimento.contaMovimento = conta.getNomeConta();
                movimento.dataMovimento = Calendar.getInstance().getTime();
                movimento.idMovimento = java.lang.System.identityHashCode(movimento);
                movimento.tipoMovimento = "Despesa";
                movimento.valorMovimento = venda.getPrecoTotal();
                conta.movimentos.add(movimento);
                conta.valorConta = conta.getValorConta() + venda.getPrecoTotal();
            }
            contasAux.add(conta);
        }
        Paper.book().write(PAPER_NAME, contasAux);
        return true;
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

    public double getValorConta() {
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
