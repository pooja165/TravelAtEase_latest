package lshankarrao.travelatease1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    TripDbHelper tripDbHelper = new TripDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acquireRunTimePermissions();
        TextView upcomingTripList = (TextView) findViewById(R.id.textViewMainUpcomingTripDetails);
        Cursor upcomingCursor = tripDbHelper.fetchUpcomingTrips();
        if(upcomingCursor.getCount()>0){
            String upcomingTrips ="";
            Log.d("hi","namskara");
            //fill with upcoming trip details
            upcomingCursor.moveToFirst();
            while (!upcomingCursor.isAfterLast()){
                Log.d("hi hello","namskara");
                upcomingTrips += upcomingCursor.getString(upcomingCursor.getColumnIndex("title"))
                        + "\n";
                upcomingCursor.moveToNext();
            }
            upcomingTripList.setText(upcomingTrips);
        }
        TextView pastTripList = (TextView) findViewById(R.id.textViewMainPastTripDetails);
        Cursor pastTripCursor = tripDbHelper.fetchPastTrips();
        if(pastTripCursor.getCount()>0){
            //fill with past trip details
            String pastTrips ="";
            Log.d("hi","namskara");
            //fill with upcoming trip details
            pastTripCursor.moveToFirst();
            while (!pastTripCursor.isAfterLast()){
                Log.d("hi hello","namskara");
                pastTrips += pastTripCursor.getString(upcomingCursor.getColumnIndex("title"))
                        + "\n";
                pastTripCursor.moveToNext();
            }
            pastTripList.setText(pastTrips);
        }


        Button upcomingTrips = (Button) findViewById(R.id.button_upcomingTrips);
        upcomingTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripListActivity.class);
                intent.putExtra("tripKind", "upcoming");
                startActivity(intent);
            }
        });

        Button pastTrips = (Button) findViewById(R.id.button_pastTrips);
        pastTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TripListActivity.class);
                intent.putExtra("tripKind", "past");
                startActivity(intent);
            }
        });

        Button currentTrip = (Button) findViewById(R.id.button_currentTrip);
        TextView currentTripTitle = (TextView) findViewById(R.id.textViewCurrentTripTitle);

        final Cursor cursor = tripDbHelper.getCurrentTrip();
        if(cursor.getCount()>0){
            currentTrip.setVisibility(View.VISIBLE);
            currentTripTitle.setVisibility(View.VISIBLE);
            cursor.moveToFirst();
            String tripTitle = cursor.getString(upcomingCursor.getColumnIndex("title"));
            currentTripTitle.setText(tripTitle);
        }else {
            currentTrip.setVisibility(View.INVISIBLE);
            currentTripTitle.setVisibility(View.INVISIBLE);
        }

        currentTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Assuming trips are mutually exclusive. If not just calling the first trip (earliest end time)
                cursor.moveToFirst();
                int tripId = cursor.getInt(cursor.getColumnIndex("_id"));
                Intent currentTripIntent = new Intent(MainActivity.this,ViewTripItineraryActivity.class);
                currentTripIntent.putExtra("id",tripId);
                startActivity(currentTripIntent);

            }
        });

    }

    private boolean acquireRunTimePermissions() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    111);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
       // permissionGranted = false;
        if (requestCode != 111) {
            return;
        }
        if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
           // permissionGranted = true;
            Toast.makeText(getApplicationContext(), "Great! We have the permission!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot write to external storage! App will not work properly!", Toast.LENGTH_SHORT).show();
        }
    }
}
