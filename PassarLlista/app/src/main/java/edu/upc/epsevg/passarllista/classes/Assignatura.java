package edu.upc.epsevg.passarllista.classes;

import android.content.ContentValues;

import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Assignatura;

public class Assignatura implements Comparable {
    private Integer id;
    private String nom;

    public Assignatura(Integer id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Integer getId() {
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
        values.put(Contracte_Assignatura.EntradaAssignatura.NOM, nom);
        return values;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Assignatura) {
            Assignatura assig = (Assignatura) o;

            if (assig.getId() != null && this.id != null) {
                return assig.getId().compareTo(this.id);
            } else {
                return assig.getNom().compareToIgnoreCase(nom);
            }
        } else {
            return 0;
        }
    }
}
