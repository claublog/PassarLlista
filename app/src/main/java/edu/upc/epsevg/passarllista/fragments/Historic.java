package edu.upc.epsevg.passarllista.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.activities.HistoricAssistencies;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class Historic extends android.support.v4.app.Fragment {

    private ListView lview;
    private DbHelper db;
    private Cursor totsSessions;
    private CursorAdapter cursorAdapter;


    public Historic() {
        // Constructor obligat per la creacio de fragments
    }

    private void inicializacio() {
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floating_afegir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putBoolean("esGestio", false);
                GestioAssignatures ga = new GestioAssignatures();
                ga.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenidor, ga);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        db = new DbHelper(getActivity().getApplicationContext());
        totsSessions = db.getTotsSessions();
        lview = (ListView) getView().findViewById(R.id.listView);

        // Missatge indicant que la llista de sessions es buida
        TextView tv = (TextView) getView().findViewById(R.id.buit);
        tv.setText(R.string.buit_llistes);
        lview.setEmptyView(tv);

        cursorAdapter = new CursorAdapter(getContext(), totsSessions, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_historic, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView id_sessio = (TextView) view.findViewById(R.id.view_id);
                TextView nom_assignatura = (TextView) view.findViewById(R.id.view_assig);
                TextView nom_grup = (TextView) view.findViewById(R.id.view_grup);
                TextView data = (TextView) view.findViewById(R.id.view_data);

                id_sessio.setText(getCursor().getString(0));
                data.setText(getCursor().getString(1).replace(" ", "\n"));

                Cursor c = db.getGrupById(getCursor().getString(2));
                c.moveToNext();
                nom_grup.setText(c.getString(1));
                String id_assig = c.getString(2);
                c = db.getAssignaturaById(id_assig);
                c.moveToNext();
                nom_assignatura.setText(c.getString(1));

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
                String data = ((TextView) v.findViewById(R.id.view_data)).getText().toString();

                ad.setMessage(getString(R.string.alert_eliminar_llista) + data.replace("\n", " ") + " ?");
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteSessio(ids);
                        totsSessions.requery();
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
                return true;
            }
        });
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView id_sessio = (TextView) view.findViewById(R.id.view_id);
                TextView nom_grup = (TextView) view.findViewById(R.id.view_grup);
                Intent intent;
                intent = new Intent(getActivity(), HistoricAssistencies.class);
                intent.putExtra("id_sessio", id_sessio.getText());
                intent.putExtra("nom_grup", nom_grup.getText());
                startActivity(intent);
            }
        });
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
