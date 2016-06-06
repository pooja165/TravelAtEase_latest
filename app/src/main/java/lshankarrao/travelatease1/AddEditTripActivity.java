package lshankarrao.travelatease1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddEditTripActivity extends ActionBarActivity implements View.OnClickListener {

    TripDbHelper db;
    //tripDB could be replaced be db.
    TripDbHelper tripDB;
    Cursor cursor;


    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    //String id_time;
    //static int id = 0;
    TripDbHelper tripDbHelper;
    TripInfo tripInfo;
    boolean purposeEdit = false;
    int tripId;

    protected void onCreate(Bundle savedInstanceState) {
        tripDB = new TripDbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);


        timePicker1 = (TimePicker)findViewById(R.id.TimePickerAddEditTripstime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerAddEditTripsdate);

        timePicker2 = (TimePicker)findViewById(R.id.TimePickerAddEditTripetime);
        datePicker2 = (DatePicker)findViewById(R.id.DatePickerAddEditTripedate);

        if(getIntent().hasExtra("purpose") && getIntent().getStringExtra("purpose").equals("edit")){
            //perform edit action
            tripId = getIntent().getIntExtra("id", -1);
            if(tripId == -1){
                Log.i("trip id invalid! ", "");
                return;
            }
            purposeEdit = true;
            tripDbHelper = new TripDbHelper(this);
            tripInfo = tripDbHelper.getTripInfo(tripId);

            EditText tvTitle = (EditText)findViewById(R.id.editTextAddEditTriptitle);
            Log.i("state qwert",tripInfo.getTitle());
            tvTitle.setText(tripInfo.getTitle());

            EditText tvcity = (EditText)findViewById(R.id.editTextAddEditTripcity);
            Log.i("state qwert",tripInfo.getCity());
            tvcity.setText(tripInfo.getCity());

            EditText tvstate = (EditText)findViewById(R.id.editTextAddEditTripstate);
            Log.i("state qwert",tripInfo.getState());
            tvstate.setText(tripInfo.getState());

            EditText tvcountry = (EditText)findViewById(R.id.editTextAddEditTripcountry);
            Log.i("state qwert",tripInfo.getCountry());
            tvcountry.setText(tripInfo.getCountry());

            EditText tvnotes = (EditText)findViewById(R.id.editTextAddEditTripnotes);
            Log.i("state qwert",tripInfo.getNotes());
            tvnotes.setText(tripInfo.getNotes());
        }


        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);


        Button tripDonebutton = (Button) findViewById(R.id.buttonAddEditTripdone);
        tripDonebutton.setOnClickListener(this);


    }

//    private String getString(DatePicker d) {
//        Calendar calendar =  new GregorianCalendar();
//        calendar.set(d.getYear(), d.getMonth() + 1, d.getDayOfMonth());
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        return sdf.format(calendar.getTime());
//    }



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        db = new TripDbHelper(this);
        String title, city, state, country, notes;
        String startDate, endDate, startTime, endTime;

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

        Calendar stCalendar = new GregorianCalendar();
        Calendar endCalendar = new GregorianCalendar();

        stCalendar.set(datePicker1.getYear(),
                datePicker1.getMonth(),
                datePicker1.getDayOfMonth(),
                timePicker1.getCurrentHour(),
                timePicker1.getCurrentMinute());

        endCalendar.set(datePicker2.getYear(),
                datePicker2.getMonth(),
                datePicker2.getDayOfMonth(),
                timePicker2.getCurrentHour(),
                timePicker2.getCurrentMinute());

        long stTimeMillis = stCalendar.getTimeInMillis();
        long endTimeMillis = endCalendar.getTimeInMillis();

        if(endTimeMillis<=stTimeMillis){
            Toast.makeText(getApplicationContext(),"Enter valid time. End time is <= Start time.", Toast.LENGTH_SHORT).show();
            return;
        }



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
        info.stTimeMillis = stTimeMillis;
        info.endTimeMillis = endTimeMillis;
        //info.events = null;

        if(purposeEdit == true){
            db.updateTripInfo(info, tripId);
        }else {
            tripId = (int)db.addTripInfo(info);
            Log.i("pooja", "addTripInfo id : " + tripId);
        }


        Intent intent = new Intent(AddEditTripActivity.this, ViewTripItineraryActivity.class);
        intent.putExtra("id", tripId);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menu != null) {

            menu.findItem(R.id.action_addtrip).setVisible(false);
        }
        return true;
    }
}
