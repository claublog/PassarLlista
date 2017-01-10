package edu.upc.epsevg.passarllista.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.activities.AfegirAlumne;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class GestioAlumnes extends android.support.v4.app.Fragment {
    private ListView lview;
    private DbHelper db;
    private Cursor totsAlumnes;
    private CursorAdapter cursorAdapter;

    public GestioAlumnes() {
        // Constructor obligat per la creacio de fragments
    }

    private void inicializacio() {
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floating_afegir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AfegirAlumne.class);
                startActivity(intent);
            }
        });
        db = new DbHelper(getActivity().getApplicationContext());
        totsAlumnes = db.getTotsAlumnes();
        lview = (ListView) getView().findViewById(R.id.listView);

        // Missatge indicant que la llista d'alumnes es buida
        TextView tv = (TextView) getView().findViewById(R.id.buit);
        tv.setText(R.string.buit_alumnes);
        lview.setEmptyView(tv);

        if (totsAlumnes.getCount() < 1) {
            AlertDialog alertDialog = new AlertDialog.Builder(getView().getContext()).create();
            alertDialog.setTitle(R.string.titol_informacio);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_add),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), AfegirAlumne.class);
                            startActivity(intent);

                        }
                    });
            alertDialog.setMessage(getString(R.string.alert_alumnes_buit));
            alertDialog.show();

        } else {
            cursorAdapter = new CursorAdapter(getContext(), totsAlumnes, 0) {
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return LayoutInflater.from(context).inflate(R.layout.item_llista_alumne, parent, false);
                }

                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                    TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                    TextView dni = (TextView) view.findViewById(R.id.view_dni);

                    id_alumne.setText(getCursor().getString(0));
                    nom_alumne.setText(getCursor().getString(1));
                    dni.setText(getCursor().getString(2));
                }
            };
            lview.setAdapter(cursorAdapter);
            lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView arg0, View v,
                                               int position, long arg3) {
                    TextView id = (TextView) v.findViewById(R.id.view_id);
                    final int ids = Integer.parseInt(id.getText().toString());
                    AlertDialog.Builder ad = new AlertDialog.Builder(getView().getContext());
                    String nom_alumne = ((TextView) v.findViewById(R.id.view_nom)).getText().toString();

                    ad.setMessage(getString(R.string.alert_eliminar) + nom_alumne + " ?");
                    ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteAlumne(ids);
                            totsAlumnes.requery();
                            cursorAdapter.notifyDataSetChanged();
                            lview.setAdapter(cursorAdapter);
                        }
                    });
                    ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                    return false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        inicializacio();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gestio, container, false);
    }
}
