package edu.upc.epsevg.passarllista.classes;

import android.content.ContentValues;

import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Assignatura;
import edu.upc.epsevg.passarllista.base_de_dades.Contracte_Grup;

public class Grup implements Comparable{

    private Integer id;
    private String nom;
    private Long id_assignatura;

    public Grup(Integer id, String nom, Long id_assignatura) {
        this.id = id;
        this.nom = nom;
        this.id_assignatura = id_assignatura;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Long getId_assignatura() {
        return id_assignatura;
    }

    public void setId_assignatura(Long id_assignatura) {
        this.id_assignatura = id_assignatura;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(Contracte_Grup.EntradaGrup.NOM, nom);
        values.put(Contracte_Grup.EntradaGrup.ID_ASSIGNATURA, id_assignatura);
        return values;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Grup) {
            Grup grup = (Grup) o;

            if (grup.getId() != null && this.id != null) {
                return grup.getId().compareTo(this.id);
            } else {
                return grup.getNom().compareToIgnoreCase(nom);
            }
        } else {
            return 0;
        }
    }

}
