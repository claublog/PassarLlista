package edu.upc.epsevg.passarllista.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.activitys.AfegirAlumne;
import edu.upc.epsevg.passarllista.activitys.AssistenciesHistoric;
import edu.upc.epsevg.passarllista.activitys.Matriculats;
import edu.upc.epsevg.passarllista.activitys.PassarLlista;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Historic.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class Historic extends android.support.v4.app.Fragment {

    private ListView lview;
    private DbHelper db;
    private Cursor totsSessions;
    private CursorAdapter cursorAdapter;


    public Historic() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void inicializacio() {
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floating_afegir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle b = new Bundle();
                b.putBoolean("esGestio", false);
                GestioGrups gg = new GestioGrups();
                gg.setArguments(b);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Contenedor, gg);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        db = new DbHelper(getActivity().getApplicationContext());
        totsSessions = db.getTotsSessions();
        cursorAdapter = new CursorAdapter(getContext(), totsSessions, 0) {
            // The newView method is used to inflate a new view and return it,
            // you don't bind any data to the view at this point.
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista_historic, parent, false);
            }

            // The bindView method is used to bind all data to a given view
            // such as setting the text on a TextView.
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView id_sessio = (TextView) view.findViewById(R.id.view_id);
                TextView nom_assignatura = (TextView) view.findViewById(R.id.view_assig);
                TextView nom_grup = (TextView) view.findViewById(R.id.view_grup);
                TextView data = (TextView) view.findViewById(R.id.view_data);

                // Populate fields with extracted properties
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
        lview = (ListView) getView().findViewById(R.id.listView);
        lview.setAdapter(cursorAdapter);
        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View v,
                                           int position, long arg3) {
                // TODO Auto-generated method stub
                TextView id = (TextView) v.findViewById(R.id.view_id);
                final int ids = Integer.parseInt(id.getText().toString());
                AlertDialog.Builder ad = new AlertDialog.Builder(getView().getContext());
                //ad.setTitle("Notice");
                String data = ((TextView) v.findViewById(R.id.view_data)).getText().toString();


                ad.setMessage("Estas segur d'eliminar la sessió del día " + data.replace("\n", " ") + " ?");
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete of record from Database and List view.
                        db.deleteSessio(ids);
                        totsSessions.requery();
                        cursorAdapter.notifyDataSetChanged();
                        lview.setAdapter(cursorAdapter);
                    }
                });
                ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
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
                intent = new Intent(getActivity(), AssistenciesHistoric.class);
                intent.putExtra("id_sessio", id_sessio.getText());
                intent.putExtra("nom_grup", nom_grup.getText());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inicializacio();
    }

    @Override
    public void onResume() {
        super.onResume();
        inicializacio();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //   rootView = inflater.inflate(R.layout.fragment_gestio, container);
        //  inicializacio();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestio, container, false);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
