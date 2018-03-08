package com.firedevz.sistemadegestaofinanceira.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firedevz.sistemadegestaofinanceira.R;


public class RelatorioVendaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.relatorio_venda_fragment, container, false);
        return view;
    }
}
