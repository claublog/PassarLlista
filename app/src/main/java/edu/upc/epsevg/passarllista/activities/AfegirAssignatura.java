package edu.upc.epsevg.passarllista.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Assignatura;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Grup;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class AfegirAssignatura extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> llista_grups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_assignatura);

        llista_grups = new ArrayList<>();
        // Restaurem els grups del bundle en cas que n'hi hagin
        String[] grups = null;
        if (savedInstanceState != null) {
            grups = savedInstanceState.getStringArray("llista_grups");
            for (String grup : grups) {
                llista_grups.add(grup);
            }
        }

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle(getString(R.string.titol_afegir_assignatura));

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Button afegir_grup = (Button) findViewById(R.id.button_add_grup);
        afegir_grup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nomGrup = (EditText) findViewById(R.id.editText_grup);

                // añade el alumno
                String nom = nomGrup.getText().toString();
                if (!(nom.equals(""))) {
                    llista_grups.add(nom);
                    nomGrup.setText("");

                }
            }
        });

        EditText edit_grup = (EditText) findViewById(R.id.editText_grup);
        edit_grup.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // metode obligatori de sobrecarregar
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button afegir_grup = (Button) findViewById(R.id.button_add_grup);
                if (s.toString().trim().length() == 0) {
                    afegir_grup.setEnabled(false);
                } else {
                    afegir_grup.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // metode obligatori de sobrecarregar
            }

        });

        // Utilitzem la plantilla basica proporcionada per android per fer ArrayAdapters
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, llista_grups);
        ListView listView = (ListView) findViewById(R.id.listViewGrups);
        listView.setAdapter(itemsAdapter);

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
                afegeixAssignatura();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        // Guardem la llista de grups creada
        String[] grups = new String[llista_grups.size()];
        for (int i = 0; i < llista_grups.size(); i++) {
            grups[i] = llista_grups.get(i);
        }
        savedState.putStringArray("llista_grups", grups);
    }

    private void afegeixAssignatura() {

        final EditText nomAssignatura = (EditText) findViewById(R.id.editText_nom_assignatura);

        int tamany_llista = llista_grups.size();
        String nom_assig = nomAssignatura.getText().toString();
        if (!nom_assig.equals("") && (tamany_llista > 0)) {
            DbHelper db = new DbHelper(getApplicationContext());
            ContentValues values = new ContentValues();
            values.put(Contracte_Assignatura.EntradaAssignatura.NOM, nom_assig);
            long id_assig = db.guardaAssignatura(values); // introdueix  l'assignatura a la base de dades
            // introdueix tots els grups a la base de dades
            for (String nom_grup : llista_grups) {
                values = new ContentValues();
                values.put(Contracte_Grup.EntradaGrup.NOM, nom_grup);
                values.put(Contracte_Grup.EntradaGrup.ID_ASSIGNATURA, id_assig);
                db.guardaGrup(values);
            }
            //tanca la activity
            Toast.makeText(getApplicationContext(), R.string.toast_assignatura_creada, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(AfegirAssignatura.this).create();
            alertDialog.setTitle(getString(R.string.error));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            //actuamos
            if (nom_assig.equals("")) {
                alertDialog.setMessage(getString(R.string.alert_nom_assig_invalid));
            } else if (tamany_llista < 1) {
                alertDialog.setMessage(getString(R.string.alert_minim_grups));
            }
            alertDialog.show();
        }
    }

}
