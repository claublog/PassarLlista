package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Grup {

    // Creem la classe interna Entrada Grup per a guardar els noms de les columnes de la taula
    public static abstract class EntradaGrup implements BaseColumns {
        public static final String TABLE_NAME ="grup";
        public static final String NOM = "nom";
        public static final String ID_ASSIGNATURA ="id_assignatura";
    }
}