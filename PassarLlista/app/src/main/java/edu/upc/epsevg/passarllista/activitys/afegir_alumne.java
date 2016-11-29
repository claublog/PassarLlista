package edu.upc.epsevg.passarllista.activitys;

import android.content.ContentValues;
import android.content.Intent;
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
        final EditText nomAlumne = (EditText) findViewById(R.id.editText_nom_alumne);
        final EditText dniAlumne = (EditText) findViewById(R.id.editText_dni);
        setTitle("Afegir alumne");

        Button addAlumne = (Button) findViewById(R.id.button_add_alumno);
        addAlumne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // añade el alumno
                AlumneDbHelper db  = new AlumneDbHelper(getApplicationContext());
                Alumne alum = new Alumne(nomAlumne.getText().toString(), null, dniAlumne.getText().toString());
                db.guardaAlumne(alum);
                //tanca el activity
                finish();
            }
        });

    }

}
