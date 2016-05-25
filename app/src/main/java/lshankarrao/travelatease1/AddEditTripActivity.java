package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddEditTripActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;
    //tripDB could be replaced be db.
    TripDbHelper tripDB;
    Cursor cursor;


    String title, city, state, country, notes;
    String startDate, endDate, startTime, endTime;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    //String id_time;
    //static int id = 0;

    protected void onCreate(Bundle savedInstanceState) {
        tripDB = new TripDbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);

        timePicker1 = (TimePicker)findViewById(R.id.TimePickerAddEditTripstime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerAddEditTripsdate);

        timePicker2 = (TimePicker)findViewById(R.id.TimePickerAddEditTripetime);
        datePicker2 = (DatePicker)findViewById(R.id.DatePickerAddEditTripedate);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);


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

        startDate = (datePicker1.getMonth()+1)+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear();
        startTime = timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();

        endDate = (datePicker2.getMonth()+1)+":"+datePicker2.getDayOfMonth()+":"+datePicker2.getYear();
        endTime = timePicker2.getCurrentHour()+":"+timePicker2.getCurrentMinute();

        /*
        EditText eStartDate = (EditText) findViewById(R.id.editTextAddEditTripsdate);
        startDate = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextAddEditTripedate);
        endDate = eEndDate.getText().toString();
        */
        TripInfo info = new TripInfo();

        //info.id = id;
        info.title = title;
        info.city =  city;
        info.state = state;
        info.country = country;
        info.notes = notes;
        info.startDate = startDate;
        info.endDate = endDate;
        info.startTime = startTime;
        info.endTime = endTime;
        //info.events = null;

        long id = db.addTripInfo(info);

        title = null;
        city = null;
        state = null;
        country = null;
        notes = null;
        startDate = null;
        endDate = null;
        startTime = null;
        endTime = null;

        Log.i("pooja", "addTripInfo id : "+id);


        Intent intent = new Intent(AddEditTripActivity.this, TripListActivity.class);
        //intent.putExtra("id", id);
        startActivity(intent);
    }
}
