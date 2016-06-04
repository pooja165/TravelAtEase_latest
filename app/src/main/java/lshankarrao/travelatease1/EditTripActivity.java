package lshankarrao.travelatease1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by lakshmi on 5/25/2016.
 */
public class EditTripActivity extends AppCompatActivity {

    TripDbHelper tripDbHelper;
    TripInfo tripInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_trip);

        Intent intent = getIntent();
        int tripID = intent.getIntExtra("id", -1);
        if(tripID == -1){
            Log.i("trip id invalid",tripID+"");
            return;
        }

        tripDbHelper = new TripDbHelper(this);
        tripInfo = tripDbHelper.getTripInfo(tripID);

        EditText tvTitle = (EditText)findViewById(R.id.editTextAddEditTriptitle);
        tvTitle.setText(tripInfo.getTitle());

        EditText tvcity = (EditText)findViewById(R.id.editTextAddEditTripcity);
        tvcity.setText(tripInfo.getCity());

        EditText tvstate = (EditText)findViewById(R.id.editTextAddEditTripstate);
        tvstate.setText(tripInfo.getState());

        EditText tvcountry = (EditText)findViewById(R.id.editTextAddEditTripcountry);
        tvstate.setText(tripInfo.getCountry());

        EditText tvnotes = (EditText)findViewById(R.id.editTextAddEditTripnotes);
        tvstate.setText(tripInfo.getNotes());
    }
}
