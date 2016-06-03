package lshankarrao.travelatease1;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshmi on 5/30/2016.
 */
public class LocationBasedNotificationHandler extends IntentService implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    public LocationBasedNotificationHandler() {
        super("LocationBasedNotificationHandler");
    }

    GoogleApiClient googleApiClient = null;

    public final int VISIBILITY_PRIVATE = 0;

    private static int notificationId = 2345;
    String tripPlace;
    int tripId;
    String requestId;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationBasedNotificationHandler(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    //.enableAutoManage(this, this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        tripPlace = intent.getStringExtra("tripPlace");
        tripId = intent.getIntExtra("tripId", -1);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e("jsun", "We have an error in geofencing intent.");
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        Geofence target = triggeringGeofences.get(0);
        requestId = target.getRequestId();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.i("Entering geofencing of ", requestId);
            sendNotification(target);
            //TODO Stop geofencing abstract PendingResult<Status> removeGeofences(GoogleApiClient client, List<String> geofenceRequestIds)
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            Log.i("Travel@Ease", "Leaving geofencing of " + requestId);
        } else {
            Log.w("Attention", "unknown/unexpected geofencing event : " + geofenceTransition);
        }

    }

    private void sendNotification(Geofence target) {
        Intent resultIntent = new Intent(LocationBasedNotificationHandler.this,
                LocationNotificationActionActivity.class);
        resultIntent.putExtra("tripPlace", tripPlace);
        resultIntent.putExtra("tripId", tripId);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(getApplicationContext(), notificationId,
                        resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent rIntent = new Intent(LocationBasedNotificationHandler.this,
                LocationBasedNotificationActionJustText.class);
        rIntent.putExtra("tripPlace", tripPlace);
        rIntent.putExtra("tripId", tripId);
        PendingIntent rPendingIntent =
                PendingIntent.getActivity(getApplicationContext(), notificationId,
                        rIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.jinggling_on)
                        .setContentTitle("Location Update...!")
                        .setContentText("Say hello to Friends/Family ")
                        .setContentIntent(resultPendingIntent)
                        .setVisibility(VISIBILITY_PRIVATE)
                        .setAutoCancel(true)
                .addAction(R.drawable.jinggling_on, "With Picture", resultPendingIntent)
                .addAction(R.drawable.jinggling_on, "Just Text", rPendingIntent);

        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

        //mBuilder.setStyle(createBigContent(numEvents, eventsList));

        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(
                        Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }

    private void stopGeofencing() {
        toast("stop geofencing ...");

        List<String> geofenceReqIDs = new ArrayList<>();
        geofenceReqIDs.add(requestId);
        LocationServices.GeofencingApi.removeGeofences(googleApiClient, geofenceReqIDs).setResultCallback(this);

    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        toast("onConnected()");
        Log.i("connect aaythu","");
        stopGeofencing();
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
}
