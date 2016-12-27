package edu.upc.epsevg.passarllista.classes;

import android.content.ContentValues;

import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;

public class Assignatura implements Comparable {
    private int id;
    private String nom;

    public Assignatura(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(Contracte_Alumne.EntradaAlumne.NOM, nom);
        return values;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Alumne) {
            return ((Alumne) o).getNom().compareToIgnoreCase(nom);
        } else {
            return 0;
        }
    }
}
