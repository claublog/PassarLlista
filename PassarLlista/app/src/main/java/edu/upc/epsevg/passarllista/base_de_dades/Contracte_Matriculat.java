package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Matriculat {

    // Creem la classe interna Entrada matriculat per a guardar els noms de les columnes de la taula
    public static abstract class EntradaMatriculat implements BaseColumns {
        public static final String TABLE_NAME ="matriculat";
        public static final String ID_ALUMNE = "id_alumne";
        public static final String ID_GRUP = "id_llistaassistencia";
    }
}