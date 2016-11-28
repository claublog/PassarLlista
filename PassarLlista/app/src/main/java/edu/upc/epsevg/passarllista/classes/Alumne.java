package edu.upc.epsevg.passarllista.classes;

import android.content.ContentValues;

import edu.upc.epsevg.passarllista.base_de_dades.AlumneDbHelper;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Alumne;

public class Alumne {
    private String nom;
    private int id;
    private String dni;

    public Alumne(String nom, int id, String dni) {
        this.nom = nom;
        this.id = id;
        this.dni = dni;


    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(Contracte_Alumne.EntradaAlumne.ID, id);
        values.put(Contracte_Alumne.EntradaAlumne.NOM, nom);
        values.put(Contracte_Alumne.EntradaAlumne.DNI, dni);
        return values;
    }
}
