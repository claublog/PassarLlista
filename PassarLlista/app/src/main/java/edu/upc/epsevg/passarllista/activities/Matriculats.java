package edu.upc.epsevg.passarllista.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.TreeSet;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Matriculat;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class Matriculats extends AppCompatActivity {

    private DbHelper db;
    private CursorAdapter cursorAdapter;
    private ListView lview;
    private TreeSet<String> alumnes_grup_inicial;
    private TreeSet<String> alumnes_grup_canvis;
    private String id_grup;
    private boolean[] canvis;
    private boolean canvisEsNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        if (savedInstanceState != null) {
            canvis = savedInstanceState.getBooleanArray("canvis");
            canvisEsNull = false;
        } else canvisEsNull = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle(getString(R.string.titol_matriculats));

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DbHelper(getApplicationContext());
        id_grup = getIntent().getStringExtra("id_grup");
        Cursor cursor_alumnes = db.getTotsAlumnes(); //recupera de la base de dades tots els alumnes que pertanyen al grup id_grup
        poblarAlumnesGrup(db.getAlumnesGrup(id_grup), cursor_alumnes.getCount());
        if (canvisEsNull) canvis = new boolean[cursor_alumnes.getCount()];
        cursorAdapter = new CursorAdapter(getApplicationContext(), cursor_alumnes, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_checkbox, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                TextView dni = (TextView) view.findViewById(R.id.view_dni);
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);

                id_alumne.setText(getCursor().getString(0));
                nom_alumne.setText(getCursor().getString(1));
                dni.setText(getCursor().getString(2));
                if (canvisEsNull) {
                    if (alumnes_grup_inicial.contains(getCursor().getString(0)))
                        pertany.setChecked(true);
                    else pertany.setChecked(false);
                    canvis[cursor.getPosition()] = pertany.isChecked();
                } else {
                    pertany.setChecked(canvis[cursor.getPosition()]);
                }
            }
        };

        lview = (ListView) findViewById(R.id.listViewAlumnes);
        lview.setAdapter(cursorAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);
                pertany.setChecked(!pertany.isChecked());
                canvis[position] = pertany.isChecked();
            }
        });
    }


    private void poblarAlumnesGrup(Cursor cursor_alumnes, int total_alumnes) {
        alumnes_grup_inicial = new TreeSet<>();
        alumnes_grup_canvis = new TreeSet<>();
        while (cursor_alumnes.moveToNext()) { //inicialment ambdues llistes son iguals
            alumnes_grup_inicial.add(cursor_alumnes.getString(0));
            alumnes_grup_canvis.add(cursor_alumnes.getString(0));
        }
        if (total_alumnes == 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(Matriculats.this).create();
            alertDialog.setTitle(getString(R.string.titol_informacio));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_add),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(Matriculats.this, AfegirAlumne.class);
                            startActivity(intent);

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.setMessage(getString(R.string.alert_afegir_alumne));

            alertDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_afegir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_done:
                guardaCanvisGrup();
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBooleanArray("canvis", canvis);

    }

    private View getViewDeLaPosicio(int posicio) {
        int posicioPrimerItemLlista = lview.getFirstVisiblePosition();
        int posicioUltimItemLlista = posicioPrimerItemLlista + lview.getChildCount() - 1;

        if (posicio < posicioPrimerItemLlista || posicio > posicioUltimItemLlista) {
            return lview.getAdapter().getView(posicio, lview.getChildAt(posicio), lview);
        } else {
            int childIndex = posicio - posicioPrimerItemLlista;
            return lview.getChildAt(childIndex);
        }
    }

    private void guardaCanvisGrup() {
        alumnes_grup_canvis = new TreeSet<>();

        for (int i = 0; i < lview.getCount(); i++) {
            View v = getViewDeLaPosicio(i);
            TextView id_view = (TextView) v.findViewById(R.id.view_id);
            CheckBox pertany = (CheckBox) v.findViewById(R.id.checkbox_grup);
            if (pertany.isChecked()) alumnes_grup_canvis.add(id_view.getText().toString());

        }
        for (String id : alumnes_grup_inicial) {
            if (alumnes_grup_canvis.contains(id)) {
                alumnes_grup_canvis.remove(id);
            } else {
                db.deleteMatricula(id, id_grup);
            }
        }

        for (String id : alumnes_grup_canvis) {
            ContentValues values = new ContentValues();
            values.put(Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE, id);
            values.put(Contracte_Matriculat.EntradaMatriculat.ID_GRUP, id_grup);
            db.guardaMatricula(values);
        }
        finish();

    }

}
