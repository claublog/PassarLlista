package edu.upc.epsevg.passarllista.activitys;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.AlumneDbHelper;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;
import edu.upc.epsevg.passarllista.classes.Alumne;

public class afegir_alumne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_alumne);

        inicialitcacio();
    }

    private void inicialitcacio() {
        setTitle("Afegir alumne");

        Button addAlumne = (Button) findViewById(R.id.button_add_alumno);
        addAlumne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afegeixAlumne();
            }
        });

    }

    private void afegeixAlumne(){

        final EditText nomAlumne = (EditText) findViewById(R.id.editText_nom_alumne);
        final EditText dniAlumne = (EditText) findViewById(R.id.editText_dni);
        // añade el alumno
        String nom = nomAlumne.getText().toString();
        String dni = dniAlumne.getText().toString();
        if (!(nom.equals("") ||  dni.equals(""))){
            AlumneDbHelper db  = new AlumneDbHelper(getApplicationContext());
            Alumne alum = new Alumne(nomAlumne.getText().toString(), null, dniAlumne.getText().toString());
            db.guardaAlumne(alum);
            //tanca el activity
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(afegir_alumne.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            //actuamos
            if (nom.equals("")){
                alertDialog.setMessage("El nom no es valid");
                alertDialog.show();
            } else {
                alertDialog.setMessage("El DNI no es valid");
                alertDialog.show();
            }
        }
    }

}
