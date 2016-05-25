package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by vijay on 5/23/2016.
 */
public class TripListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView tripListView;
    private TripListAdapter tla;
    TripDbHelper tripDB;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripListView = (ListView) findViewById(R.id.listViewTLTripList);
        tripListView.setOnItemClickListener(this);
        tripDB = new TripDbHelper(this);
        cursor = tripDB.fetchAllTrips();
        tla = new TripListAdapter(this, cursor, 0);
        //tla.notifyDataSetChanged();
        tripListView.setAdapter(tla);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent existingTrips = new Intent(TripListActivity.this, ViewTripItineraryActivity.class);
        existingTrips.putExtra("position", position+1);
        int tripId = cursor.getInt(cursor.getColumnIndex("_id"));
        existingTrips.putExtra("id",tripId);
        Log.i("position", position+"+1");
        Log.i("TLA Trip ID", tripId+"");
        startActivity(existingTrips);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tripDB = new TripDbHelper(this);
        //cursor = tripDB.fetchAllTrips();
        cursor.requery();
        tla = new TripListAdapter(this, cursor, 0);
        //ca.notifyDataSetChanged();
        tripListView.setAdapter(tla);

    }



}
