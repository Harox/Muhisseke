package com.firedevz.sistemadegestaofinanceira.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firedevz.sistemadegestaofinanceira.ProducaoActivity;
import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityFundos;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaClientes;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityRelatorios;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityVendas;
import com.firedevz.sistemadegestaofinanceira.activity.activityListaProdutos;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRendimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRequisicaoAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;

import java.util.List;


public class RendimentoFragment extends Fragment {

    private View view;
    RecyclerView recyclerView;
    ListaRendimentosAdapter listaRendimentosAdapter;
    List<Rendimento> rendimentos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rendimento_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        rendimentos = Rendimento.list();
        listaRendimentosAdapter = new ListaRendimentosAdapter(getActivity(), rendimentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listaRendimentosAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
