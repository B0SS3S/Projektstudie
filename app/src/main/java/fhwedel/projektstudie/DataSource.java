//TODO ALT -> LÖSCHEN

package fhwedel.projektstudie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class DataSource {

    private static final String LOG_TAG = DataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private String[] columns = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_PRODUCT,
            DatabaseHelper.COLUMN_RESTAURANT
           // DatabaseHelper.COLUMN_LATITUDE,
           // DatabaseHelper.COLUMN_LONGITUDE
    };

    public DataSource(Context context) {
        Log.d(LOG_TAG, "Unsere DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

    //public Dataset createShoppingMemo(String product, String restaurant, double latitude, double longitude) {
    public Dataset createShoppingMemo(String product, String restaurant) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PRODUCT, product);
        values.put(DatabaseHelper.COLUMN_RESTAURANT, restaurant);
        /*
        values.put(DatabaseHelper.COLUMN_LATITUDE, latitude);
        values.put(DatabaseHelper.COLUMN_LONGITUDE, longitude);
        */

        long insertId = database.insert(DatabaseHelper.TABLE_PROJEKTSTUDIE, null, values);

        Cursor cursor = database.query(DatabaseHelper.TABLE_PROJEKTSTUDIE,
                //"='" + suche + "'"
                columns, DatabaseHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        Dataset dataset = cursorToDataset(cursor);
        cursor.close();

        return dataset;
    }

    public void deleteDataset(Dataset dataset) {
        long id = dataset.getId();

        database.delete(DatabaseHelper.TABLE_PROJEKTSTUDIE,
                DatabaseHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + dataset.toString());
    }

    private Dataset cursorToDataset(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
        int idProduct = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT);
        int idRestaurant = cursor.getColumnIndex(DatabaseHelper.COLUMN_RESTAURANT);
        /*
        int idLatitude = cursor.getColumnIndex(DatabaseHelper.COLUMN_LATITUDE);
        int idLongitude = cursor.getColumnIndex(DatabaseHelper.COLUMN_LONGITUDE);
        */

        String product = cursor.getString(idProduct);
        String restaurant = cursor.getString(idRestaurant);
        long id = cursor.getLong(idIndex);
        /*
        double latitude = cursor.getDouble(idLatitude);
        double longitude = cursor.getDouble(idLongitude);
        */

        //Dataset dataset = new Dataset(product, restaurant, id, latitude, longitude);
        Dataset dataset = new Dataset(product, restaurant, id);

        return dataset;
    }

    public List<Dataset> getAllDatasets() {
        List<Dataset> DatasetList = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_PROJEKTSTUDIE,
                columns, null, null, null, null, null);

        cursor.moveToFirst();
        Dataset dataset;

        while(!cursor.isAfterLast()) {
            dataset = cursorToDataset(cursor);
            DatasetList.add(dataset);
            Log.d(LOG_TAG, "ID: " + dataset.getId() + ", Inhalt: " + dataset.toString());
            cursor.moveToNext();
        }

        cursor.close();

        return DatasetList;
    }
}