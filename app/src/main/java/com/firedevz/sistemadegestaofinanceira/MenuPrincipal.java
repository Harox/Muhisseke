package com.firedevz.sistemadegestaofinanceira;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firedevz.sistemadegestaofinanceira.activity.ActivityFornecedores;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityFundos;

import com.firedevz.sistemadegestaofinanceira.activity.ActivityListaClientes;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityRelatorios;
import com.firedevz.sistemadegestaofinanceira.activity.ActivityVendas;
import com.firedevz.sistemadegestaofinanceira.activity.LoginActivity;
import com.firedevz.sistemadegestaofinanceira.activity.activityListaProdutos;
import com.firedevz.sistemadegestaofinanceira.fragments.HomeFragment;
import com.firedevz.sistemadegestaofinanceira.fragments.NotificacoesFragment;
import com.firedevz.sistemadegestaofinanceira.fragments.OportunidadeFragment;
import com.firedevz.sistemadegestaofinanceira.modelo.Clientes;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //private FirebaseAuth mAuth;
    private Button btnFundos,btnVendas,btnProdutos,btnClientes,btnProducao,btnRelatorio;


    private OportunidadeFragment oportunidadeFragment;
    private NotificacoesFragment notificacoesFragment;
    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        oportunidadeFragment = new OportunidadeFragment();
        notificacoesFragment = new NotificacoesFragment();
        homeFragment = new HomeFragment();

        setFragment(homeFragment);

        setSupportActionBar(toolbar);

        ////Side Menu togle

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //messageView = (TextView) findViewById(R.id.messageView);

        BottomNavigationView bottomBar = (BottomNavigationView) findViewById(R.id.navigation);

        bottomBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                setFragment(homeFragment);
                                break;

                            case R.id.navigation_oportunites:
                                setFragment(oportunidadeFragment);
                                break;

                            case R.id.navigation_notifications:
                                setFragment(notificacoesFragment);
                                break;

                            default:
                                setFragment(homeFragment);
                                break;
                        }
                        return false;
                    }
                }

        );

    }


////        bottomBar.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
////            @Override
////            public void onNavigationItemReselected(@NonNull MenuItem item) {
////                switch (item.getItemId()) {
////                    case R.id.navigation_home:
////                        setFragment(homeFragment);
////
////                    case R.id.navigation_oportunites:
////                        setFragment(oportunidadeFragment);
////
////                    case R.id.navigation_notifications:
////                        setFragment(notificacoesFragment);
////
////                    default:
////                        break;
////                }
////
////            }
////        });
//    }


    //METODO DE TRASICAO DE FRAGEMENTS
    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_menu) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
        } else if (id == R.id.nav_fornecedores) {
            Intent i = new Intent(getApplicationContext(),ActivityFornecedores.class);
            startActivity(i);
        } else if (id == R.id.nav_configuracoes) {

        } else if (id == R.id.nav_ajuda) {

        } else if (id == R.id.nav_sobre) {

        }else if (id == R.id.nav_sair) {
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
