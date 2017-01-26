package edu.upc.epsevg.passarllista.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class AfegirAlumne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_alumne);

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle(getString(R.string.titol_afegir_alumne));

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
                afegeixAlumne();
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void afegeixAlumne() {

        final EditText nomAlumne = (EditText) findViewById(R.id.editText_nom_assignatura);
        final EditText dniAlumne = (EditText) findViewById(R.id.editText_dni);
        // añade el alumno
        String nom = nomAlumne.getText().toString();
        String dni = dniAlumne.getText().toString();
        if (!(nom.equals("") || dni.equals(""))) {
            DbHelper db = new DbHelper(getApplicationContext());
            ContentValues values = new ContentValues();
            values.put(Contracte_Alumne.EntradaAlumne.NOM, nom);
            values.put(Contracte_Alumne.EntradaAlumne.DNI, dni);
            db.guardaAlumne(values);
            //tanca la activity
            Toast.makeText(getApplicationContext(), R.string.toast_alumne_creat, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(AfegirAlumne.this).create();
            alertDialog.setTitle(getString(R.string.error));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            //actuamos
            if (nom.equals("")) {
                alertDialog.setMessage(getString(R.string.alert_nom_invalid));
            } else {
                alertDialog.setMessage(getString(R.string.alert_dni_invalid));
            }
            alertDialog.show();
        }
    }

}
