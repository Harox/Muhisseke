package com.firedevz.sistemadegestaofinanceira.modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by mjaz93 on 2018/03/12.
 */

public class Requisicao {
    public static final String PAPER_NAME = "Requisicoes";

    public int id;
    public String produto;
    public int idCliente;
    public Date data;

    public static boolean register(Requisicao requisicao){
        requisicao.id = java.lang.System.identityHashCode(requisicao);
        requisicao.data = Calendar.getInstance().getTime();
        List<Requisicao> requisicaos = Paper.book().read(PAPER_NAME, new ArrayList<Requisicao>());
        requisicaos.add(requisicao);
        Paper.book().write(PAPER_NAME, requisicaos);
        return true;
    }

    public static List<Requisicao> list(){
        List<Requisicao> requisicaos = Paper.book().read(PAPER_NAME, new ArrayList<Requisicao>());
        return requisicaos;
    }
}
