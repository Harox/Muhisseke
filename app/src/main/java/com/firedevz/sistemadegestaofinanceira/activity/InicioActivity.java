package com.firedevz.sistemadegestaofinanceira.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Conta;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

/**
 * Created by PUDENTE on 2/5/2018.
 */

public class InicioActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    public ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Paper.init(this);
        if(!Paper.book().contains(Conta.PAPER_NAME)){
            List<Conta> contas = new ArrayList<>();
            Conta conta = new Conta();
            conta.setNomeConta("Caixa");
            conta.setTipoConta("Caixa");
            conta.setValorConta(0);
            Conta.register(conta);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(InicioActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}