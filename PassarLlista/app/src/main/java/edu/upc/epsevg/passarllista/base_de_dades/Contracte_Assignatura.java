package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Assignatura {

    // Creem la classe interna Entrada alumne per a guardar els noms de les columnes de la taula
    public static abstract class EntradaAssignatura implements BaseColumns {
        public static final String TABLE_NAME ="assignatura";
        public static final String NOM = "nom";
    }
}