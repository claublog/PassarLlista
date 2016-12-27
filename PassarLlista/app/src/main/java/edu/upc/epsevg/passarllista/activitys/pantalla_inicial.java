package edu.upc.epsevg.passarllista.activitys;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;


public class pantalla_inicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  gestio_assignatures.OnFragmentInteractionListener, historic.OnFragmentInteractionListener, ajuda.OnFragmentInteractionListener, sobre.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //abrimos passar llista
        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, new passa_llista()).commit();
        DbHelper db = new DbHelper(getApplicationContext()); // Forçem la creacio de taules

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
        getMenuInflater().inflate(R.menu.toolbar_opcions, menu);
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

        Fragment frag = null;
        Boolean elementSelecionado=false;

        if (id == R.id.passa_llista) {
            frag = new passa_llista();
            setTitle("Passa llista");
            elementSelecionado=true;
        } else if (id == R.id.gestio_alumnes) {
            frag = new gestio_alumnes();
            setTitle("Gestio alumnes");
            elementSelecionado=true;
        } else if (id == R.id.gestio_assignatures) {
            frag = new gestio_assignatures();
            setTitle("Gestio assignatures");
            elementSelecionado=true;
        } else if (id == R.id.historic) {
            frag = new historic();
            setTitle("Históric");
            elementSelecionado=true;
        } else if (id == R.id.nav_ajuda) {
            frag = new ajuda();
            setTitle("Ajuda");
            elementSelecionado=true;
        } else if (id == R.id.nav_sobre) {
            frag = new sobre();
            setTitle("Sobre");
            elementSelecionado=true;
        }

        if (elementSelecionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, frag).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
