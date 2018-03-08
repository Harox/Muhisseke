package com.firedevz.sistemadegestaofinanceira.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.*;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.adapter.ListaRendimentosAdapter;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;
import com.firedevz.sistemadegestaofinanceira.sql.DatabaseHelper;

public class ActivityRelatorioRendimentos extends AppCompatActivity {

    private static final String TAG ="ActivityRelatorioRendimentos";
    private Button btnDataIncioRendimento,tbnDataFimRendimento,btnVerRelatorioRendimento,btnSalvarPdf;
    private DatePickerDialog.OnDateSetListener mDateSetListener1,mDateSetListener2;
    private String data1,data2;


    DatabaseHelper db = new DatabaseHelper(this);

    private List<Rendimento> listaRendimentos = new ArrayList<>();
    private ListaRendimentosAdapter listaRendimentosAdapter;
    private RecyclerView recyclerView;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatorio_rendimento);
        btnDataIncioRendimento = (Button)findViewById(R.id.btnDataIncioRendimento);
        tbnDataFimRendimento = (Button)findViewById(R.id.tbnDataFimRendimento);
        btnVerRelatorioRendimento = (Button)findViewById(R.id.btnVerRelatorioRendimento);
        btnSalvarPdf = (Button)findViewById(R.id.btnSalvarPdf);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_lista_rendimentos_relatorio);


        btnDataIncioRendimento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityRelatorioRendimentos.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener1,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener(){
            @SuppressLint("LongLogTag")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG, ": data "+dayOfMonth+"/"+month+"/"+year);
                data1 = dayOfMonth+"/"+month+"/"+year;
                btnDataIncioRendimento.setText(data1);

            }
        };

        tbnDataFimRendimento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityRelatorioRendimentos.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener2 = new DatePickerDialog.OnDateSetListener(){
            @SuppressLint("LongLogTag")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                Log.d(TAG, ": data "+dayOfMonth+"/"+month+"/"+year);
                data2 = dayOfMonth+"/"+month+"/"+year;
                tbnDataFimRendimento.setText(data2);

            }
        };


        listaRendimentosAdapter = new ListaRendimentosAdapter(listaRendimentos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(listaRendimentosAdapter);

        btnVerRelatorioRendimento.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityRelatorioRendimentos.this, "Data inicio:"+data1+", Data Fim:"+data2, Toast.LENGTH_LONG).show();

                listaRendimentos(data1,data2);

            }
        });

        listaRendimentos(data1,data2);

    }


    public void listaRendimentos(String dataInicio,String dataFim) {

        Cursor dados = db.listaRendimentosMarcados(dataInicio,dataFim);

        if (dados.getCount() == 0) {
            Toast.makeText(this, "Não selecionou uma data ou Nao existem rendimntos na data selecionada", Toast.LENGTH_LONG).show();
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

    ///FIm
}
