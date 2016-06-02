package lshankarrao.travelatease1;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

//import static com.google.android.gms.common.api.GoogleApiClient.*;

/**
 * Created by lakshmi on 5/30/2016.
 */
public class LocationBasedNotifierActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    GoogleApiClient googleApiClient = null;
    ArrayList<Geofence> geofenceArrayList = null;
    PendingIntent geofencingNotifyIntent = null;
    AddressToGeocode addressToGeocode;
    //Context context;
    String tripPlace;
    int tripId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based_notifier_activity);

        tripId = getIntent().getIntExtra("tripId", -1);
        tripPlace = getIntent().getStringExtra("tripPlace");
        Log.i("trip Place = ",tripPlace);
        addressToGeocode = new AddressToGeocode();
        LatLng latLng = addressToGeocode.AddressToLatLng(tripPlace,
                this);

        Log.i("LatLng",latLng.latitude+" "+latLng.longitude);

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    //.enableAutoManage(this, GoogleApiClient.OnConnectionFailedListener)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();


        // build geofencing list
        geofenceArrayList = new ArrayList<Geofence>();
        geofenceArrayList.add(new Geofence.Builder()
                .setRequestId(tripPlace)
                .setCircularRegion(latLng.latitude, latLng.longitude, 200)
                //.setCircularRegion(55.942382, -4.214698, 160)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                //// TODO : set this to endTimeMillis - currentTimeMillis if user goes there before trip time? dont bother for now...
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build());

        // create pendingintent
        if (geofencingNotifyIntent == null) {
            Intent intent = new Intent(this, LocationBasedNotificationHandler.class);
            intent.putExtra("tripPlace", tripPlace);
            intent.putExtra("tripId", tripId);
            // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
            // addGeofences() and removeGeofences().
            geofencingNotifyIntent = PendingIntent.getService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        }
    }

//    public LocationBasedNotifierActivity(String userGivenAddress, Context context) {
//        this.context = context;
//
//    }

    private void startGeofencing() {
        toast("start geofencing ...");
        Log.i("jsun", "start geofencing ...");

        // build a geofencing request
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceArrayList);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.GeofencingApi.addGeofences(
                googleApiClient,
                // The GeofenceRequest object.
                builder.build(),
                // A pending intent that that is reused when calling removeGeofences(). This
                // pending intent is used to generate an intent when a matched geofence
                // transition is observed.
                geofencingNotifyIntent
        ).setResultCallback(this); // Result processed in onResult().
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        toast("onConnected()");
        Log.i("connect aaythu","");
        startGeofencing();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        toast("onConnectionFailed()");
    }

    @Override
    public void onResult(@NonNull Status status) {
        toast("onResult() called : status=" + status.toString());
        googleApiClient.disconnect();
    }

//   @Override
//    protected void onStart() {
//        super.onStart();
//       Log.i("hi","hi");
//        googleApiClient.connect();
//    }
//
//   @Override
//    public void onStop() {
//        super.onStop();
//        googleApiClient.disconnect();
//    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
