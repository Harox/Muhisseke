package com.firedevz.sistemadegestaofinanceira.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaProdutosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.util.List;


public class NotificacoesFragment extends Fragment {

    RecyclerView recycleViewProdutosSemStock;
    RecyclerView recycleViewProdutosPoucoStock;
    RecyclerView recycleViewDividas;
    RecyclerView recycleViewProdutosForaDoPrazo;
    RecyclerView recycleViewProdutosPrestesExpirar;

    ListaProdutosAdapter listaProdutosAdapterProdutosSemStock;
    ListaProdutosAdapter listaProdutosAdapterProdutosPoucoStock;
    ListaVendaAdapter listaProdutosAdapterDividas;
    ListaProdutosAdapter listaProdutosAdapterProdutosForaDoPrazo;
    ListaProdutosAdapter listaProdutosAdapterProdutosPrestesExpirar;

    Button buttonProdutosPoucoStock;
    Button buttonProdutosSemStock;
    Button buttonDividasAntigas;
    Button buttonProdutosForaDoPrazo;
    Button buttonProdutosPrestesExpirar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.notificacoes_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setValues();
    }

    public void setValues() {
        buttonDividasAntigas = getActivity().findViewById(R.id.buttonDividasAntigas);
        buttonProdutosSemStock = getActivity().findViewById(R.id.buttonProdutosSemStock);
        buttonProdutosPoucoStock = getActivity().findViewById(R.id.buttonProdutosPoucoStock);
        buttonProdutosForaDoPrazo = getActivity().findViewById(R.id.buttonProdutosForaDoPrazo);
        buttonProdutosPrestesExpirar = getActivity().findViewById(R.id.buttonProdutosPrestesExpirar);

        recycleViewProdutosSemStock = getActivity().findViewById(R.id.recycleViewProdutosSemStock);
        recycleViewProdutosPoucoStock = getActivity().findViewById(R.id.recycleViewProdutosPoucoStock);
        recycleViewDividas = getActivity().findViewById(R.id.recycleViewDividas);
        recycleViewProdutosForaDoPrazo = getActivity().findViewById(R.id.recycleViewProdutosForaDoPrazo);
        recycleViewProdutosPrestesExpirar = getActivity().findViewById(R.id.recycleViewProdutosPrestesExpirar);

        recycleViewProdutosSemStock.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewProdutosPoucoStock.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewDividas.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewProdutosForaDoPrazo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewProdutosPrestesExpirar.setLayoutManager(new LinearLayoutManager(getActivity()));

        recycleViewProdutosSemStock.setNestedScrollingEnabled(false);
        recycleViewProdutosPoucoStock.setNestedScrollingEnabled(false);
        recycleViewDividas.setNestedScrollingEnabled(false);
        recycleViewProdutosForaDoPrazo.setNestedScrollingEnabled(false);
        recycleViewProdutosPrestesExpirar.setNestedScrollingEnabled(false);

        List<Produto> produtosSemStock = Produto.listarTodosSemStock();
        List<Produto> produtosStockMinimo = Produto.listarTodosComStockMinimo();
        List<Produto> produtosExpirados = Produto.listarTodosExpirados();
        List<Produto> produtosPrestesAExpirar = Produto.listarTodosComPrazoDentroDeUmMes();
        List<Venda> vendasPendestes = Venda.listPendentes();

        if (produtosSemStock.size() > 2)
            produtosSemStock = produtosSemStock.subList(0, 1);
        if (produtosStockMinimo.size() > 2)
            produtosStockMinimo = produtosStockMinimo.subList(0, 1);
        if (produtosExpirados.size() > 2)
            produtosExpirados = produtosExpirados.subList(0, 1);
        if (produtosPrestesAExpirar.size() > 2)
            produtosPrestesAExpirar = produtosPrestesAExpirar.subList(0, 1);
        if (vendasPendestes.size() > 2)
            vendasPendestes = vendasPendestes.subList(0, 1);

        listaProdutosAdapterProdutosSemStock = new ListaProdutosAdapter(getActivity(), produtosSemStock, ListaProdutosAdapter.PRODUTOS_SEM_STOCK);
        listaProdutosAdapterProdutosPoucoStock = new ListaProdutosAdapter(getActivity(), produtosStockMinimo, ListaProdutosAdapter.PRODUTOS_COM_POUCO_STOCK);
        listaProdutosAdapterProdutosForaDoPrazo = new ListaProdutosAdapter(getActivity(), produtosExpirados, ListaProdutosAdapter.PRODUTOS_FORA_PRAZO);
        listaProdutosAdapterProdutosPrestesExpirar = new ListaProdutosAdapter(getActivity(), produtosPrestesAExpirar, ListaProdutosAdapter.PRODUTOS_PRESTES_A_EXPIRAR);
        listaProdutosAdapterDividas = new ListaVendaAdapter(getActivity(), vendasPendestes);

        recycleViewProdutosSemStock.setAdapter(listaProdutosAdapterProdutosSemStock);
        recycleViewProdutosPoucoStock.setAdapter(listaProdutosAdapterProdutosPoucoStock);
        recycleViewProdutosForaDoPrazo.setAdapter(listaProdutosAdapterProdutosForaDoPrazo);
        recycleViewProdutosPrestesExpirar.setAdapter(listaProdutosAdapterProdutosPrestesExpirar);
        recycleViewDividas.setAdapter(listaProdutosAdapterDividas);

        buttonDividasAntigas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Venda> vendasPendestes = Venda.listPendentes();
                listaProdutosAdapterDividas = new ListaVendaAdapter(getActivity(), vendasPendestes);
                showDialog(listaProdutosAdapterDividas, "Dívidas");
            }
        });

        buttonProdutosForaDoPrazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Produto> produtosExpirados = Produto.listarTodosExpirados();
                listaProdutosAdapterProdutosForaDoPrazo = new ListaProdutosAdapter(getActivity(), produtosExpirados, ListaProdutosAdapter.PRODUTOS_FORA_PRAZO);
                showDialog(listaProdutosAdapterProdutosForaDoPrazo, "Produtos fora do prazo");
            }
        });

        buttonProdutosSemStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Produto> produtosSemStock = Produto.listarTodosSemStock();
                listaProdutosAdapterProdutosSemStock = new ListaProdutosAdapter(getActivity(), produtosSemStock, ListaProdutosAdapter.PRODUTOS_SEM_STOCK);
                showDialog(listaProdutosAdapterProdutosSemStock, "Produtos Sem Stock");
            }
        });

        buttonProdutosPoucoStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Produto> produtosStockMinimo = Produto.listarTodosComStockMinimo();
                listaProdutosAdapterProdutosPoucoStock = new ListaProdutosAdapter(getActivity(), produtosStockMinimo, ListaProdutosAdapter.PRODUTOS_COM_POUCO_STOCK);
                showDialog(listaProdutosAdapterProdutosPoucoStock, "Produtos Pouco Stock");
            }
        });

        buttonProdutosPrestesExpirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Produto> produtosPrestesAExpirar = Produto.listarTodosComPrazoDentroDeUmMes();
                listaProdutosAdapterProdutosPrestesExpirar = new ListaProdutosAdapter(getActivity(), produtosPrestesAExpirar, ListaProdutosAdapter.PRODUTOS_PRESTES_A_EXPIRAR);
                showDialog(listaProdutosAdapterProdutosPrestesExpirar, "Produtos Prestes a Expirar");
            }
        });
    }

    public void showDialog(RecyclerView.Adapter adapter, String title) {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View vi = li.inflate(R.layout.dialog_ver_todos, null);

        RecyclerView recyclerView = vi.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TextView textView = vi.findViewById(R.id.textView);

        textView.setText(title);
        recyclerView.setAdapter(adapter);

        new AlertDialog
                .Builder(getActivity())
                .setView(vi)
                .setPositiveButton("Ok", null)
                .show();
    }
}
