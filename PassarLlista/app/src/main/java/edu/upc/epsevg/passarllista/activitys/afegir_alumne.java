package edu.upc.epsevg.passarllista.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button addAlumne = (Button) findViewById(R.id.button_add_alumno);
        addAlumne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
