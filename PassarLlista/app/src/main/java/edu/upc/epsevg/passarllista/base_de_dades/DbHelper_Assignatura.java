package edu.upc.epsevg.passarllista.base_de_dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.upc.epsevg.passarllista.classes.Assignatura;


public class DbHelper_Assignatura extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PassarLlista.db";

    private ContentValues values;


    public DbHelper_Assignatura(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandes SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Assignatura.EntradaAssignatura.TABLE_NAME + " ("
                + Contracte_Assignatura.EntradaAssignatura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Assignatura.EntradaAssignatura.NOM + " TEXT NOT NULL,"
                + "UNIQUE (" + Contracte_Assignatura.EntradaAssignatura._ID + "))");

        // Contenedor de valores
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put(Contracte_Assignatura.EntradaAssignatura.NOM, "DAMO");
        values.put(Contracte_Assignatura.EntradaAssignatura.NOM, "DABD");

        sqLiteDatabase.insert(Contracte_Assignatura.EntradaAssignatura.TABLE_NAME, null, values);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hi ha operacions
    }

    public long guardaAssignatura(Assignatura Assignatura) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Contracte_Assignatura.EntradaAssignatura.TABLE_NAME,
                null,
                Assignatura.toContentValues());

    }

    public Cursor getTotsAssignatures() {
        return getReadableDatabase()
                .query(
                        Contracte_Assignatura.EntradaAssignatura.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getAssignaturaById(String id_Assignatura) {
        Cursor c = getReadableDatabase().query(
                Contracte_Assignatura.EntradaAssignatura.TABLE_NAME,
                null,
                Contracte_Assignatura.EntradaAssignatura._ID + " LIKE ?",
                new String[]{id_Assignatura},
                null,
                null,
                null);
        return c;
    }

    public void delete(int ids) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("Assignatura", "_ID=" + ids, null);
    }


}
