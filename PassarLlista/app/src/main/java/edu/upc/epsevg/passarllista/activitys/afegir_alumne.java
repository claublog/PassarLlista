package edu.upc.epsevg.passarllista.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.upc.epsevg.passarllista.R;

public class afegir_alumne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_alumne);

        inicialitcacio();
    }

    private void inicialitcacio() {
        setTitle("Afegir alumne");
    }

}
