package lshankarrao.travelatease1;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Created by pooja on 5/21/16.
 */
public class OtherReservationActivity extends ActionBarActivity implements View.OnClickListener{

    TripDbHelper db;

    String startDate, endDate;
    String startTime, endTime;
    String typeofEvent;
    String address, city, state, country;
    String notes;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    long eventId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_reservations);

        timePicker1 = (TimePicker)findViewById(R.id.timePickerotherResStartTime);
        datePicker1 = (DatePicker)findViewById(R.id.datePickerOtherResStartDate);

        timePicker2 = (TimePicker)findViewById(R.id.timePickerOtherResEndTime);
        datePicker2 = (DatePicker)findViewById(R.id.datePickerOtherResEndDate);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);

        Button tripDonebutton = (Button) findViewById(R.id.buttonOtherResSave);
        tripDonebutton.setOnClickListener(this);
    }

    public void onClick(View v) {

        db = new TripDbHelper(this);

        startDate = (datePicker1.getMonth()+1)+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear();
        startTime = timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();
        endDate = (datePicker2.getMonth()+1)+":"+datePicker2.getDayOfMonth()+":"+datePicker2.getYear();
        endTime = timePicker2.getCurrentHour()+":"+timePicker2.getCurrentMinute();

        EditText eStartDate = (EditText) findViewById(R.id.editTextOtherResTypeofEvent);
        typeofEvent = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextOtherResAddress);
        address = eEndDate.getText().toString();
        EditText eAddress = (EditText) findViewById(R.id.editTextOtherResCity);
        city = eAddress.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextOtherResState);
        state = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.editTextOtherResCountry);
        country = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextOtherResNotes);
        notes = eCountry.getText().toString();

        OtherResInfo info = new OtherResInfo();

        info.startDate = startDate;
        info.startTime = startTime;
        info.endDate = endDate;
        info.endTime = endTime;
        info.address = address;
        info.city =  city;
        info.state = state;
        info.country = country;
        info.typeofEvent = typeofEvent;
        info.notes = notes;
        info.eventId =  Long.parseLong(getIntent().getExtras().get("eventId").toString());

        db.addOtherResInfo(info);

        startTime = null;
        startDate = null;
        endDate = null;
        endTime = null;
        address = null;
        city = null;
        state = null;
        country = null;
        typeofEvent = null;
        notes = null;

        finish();
        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));

        //Intent intent = new Intent(OtherReservationActivity.this, AddEditEventActivity.class);
        //startActivity(intent);

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
