package com.firedevz.sistemadegestaofinanceira.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.fragments.RelatorioDespesaFragment;
import com.firedevz.sistemadegestaofinanceira.fragments.RelatorioRendimentoFragment;
import com.firedevz.sistemadegestaofinanceira.fragments.RelatorioVendaFragment;

import java.util.Calendar;


public class ActivityRelatorios extends AppCompatActivity {

    private static final String TAG ="ActivityRelatorios";

    private Button btnVerRelatorio;
    private Spinner spnTipoRelatorio;
    private ArrayAdapter<String > adpTipoRelatorio;

    EditText edtDataInicio,edtDataFim;
    private DatePickerDialog.OnDateSetListener mDateSetListener1,mDateSetListener2;
    private String data1,data2;
    private RelatorioRendimentoFragment relatorioRendimentoFragment;
    private RelatorioDespesaFragment relatorioDespesaFragment;
    private RelatorioVendaFragment relatorioVendaFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_relatorio_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        relatorioRendimentoFragment = new RelatorioRendimentoFragment();
        relatorioDespesaFragment = new RelatorioDespesaFragment();
        relatorioVendaFragment = new RelatorioVendaFragment();

        edtDataInicio = findViewById(R.id.edtDataInicio);
        edtDataFim = findViewById(R.id.edtDataFim);
        btnVerRelatorio = findViewById(R.id.btnVerRelatorio);
        spnTipoRelatorio = findViewById(R.id.spnTipoRelatorio);

        edtDataInicio.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityRelatorios.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener1,year,month,day);
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
                edtDataInicio.setText(data1);

            }
        };

        edtDataFim.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityRelatorios.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener2,year,month,day);
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
                edtDataFim.setText(data2);

            }
        };

        adpTipoRelatorio = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        adpTipoRelatorio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoRelatorio.setAdapter(adpTipoRelatorio);

        adpTipoRelatorio.add("Rendimentos");
        adpTipoRelatorio.add("Despesa");
        adpTipoRelatorio.add("Vendas");


        setFragment(relatorioVendaFragment);


        btnVerRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String despesa = spnTipoRelatorio.getSelectedItem().toString();

                if(despesa=="Vendas"){
                    setFragment(relatorioVendaFragment);

                }else if(despesa=="Rendimentos"){
                    setFragment(relatorioRendimentoFragment);

                }else{
                    setFragment(relatorioDespesaFragment);

                }
            }
        });


    }

    //METODO DE TRASICAO DE FRAGEMENTS
    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutrelatorio, fragment);
        fragmentTransaction.commit();
    }



}
