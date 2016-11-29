package edu.upc.epsevg.passarllista.activitys;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.AlumneDbHelper;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;


public class prova_alumne extends AppCompatActivity {

    AlumneDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_alumne);

        db = new AlumneDbHelper(getApplicationContext());
        Cursor c = db.getTotsAlumnes();

        String str = "";
        while(c.moveToNext()){
            str += c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne.NOM)) + " ";
            str += c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne.DNI)) + " ";
            str += c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne._ID)) + " ";

        }
        //resultat.getInt(1) + ", nom = " + resultat.getString(2) + ", dni = " + resultat.getString(3)
        Toast.makeText(prova_alumne.this, str, Toast.LENGTH_LONG).show();
    }
}
