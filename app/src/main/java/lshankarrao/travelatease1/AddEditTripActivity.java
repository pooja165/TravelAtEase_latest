package lshankarrao.travelatease1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class AddEditTripActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;

    String title, city, state, country, notes;
    String startDate, endDate;
    //String id_time;
    //static int id = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);

        Button tripDonebutton = (Button) findViewById(R.id.buttonAddEditTripdone);
        tripDonebutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        db = new TripDbHelper(this);

        EditText eTitle = (EditText) findViewById(R.id.editTextAddEditTriptitle);
        title = eTitle.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextAddEditTripcity);
        city = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.editTextAddEditTripstate);
        state = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextAddEditTripcountry);
        country = eCountry.getText().toString();
        EditText eNotes = (EditText) findViewById(R.id.editTextAddEditTripnotes);
        notes = eNotes.getText().toString();
        EditText eStartDate = (EditText) findViewById(R.id.editTextAddEditTripsdate);
        startDate = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextAddEditTripedate);
        endDate = eEndDate.getText().toString();

        TripInfo info = new TripInfo();

        //info.id = id;
        info.title = title;
        info.city =  city;
        info.state = state;
        info.country = country;
        info.notes = notes;
        info.startDate = startDate;
        info.endDate = endDate;
        //info.events = null;

        long id = db.addTripInfo(info);

        title = null;
        city = null;
        state = null;
        country = null;
        notes = null;
        startDate = null;
        endDate = null;

        Log.i("pooja", "addTripInfo id : "+id);


        Intent intent = new Intent(AddEditTripActivity.this, AddEditEventActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
