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
import edu.upc.epsevg.passarllista.classes.Grup;

public class AfegirGrup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_grup);

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle("Afegir nou grup");

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
                afegeixGrup();
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void afegeixGrup() {

        String id_assig = getIntent().getStringExtra("id_assig");


        EditText nomGrup = (EditText) findViewById(R.id.editText_nom_grup);
        // añade el grupo
        String nom = nomGrup.getText().toString();

        if (!nom.equals("")) {
            DbHelper db = new DbHelper(getApplicationContext());
            Grup grup = new Grup(null, nomGrup.getText().toString(), Long.parseLong(id_assig));
            db.guardaGrup(grup);
            //tanca el activity
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(AfegirGrup.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            alertDialog.setMessage("El nom del grup no és vàlid");
            alertDialog.show();
        }
    }

}
