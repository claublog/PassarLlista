package edu.upc.epsevg.passarllista.activitys;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TreeSet;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Matriculat;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class Matriculats extends AppCompatActivity {

    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> llista_alumnes;
    private DbHelper db;
    private CursorAdapter cursorAdapter;
    private ListView lview;
    private TreeSet<String> alumnes_grup_inicial;
    private TreeSet<String> alumnes_grup_canvis;
    private String id_grup;

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
        id_grup = getIntent().getStringExtra("id_grup");
        poblarAlumnesGrup(db.getAlumnesGrup(id_grup));
        Cursor c = db.getTotsAlumnes();

        cursorAdapter = new CursorAdapter(getApplicationContext(), c, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_checkbox, parent, false);

            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                final TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                TextView dni = (TextView) view.findViewById(R.id.view_dni);
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);


                // Populate fields with extracted properties
                id_alumne.setText(getCursor().getString(0));
                nom_alumne.setText(getCursor().getString(1));
                dni.setText(getCursor().getString(2));
                if (alumnes_grup_inicial.contains(getCursor().getString(0)))
                    pertany.setChecked(true);
                else pertany.setChecked(false);
            }
        };

        lview = (ListView) findViewById(R.id.listViewAlumnes);
        lview.setAdapter(cursorAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox pertany = (CheckBox) view.findViewById(R.id.checkbox_grup);
                TextView id_view = (TextView) view.findViewById(R.id.view_id);
                pertany.setChecked(!pertany.isChecked());
            }
        });
    }


    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private void poblarAlumnesGrup(Cursor id_grup) {
        alumnes_grup_inicial = new TreeSet<>();
        alumnes_grup_canvis = new TreeSet<>();
        while (id_grup.moveToNext()) {
            alumnes_grup_inicial.add(id_grup.getString(0));
            alumnes_grup_canvis.add(id_grup.getString(0));
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
                guardaCanvisGrup();
                break;
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void guardaCanvisGrup() {
        alumnes_grup_canvis = new TreeSet<>();

        for ( int i = 0 ; i < lview.getCount() ; i++){
            View v = getViewByPosition(i,lview);
            TextView id_view = (TextView) v.findViewById(R.id.view_id);
            CheckBox pertany = (CheckBox) v.findViewById(R.id.checkbox_grup);
            if (pertany.isChecked()) alumnes_grup_canvis.add(id_view.getText().toString());

        }
        for (String id : alumnes_grup_inicial) {
            if (alumnes_grup_canvis.contains(id)) {
                alumnes_grup_canvis.remove(id);
            } else {
                db.deleteMatricula(id, id_grup);
            }
        }

        for (String id : alumnes_grup_canvis) {
            ContentValues values = new ContentValues();
            values.put(Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE, id);
            values.put(Contracte_Matriculat.EntradaMatriculat.ID_GRUP, id_grup);
            db.guardaMatricula(values);
        }
        finish();

    }

}