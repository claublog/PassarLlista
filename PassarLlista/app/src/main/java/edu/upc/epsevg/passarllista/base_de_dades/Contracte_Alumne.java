package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Alumne {

    // Creem la classe interna Entrada alumne per a guardar els noms de les columnes de la taula
    public static abstract class EntradaAlumne implements BaseColumns {
        public static final String TABLE_NAME ="alumne";

        public static final String ID = "id";
        public static final String NOM = "nom";
        public static final String DNI = "dni";
    }
}