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
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.activitys.AfegirAssignatura;
import edu.upc.epsevg.passarllista.activitys.AfegirGrup;
import edu.upc.epsevg.passarllista.activitys.Matriculats;
import edu.upc.epsevg.passarllista.activitys.PassarLlista;
import edu.upc.epsevg.passarllista.base_de_dades.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Historic.OnFragmentInteractionListener} interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class GestioAssignatures extends android.support.v4.app.Fragment {
    private ListView lview;
    private DbHelper db;
    private Cursor totesAssignatures;
    private CursorAdapter cursorAdapter;
    private boolean esGestio;


    public GestioAssignatures() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        esGestio = getArguments().getBoolean("esGestio");

    }

    private void inicialitzacio() {
        db = new DbHelper(getActivity().getApplicationContext());

        totesAssignatures = db.getTotsAssignatures();
        int aux = totesAssignatures.getCount();
        if (totesAssignatures.getCount() < 1) {
            //preparamos el alert
            AlertDialog alertDialog = new AlertDialog.Builder(getView().getContext()).create();
            alertDialog.setTitle(getString(R.string.titol_informacio));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_add),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(getActivity(), AfegirAssignatura.class);
                            startActivity(intent);

                        }
                    });

            //actuamos

            alertDialog.setMessage(getString(R.string.alert_grups_buit));
            alertDialog.show();

        } else {

            if (esGestio) {
                FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floating_afegir);
                fab.setVisibility(View.VISIBLE);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AfegirAssignatura.class);
                        startActivity(intent);
                    }
                });
            }


            cursorAdapter = new CursorAdapter(getContext(), totesAssignatures, 0) {
                // The newView method is used to inflate a new view and return it,
                // you don't bind any data to the view at this point.
                @Override
                public View newView(Context context, Cursor cursor, ViewGroup parent) {
                    return LayoutInflater.from(context).inflate(R.layout.item_llista, parent, false);
                }

                // The bindView method is used to bind all data to a given view
                // such as setting the text on a TextView.
                @Override
                public void bindView(View view, Context context, Cursor cursor) {
                    // Find fields to populate in inflated template
                    TextView id_assig = (TextView) view.findViewById(R.id.view_id);
                    TextView nom_assig = (TextView) view.findViewById(R.id.view_nom);

                    // Populate fields with extracted properties
                    id_assig.setText(getCursor().getString(0));
                    nom_assig.setText(getCursor().getString(1));
                }
            };
            lview = (ListView) getView().findViewById(R.id.listView);
            lview.setAdapter(cursorAdapter);

            lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView id_assig = (TextView) view.findViewById(R.id.view_id);
                    inicialitzaGrups(id_assig.getText().toString());
                }
            });
            lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView arg0, View v,
                                               int position, long arg3) {
                    // TODO Auto-generated method stub
                    TextView id = (TextView) v.findViewById(R.id.view_id);
                    final int ids = Integer.parseInt(id.getText().toString());
                    AlertDialog.Builder ad = new AlertDialog.Builder(getView().getContext());
                    //ad.setTitle("Notice");
                    String nom_assignatura = ((TextView) v.findViewById(R.id.view_nom)).getText().toString();


                    ad.setMessage(getString(R.string.alert_eliminar) + nom_assignatura + "?");
                    ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Delete of record from Database and List view.
                            db.deleteAssignatura(ids);
                            totesAssignatures.requery();
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

        }
    }

    private void inicialitzaGrups(final String id_assig) {

        if (esGestio) {
            FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floating_afegir);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AfegirGrup.class);
                    intent.putExtra("id_assig", id_assig);
                    startActivity(intent);
                }
            });
        }

        final Cursor totsGrups = db.getGrupsAssignatura(id_assig);
        cursorAdapter = new CursorAdapter(getContext(), totsGrups, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_llista, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView id_grup = (TextView) view.findViewById(R.id.view_id);
                TextView nom_grup = (TextView) view.findViewById(R.id.view_nom);

                // Populate fields with extracted properties
                id_grup.setText(getCursor().getString(0));
                nom_grup.setText(getCursor().getString(1));
            }
        };
        getActivity().setTitle(getString(R.string.titol_selecciona_grup));
        lview = (ListView) getView().findViewById(R.id.listView);
        lview.setAdapter(cursorAdapter);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView id_grup = (TextView) view.findViewById(R.id.view_id);
                Intent intent;
                if (esGestio) {
                    intent = new Intent(getActivity(), Matriculats.class);
                } else {
                    intent = new Intent(getActivity(), PassarLlista.class);
                }
                intent.putExtra("id_grup", id_grup.getText());
                startActivity(intent);
            }
        });
        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View v,
                                           int position, long arg3) {
                // TODO Auto-generated method stub
                TextView id = (TextView) v.findViewById(R.id.view_id);
                final int ids = Integer.parseInt(id.getText().toString());
                AlertDialog.Builder ad = new AlertDialog.Builder(getView().getContext());
                //ad.setTitle("Notice");
                String nom_grup = ((TextView) v.findViewById(R.id.view_nom)).getText().toString();

                ad.setMessage(getString(R.string.alert_eliminar) + nom_grup + "?");
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete of record from Database and List view.
                        db.deleteGrup(ids);
                        totsGrups.requery();
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


        cursorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inicialitzacio();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.titol_selecciona_assignatura));
        inicialitzacio();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        esGestio = getArguments().getBoolean("esGestio");
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
