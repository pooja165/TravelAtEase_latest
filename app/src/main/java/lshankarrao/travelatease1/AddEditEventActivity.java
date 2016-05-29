package lshankarrao.travelatease1;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by pooja on 5/21/16.
 */
public class AddEditEventActivity extends ActionBarActivity implements View.OnClickListener{

    TripDbHelper db;
    int tripId;
    long eventId;

    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    Button hotelReservationbutton, transportReservationbutton, otherReservationbutton, doneButton;
    Button checkweather, planningRem, saveButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        timePicker1 = (TimePicker)findViewById(R.id.TimePickerAddEditEventstime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerAddEditEventsdate);

        timePicker2 = (TimePicker)findViewById(R.id.TimePickerAddEditEventetime);
        datePicker2 = (DatePicker)findViewById(R.id.DatePickerAddEditEventedate);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);

        checkweather = (Button) findViewById(R.id.buttonAddEditEventweather);
        planningRem = (Button) findViewById(R.id.buttonAddEditEventplanningReminders);

        saveButton = (Button) findViewById(R.id.buttonAddEditEventsave);
        saveButton.setOnClickListener(this);

        hotelReservationbutton = (Button) findViewById(R.id.buttonAddEditEventhotel);

        tripId = getIntent().getIntExtra("id", -1);
        if(tripId == -1){
            Log.i(" AEEA invalid trip id", tripId+"");
            return;
        }

        //hotelReservationbutton.setEnabled(false);
        hotelReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, HotelReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });


        transportReservationbutton = (Button) findViewById(R.id.buttonAddEditEventtransport);
        //transportReservationbutton.setEnabled(false);
        transportReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, TransportReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });
        otherReservationbutton = (Button) findViewById(R.id.buttonAddEditEventother);
        //otherReservationbutton.setEnabled(false);
        otherReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, OtherReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });
        doneButton = (Button) findViewById(R.id.buttonAddEditEventDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditEventActivity.this, ViewTripItineraryActivity.class);
                intent.putExtra("id", tripId);
                //intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });

        hotelReservationbutton.setEnabled(false);
        transportReservationbutton.setEnabled(false);
        otherReservationbutton.setEnabled(false);
        checkweather.setEnabled(false);
        planningRem.setEnabled(false);
        doneButton.setEnabled(false);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClick(View v) { // Save button on click.

        db = new TripDbHelper(this);
        String city, state, country, address, title;
        String startDate, endDate, startTime, endTime;

        EditText etitle = (EditText) findViewById(R.id.editTextAddEditEventtitle);
        title = etitle.getText().toString();
        /*
        EditText eEndDate = (EditText) findViewById(R.id.editTextAddEditEventendDate);
        endDate = eEndDate.getText().toString();*/
        EditText eAddress = (EditText) findViewById(R.id.editTextAddEditEventaddress);
        address = eAddress.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextAddEditEventcity);
        city = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.editTextAddEditEventstate);
        state = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextAddEditEventcountry);
        country = eCountry.getText().toString();

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

        EventInfo info = new EventInfo();

        info.title =title;
        info.address = address;
        info.city =  city;
        info.state = state;
        info.country = country;
        info.startDate = startDate;
        info.endDate = endDate;
        info.startTime = startTime;
        info.endTime = endTime;
        info.stTimeMillis = stTimeMillis;
        info.endTimeMillis = endTimeMillis;
        info.tripId =  tripId ;//Integer.parseInt(getIntent().getExtras().get("id").toString());

        eventId = db.addEventInfo(info);


        hotelReservationbutton.setEnabled(true);
        transportReservationbutton.setEnabled(true);
        otherReservationbutton.setEnabled(true);
        checkweather.setEnabled(true);
        planningRem.setEnabled(true);
        saveButton.setEnabled(false);
        doneButton.setEnabled(true);

        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));
        /*
        Intent intent = new Intent(AddEditTripActivity.this, AddEditEventActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);*/
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
