package edu.upc.epsevg.passarllista.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;
import edu.upc.epsevg.passarllista.fragments.GestioAlumnes;
import edu.upc.epsevg.passarllista.fragments.GestioAssignatures;
import edu.upc.epsevg.passarllista.fragments.Historic;
import edu.upc.epsevg.passarllista.fragments.Informacio;


public class PantallaInicial extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
        navigationView.getMenu().getItem(0).setChecked(true);

        Fragment frag = new Historic();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenidor, frag).commit(); //mostrem l'historic de sessions
        new DbHelper(getApplicationContext()); // Forçem la creacio de taules

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag = null;
        Boolean elementSeleccionat = false;

        if (id == R.id.passa_llista) {
            frag = new Historic();
            setTitle(R.string.passa_llista);
            elementSeleccionat = true;
        } else if (id == R.id.gestio_alumnes) {
            frag = new GestioAlumnes();
            setTitle(R.string.gesti_d_alumnes);
            elementSeleccionat = true;
        } else if (id == R.id.gestio_assignatures) {
            Bundle b = new Bundle();
            b.putBoolean("esGestio", true);
            frag = new GestioAssignatures();
            frag.setArguments(b);
            setTitle(R.string.gesti_d_assignatures);
            elementSeleccionat = true;
        } else if (id == R.id.nav_ajuda) {
            Bundle b = new Bundle();
            b.putBoolean("esAjuda", true);
            frag = new Informacio();
            frag.setArguments(b);
            setTitle(R.string.ajuda);
            elementSeleccionat = true;
        } else if (id == R.id.nav_sobre) {
            Bundle b = new Bundle();
            b.putBoolean("esAjuda", false);
            frag = new Informacio();
            frag.setArguments(b);
            setTitle(R.string.sobre);
            elementSeleccionat = true;
        }

        if (elementSeleccionat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenidor, frag).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
