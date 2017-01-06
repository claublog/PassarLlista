package edu.upc.epsevg.passarllista.base_de_dades;

import android.provider.BaseColumns;

public class Contracte_Sessio {

    // Creem la classe interna Entrada sessio per a guardar els noms de les columnes de la taula
    public static abstract class EntradaSessio implements BaseColumns {
        public static final String TABLE_NAME ="sessio";
        public static final String DATA = "data";
        public static final String ID_GRUP = "id_grup";
    }
}