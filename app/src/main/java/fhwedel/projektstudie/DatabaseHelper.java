//TODO ALT -> LÖSCHEN

package fhwedel.projektstudie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{


    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();

    public static final String DB_NAME = "projektstudie.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_PROJEKTSTUDIE = "projektstudie";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_RESTAURANT = "restaurant";
    /*
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    */


    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_PROJEKTSTUDIE +
                    "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT + " TEXT NOT NULL, " +
                    COLUMN_RESTAURANT + " TEXT NOT NULL); ";
         //           COLUMN_RESTAURANT + " TEXT NOT NULL, " +
         //   COLUMN_LATITUDE + " TEXT NOT NULL, " +
         //   COLUMN_LONGITUDE + " TEXT NOT NULL); ";


    public DatabaseHelper(Context context) {
        //super(context, "PLATZHALTER_DATENBANKNAME", null, 1);
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}