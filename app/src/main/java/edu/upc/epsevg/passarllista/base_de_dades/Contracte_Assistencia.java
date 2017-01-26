package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Assistencia {

    // Creem la classe interna Entrada Assistencia per a guardar els noms de les columnes de la taula
    public static abstract class EntradaAssistencia implements BaseColumns {
        public static final String TABLE_NAME ="assistencia";
        public static final String TIPUS = "tipus";
        public static final String ID_ALUMNE = "id_alumne";
        public static final String ID_SESSIO = "id_sessio";
    }
}