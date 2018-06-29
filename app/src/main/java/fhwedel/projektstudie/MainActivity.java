package fhwedel.projektstudie;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
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
    private int radius;

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
        buildRadius();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void buttonSearchClicked(View view) {
        mMap.clear();
        int counter = 0;
        this.search = (EditText) this.findViewById(R.id.editSearch);
        searchStr = search.getText().toString().toUpperCase();

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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, 12));
                counter++;
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Note note = list.get(i);
            //TODO Abfrage ob Restaurant im Radius liegt
            if (note.getNoteMenu().contains(searchStr)) {
                LatLng tmp = new LatLng(note.getNoteLatitude(), note.getNoteLongitude());
                mMap.addMarker(new MarkerOptions().position(tmp).title(note.getNoteRestaurant() + " - " + note.getNoteMenu()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tmp, 12));
                counter++;
            }
        }

        //Wenn Gericht nicht vorhanden
        if (counter < 1) {
            Toast.makeText(getApplicationContext(),
                    "The Dish you´d like to eat, is not near to you", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void buildRadius() {
        if (radiusActivity.activated) {
            radius = radiusActivity.getRadius();
            //TODO
            Toast.makeText(getApplicationContext(), "Radius activated: " + radius, Toast.LENGTH_SHORT).show();
        }
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
        //if (mMap != null) {
        LatLng actualLocation = new LatLng(location.getLatitude(), location.getLongitude());
        mPosition = mMap.addMarker(new MarkerOptions().position(actualLocation).title("Aktuelle Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 12));
        //}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LocationService location = new LocationService(getApplicationContext(), this);
    }

}