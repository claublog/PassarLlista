package edu.upc.epsevg.passarllista.activitys;

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

import java.util.ArrayList;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;
import edu.upc.epsevg.passarllista.classes.Assignatura;
import edu.upc.epsevg.passarllista.classes.Grup;

public class AfegirAssignatura extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> llista_grups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_assignatura);

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle(getString(R.string.titol_afegir_assignatura));

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        final Button afegir_grup = (Button) findViewById(R.id.button_add_grup);
        final EditText edit_grup = (EditText) findViewById(R.id.editText_grup);

        afegir_grup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText nomGrup = (EditText) findViewById(R.id.editText_grup);

                // añade el alumno
                String nom = nomGrup.getText().toString();
                if (!(nom.equals("") )){
                    llista_grups.add(nom);
                    edit_grup.setText("");

                }
            }
        });


        edit_grup.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    afegir_grup.setEnabled(false);
                } else {
                    afegir_grup.setEnabled(true);
                }


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        llista_grups = new ArrayList<>();
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
        // handle arrow click here
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


    private void afegeixAssignatura(){

        final EditText nomAssignatura = (EditText) findViewById(R.id.editText_nom_assignatura);

        int tamany_llista = llista_grups.size();
        // añade el alumno
        String nom = nomAssignatura.getText().toString();
        if (!nom.equals("") && (tamany_llista > 0)){
            DbHelper db  = new DbHelper(getApplicationContext());
            //add assignatura
            Assignatura assig = new Assignatura(null, nomAssignatura.getText().toString());
            long id_assig = db.guardaAssignatura(assig);
            // add grups
            for (String nom_grup : llista_grups) {
                Grup grup = new Grup(null, nom_grup, id_assig);
                db.guardaGrup(grup);
            }
            //tanca el activity
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
            if (nom.equals("")){
                alertDialog.setMessage(getString(R.string.alert_nom_assig_invalid));
            } else if (tamany_llista < 1) {
                alertDialog.setMessage(getString(R.string.alert_minim_grups));
            }
            alertDialog.show();
        }
    }

}
