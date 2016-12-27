package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_LlistaAssistencia {

    // Creem la classe interna Entrada LlistaAssistencia per a guardar els noms de les columnes de la taula
    public static abstract class EntradaLlistaAssistencia implements BaseColumns {
        public static final String TABLE_NAME ="llista_assistencia";
        public static final String ID_GRUP = "id_grup";
        public static final String ID_SESSIO = "id_sessio";
    }
}