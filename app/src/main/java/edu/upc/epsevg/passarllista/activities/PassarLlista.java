package edu.upc.epsevg.passarllista.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Assistencia;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Sessio;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class PassarLlista extends AppCompatActivity {

    private DbHelper db;
    private CursorAdapter cursorAdapter;
    private ListView lview;
    private TreeSet<String> alumnes_grup;
    private String id_grup;
    private int[] assistencies;
    private boolean assistEsNull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        if (savedInstanceState != null) {
            assistencies = savedInstanceState.getIntArray("assistencies");
            assistEsNull = false;
        } else assistEsNull = true;
    }

    private void inicialitzacio() {
        setTitle(R.string.titol_llista_assist);

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DbHelper(getApplicationContext());
        id_grup = getIntent().getStringExtra("id_grup");
        Cursor c = db.getAlumnesGrup(id_grup);
        poblarAlumnesGrup(c, id_grup);
        if (assistEsNull) assistencies = new int[c.getCount()];
        cursorAdapter = new CursorAdapter(getApplicationContext(), c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_passar, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                TextView dni = (TextView) view.findViewById(R.id.view_dni);
                TextView assistencia = (TextView) view.findViewById(R.id.assistencia);

                id_alumne.setText(getCursor().getString(0));
                nom_alumne.setText(getCursor().getString(1));
                dni.setText(getCursor().getString(2));

                if (!assistEsNull) {
                    switch (assistencies[cursor.getPosition()]) {
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
                } else {
                    assistencia.setText(R.string.assistencia);
                    assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.assistencia));
                }
            }
        };

        lview = (ListView) findViewById(R.id.listViewAlumnes);

        // Missatge indicant que la llista d'alumnes matriculats es buida
        TextView tv = (TextView) findViewById(R.id.buit);
        tv.setText(R.string.buit_matriculats);
        lview.setEmptyView(tv);

        lview.setAdapter(cursorAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView assistencia = (TextView) view.findViewById(R.id.assistencia);
                if (assistencia.getText().equals(getString(R.string.assistencia))) {
                    assistencia.setText(R.string.ausent);
                    assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ausent));
                    assistencies[position] = 1;
                } else if (assistencia.getText().equals(getString(R.string.ausent))) {
                    assistencia.setText(R.string.retard);
                    assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.retard));
                    assistencies[position] = 2;
                } else {
                    assistencia.setText(R.string.assistencia);
                    assistencia.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.assistencia));
                    assistencies[position] = 0;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        inicialitzacio();
        super.onResume();
    }

    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void poblarAlumnesGrup(final Cursor id_grup, final String idGrup) {
        alumnes_grup = new TreeSet<>();
        int nAlumnes = 0;
        while (id_grup.moveToNext()) {
            alumnes_grup.add(id_grup.getString(0));
            nAlumnes = nAlumnes + 1;
        }
        id_grup.close();
        if (nAlumnes == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(PassarLlista.this).create();
            alertDialog.setTitle(R.string.titol_informacio);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_matricular),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(PassarLlista.this, Matriculats.class);
                            intent.putExtra("id_grup", idGrup);
                            startActivity(intent);

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(android.R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.setMessage(getString(R.string.alert_grup_sense_alumne));

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
                if (lview.getCount() < 1) {
                    AlertDialog alertDialog = new AlertDialog.Builder(PassarLlista.this).create();
                    alertDialog.setTitle(getString(R.string.error));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.setMessage(getString(R.string.alert_sense_alumnes));
                    alertDialog.show();
                } else {
                    guardaLlistaAssitencia();
                    Toast.makeText(getApplicationContext(), R.string.toast_llista_assistencia, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);
        savedState.putIntArray("assistencies", assistencies);

    }

    private void guardaLlistaAssitencia() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues values_sessio = new ContentValues();
        values_sessio.put(Contracte_Sessio.EntradaSessio.DATA, dateFormat.format(date));
        values_sessio.put(Contracte_Sessio.EntradaSessio.ID_GRUP, id_grup);
        long id_sessio = db.guardaSessio(values_sessio);

        ContentValues values_assistencia;
        for (int i = 0; i < lview.getCount(); i++) {
            View v = getViewByPosition(i, lview);
            TextView id_view = (TextView) v.findViewById(R.id.view_id);
            TextView assistencia = (TextView) v.findViewById(R.id.assistencia);

            values_assistencia = new ContentValues();
            if (assistencia.getText().equals(getString(R.string.assistencia))) {
                values_assistencia.put(Contracte_Assistencia.EntradaAssistencia.TIPUS, 0);
            } else if (assistencia.getText().equals(getString(R.string.ausent))) {
                values_assistencia.put(Contracte_Assistencia.EntradaAssistencia.TIPUS, 1);
            } else {
                values_assistencia.put(Contracte_Assistencia.EntradaAssistencia.TIPUS, 2);
            }

            values_assistencia.put(Contracte_Assistencia.EntradaAssistencia.ID_ALUMNE, id_view.getText().toString());
            values_assistencia.put(Contracte_Assistencia.EntradaAssistencia.ID_SESSIO, id_sessio);
            db.guardaAssitencia(values_assistencia);
        }
    }
}
