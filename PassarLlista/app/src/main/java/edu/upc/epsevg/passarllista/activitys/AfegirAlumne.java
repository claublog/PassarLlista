package edu.upc.epsevg.passarllista.activitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;
import edu.upc.epsevg.passarllista.classes.Alumne;

public class AfegirAlumne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_alumne);

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle("Afegir alumne");

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
        // handle arrow click here
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


    private void afegeixAlumne(){

        final EditText nomAlumne = (EditText) findViewById(R.id.editText_nom_assignatura);
        final EditText dniAlumne = (EditText) findViewById(R.id.editText_dni);
        // añade el alumno
        String nom = nomAlumne.getText().toString();
        String dni = dniAlumne.getText().toString();
        if (!(nom.equals("") ||  dni.equals(""))){
            DbHelper db  = new DbHelper(getApplicationContext());
            Alumne alum = new Alumne(nomAlumne.getText().toString(), null, dniAlumne.getText().toString());
            db.guardaAlumne(alum);
            //tanca el activity
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(AfegirAlumne.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            //actuamos
            if (nom.equals("")){
                alertDialog.setMessage("El nom no es valid");
            } else {
                alertDialog.setMessage("El DNI no es valid");
            }
            alertDialog.show();
        }
    }

}
