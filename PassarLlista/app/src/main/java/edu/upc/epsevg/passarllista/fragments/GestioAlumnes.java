package edu.upc.epsevg.passarllista.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.activitys.AfegirAlumne;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

public class GestioAlumnes extends android.support.v4.app.Fragment {
    //private ArrayList<Alumne> list_alumnes;
    private ListView lview;
    private DbHelper db;
    private Cursor totsAlumnes;
    private CursorAdapter cursorAdapter;


    public GestioAlumnes() {
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
                Intent intent = new Intent(getActivity(), AfegirAlumne.class);
                startActivity(intent);
            }
        });
        db = new DbHelper(getActivity().getApplicationContext());
        totsAlumnes = db.getTotsAlumnes();
        lview = (ListView) getView().findViewById(R.id.listView);

        if (totsAlumnes.getCount() < 1) {
            ViewStub stub = (ViewStub) getView().findViewById(R.id.empty);
            View inflated = stub.inflate();
            TextView tv = (TextView) inflated.findViewById(R.id.view_missatge);
            tv.setText(R.string.buit_alumnes);
            ImageView iv = (ImageView)inflated.findViewById(R.id.view_icona);
            iv.setImageResource(R.drawable.icon_gestio_alumnes);
            lview.setEmptyView(inflated);
        }

        if (totsAlumnes.getCount() < 1) {
            //preparamos el alert
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

            //actuamos

            alertDialog.setMessage(getString(R.string.alert_alumnes_buit));
            alertDialog.show();

        } else {
            cursorAdapter = new CursorAdapter(getContext(), totsAlumnes, 0) {
                // The newView method is used to inflate a new view and return it,
                // you don't bind any data to the view at this point.
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return LayoutInflater.from(context).inflate(R.layout.item_llista_alumne, parent, false);
                }

                // The bindView method is used to bind all data to a given view
                // such as setting the text on a TextView.
                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    // Find fields to populate in inflated template
                    TextView id_alumne = (TextView) view.findViewById(R.id.view_id);
                    TextView nom_alumne = (TextView) view.findViewById(R.id.view_nom);
                    TextView dni = (TextView) view.findViewById(R.id.view_dni);

                    // Populate fields with extracted properties
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
                    // TODO Auto-generated method stub
                    TextView id = (TextView) v.findViewById(R.id.view_id);
                    final int ids = Integer.parseInt(id.getText().toString());
                    AlertDialog.Builder ad = new AlertDialog.Builder(getView().getContext());
                    //ad.setTitle("Notice");
                    String nom_alumne = ((TextView) v.findViewById(R.id.view_nom)).getText().toString();


                    ad.setMessage(getString(R.string.alert_eliminar) + nom_alumne + " ?");
                    ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Delete of record from Database and List view.
                            db.deleteAlumne(ids);
                            totsAlumnes.requery();
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
                    return false;
                }
            });


        }
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
