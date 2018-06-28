package fhwedel.projektstudie.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import fhwedel.projektstudie.MainActivity;


public class LocationService extends LocationCallback {

    private final Context context;
    private final FusedLocationProviderClient locationClient;
    private final MainActivity activity;


    @Override
    public void onLocationResult(LocationResult locationResult) {
       if(locationResult != null && locationResult.getLocations().size() != 0) {
           android.location.Location location = locationResult.getLocations().get(0);
           activity.whenLocationUpdate(location);
           locationClient.removeLocationUpdates(this);
       }
        Toast.makeText(context, "Found your Location", Toast.LENGTH_LONG).show();

    }

    public LocationService(Context context, MainActivity activity) {
        this.context = context;
        this.activity = activity;
        locationClient = LocationServices.getFusedLocationProviderClient(context);



        // Benachrichtigung bei Updates
        // Fenster: Standortabruf erlauben
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            throw new NoLocationException();
        }
        locationClient.requestLocationUpdates(locationRequest, this, null);
    }


}
