package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        TripDbHelper tripDbHelper = new TripDbHelper(this);
        final Cursor cursor = tripDbHelper.getCurrentTrip();
        if(cursor.getCount()>0){
            currentTrip.setVisibility(View.VISIBLE);
        }else {
            currentTrip.setVisibility(View.INVISIBLE);
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
