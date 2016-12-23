package edu.upc.epsevg.passarllista.base_de_dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.upc.epsevg.passarllista.classes.Alumne;


public class AlumneDbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alumnes.db";

    private ContentValues values;


    public AlumneDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandes SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Alumne.EntradaAlumne.TABLE_NAME + " ("
                + Contracte_Alumne.EntradaAlumne._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Alumne.EntradaAlumne.NOM + " TEXT NOT NULL,"
                + Contracte_Alumne.EntradaAlumne.DNI + " TEXT NOT NULL,"
                + "UNIQUE (" + Contracte_Alumne.EntradaAlumne._ID + "))");

        // Contenedor de valores
        ContentValues values = new ContentValues();

       /* // Pares clave-valor
        values.put(Contracte_Alumne.EntradaAlumne.NOM, "Claudio");
        values.put(Contracte_Alumne.EntradaAlumne.DNI, "48923892B");

        sqLiteDatabase.insert(Contracte_Alumne.EntradaAlumne.TABLE_NAME, null, values);*/

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hi ha operacions
    }

    public long guardaAlumne(Alumne alumne) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Contracte_Alumne.EntradaAlumne.TABLE_NAME,
                null,
                alumne.toContentValues());

    }

    public Cursor getTotsAlumnes() {
        return getReadableDatabase()
                .query(
                        Contracte_Alumne.EntradaAlumne.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAlumneById(String id_alumne) {
        Cursor c = getReadableDatabase().query(
                Contracte_Alumne.EntradaAlumne.TABLE_NAME,
                null,
                Contracte_Alumne.EntradaAlumne._ID + " LIKE ?",
                new String[]{id_alumne},
                null,
                null,
                null);
        return c;
    }

    public void delete(int ids) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ALUMNE", "_ID=" + ids, null);
    }


}
