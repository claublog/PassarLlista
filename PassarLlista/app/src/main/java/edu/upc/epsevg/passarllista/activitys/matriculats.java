package edu.upc.epsevg.passarllista.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;
import edu.upc.epsevg.passarllista.classes.Assignatura;
import edu.upc.epsevg.passarllista.classes.Grup;

public class matriculats extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> llista_alumnes;
    private DbHelper db;
    private CursorAdapter cursorAdapter;
    private ListView lview;
    private TreeSet<String> alumnes_grup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);

        inicialitzacio();
    }

    private void inicialitzacio() {
        setTitle("Editar alumnes");

        //boto enrere
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        db = new DbHelper(getApplicationContext());
        poblarAlumnesGrup(db.getAlumnesGrup(getIntent().getStringExtra("id_grup")));
        Cursor c = db.getTotsAlumnes();

        cursorAdapter = new CursorAdapter(getApplicationContext(), c, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_checkbox, parent, false);

            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                TextView dni = (TextView) view.findViewById(R.id.view_dni);
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);


                // Populate fields with extracted properties
                id_alumne.setText(getCursor().getString(0));
                nom_alumne.setText(getCursor().getString(1));
                dni.setText(getCursor().getString(2));
                if (alumnes_grup.contains(getCursor().getString(0))) pertany.setChecked(true);
                else pertany.setChecked(false);
            }
        };

        lview = (ListView) findViewById(R.id.listViewAlumnes);
        lview.setAdapter(cursorAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);
                if (pertany.isChecked()) pertany.setChecked(false);
                else pertany.setChecked(true);
            }
        });
    }

    private void poblarAlumnesGrup(Cursor id_grup) {
        alumnes_grup = new TreeSet<>();
        while (id_grup.moveToNext()) {
            alumnes_grup.add(id_grup.getString(0));
        }
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

        int tamany_llista = llista_alumnes.size();
        // añade el alumno
        String nom = nomAssignatura.getText().toString();
        if (!nom.equals("") && (tamany_llista > 0)){
            DbHelper db  = new DbHelper(getApplicationContext());
            //add assignatura
            Assignatura assig = new Assignatura(null, nomAssignatura.getText().toString());
            long id_assig = db.guardaAssignatura(assig);
            // add grups
            for (String nom_grup : llista_alumnes) {
                Grup grup = new Grup(null, nom_grup, id_assig);
                db.guardaGrup(grup);
            }
            //tanca el activity
            finish();
        } else {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(matriculats.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            //actuamos
            if (nom.equals("")){
                alertDialog.setMessage("El nom de la assignatura no es valid");
            } else if (tamany_llista < 1) {
                alertDialog.setMessage("Tens que afegir un grup com a minim per a una assignatura");
            }
            alertDialog.show();
        }
    }

}
