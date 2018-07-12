package fhwedel.projektstudie;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import fhwedel.projektstudie.location.LocationService;
import fhwedel.projektstudie.location.NoLocationException;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private Marker mPosition;
    private EditText search;
    private String searchStr;
    private RadiusActivity radiusActivity = new RadiusActivity();
    LatLng actualLocation;
    Circle circle;
    int radius;

    private final List<Note> noteList = new ArrayList<Note>();

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    List<Note> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        // Versuchen Location zu bekommen
        try {
            LocationService location = new LocationService(getApplicationContext(), this);
        } catch (NoLocationException noLocEx) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.createDefaultDatabase();

        list = db.getAllNotes();
        this.noteList.addAll(list);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("Radius", 5);
        editor.apply();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void buttonSearchClicked(View view) {
        mMap.clear();

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.createDefaultDatabase();
        list = db.getAllNotes();
        this.noteList.addAll(list);

        int counter = 0;
        this.search = (EditText) this.findViewById(R.id.editSearch);
        searchStr = search.getText().toString().toUpperCase();
        mPosition = mMap.addMarker(new MarkerOptions().position(actualLocation).title("Aktuelle Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 12));
        radius = getRadius() * 1000;
        addCircle(actualLocation, radius);

        if (searchStr.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter a Dish", Toast.LENGTH_LONG).show();
            return;
        }

        if (searchStr.equals("ALLES")) {
            for (int i = 0; i < list.size(); i++) {
                Note note = list.get(i);
                LatLng tmp = new LatLng(note.getNoteLatitude(), note.getNoteLongitude());
                mMap.addMarker(new MarkerOptions().position(tmp).title(note.getNoteRestaurant() + " - " + note.getNoteMenu()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 12));
                counter++;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Note note = list.get(i);
            if (note.getNoteMenu().contains(searchStr) && isInRadius(note, circle)) {
                LatLng tmp = new LatLng(note.getNoteLatitude(), note.getNoteLongitude());
                mMap.addMarker(new MarkerOptions().position(tmp).title(note.getNoteRestaurant() + " - " + note.getNoteMenu()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, 12));
                counter++;
            }
        }

        //Wenn Gericht nicht vorhanden oder nicht in der Nähe
        if (counter < 1) {
            Toast.makeText(getApplicationContext(),
                    "The Dish is not available", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public boolean isInRadius(Note note, Circle circle) {
        boolean isInRadius = true;
        float[] distance = new float[2];

        Location.distanceBetween(note.getNoteLatitude(), note.getNoteLongitude(), circle.getCenter().latitude, circle.getCenter().longitude, distance);

        if (distance[0] > circle.getRadius()) {
            isInRadius = false;
        } else {
            isInRadius = true;
        }
        return isInRadius;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, DatabaseActivity2.class);
            startActivity(intent);
            return true;
        } else {
            if (id == R.id.action_settings_radius) {
                Intent intent2 = new Intent(this, RadiusActivity.class);
                startActivity(intent2);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void whenLocationUpdate(Location location) {
        actualLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mPosition = mMap.addMarker(new MarkerOptions().position(actualLocation).title("Aktuelle Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 12));
        radius = getRadius() * 1000;
        addCircle(actualLocation, radius);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LocationService location = new LocationService(getApplicationContext(), this);
    }

    public void addCircle(LatLng latLng, int radius) {
        circle = mMap.addCircle(new CircleOptions().center(latLng).radius(radius).strokeColor(Color.BLUE).fillColor(Color.argb(30, 0, 50, 245)));
        //Toast.makeText(getApplicationContext(), "Radius activated: " + radius, Toast.LENGTH_SHORT).show();
    }

    public int getRadius() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getInt("Radius", 5);
    }
}