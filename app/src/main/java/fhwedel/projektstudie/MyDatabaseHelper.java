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
                    "Pizza, Pasta", 53.551223, 9.968452);
            Note note2 = new Note("Mickies",
                    "Burger, Pommes", 53.559669, 9.995836);
            Note note3 = new Note("Goofys",
                    "Pasta, Salat", 53.554030, 9.991413);
            Note note4 = new Note("Hafenkante",
                    "Fisch", 53.543842, 9.9799203);
            Note note5 = new Note("Boot",
                    "Fisch, Fischbrötchen, Lachs", 53.562367, 10.004804);

            //Wedeler Fraktion
            Note note6 = new Note("Wedeler Wurst",
                    "Currywurst, Pommes", 53.577694, 9.727997);
            Note note7 = new Note("Taverna",
                    "Gyyros, Salat", 53.576213, 9.715068);
            Note note8 = new Note("McDonald´s",
                    "Burger, Pommes", 53.583334, 9.723802);
            Note note9 = new Note("Burger King",
                    "Burger, Pommes", 53.583818, 9.726226);
            Note note10 = new Note("Subways",
                    "Sandwich, Baguette, Sub, Salat", 53.584478, 9.724300);
            Note note11 = new Note("Italiano",
                    "Pizza, Pasta, Salat", 53.577737, 9.742876);
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
