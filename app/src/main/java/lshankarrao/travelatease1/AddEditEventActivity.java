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
public class AddEditEventActivity extends AppCompatActivity implements View.OnClickListener{

    TripDbHelper db;

    String city, state, country, address;
    String startDate, endDate;
    long tripId=0;
    long eventId=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_event);

        Button tripDonebutton = (Button) findViewById(R.id.buttonAddEditEventsave);
        tripDonebutton.setOnClickListener(this);

        Button hotelReservationbutton = (Button) findViewById(R.id.buttonAddEditEventhotel);

        //hotelReservationbutton.setEnabled(false);
        hotelReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, HotelReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });


        Button transportReservationbutton = (Button) findViewById(R.id.buttonAddEditEventtransport);
        //transportReservationbutton.setEnabled(false);
        transportReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, TransportReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });
        Button otherReservationbutton = (Button) findViewById(R.id.buttonAddEditEventother);
        //otherReservationbutton.setEnabled(false);
        otherReservationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditEventActivity.this, OtherReservationActivity.class);
                intent.putExtra("eventId", eventId);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {

        db = new TripDbHelper(this);

        EditText eStartDate = (EditText) findViewById(R.id.editTextAddEditEventstartDate);
        startDate = eStartDate.getText().toString();
        EditText eEndDate = (EditText) findViewById(R.id.editTextAddEditEventendDate);
        endDate = eEndDate.getText().toString();
        EditText eAddress = (EditText) findViewById(R.id.editTextAddEditEventaddress);
        address = eAddress.getText().toString();
        EditText eCity = (EditText) findViewById(R.id.editTextAddEditEventcity);
        city = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.editTextAddEditEventstate);
        state = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.editTextAddEditEventcountry);
        country = eCountry.getText().toString();

        EventInfo info = new EventInfo();

        info.address = address;
        info.city =  city;
        info.state = state;
        info.country = country;
        info.startDate = startDate;
        info.endDate = endDate;
        info.tripId =  Long.parseLong(getIntent().getExtras().get("id").toString());

        eventId = db.addEventInfo(info);

        address = null;
        city = null;
        state = null;
        country = null;
        startDate = null;
        endDate = null;
        tripId=0;

        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));
        /*
        Intent intent = new Intent(AddEditTripActivity.this, AddEditEventActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);*/
    }

}
