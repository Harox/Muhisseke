package com.firedevz.sistemadegestaofinanceira.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaDespesa;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRendimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaSaidasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;

import java.util.List;


public class DespesaFragment extends Fragment {

    private View view;
    RecyclerView recyclerView;
    ListaSaidasAdapter listaDespesa;
    List<Despesa> despesas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.despesas_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        despesas = Despesa.list();
        listaDespesa = new ListaSaidasAdapter(getActivity(), despesas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listaDespesa);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
