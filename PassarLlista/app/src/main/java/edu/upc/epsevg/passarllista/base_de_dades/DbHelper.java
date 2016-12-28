package edu.upc.epsevg.passarllista.base_de_dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.upc.epsevg.passarllista.classes.Alumne;
import edu.upc.epsevg.passarllista.classes.Assignatura;
import edu.upc.epsevg.passarllista.classes.Grup;


public class DbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PassarLlista.db";

    private ContentValues values;


    public DbHelper(Context context) {
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

        // Comandes SQL
        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Assignatura.EntradaAssignatura.TABLE_NAME + " ("
                + Contracte_Assignatura.EntradaAssignatura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Assignatura.EntradaAssignatura.NOM + " TEXT NOT NULL,"
                + "UNIQUE (" + Contracte_Assignatura.EntradaAssignatura._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Sessio.EntradaSessio.TABLE_NAME + " ("
                + Contracte_Sessio.EntradaSessio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Sessio.EntradaSessio.DATA + " INTEGER NOT NULL,"
                + "UNIQUE (" + Contracte_Sessio.EntradaSessio._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Grup.EntradaGrup.TABLE_NAME + " ("
                + Contracte_Grup.EntradaGrup._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Grup.EntradaGrup.NOM + " TEXT NOT NULL,"
                + Contracte_Grup.EntradaGrup.ID_ASSIGNATURA + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Grup.EntradaGrup.ID_ASSIGNATURA + ") REFERENCES " + Contracte_Assignatura.EntradaAssignatura.TABLE_NAME + "(" +Contracte_Assignatura.EntradaAssignatura._ID + "),"
                + "UNIQUE (" + Contracte_Grup.EntradaGrup._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.TABLE_NAME + " ("
                + Contracte_LlistaAssistencia.EntradaLlistaAssistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.ID_GRUP + " INTEGER NOT NULL,"
                + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.ID_SESSIO + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.ID_GRUP + ") REFERENCES " + Contracte_Grup.EntradaGrup.TABLE_NAME + "(" +Contracte_Grup.EntradaGrup._ID + "),"
                + "FOREIGN KEY(" + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.ID_SESSIO + ") REFERENCES " + Contracte_Sessio.EntradaSessio.TABLE_NAME + "(" +Contracte_Sessio.EntradaSessio._ID + "),"
                + "UNIQUE (" + Contracte_LlistaAssistencia.EntradaLlistaAssistencia._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Assistencia.EntradaAssistencia.TABLE_NAME + " ("
                + Contracte_Assistencia.EntradaAssistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Assistencia.EntradaAssistencia.TIPUS + " INTEGER NOT NULL,"
                + Contracte_Assistencia.EntradaAssistencia.OBSERVACIONS + " TEXT,"
                + Contracte_Assistencia.EntradaAssistencia.ID_ALUMNE + " INTEGER NOT NULL,"
                + Contracte_Assistencia.EntradaAssistencia.ID_LLISTAASSISTENCIA + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Assistencia.EntradaAssistencia.ID_ALUMNE + ") REFERENCES " + Contracte_Alumne.EntradaAlumne.TABLE_NAME + "(" +Contracte_Alumne.EntradaAlumne._ID + "),"
                + "FOREIGN KEY(" + Contracte_Assistencia.EntradaAssistencia.ID_LLISTAASSISTENCIA + ") REFERENCES " + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.TABLE_NAME + "(" +Contracte_LlistaAssistencia.EntradaLlistaAssistencia._ID + "),"
                + "UNIQUE (" + Contracte_Assistencia.EntradaAssistencia._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Matriculat.EntradaMatriculat.TABLE_NAME + " ("
                + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + " INTEGER NOT NULL,"
                + Contracte_Matriculat.EntradaMatriculat.ID_LLISTAASSISTENCIA + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + ") REFERENCES " + Contracte_Alumne.EntradaAlumne.TABLE_NAME + "(" +Contracte_Alumne.EntradaAlumne._ID + "),"
                + "FOREIGN KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_LLISTAASSISTENCIA + ") REFERENCES " + Contracte_LlistaAssistencia.EntradaLlistaAssistencia.TABLE_NAME + "(" +Contracte_LlistaAssistencia.EntradaLlistaAssistencia._ID + ")"
                + "PRIMARY KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + ", " + Contracte_Matriculat.EntradaMatriculat.ID_LLISTAASSISTENCIA + "))");
        
        /*
        // Contenedor de valores
        ContentValues values = new ContentValues();

        // Pares clave-valor
        values.put(Contracte_Assignatura.EntradaAssignatura.NOM, "DAMO");
        values.put(Contracte_Assignatura.EntradaAssignatura.NOM, "DABD");

        sqLiteDatabase.insert(Contracte_Assignatura.EntradaAssignatura.TABLE_NAME, null, values);*/
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

    public long guardaAssignatura(Assignatura assignatura) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Contracte_Assignatura.EntradaAssignatura.TABLE_NAME,
                null,
                assignatura.toContentValues());

    }

    public long guardaGrup(Grup grup) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Contracte_Grup.EntradaGrup.TABLE_NAME,
                null,
                grup.toContentValues());

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

    public void deleteAlumne(int ids) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("ALUMNE", "_ID=" + ids, null);
    }

    public void deleteAssignatura(int ids) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete("Assignatura", "_ID=" + ids, null);
    }
}
