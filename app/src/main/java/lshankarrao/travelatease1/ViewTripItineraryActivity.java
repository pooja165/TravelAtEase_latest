package lshankarrao.travelatease1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by vijay on 5/23/2016.
 */
public class ViewTripItineraryActivity extends AppCompatActivity{

    TripDbHelper tripDb;
    Cursor cursor;
    EventListAdapter ela;
    int tripId;
    //private int x = 0;
    String tripTitle;
    final int REMINDER_DURATION =12345;
    String choice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip_activity);
        tripDb = new TripDbHelper(this);
//eventId //id
        Intent tripIntent = getIntent();
        tripId = tripIntent.getIntExtra("id", -1);
        if(tripId == -1){
            Log.i("invalid trip id","View Trip activity");
            return;
        }
        Log.i("tripId: VTA ", tripId+"");
        TextView detailsDisplay = (TextView) findViewById(R.id.textViewVTADetailsDisplay);
        TripInfo tripInfo = tripDb.getTripInfo(tripId);
        String s = "TITLE: "+ tripInfo.getTitle() + "\n" +
                "PLACE: "+ tripInfo.getCity() + "\n" +
                "From: "+ tripInfo.getStartDate() + "\t"+
                "To: "+ tripInfo.getEndDate();
        detailsDisplay.setText(s);
        Log.i("string:  ",s);

        tripTitle = tripInfo.getTitle();

        ListView eventListView = (ListView) findViewById(R.id.listViewTLAEventList);
        cursor = tripDb.fetchAllEventsForTrip(tripId);
        Log.i("Creating ", cursor.getCount() + "");
        ela = new EventListAdapter(this, cursor, 0);
        //tla.notifyDataSetChanged();
        eventListView.setAdapter(ela);

//        List<EventInfo> eventInfo = tripDb.getEventInfo(tripId);
//        if (eventInfo != null) {
//            for (EventInfo info : eventInfo) {
//
//            }
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_view_trip_itinerary, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.editTrip) {
            Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditTripActivity.class);
            intent.putExtra("id", tripId);
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
            return true;
        }
        else if (id == R.id.addEvent) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditEventActivity.class);
            startActivity(intent); //startActivityForResult


            return true;
        }else if(id == R.id.setTripPlanningReminders){
            //calling set planning reminder activity
            //Intent newIntent = new Intent(ViewTripItineraryActivity.this,SetTripPlanningReminderActivity.class);
            //startActivityForResult(newIntent,REMINDER_DURATION);

            Random r = new Random();
            int x = (r.nextInt(100) + 1);
            cursor = tripDb.fetchAllTrips();
            Calendar calendar =  Calendar.getInstance();
            //calendar.set(2014,Calendar.getInstance().get(Calendar.MONTH),Calendar.SUNDAY , 8, 00, 00);
            int seconds = calendar.get(Calendar.SECOND);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            calendar.set(year,month,day,hour,minute+1,seconds);
            long when = calendar.getTimeInMillis();         // notification time


            Log.i("time", when+" ");

            Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);

            intentAlarm.putExtra("tripId",tripId);
            intentAlarm.putExtra("tripTitle", tripTitle + x);
            intentAlarm.putExtra("notificationID", + x);



            // create the object
            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            //set the alarm for particular time
            alarmManager.set(AlarmManager.RTC_WAKEUP,when, PendingIntent.getBroadcast(getApplicationContext(),x,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA));
            Toast.makeText(getApplicationContext(), "Alarm Set 2 " + x, Toast.LENGTH_SHORT).show();
            x++;

            // ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(notificationId);
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != 1234) {
//            toast("Reply is not from expected activity");
//            return;
//        }
//
//        if (resultCode != Activity.RESULT_OK) {
//            if (resultCode == Activity.RESULT_CANCELED) {
//                toast("Result code is not OK : RESULT_CANCELED");
//            } else {
//                toast("Result code is not OK : " + resultCode);
//            }
//            return;
//        }
//
//        choice = data.getDataString();
//    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}