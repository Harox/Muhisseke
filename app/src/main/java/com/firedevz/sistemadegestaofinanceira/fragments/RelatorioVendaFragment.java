package com.firedevz.sistemadegestaofinanceira.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRendimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaVendaAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Cliente;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.modelo.Venda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RelatorioVendaFragment extends Fragment {
    RecyclerView recyclerView;
    ListaVendaAdapter listaVendaAdapter;
    List<Venda> vendas;
    Button buttonDataDe;
    Button buttonDataA;
    Button buttonExportar;
    Date dataA, dataDe;
    private View view;

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.relatorio_despesas_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        buttonDataDe = view.findViewById(R.id.buttonDataDe);
        buttonDataA = view.findViewById(R.id.buttonDataA);
        buttonExportar = view.findViewById(R.id.buttonExportar);

        vendas = Venda.list();
        listaVendaAdapter = new ListaVendaAdapter(getActivity(), vendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listaVendaAdapter);

        buttonDataA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.pop_up_datepicker, null, false);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
                new AlertDialog
                        .Builder(getActivity())
                        .setView(dialogView)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataA = getDateFromDatePicker(datePicker);
                                buttonDataA.setText(new SimpleDateFormat("dd/MM/yyyy").format(dataA));
                                if (dataDe != null) {
                                    updateData();
                                }
                            }
                        }).show();
            }
        });

        buttonDataDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.pop_up_datepicker, null, false);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
                datePicker.setMaxDate(Calendar.getInstance().getTimeInMillis());
                new AlertDialog
                        .Builder(getActivity())
                        .setView(dialogView)
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataDe = getDateFromDatePicker(datePicker);
                                buttonDataDe.setText(new SimpleDateFormat("dd/MM/yyyy").format(dataDe));
                                if (dataA != null) {
                                    updateData();
                                }
                            }
                        }).show();
            }
        });

        buttonExportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                            getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    1);
                        } else {
                            exportar();
                        }
                    } else {
                        exportar();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void updateData() {
        vendas = Venda.listByInterval(dataDe, dataA);
        listaVendaAdapter = new ListaVendaAdapter(getActivity(), vendas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(listaVendaAdapter);
    }

    void exportar() throws IOException {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Muhisseke/csvs");
        myDir.mkdirs();
        final File file = new File(myDir, "vendas_" + new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()) + ".csv");
        file.createNewFile();
        FileWriter fw = new FileWriter(file);

        fw.append("id,");
        fw.append("data,");
        fw.append("valor,");
        fw.append("cliente,");
        fw.append("nuit");
        fw.append('\n');
        for (Venda venda : vendas){
            Cliente cliente = Cliente.getById(venda.getIdCliente());
            fw.append(""+venda.getIdVenda());
            fw.append(","+new SimpleDateFormat("dd/MM/yyyy").format(venda.getDate()));
            fw.append(","+venda.getPrecoTotal());
            fw.append(","+cliente.getNome());
            fw.append(","+cliente.getNuitCliente());
            fw.append('\n');
        }
        fw.flush();
        fw.close();

        Log.d("file", file.toString());
        Log.d("file lenght", file.length() + "");

        Uri uri = Uri.fromFile(file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Partilhar por:"));
    }

}
