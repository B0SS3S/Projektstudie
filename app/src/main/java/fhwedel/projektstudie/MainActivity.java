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
    private Marker mSydney;
    private EditText search;
    private String searchStr;

    private final List<Note> noteList = new ArrayList<Note>();

    public static final String LOG_TAG = MainActivity.class.getSimpleName();


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



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void buttonSearchClicked(View view)  {
        //TODO
        int counter = 0;
        this.search = (EditText)this.findViewById(R.id.editSearch);
        searchStr = search.getText().toString();


        if(searchStr.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter a Dish", Toast.LENGTH_LONG).show();
            return;
        }

        //TODO Datenbankabfrage einfügen (Methodenaufruf)
        MyDatabaseHelper db = new DatabaseActivity2().getDatabase();
        List<Note> list=  db.getAllNotes();

        for(int i = 0; i < list.size(); i++){
            Note note = list.get(i);
            if (note.getNoteMenu().contains(searchStr)){
                LatLng tmp = new LatLng(note.getNoteLatitude(),note.getNoteLongitude());
                mMap.addMarker(new MarkerOptions().position(tmp));
                counter++;
            }
        }

        //Wenn Gericht nicht vorhanden
        if(counter < 1) {
            Toast.makeText(getApplicationContext(),
                    "The Dish you´d like to eat, is not near to you", Toast.LENGTH_LONG).show();
            return;
        }


        LatLng sydney = new LatLng(-34, 151);
        mSydney = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void whenLocationUpdate(Location location) {
        if (mMap != null) {
            LatLng actualLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mPosition = mMap.addMarker(new MarkerOptions().position(actualLocation).title("Aktuelle Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actualLocation, 12));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LocationService location = new LocationService(getApplicationContext(), this);
    }

}