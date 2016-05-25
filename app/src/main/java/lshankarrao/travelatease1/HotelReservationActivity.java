package lshankarrao.travelatease1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * Created by pooja on 5/21/16.
 */
public class HotelReservationActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;

    String hotel, checkin_date, checkout_date, address;
    String confirmationNo, notes, checkin_time, checkout_time;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    //long tripId=0;
    //static long eventId=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservations);

        timePicker1 = (TimePicker)findViewById(R.id.TimePickerHotelReservationstime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerHotelReservationsdate);

        timePicker2 = (TimePicker)findViewById(R.id.TimePickerHotelReservationetime);
        datePicker2 = (DatePicker)findViewById(R.id.DatePickerHotelReservationedate);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);


        Button tripDonebutton = (Button) findViewById(R.id.buttonHotelReservationsave);
        tripDonebutton.setOnClickListener(this);
    }

    public void onClick(View v) {

        db = new TripDbHelper(this);

        EditText eStartDate = (EditText) findViewById(R.id.editTextHotelReservationhotel);
        hotel = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextHotelReservationaddress);
        address = eEndDate.getText().toString();
        /*
        EditText eAddress = (EditText) findViewById(R.id.editTextHotelReservationcheckin);
        checkin = eAddress.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextHotelReservationcheckout);
        checkout = eCity.getText().toString();*/
        EditText eState = (EditText) findViewById(R.id.editTextHotelReservationconfirmationNo);
        confirmationNo = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextHotelReservationaddNotes);
        notes = eCountry.getText().toString();

        checkin_date = (datePicker1.getMonth()+1)+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear();
        checkin_time = timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();

        checkout_date = (datePicker2.getMonth()+1)+":"+datePicker2.getDayOfMonth()+":"+datePicker2.getYear();
        checkin_time = timePicker2.getCurrentHour()+":"+timePicker2.getCurrentMinute();

        HotelInfo info = new HotelInfo();

        info.address = address;
        info.hotel =  hotel;
        info.checkout_date = checkout_date;
        info.checkin_date = checkin_date;
        this.checkin_time = checkin_time;
        this.checkout_time = checkout_time;
        info.confirmationNo = confirmationNo;
        info.notes = notes;
        info.eventId =  Long.parseLong(getIntent().getExtras().get("eventId").toString());

        db.addHotelInfo(info);

        address = null;
        hotel = null;
        checkout_time = null;
        checkin_time = null;
        checkout_date = null;
        checkin_date = null;

        confirmationNo = null;
        notes = null;

        finish();

        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));

        //Intent intent = new Intent(HotelReservationActivity.this, AddEditEventActivity.class);
        //intent.putExtra("id", id);
        //startActivity(intent);
    }

}
