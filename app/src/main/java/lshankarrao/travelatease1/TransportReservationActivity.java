package lshankarrao.travelatease1;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

/**
 * Created by pooja on 5/21/16.
 */
public class TransportReservationActivity extends ActionBarActivity implements View.OnClickListener{

    TripDbHelper db;

    String fromPlace, toPlace, fromDate, fromTime, toDate, toTime;
    String typeofTransport, notes, confNo, flightNo;
    TimePicker timePicker1, timePicker2;
    DatePicker datePicker1, datePicker2;
    long eventId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_reservations);

        timePicker1 = (TimePicker)findViewById(R.id.TimePickerTransportReservationFromTime);
        datePicker1 = (DatePicker)findViewById(R.id.DatePickerTransportReservationFromDate);

        timePicker2 = (TimePicker)findViewById(R.id.TimePickerTransportReservationToTime);
        datePicker2 = (DatePicker)findViewById(R.id.DatePickerTransportReservationToDate);

        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);

        //Log.i("pooja", "addTripInfo id : " + timePicker1.getHour() + ":" + timePicker1.getMinute());
        //Log.i("pooja", "addTripInfo id : " + datePicker1.getMonth()+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear());

        Button tripDonebutton = (Button) findViewById(R.id.ButtonTransportReservationSave);
        tripDonebutton.setOnClickListener(this);
    }

    public void onClick(View v) {

        //Log.i("pooja", "transport on click id : " + datePicker1.getMonth()+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear());
        //Log.i("pooja", "transport id : " + timePicker1.getCurrentHour() + ":" + timePicker1.getCurrentMinute());

        db = new TripDbHelper(this);

        EditText eStartDate = (EditText) findViewById(R.id.EditTextTransportReservationFromPlace);
        fromPlace = eStartDate.getText().toString();
        fromDate = (datePicker1.getMonth()+1)+":"+datePicker1.getDayOfMonth()+":"+datePicker1.getYear();
        fromTime = timePicker1.getCurrentHour()+":"+timePicker1.getCurrentMinute();

        //Log.i("pooja", "transport id from info: " + fromPlace + " - " + fromDate + " - " + fromTime);

        EditText eStartDate1 = (EditText) findViewById(R.id.EditTextTransportReservationToPlace);
        toPlace = eStartDate1.getText().toString();
        toDate = (datePicker2.getMonth()+1)+":"+datePicker2.getDayOfMonth()+":"+datePicker2.getYear();
        toTime = timePicker2.getCurrentHour()+":"+timePicker2.getCurrentMinute();

        //Log.i("pooja", "transport id to info: " + toPlace + " - " + toDate + " - " + toTime);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroupTransportReservation);
        //Log.i("pooja", "radiogroup access");
        int checked = radioGroup.getCheckedRadioButtonId();
        //Log.i("pooja", "checked : " + checked);
        typeofTransport = ((RadioButton) findViewById(checked)).getText().toString();
        //Log.i("pooja", "transport id typeofTransport: " + typeofTransport);

        EditText eCity = (EditText) findViewById(R.id.EditTextTransportReservationConfirmationNo);
        confNo = eCity.getText().toString();
        EditText eState = (EditText) findViewById(R.id.EditTextTransportReservationFlightNo);
        flightNo = eState.getText().toString();
        EditText eCountry = (EditText) findViewById(R.id.EditTextTransportReservationNotes);
        notes = eCountry.getText().toString();

        //Log.i("pooja", "transport id conf,flight,notes: " + confNo + ":" + flightNo + ":" + notes);

        //Log.i("pooja", "transport id eventid: " + Long.parseLong(getIntent().getExtras().get("eventId").toString()));


        TransportInfo info = new TransportInfo();

        info.fromPlace = fromPlace;
        info.fromDate =  fromDate;
        info.fromTime = fromTime;
        info.toPlace = toPlace;
        info.toDate = toDate;
        info.toTime = toTime;
        info.typeofTransport = typeofTransport;
        info.confNo = confNo;
        info.flightNo = flightNo;
        info.notes = notes;
        info.eventId =  Long.parseLong(getIntent().getExtras().get("eventId").toString());

        db.addTransportInfo(info);

        fromPlace = null;
        fromTime = null;
        fromDate = null;
        toDate = null;
        toTime = null;
        toPlace = null;
        typeofTransport = null;
        confNo = null;
        flightNo =null;
        notes = null;

        finish();
        //Log.i("pooja", "in addEventInfo id : " + getIntent().getExtras().get("id"));

        //Intent intent = new Intent(TransportReservationActivity.this, AddEditEventActivity.class);
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
