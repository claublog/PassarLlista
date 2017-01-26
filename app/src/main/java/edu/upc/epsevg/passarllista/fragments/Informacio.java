package edu.upc.epsevg.passarllista.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import edu.upc.epsevg.passarllista.R;

public class Informacio extends android.support.v4.app.Fragment {

    public Informacio() {
        // Constructor obligat per la creacio de fragments
    }

    private void inicialitzacio() {
        boolean esAjuda = getArguments().getBoolean("esAjuda");
        WebView wv = (WebView) getView().findViewById(R.id.web_view);
        String titol;
        if (esAjuda) {
            titol = "ajuda";
        } else {
            titol = "sobre";
        }
        wv.loadUrl("file:///android_asset/" + titol + ".html");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informacio, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        inicialitzacio();
    }
}
