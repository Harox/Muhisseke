package com.firedevz.sistemadegestaofinanceira.activities20;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.fragments.DespesaFragment;
import com.firedevz.sistemadegestaofinanceira.fragments.RendimentoFragment;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;
import com.firedevz.sistemadegestaofinanceira.modelo.Despesa;
import com.firedevz.sistemadegestaofinanceira.modelo.Rendimento;

import java.util.Calendar;

public class ProducaoActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producao2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] array = new String[]{"Rendimento", "Despesa"};
                new AlertDialog.Builder(ProducaoActivity.this)
                        .setTitle("Selecione uma opção")
                        .setItems(array, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {//Rendimento
                                    addRendimento();
                                }
                                if (i == 1) {//Despesa
                                    addDespesa();
                                }
                            }
                        }).show();
            }
        });

    }

    public void addRendimento() {
        LayoutInflater li = LayoutInflater.from(this);
        View vi = li.inflate(R.layout.popup_add_rendimentos, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(vi);

        final EditText edtData = vi.findViewById(R.id.edtData);
        final EditText edtDescricao = vi.findViewById(R.id.edtDescricao);
        final EditText edtValor = vi.findViewById(R.id.edtValor);
        final Spinner spnTipo = vi.findViewById(R.id.spnTipo);
        final Spinner spnContaAdicionar = vi.findViewById(R.id.spnContaAdicionar);

        ArrayAdapter adpOpcoes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adpOpcoes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        adpOpcoes.add("Empresariais");
        adpOpcoes.add("Profissionais");
        adpOpcoes.add("Renda");
        adpOpcoes.add("Juro");
        adpOpcoes.add("Lucro");
        adpOpcoes.add("Incrementos Patrimoniais e indemnizações");
        adpOpcoes.add("Pensão");
        adpOpcoes.add("Outro");
        

        spnTipo.setAdapter(adpOpcoes);

        final DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String data1 = dayOfMonth + "/" + month + "/" + year;
                edtData.setText(data1);

            }
        };

        edtData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ProducaoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        String[] spinnerLists = Conta.listStringArray();//db.getAllSpinnerAccounts();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnContaAdicionar.setAdapter(spinnerAdapter);

        alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String descricao = edtDescricao.getText().toString();
                float valor = Float.parseFloat(edtValor.getText().toString());
                String tipo = spnTipo.getSelectedItem().toString();
                String data = edtData.getText().toString();
                String contaAdicionar = spnContaAdicionar.getSelectedItem().toString();
                float valorRendimentoConta = 0;

                if (descricao.isEmpty()) {
                    edtDescricao.setError("campo Obrigatorio");
                    edtValor.setError("campo Obrigatorio");
                } else {
                    //insert
                    Rendimento rendimento = new Rendimento(descricao, Conta.list().get(spnContaAdicionar.getSelectedItemPosition()).getId(), valor, tipo, data, contaAdicionar);
                    Rendimento.register(rendimento);
                    Toast.makeText(ProducaoActivity.this, "Rendimento adicionado com Sucesso", Toast.LENGTH_LONG).show();
                    ProducaoActivity.this.recreate();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // Criar O Alerta
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Mostra o alerta
        alertDialog.show();
    }

    public void addDespesa() {
        LayoutInflater li = LayoutInflater.from(this);
        View vi = li.inflate(R.layout.popup_add_despesas, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setView(vi);

        final EditText edtNomeDespesa = (EditText) vi.findViewById(R.id.edtNomeDespesa);
        final EditText edtValorDespesa = (EditText) vi.findViewById(R.id.edtValorDespesa);
        final Spinner spnTipoDespesa = (Spinner) vi.findViewById(R.id.spnTipoDespesa);
        final Spinner spnContaRetirada = (Spinner) vi.findViewById(R.id.spnContaRetirada);

        String[] spinnerLists = Conta.listStringArray();//db.getAllSpinnerAccounts();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLists);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnContaRetirada.setAdapter(spinnerAdapter);

        ArrayAdapter<String> adpTipoDespesa;
        adpTipoDespesa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adpTipoDespesa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoDespesa.setAdapter(adpTipoDespesa);

        adpTipoDespesa.add("Pessoal");
        adpTipoDespesa.add("Comercial");
        adpTipoDespesa.add("Imposto");
        adpTipoDespesa.add("Outro");


        alertDialogBuilder.setCancelable(false).setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String nomeDespesa = edtNomeDespesa.getText().toString();
                float valorDespesa = Float.parseFloat(edtValorDespesa.getText().toString());
                String tipoDespesa = spnTipoDespesa.getSelectedItem().toString();
                int contaRetirada = spnContaRetirada.getSelectedItemPosition();
                float totalDespesaCOnta = 0;

                if (Despesa.register(new Despesa(nomeDespesa, valorDespesa, tipoDespesa, contaRetirada))) {
                    Toast.makeText(ProducaoActivity.this, "Despesa Adicionada Com Sucesso! ", Toast.LENGTH_SHORT).show();
//                            totalDespesaCOnta = db.somaDespesaConta(contaRetirada);
                    Toast.makeText(ProducaoActivity.this, "Retirado da conta: " + contaRetirada + " " + totalDespesaCOnta + "0 MZN", Toast.LENGTH_SHORT).show();
//                            db.retiraNaConta(contaRetirada, totalDespesaCOnta);
                    ProducaoActivity.this.recreate();
                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        // Criar O Alerta
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Mostra o alerta
        alertDialog.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_producao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_producao, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return new RendimentoFragment();
            } else if (position == 1) {
                return new DespesaFragment();
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
