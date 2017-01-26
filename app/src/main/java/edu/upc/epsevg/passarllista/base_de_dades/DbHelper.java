package edu.upc.epsevg.passarllista.base_de_dades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PassarLlista.db";

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

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Assignatura.EntradaAssignatura.TABLE_NAME + " ("
                + Contracte_Assignatura.EntradaAssignatura._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Assignatura.EntradaAssignatura.NOM + " TEXT NOT NULL,"
                + "UNIQUE (" + Contracte_Assignatura.EntradaAssignatura._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Sessio.EntradaSessio.TABLE_NAME + " ("
                + Contracte_Sessio.EntradaSessio._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Sessio.EntradaSessio.DATA + " INTEGER NOT NULL,"
                + Contracte_Sessio.EntradaSessio.ID_GRUP + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Sessio.EntradaSessio.ID_GRUP + ") REFERENCES " + Contracte_Grup.EntradaGrup.TABLE_NAME + "(" + Contracte_Grup.EntradaGrup._ID + ") ON DELETE CASCADE,"
                + "UNIQUE (" + Contracte_Sessio.EntradaSessio._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Grup.EntradaGrup.TABLE_NAME + " ("
                + Contracte_Grup.EntradaGrup._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Grup.EntradaGrup.NOM + " TEXT NOT NULL,"
                + Contracte_Grup.EntradaGrup.ID_ASSIGNATURA + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Grup.EntradaGrup.ID_ASSIGNATURA + ") REFERENCES " + Contracte_Assignatura.EntradaAssignatura.TABLE_NAME + "(" + Contracte_Assignatura.EntradaAssignatura._ID + ") ON DELETE CASCADE,"
                + "UNIQUE (" + Contracte_Grup.EntradaGrup._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Assistencia.EntradaAssistencia.TABLE_NAME + " ("
                + Contracte_Assistencia.EntradaAssistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contracte_Assistencia.EntradaAssistencia.TIPUS + " INTEGER NOT NULL,"
                + Contracte_Assistencia.EntradaAssistencia.ID_ALUMNE + " INTEGER NOT NULL,"
                + Contracte_Assistencia.EntradaAssistencia.ID_SESSIO + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Assistencia.EntradaAssistencia.ID_ALUMNE + ") REFERENCES " + Contracte_Alumne.EntradaAlumne.TABLE_NAME + "(" + Contracte_Alumne.EntradaAlumne._ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + Contracte_Assistencia.EntradaAssistencia.ID_SESSIO + ") REFERENCES " + Contracte_Sessio.EntradaSessio.TABLE_NAME + "(" + Contracte_Sessio.EntradaSessio._ID + ") ON DELETE CASCADE,"
                + "UNIQUE (" + Contracte_Assistencia.EntradaAssistencia._ID + "))");

        sqLiteDatabase.execSQL("CREATE TABLE " + Contracte_Matriculat.EntradaMatriculat.TABLE_NAME + " ("
                + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + " INTEGER NOT NULL,"
                + Contracte_Matriculat.EntradaMatriculat.ID_GRUP + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + ") REFERENCES " + Contracte_Alumne.EntradaAlumne.TABLE_NAME + "(" + Contracte_Alumne.EntradaAlumne._ID + ") ON DELETE CASCADE,"
                + "FOREIGN KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_GRUP + ") REFERENCES " + Contracte_Grup.EntradaGrup.TABLE_NAME + "(" + Contracte_Grup.EntradaGrup._ID + ") ON DELETE CASCADE,"
                + "PRIMARY KEY(" + Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + ", " + Contracte_Matriculat.EntradaMatriculat.ID_GRUP + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hi ha operacions (mètode obligat de herencia)
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Habilita les restriccions de les claus foranies
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public long guardaItem(String nom_taula, ContentValues cv) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                nom_taula,
                null,
                cv);
    }

    public long guardaAlumne(ContentValues cv) {
        return guardaItem(Contracte_Alumne.EntradaAlumne.TABLE_NAME, cv);
    }

    public long guardaAssignatura(ContentValues cv) {
        return guardaItem(Contracte_Assignatura.EntradaAssignatura.TABLE_NAME, cv);
    }

    public long guardaMatricula(ContentValues cv) {
        return guardaItem(Contracte_Matriculat.EntradaMatriculat.TABLE_NAME, cv);
    }
    public long guardaGrup(ContentValues cv) {
        return guardaItem(Contracte_Grup.EntradaGrup.TABLE_NAME, cv);
    }

    public long guardaSessio(ContentValues cv) {
        return guardaItem(Contracte_Sessio.EntradaSessio.TABLE_NAME, cv);
    }

    public long guardaAssitencia(ContentValues cv) {
        return guardaItem(Contracte_Assistencia.EntradaAssistencia.TABLE_NAME, cv);
    }

    public Cursor getTotsItems(String nom_taula) {
        return getTotsItems(nom_taula, null);
    }

    public Cursor getTotsItems(String nom_taula, String ordre) {
        return getReadableDatabase()
                .query(
                        nom_taula,
                        null,
                        null,
                        null,
                        null,
                        null,
                        ordre);
    }

    public Cursor getTotsAlumnes() {
        return getTotsItems(Contracte_Alumne.EntradaAlumne.TABLE_NAME);
    }

    public Cursor getTotsAssignatures() {
        return getTotsItems(Contracte_Assignatura.EntradaAssignatura.TABLE_NAME);
    }

    public Cursor getTotsSessions() {
        return getTotsItems(Contracte_Sessio.EntradaSessio.TABLE_NAME, Contracte_Sessio.EntradaSessio._ID + " DESC");
    }

    public Cursor getItemById(String id_item, String nom_taula, String columna_eval) {
        return getReadableDatabase().query(
                nom_taula,
                null,
                columna_eval + " LIKE ?",
                new String[]{id_item},
                null,
                null,
                null);
    }

    public Cursor getAlumneById(String id_alumne) {
        return getItemById(id_alumne, Contracte_Alumne.EntradaAlumne.TABLE_NAME, Contracte_Alumne.EntradaAlumne._ID);
    }

    public Cursor getGrupById(String id_grup) {
        return getItemById(id_grup, Contracte_Grup.EntradaGrup.TABLE_NAME, Contracte_Grup.EntradaGrup._ID);
    }

    public Cursor getAssignaturaById(String id_Assignatura) {
        return getItemById(id_Assignatura, Contracte_Assignatura.EntradaAssignatura.TABLE_NAME, Contracte_Assignatura.EntradaAssignatura._ID);
    }

    public Cursor getGrupsAssignatura(String id_Assignatura) {
        return getItemById(id_Assignatura, Contracte_Grup.EntradaGrup.TABLE_NAME, Contracte_Grup.EntradaGrup.ID_ASSIGNATURA);
    }

    public Cursor getAlumnesGrup(String id_grup) {
        Cursor matric_cursor = getItemById(id_grup, Contracte_Matriculat.EntradaMatriculat.TABLE_NAME, Contracte_Matriculat.EntradaMatriculat.ID_GRUP);

        // Creem un nou cursor amb les files personalitzades
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        Contracte_Alumne.EntradaAlumne._ID,
                        Contracte_Alumne.EntradaAlumne.NOM,
                        Contracte_Alumne.EntradaAlumne.DNI
                });

        while (matric_cursor.moveToNext()) {
            Cursor alumneById = getAlumneById(matric_cursor.getInt(0) + "");
            alumneById.moveToNext();
            Object[] res = new Object[3];
            res[0] = alumneById.getString(0);
            res[1] = alumneById.getString(1);
            res[2] = alumneById.getString(2);
            matrixCursor.addRow(res);
        }
        return matrixCursor;
    }

    public Cursor getAssistenciesSessio(String id_sessio) {
        Cursor assist_cursor = getItemById(id_sessio, Contracte_Assistencia.EntradaAssistencia.TABLE_NAME, Contracte_Assistencia.EntradaAssistencia.ID_SESSIO);

        // Creem un nou cursor amb les files personalitzades
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        Contracte_Assistencia.EntradaAssistencia._ID,
                        Contracte_Assistencia.EntradaAssistencia.TIPUS,
                        Contracte_Alumne.EntradaAlumne.NOM,
                        Contracte_Alumne.EntradaAlumne.DNI
                });

        while (assist_cursor.moveToNext()) {
            Cursor alumneById = getAlumneById(assist_cursor.getInt(2) + "");
            alumneById.moveToNext();
            Object[] res = new Object[4];
            res[0] = assist_cursor.getInt(0);
            res[1] = assist_cursor.getInt(1);
            res[2] = alumneById.getString(1);
            res[3] = alumneById.getString(2);
            matrixCursor.addRow(res);
        }
        return matrixCursor;
    }

    public void deleteItem(int ids, String nom_taula) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(nom_taula, "_ID=" + ids, null);
    }

    public void deleteAlumne(int ids) {
        deleteItem(ids, Contracte_Alumne.EntradaAlumne.TABLE_NAME);
    }

    public void deleteAssignatura(int ids) {
        deleteItem(ids, Contracte_Assignatura.EntradaAssignatura.TABLE_NAME);
    }

    public void deleteGrup(int ids) {
        deleteItem(ids, Contracte_Grup.EntradaGrup.TABLE_NAME);
    }

    public void deleteMatricula(String id_alumne, String id_grup) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(Contracte_Matriculat.EntradaMatriculat.TABLE_NAME, Contracte_Matriculat.EntradaMatriculat.ID_ALUMNE + " = " + id_alumne + " AND " + Contracte_Matriculat.EntradaMatriculat.ID_GRUP + " = " + id_grup, null);
    }

    public void deleteSessio(int ids) {
        deleteItem(ids, Contracte_Sessio.EntradaSessio.TABLE_NAME);
    }
}
