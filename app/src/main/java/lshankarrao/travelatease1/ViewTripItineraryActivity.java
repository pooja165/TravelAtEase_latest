package lshankarrao.travelatease1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class ViewTripItineraryActivity extends AppCompatActivity{

    TripDbHelper tripDb;
    Cursor cursor;
    EventListAdapter ela;
    int tripId;
    //private int x = 0;
    String tripTitle;
    String tripStartDate;
    String tripStartTime;
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

        TripInfo tripInfo = tripDb.getTripInfo(tripId);
        TextView titleDisplay = (TextView) findViewById(R.id.textViewVTATitle);
        String s = tripInfo.getTitle();
        titleDisplay.setText(s);

        TextView placeDisplay = (TextView) findViewById(R.id.textViewVTAPlace);
        String t = tripInfo.getCity() + ", "+ tripInfo.getState() +", " + tripInfo.getCountry();
        placeDisplay.setText(t);
        placeDisplay.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        placeDisplay.setSingleLine(true);
        placeDisplay.setMarqueeRepeatLimit(10);
        placeDisplay.setSelected(true);

        TextView durationDisplay = (TextView) findViewById(R.id.textViewVTADuration);
        String u = tripInfo.getStartDate() + " to " + tripInfo.getEndDate();
        durationDisplay.setText(u);
//        durationDisplay.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        durationDisplay.setSingleLine(true);
//        durationDisplay.setMarqueeRepeatLimit(6);
//        durationDisplay.setSelected(true);

        String full = "TITLE: "+ tripInfo.getTitle() + "\n" +
                "PLACE: "+ tripInfo.getCity() + "\n" +
                "From: "+ tripInfo.getStartDate() + "\t"+
                "To: "+ tripInfo.getEndDate();
        titleDisplay.setText(s);
        Log.i("string:  ",s);

        tripTitle = tripInfo.getTitle();
        tripStartDate = tripInfo.getStartDate();
        tripStartTime = tripInfo.getStartTime();

        ListView eventListView = (ListView) findViewById(R.id.listViewTLAEventList);
        cursor = tripDb.fetchAllEventsForTrip(tripId);
        Log.i("Creating ", cursor.getCount() + "");
        ela = new EventListAdapter(this, cursor, 0);
        //tla.notifyDataSetChanged();
        eventListView.setAdapter(ela);

//        List<EventInfo> eventInfos = tripDb.getEventInfo(tripId);
/*        for(EventInfo ev: eventInfos){
            Log.i("event totle: ", ev.title);
            Log.i("event id ", ev.id+"");
        }*/


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
            Intent intent = new Intent(ViewTripItineraryActivity.this, EditTripActivity.class);
            intent.putExtra("id", tripId);
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
            return true;
        }
        else if (id == R.id.addEvent) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditEventActivity.class);
            intent.putExtra("id", tripId);
            startActivity(intent); //startActivityForResult
            return true;

        }else if(id == R.id.setTripPlanningReminders){
            //calling set planning reminder activity
            Intent newIntent = new Intent(ViewTripItineraryActivity.this,SetTripPlanningReminderActivity.class);
            newIntent.putExtra("StartDate", tripStartDate);
            newIntent.putExtra("StartTime", tripStartTime);
            newIntent.putExtra("Id", tripId);
            newIntent.putExtra("Title", tripTitle);
            startActivity(newIntent);


//            Calendar calendar =  Calendar.getInstance();
//            //calendar.set(2014,Calendar.getInstance().get(Calendar.MONTH),Calendar.SUNDAY , 8, 00, 00);
//            int seconds = calendar.get(Calendar.SECOND);
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            int minute = calendar.get(Calendar.MINUTE);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int month = calendar.get(Calendar.MONTH);
//            int year = calendar.get(Calendar.YEAR);
//            calendar.set(year,month,day,hour,minute+1,seconds);
//
//            long when = calendar.getTimeInMillis();         // notification time
//            Random r = new Random();
//            int x = (r.nextInt(100) + 1);
//
//            Log.i("time", when+" ");
//
//            Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);
//
//            intentAlarm.putExtra("tripId",tripId);
//            intentAlarm.putExtra("tripTitle", tripTitle + x);
//            intentAlarm.putExtra("notificationID", + x);
//
//
//
//            // create the object
//            AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//            //set the alarm for particular time
//            alarmManager.set(AlarmManager.RTC_WAKEUP,when, PendingIntent.getBroadcast(getApplicationContext(),x,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA));
//            Toast.makeText(getApplicationContext(), "Alarm Set 2 " + x, Toast.LENGTH_SHORT).show();
//            x++;

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