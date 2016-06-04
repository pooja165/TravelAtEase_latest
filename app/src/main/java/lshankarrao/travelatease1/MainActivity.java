package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    TripDbHelper tripDbHelper = new TripDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
