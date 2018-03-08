package com.firedevz.sistemadegestaofinanceira.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRendimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RelatorioRendimentoFragment extends Fragment {
    Context context;

    DatabaseHelper db = new DatabaseHelper(context);

    private List<Rendimento> listaRendimentos = new ArrayList<>();
    private ListaRendimentosAdapter listaRendimentosAdapter;
    private RecyclerView recyclerView;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.relatorio_rendimento_fragment, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_lista_fragment_rendimentos_relatorio);

        listaRendimentosAdapter = new ListaRendimentosAdapter(listaRendimentos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listaRendimentosAdapter);

        listaRendimentos();
        return view;
    }

    public void listaRendimentos() {

        Cursor dados = db.listaTodosRendimentos();

        if (dados.getCount() == 0) {
            Toast.makeText(getContext(), "Não selecionou uma data ou Nao existem rendimntos na data selecionada", Toast.LENGTH_LONG).show();
        } else {
            while (dados.moveToNext()) {
                String descricao = dados.getString(1);
                float valor = Float.parseFloat(dados.getString(2));
                String data = dados.getString(4);

                Rendimento listaiten = new Rendimento(descricao, valor, data);
                listaRendimentos.add(listaiten);
            }
        }


    }


    ////FIM/////
}
