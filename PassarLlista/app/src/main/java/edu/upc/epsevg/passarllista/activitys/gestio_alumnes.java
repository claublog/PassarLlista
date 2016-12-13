package edu.upc.epsevg.passarllista.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import edu.upc.epsevg.passarllista.R;
import edu.upc.epsevg.passarllista.base_de_dades.AlumneDbHelper;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;
import edu.upc.epsevg.passarllista.classes.Alumne;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link historic.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link historic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gestio_alumnes extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Alumne> list_alumnes;
    private ListView lview;
    private AlumneDbHelper db;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public gestio_alumnes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historic.
     */
    // TODO: Rename and change types and number of parameters
    public static gestio_alumnes newInstance(String param1, String param2) {
        gestio_alumnes fragment = new gestio_alumnes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //inicializacion();
    }

    private void inicializacion() {
        poblarAlumnes();
        FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.floting_afellir_alumnes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), afegir_alumne.class);
                startActivity(intent);
            }
        });

        CursorAdapter a  = new CursorAdapter(getContext(), db.getTotsAlumnes(), 0) {
            // The newView method is used to inflate a new view and return it,
            // you don't bind any data to the view at this point.
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
            }

            // The bindView method is used to bind all data to a given view
            // such as setting the text on a TextView.
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Find fields to populate in inflated template
                TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
                TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);

                // Populate fields with extracted properties
                tvBody.setText("Test");
                tvPriority.setText(String.valueOf(3));
            }
        };

        lview = (ListView) getView().findViewById(R.id.listViewAlumnes);
        lview.setAdapter(a);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializacion();
    }

    private void poblarAlumnes() {
        db = new AlumneDbHelper(getActivity().getApplicationContext());
        Cursor c = db.getTotsAlumnes();
        list_alumnes = new ArrayList<>();

        while (c.moveToNext()) {
            list_alumnes.add(
                    new Alumne(
                            c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne.NOM)),
                            Integer.parseInt(c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne._ID))),
                            c.getString(c.getColumnIndex(Contracte_Alumne.EntradaAlumne.DNI))
                    )
            );
        }
        Collections.sort(list_alumnes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //   rootView = inflater.inflate(R.layout.fragment_gestio_alumnes, container);
      //  inicializacion();



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestio_alumnes, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
