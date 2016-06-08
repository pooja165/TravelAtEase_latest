package lshankarrao.travelatease1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by lakshmi on 5/24/2016.
 */
public class SetTripPlanningReminderActivity extends ActionBarActivity{

    Calendar calendar;
    int tripId;
    String tripTitle;
    boolean validInput= true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_planning_reminder);

        Intent intent = getIntent();
        String startTime = intent.getStringExtra("StartTime");
        String startDate = intent.getStringExtra("StartDate");
        Log.i("trip start date ",startDate+"");
        Log.i("trip start time ",startTime+"");

        tripId = intent.getIntExtra("Id", -1);
        if(tripId == -1){
            Log.i("trip id invalid: ",tripId+"");
            return;
        }
        tripTitle = intent.getStringExtra("Title");


        calendar = getCalendar(startDate,startTime);
        long whenTime = calendar.getTimeInMillis();
        Log.i("time given: ", whenTime + " ");

        Button set = (Button) findViewById(R.id.buttonSTPRset);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupReminderChoice);
                String choice;
                int selected=rg.getCheckedRadioButtonId();
                if (selected == -1) {
                    validInput = false;
                    String message = "Please select an option!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else{
                    choice = ((RadioButton)findViewById(selected)).getText().toString();
                    Log.i("choice: ",choice+"");
                    if(choice.equals("hour")){
                        calendar.add(Calendar.HOUR_OF_DAY,-1);
                        Log.i("time: ",calendar.getTime()+"");
                    }
                    else  if(choice.equals("day")){
                        calendar.add(Calendar.DAY_OF_MONTH,-1);
                    }
                    else if(choice.equals("week")){
                        calendar.add(Calendar.WEEK_OF_MONTH,-1);
                    }else if(choice.equals("month")){
                        calendar.add(Calendar.MONTH,-1);
                    }
                    else if(choice.equals("minute")){
                        calendar.add(Calendar.MINUTE,-1);
                    }

                }

                if(validInput == true) {

                    long when = calendar.getTimeInMillis();
                    Log.i("time after sub: ", when + " ");

                    // notification time
                    Random r = new Random();
                    int x = r.nextInt();

                    Intent intentAlarm = new Intent(getApplicationContext(), AlarmReceiver.class);

                    intentAlarm.putExtra("tripId", tripId);
                    intentAlarm.putExtra("tripTitle", tripTitle + x);
                    //intentAlarm.putExtra("notificationID", +x);


                    // create the object
                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                    //set the alarm for particular time
                    alarmManager.set(AlarmManager.RTC_WAKEUP, when, PendingIntent.getBroadcast(getApplicationContext(), x, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA));
                    Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
                    x++;
                    String message = "Reminder Set!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    String message = "Please select an option to Set Reminder!";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                return;
            }
        });
    }

    private Calendar getCalendar(String date, String time) {
        String[] mon_day_year = date.split(":");
        String[] hh_mm = time.split(":");
        Calendar calendar =  new GregorianCalendar();
        calendar.set(Integer.parseInt(mon_day_year[2]),
                (Integer.parseInt(mon_day_year[0])-1),
                Integer.parseInt(mon_day_year[1]),
                Integer.parseInt(hh_mm[0]),
                Integer.parseInt(hh_mm[1]));
//        SimpleDateFormat sdf =  new SimpleDateFormat();
//        Date d;
//        try {
//            d = sdf.parse(date);
//            calendar.setY(d);
//        } catch (ParseException p) {
//            Log.i("Govinda", "Gopala");
//        }

        return calendar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menu != null) {

            menu.findItem(R.id.action_addtrip).setVisible(false);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goBackToItinerary = new Intent(SetTripPlanningReminderActivity.this, ViewTripItineraryActivity.class);
        goBackToItinerary.putExtra("id", tripId);
        startActivity(goBackToItinerary);
    }
}
