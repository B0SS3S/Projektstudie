package fhwedel.projektstudie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Note_Manager";

    // Table name: Note.
    private static final String TABLE_NOTE = "Note";

    private static final String COLUMN_NOTE_ID = "Note_Id";
    private static final String COLUMN_NOTE_RESTAURANT = "Note_Restaurant";
    private static final String COLUMN_NOTE_MENU = "Note_Menu";
    private static final String COLUMN_NOTE_LATITUDE = "Note_Latitude";
    private static final String COLUMN_NOTE_LONGITUDE = "Note_Longitude";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY," + COLUMN_NOTE_RESTAURANT + " TEXT,"
                + COLUMN_NOTE_MENU + " TEXT," + COLUMN_NOTE_LATITUDE + " TEXT," + COLUMN_NOTE_LONGITUDE + " TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        // Create tables again
        onCreate(db);
    }

    public void createDefaultDatabase() {
        int count = this.getNotesCount();
        if (count == 0) {

            //Hamburger Fraktion
            Note note1 = new Note("Minies",
                    "PIZZA, PASTA", 53.551223, 9.968452);
            Note note2 = new Note("Mickies",
                    "BURGER, POMMES", 53.559669, 9.995836);
            Note note3 = new Note("Goofys",
                    "PASTA, SALAT", 53.554030, 9.991413);
            Note note4 = new Note("Hafenkante",
                    "FISCH", 53.543842, 9.9799203);
            Note note5 = new Note("Boot",
                    "FISCH, FISCHBRÖTCHEN, LACHS", 53.562367, 10.004804);

            //Wedeler Fraktion
            Note note6 = new Note("Wedeler Wurst",
                    "CURRYWURST, POMMES", 53.577694, 9.727997);
            Note note7 = new Note("Taverna",
                    "GYROS, SALAT", 53.576213, 9.715068);
            Note note8 = new Note("McDonald´s",
                    "BURGER, POMMES", 53.583334, 9.723802);
            Note note9 = new Note("Burger King",
                    "BURGER, POMMES", 53.583818, 9.726226);
            Note note10 = new Note("Subways",
                    "SANDWICH, BAGUETTE, SUB, SALAT", 53.584478, 9.724300);
            Note note11 = new Note("Italiano",
                    "PIZZA, PASTA, SALAT", 53.577737, 9.742876);
            Note note12 = new Note("Taverna Zum Griechen",
                    "GYROS, FLEISCH, FISCH, POMMES", 53.576218, 9.715076);
            Note note13 = new Note("Thai & Tolerance",
                    "THAILÄNDISCH, SUPPE, REIS, FLEISCH", 53.581983, 9.705970);
            Note note14 = new Note("Aytac",
                    "SALAT, FLEISCH, POMMES", 53.577343, 9.705034);
            Note note15= new Note("Highlight Sportsbar & Restaurant",
                    "BURGER, SALAT, POMMES, FLEISCH", 53.575211, 9.700013);
            Note note16 = new Note("elbe1",
                    "FISCH, BRATKARTOFFELN, RISOTTO", 53.571151, 9.693366);
            Note note17 = new Note("Monsoon",
                    "INDISCH, NAANBROT, CURRY", 53.583942, 9.698216);
            Note note18 = new Note("Porter-House Wedel",
                    "FLEISCH, STEAK, POMMES, FISCH, SALAT", 53.583461, 9.697441);
            Note note19 = new Note("Asia Haus",
                    "ASIATISCH, CHINESISCH, REIS, FLEISCH, FISCH", 53.579292, 9.704675);
            Note note20 = new Note("La Giara",
                    "PIZZA, FLEISCH, FISCH, PASTA", 53.582603, 9.700738);
            Note note21 = new Note("Restaurant Mühlenstein",
                    "SALAT, FLEISCH, FISCH, BURGER, PASTA", 53.582604, 9.701142);
            Note note22 = new Note("Italiano",
                    "PIZZA, PASTA, SALAT", 53.577737, 9.742876);

            // Blankenese
            Note note23 = new Note("Athen Pallas",
                    "GYROS, POMMES, FLEISCH", 53.566724, 9.795779);
            Note note24 = new Note("Asiahub",
                    "ASIATISCH, REIS, ASIANUDELN, FLEISCH, GEMÜSE", 53.564152, 9.812978);
            Note note25 = new Note("Ristorante Fal Fabbro",
                    "PIZZA, CALAMARI, SALAT, PASTA, FISCH", 53.559333, 9.811240);
            Note note26 = new Note("Rio Grande",
                    "FLEISCH, BRATKARTOFFELN, POMMES, SALAT, STEAK", 53.561883, 9.820075);
            Note note27 = new Note("Peter Pane",
                    "BURGER, POMMES, SALAT", 53.563145, 9.815729);


            //Test für Emulator
            Note note28 = new Note("TestEins",
                    "CURRYWURST, POMMES", 37.416328, -122.086024);
            Note note29 = new Note("TestZwei",
                    "TEST", 37.424236, -122.090315);
            Note note30 = new Note("TestDrei",
                    "SANDWICH", 37.439503, -122.113576);

            // Test Hittfeld
            Note note31 = new Note("Aroma Bistro",
                    "BRUSCHETTA, PIZZA, SALAT, PASTA", 53.388087, 9.983036);
            Note note32 = new Note("Restaurant Rossini",
                    "SALAT, PASTA, FISCH, FLEISCH", 53.386318, 9.983438);


            this.addNote(note1);
            this.addNote(note2);
            this.addNote(note3);
            this.addNote(note4);
            this.addNote(note5);
            this.addNote(note6);
            this.addNote(note7);
            this.addNote(note8);
            this.addNote(note9);
            this.addNote(note10);
            this.addNote(note11);
            this.addNote(note12);
            this.addNote(note13);
            this.addNote(note14);
            this.addNote(note15);
            this.addNote(note16);
            this.addNote(note17);
            this.addNote(note18);
            this.addNote(note19);
            this.addNote(note20);
            this.addNote(note21);
            this.addNote(note22);
            this.addNote(note23);
            this.addNote(note24);
            this.addNote(note25);
            this.addNote(note26);
            this.addNote(note27);

            //Test
            this.addNote(note28);
            this.addNote(note29);
            this.addNote(note30);

            // Test Hittfeld
            this.addNote(note31);
            this.addNote(note32);
        }
    }


    public void addNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getNoteRestaurant());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_RESTAURANT, note.getNoteRestaurant());
        values.put(COLUMN_NOTE_MENU, note.getNoteMenu());
        values.put(COLUMN_NOTE_LATITUDE, note.getNoteLatitude());
        values.put(COLUMN_NOTE_LONGITUDE, note.getNoteLongitude());

        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }


    public Note getNote(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_NOTE_ID,
                        COLUMN_NOTE_RESTAURANT, COLUMN_NOTE_MENU, COLUMN_NOTE_LATITUDE, COLUMN_NOTE_LONGITUDE}, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4));
        // return note
        return note;
    }


    public List<Note> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... ");

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteRestaurant(cursor.getString(1));
                note.setNoteMenu(cursor.getString(2));
                note.setNoteLatitude(cursor.getDouble(3));
                note.setNoteLongitude(cursor.getDouble(4));
                // Adding note to list
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }

    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteRestaurant());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_RESTAURANT, note.getNoteRestaurant());
        values.put(COLUMN_NOTE_MENU, note.getNoteMenu());
        values.put(COLUMN_NOTE_LATITUDE, note.getNoteLatitude());
        values.put(COLUMN_NOTE_LONGITUDE, note.getNoteLongitude());

        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
    }

    public void deleteNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + note.getNoteRestaurant());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
        db.close();
    }

}
