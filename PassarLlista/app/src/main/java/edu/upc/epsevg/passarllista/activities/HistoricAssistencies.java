package edu.upc.epsevg.passarllista.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class HistoricAssistencies extends AppCompatActivity {

    private DbHelper db;
    private CursorAdapter cursorAdapter;
    private ListView lview;
    private String id_sessio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle(getString(R.string.titol_llista_assist) + " (" + getIntent().getStringExtra("nom_grup") + ")");

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DbHelper(getApplicationContext());
        id_sessio = getIntent().getStringExtra("id_sessio");
        Cursor c = db.getAssistenciesSessio(id_sessio);
        cursorAdapter = new CursorAdapter(getApplicationContext(), c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_passar, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView assistencia = (TextView) view.findViewById(R.id.assistencia);
                TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                TextView dni = (TextView) view.findViewById(R.id.view_dni);

                switch (getCursor().getInt(1)) {
                    case 0:
                        assistencia.setText(R.string.assistencia);
                        assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.assistencia));
                        break;
                    case 1:
                        assistencia.setText(R.string.ausent);
                        assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ausent));
                        break;
                    case 2:
                        assistencia.setText(R.string.retard);
                        assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.retard));
                }
                nom_alumne.setText(getCursor().getString(2));
                dni.setText(getCursor().getString(3));
            }
        };
        lview = (ListView) findViewById(R.id.listViewAlumnes);
        lview.setAdapter(cursorAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
