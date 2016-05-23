package lshankarrao.travelatease1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by pooja on 5/21/16.
 */
public class TransportReservationActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;

    String fromPlace, toPlace, fromDate, fromTime, toDate, toTime;
    String typeofTransport, notes, confNo, flightNo;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    //long tripId=0;
    //static long eventId=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_reservations);

        timePicker1 = (TimePicker)findViewById(R.id.TimePickerTransportReservationFromTime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerTransportReservationFromDate);

        timePicker1.setIs24HourView(true);

        //Log.i("pooja", "addTripInfo id : " + timePicker1.getHour() + ":" + timePicker1.getMinute());
        Log.i("pooja", "addTripInfo id : " + datePicker1.getMonth()+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear());

        Button tripDonebutton = (Button) findViewById(R.id.ButtonTransportReservationSave);
        tripDonebutton.setOnClickListener(this);
    }

    public void onClick(View v) {

        Log.i("pooja", "transport on click id : " + datePicker1.getMonth()+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear());
        Log.i("pooja", "transport id : " + timePicker1.getCurrentHour() + ":" + timePicker1.getCurrentMinute());
/*
        db = new TripDbHelper(this);

        EditText eStartDate = (EditText) findViewById(R.id.editTextHotelReservationhotel);
        hotel = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextHotelReservationaddress);
        address = eEndDate.getText().toString();
        EditText eAddress = (EditText) findViewById(R.id.editTextHotelReservationcheckin);
        checkin = eAddress.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextHotelReservationcheckout);
        checkout = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.editTextHotelReservationconfirmationNo);
        confirmationNo = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextHotelReservationaddNotes);
        notes = eCountry.getText().toString();

        HotelInfo info = new HotelInfo();

        info.address = address;
        info.hotel =  hotel;
        info.checkout = checkout;
        info.checkin = checkin;
        info.confirmationNo = confirmationNo;
        info.notes = notes;
        info.eventId =  Long.parseLong(getIntent().getExtras().get("eventId").toString());

        db.addHotelInfo(info);

        address = null;
        hotel = null;
        checkout = null;
        checkin = null;
        confirmationNo = null;
        notes = null;

        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));

        Intent intent = new Intent(TransportReservationActivity.this, AddEditEventActivity.class);
        //intent.putExtra("id", id);
        startActivity(intent);
        */
    }

}
