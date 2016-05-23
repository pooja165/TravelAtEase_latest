package lshankarrao.travelatease1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by pooja on 5/21/16.
 */
public class HotelReservationActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;

    String hotel, checkin, checkout, address;
    String confirmationNo, notes;
    //long tripId=0;
    //static long eventId=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservations);

        Button tripDonebutton = (Button) findViewById(R.id.buttonHotelReservationsave);
        tripDonebutton.setOnClickListener(this);
    }

    public void onClick(View v) {

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

        Intent intent = new Intent(HotelReservationActivity.this, AddEditEventActivity.class);
        //intent.putExtra("id", id);
        startActivity(intent);
    }

}
