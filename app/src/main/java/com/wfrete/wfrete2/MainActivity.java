package com.wfrete.wfrete2;

import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wfrete.wfrete2.activity.CategoriaListarActivity;
import com.wfrete.wfrete2.activity.FreteListarActivity;
import com.wfrete.wfrete2.activity.InicioActivity;
import com.wfrete.wfrete2.activity.MotoristaListarActivity;
import com.wfrete.wfrete2.activity.VeiculoListarActivity;
import com.wfrete.wfrete2.dao.FreteDAO;
import com.wfrete.wfrete2.model.Frete;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();

        //enquanto acessar o app e nao existir um frete, mostrar a tela de incio
        Frete frete = new FreteDAO(this).retornarUltimo();
        if (!(frete != null)){
            fragmentManager.beginTransaction().replace(R.id.content_frame, new InicioActivity()).commit();
        }

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
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_frete) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new FreteListarActivity()).commit();

        }
        else if (id == R.id.nav_categorias) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new CategoriaListarActivity()).commit();

        }
        else if (id == R.id.nav_motorista) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new MotoristaListarActivity()).commit();

        } else if (id == R.id.nav_veiculo) {

            fragmentManager.beginTransaction().replace(R.id.content_frame, new VeiculoListarActivity()).commit();

        }
        else if (id == R.id.nav_sinc_status) {
        }
        else if (id == R.id.nav_configuracoes) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
