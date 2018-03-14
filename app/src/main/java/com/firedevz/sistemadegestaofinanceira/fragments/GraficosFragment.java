package com.firedevz.sistemadegestaofinanceira.fragments;

import android.graphics.Color;
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
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaContasAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class GraficosFragment extends Fragment {

    Double ganhos;
    Double perdas;

    List<Venda.ProdutoVenda> produtoVendas;
    List<Conta> contas;

    ListaContasAdapter listaContasAdapter;

    PieChart pieChartRendimentoDespesa;
    PieChart pieChartProdutos;

    RecyclerView recycleView;

    Button buttonViewAllContas;

    TextView textViewPerdas;
    TextView textViewGanhos;
    private float[] yData = {};
    private String[] xData = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.oportunidades_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChartRendimentoDespesa = view.findViewById(R.id.pieChartRendimentoDespesa);
        pieChartProdutos = view.findViewById(R.id.pieChartProdutos);
        textViewPerdas = view.findViewById(R.id.textViewPerdas);
        textViewGanhos = view.findViewById(R.id.textViewGanhos);
        recycleView = view.findViewById(R.id.recycleView);
        buttonViewAllContas = view.findViewById(R.id.buttonViewAllContas);

        init();

        setGenreChart();
        set5ProdutosMaisVendidos();
    }

    public void init(){
        produtoVendas = Venda.getProdutosMaisVendidos();

        if (produtoVendas.size() > 5) {
            produtoVendas = produtoVendas.subList(0, 4);
        }

        ganhos = Venda.getAllIncome() + Rendimento.getAllIncome();
        perdas = Despesa.getAllIncome();
        contas = Conta.list();

        List<Conta> contas2 = contas;

        if (contas.size() > 2)
            contas2 = contas.subList(0, 1);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setNestedScrollingEnabled(false);

        listaContasAdapter = new ListaContasAdapter(getActivity(), contas2, false);
        recycleView.setAdapter(listaContasAdapter);

        buttonViewAllContas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaContasAdapter = new ListaContasAdapter(getActivity(), contas, false);
                showDialog(listaContasAdapter, "Contas");
            }
        });


        textViewGanhos.setText(ganhos + "Mt");
        textViewPerdas.setText(perdas + "Mt");

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        setGenreChart();
        set5ProdutosMaisVendidos();
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

    public void setGenreChart() {
        yData = new float[2];
        xData = new String[2];

        yData[0] = Float.parseFloat(ganhos.toString());
        yData[1] = Float.parseFloat(perdas.toString());

        xData[0] = "Ganhos";
        xData[1] = "Perdas";

        pieChartRendimentoDespesa.setUsePercentValues(true);
        pieChartRendimentoDespesa.setDescription(" ");

        // enable hole and configure
        pieChartRendimentoDespesa.setDrawHoleEnabled(true);
        pieChartRendimentoDespesa.setHoleColorTransparent(true);
        pieChartRendimentoDespesa.setHoleRadius(7);
        pieChartRendimentoDespesa.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        pieChartRendimentoDespesa.setRotationAngle(0);
        pieChartRendimentoDespesa.setRotationEnabled(true);

        // set a chart value selected listener
        pieChartRendimentoDespesa.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(getActivity(),
                        xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.GREEN);
        colors.add(Color.RED);
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

        pieChartRendimentoDespesa.setData(data);
        pieChartRendimentoDespesa.setDrawHoleEnabled(true);
        pieChartRendimentoDespesa.setHoleRadius(70f);

        // undo all highlights
        pieChartRendimentoDespesa.highlightValues(null);

        // update pie chart
        pieChartRendimentoDespesa.invalidate();

        pieChartRendimentoDespesa.setTouchEnabled(false);
    }

    public void set5ProdutosMaisVendidos() {
        yData = new float[produtoVendas.size()];
        xData = new String[produtoVendas.size()];

        int a = 0;
        for (Venda.ProdutoVenda produtoVenda : produtoVendas) {
            yData[a] = Float.parseFloat(String.valueOf(produtoVenda.preco));
            xData[a] = produtoVenda.nome;
            a++;
        }


        pieChartProdutos.setUsePercentValues(true);
        pieChartProdutos.setDescription(" ");

        // enable hole and configure
        pieChartProdutos.setDrawHoleEnabled(true);
        pieChartProdutos.setHoleColorTransparent(true);
        pieChartProdutos.setHoleRadius(7);
        pieChartProdutos.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        pieChartProdutos.setRotationAngle(0);
        pieChartProdutos.setRotationEnabled(true);

        // set a chart value selected listener
        pieChartProdutos.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                Toast.makeText(getActivity(),
                        xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

        pieChartProdutos.setData(data);
        pieChartProdutos.setDrawHoleEnabled(true);
        pieChartProdutos.setHoleRadius(70f);

        // undo all highlights
        pieChartProdutos.highlightValues(null);

        // update pie chart
        pieChartProdutos.invalidate();

        pieChartProdutos.setTouchEnabled(false);
    }
}
