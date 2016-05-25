package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by lakshmi on 5/23/2016.
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip_list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_uninstall) {
            Uri packageURI = Uri.parse("package:"+ActionBarActivity.class.getPackage().getName());
            Intent deleteIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(deleteIntent);
            return true;
        }
        else if (id == R.id.action_home) {

            Intent intent = new Intent(TripListActivity.this, MainActivity.class);
            startActivity(intent); //startActivityForResult
            return true;
        }
        return true;
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }



}
