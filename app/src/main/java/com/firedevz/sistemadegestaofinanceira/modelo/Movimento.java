package com.firedevz.sistemadegestaofinanceira.modelo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class Movimento {

    public static String PAPER_NAME = "movimentos";

    public int idMovimento;
    public String contaMovimento;
    public double valorMovimento;
    public Date dataMovimento;
    public String tipoMovimento;

    public Movimento() {
    }

    public static List<Movimento> getAllMovimentos() {
        List<Conta> contas = Paper.book().read(Conta.PAPER_NAME, new ArrayList<Conta>());
        List<Movimento> movimentos = new ArrayList<>();
        for (Conta conta : contas){
            movimentos.addAll(conta.movimentos);
            Log.d("debbug", conta.getNomeConta() + " " + conta.movimentos.size());
        }
        return movimentos;
    }

}
