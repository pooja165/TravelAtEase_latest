package lshankarrao.travelatease1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class ViewTripItineraryActivity extends ActionBarActivity2 {

    TripDbHelper tripDb;
    Cursor cursor;
    EventListAdapter ela;
    int tripId;
    //private int x = 0;
    String tripTitle;
    String tripStartDate;
    String tripStartTime;
    String tripTimings;
    String tripPlace;
    final int REMINDER_DURATION = 12345;
    String choice;
    String mfullTripDetails;
    String mCompleteEventDetails ="";// = "Trip includes the below Events" + "\n";
    List<String> mAllEventsDetailsInOnePlace;//not used
    TripInfo tripInfo;
    String endTime,stTime;
    String[] st_mon_day_year,end_mon_day_year;
    String tripKind = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip_activity);
        tripDb = new TripDbHelper(this);
        //eventId //id
        Intent tripIntent = getIntent();
        tripId = tripIntent.getIntExtra("id", -1);
        if (tripId == -1) {
            Log.i("invalid trip id", "View Trip activity");
            return;
        }
        Log.i("tripId: VTA ", tripId + "");
        tripKind =tripIntent.getStringExtra("tripKind");

        tripInfo = tripDb.getTripInfo(tripId);
        TextView titleDisplay = (TextView) findViewById(R.id.textViewVTATitle);
        tripTitle = tripInfo.getTitle();
        titleDisplay.setText(tripTitle);
        if (tripTitle == null) {
            Log.i("empty s ", "");
        }

        TextView placeDisplay = (TextView) findViewById(R.id.textViewVTAPlace);
        tripPlace = tripInfo.getCity() + ", " + tripInfo.getState() + ", " + tripInfo.getCountry();
        placeDisplay.setText(tripPlace);
        placeDisplay.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        placeDisplay.setSingleLine(true);
        placeDisplay.setMarqueeRepeatLimit(10);
        placeDisplay.setSelected(true);

        TextView durationDisplay = (TextView) findViewById(R.id.textViewVTADuration);
        tripTimings = tripInfo.getStartDate() + " " + tripInfo.getStartTime() + " to " + tripInfo.getEndDate() + " " + tripInfo.getEndTime();
        durationDisplay.setText(tripTimings);

        st_mon_day_year = tripDb.getDateFromMilli(tripInfo.stTimeMillis,"MMM/dd/yy");
        end_mon_day_year = tripDb.getDateFromMilli(tripInfo.endTimeMillis,"MMM/dd/yy");

        stTime = tripDb.getTimeFromMilli(tripInfo.stTimeMillis,"hh:mm aaa");
        endTime = tripDb.getTimeFromMilli(tripInfo.endTimeMillis,"hh:mm aaa");
        Log.i("start timings: ", st_mon_day_year[0]+st_mon_day_year[1]+st_mon_day_year[2]+stTime);
        Log.i("end timings: ", end_mon_day_year[0]+end_mon_day_year[1]+end_mon_day_year[2]+endTime);

//        durationDisplay.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//        durationDisplay.setSingleLine(true);
//        durationDisplay.setMarqueeRepeatLimit(6);
//        durationDisplay.setSelected(true);

        mfullTripDetails = tripInfo.getTitle() + "\n" +
                "PLACE: " + tripPlace + "\n" +
                "Timings: " + tripTimings + "\n" + "\n";
        titleDisplay.setText(tripTitle);
        if (tripTitle == null || tripTitle.isEmpty()) {
            Log.i("empty s ", "");
        }

        Log.i("string:  ", tripTitle);

        this.tripTitle = tripInfo.getTitle();
        tripStartDate = tripInfo.getStartDate();
        tripStartTime = tripInfo.getStartTime();

        ListView eventListView = (ListView) findViewById(R.id.listViewTLAEventList);
        cursor = tripDb.fetchAllEventsForTrip(tripId);
        Log.i("Creating ", cursor.getCount() + "");
        ela = new EventListAdapter(this, cursor, 0);
        //tla.notifyDataSetChanged();
        eventListView.setAdapter(ela);

        String eventDate;
        String eventPlace;
        String eventTitle;

        //mAllEventsDetailsInOnePlace = new ArrayList<String>();
        List<EventInfo> eventInfos = tripDb.getEventInfo(tripId);
        if (eventInfos != null) {
            for (EventInfo ev : eventInfos) {
                String[] ev_st_mon_day_year = tripDb.getDateFromMilli(ev.stTimeMillis,"MMM/dd/yy");
                Log.i("abracada: ",ev.getStTimeMillis()+"");//ev_st_mon_day_year[0]+ev_st_mon_day_year[1]+ev_st_mon_day_year[2]+
                String[] ev_end_mon_day_year = tripDb.getDateFromMilli(ev.endTimeMillis,"MMM/dd/yy");
                Log.i("dabba", ev_end_mon_day_year[0]+ev_end_mon_day_year[1]+ev_end_mon_day_year[2]);

                String ev_stTime = tripDb.getTimeFromMilli(ev.getStTimeMillis(),"hh:mm aaa");
                String ev_endTime = tripDb.getTimeFromMilli(ev.getEndTimeMillis(),"hh:mm aaa");
                eventPlace = ev.city + ", " + ev.state + ", " + ev.country;
                eventDate = "<p>"+ev_st_mon_day_year[0] +" "+ ev_st_mon_day_year[1] +", "+ev_st_mon_day_year[2]+" - "+ ev_end_mon_day_year[0] +" "+ ev_end_mon_day_year[1] +", "+ev_end_mon_day_year[2]+"<br>"+ev_stTime+"</p>";
                // "<h2 style=\"color:blue\"><i>Timings:</i> " + ev.startDate + " @ " + ev.startTime + " to " + ev.endDate + "<i><b>@</b></i>" + ev.endTime + "</h2>";
                eventTitle = ev.title;
                Log.i("event title: ", ev.title);
                Log.i("event id ", ev.id + "");
                mCompleteEventDetails = mCompleteEventDetails +
                        "<br><h4>" + eventTitle + "</h4>"+
                        "" + eventDate +
                        "" + "    "+eventPlace +
                        "<br>" + "    "+ev_stTime +" to " + ev_endTime+
                        "<br>" + "     Note: "+"Get some snacks! :) ";

                //mAllEventsDetailsInOnePlace.add(mCompleteEventDetails);
                Cursor hotelCursor = tripDb.fetchAllHotelsForEvent(ev.id);
                if (hotelCursor.getCount() > 0) {
                    String hotelName, hotelPlace, hotelDate, hotelCheckinTime, hotelTimings, hotelConfirmationNo;
                    List<HotelInfo> hotelInfos = tripDb.getHotelsInfo(ev.id);
                    String allHotelDetails = ""; //= "\n Hotel Reservation details for the Event: " + "\n";
                    for (HotelInfo hotelInfo : hotelInfos) {

                        hotelName = "Hotel: " + hotelInfo.hotel;
                        hotelPlace = "Address: " + hotelInfo.address;
                        hotelTimings = "Check-in: " + hotelInfo.checkin_date + " at " + hotelInfo.checkin_time +
                                "\nCheck-out: " + hotelInfo.checkout_date + " at " + hotelInfo.checkout_time;
                        hotelDate = hotelInfo.checkin_date + " - "+ hotelInfo.checkout_date;
                        hotelCheckinTime = "Check-in: " + hotelInfo.checkin_time ;
                        hotelConfirmationNo = "Confirmation No: " + hotelInfo.confirmationNo;


                        allHotelDetails = allHotelDetails+
                                "<br>"+
                                "<br>"+"    "+hotelName +
                                "<br>"+"    "+hotelDate+
                                "<br>"+"        "+hotelPlace +
                                "<br>"+"        "+hotelConfirmationNo+
                                "<br>"+"        "+hotelTimings ;
                    }
                    mCompleteEventDetails = mCompleteEventDetails + allHotelDetails;
                }

            }
        }


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
            intent.putExtra("purpose","edit");
            intent.putExtra("id", tripId);
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
            return true;
        } else if (id == R.id.action_home) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
            startActivity(intent); //startActivityForResult
            return true;
        } else if (id == R.id.addEvent) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditEventActivity.class);
            intent.putExtra("id", tripId);
            startActivity(intent); //startActivityForResult
            return true;

        } else if (id == R.id.setTripPlanningReminders) {
            //calling set planning reminder activity
            Intent newIntent = new Intent(ViewTripItineraryActivity.this, SetTripPlanningReminderActivity.class);
            newIntent.putExtra("StartDate", tripStartDate);
            newIntent.putExtra("StartTime", tripStartTime);
            newIntent.putExtra("Id", tripId);
            newIntent.putExtra("Title", tripTitle);
            startActivity(newIntent);

        } else if (id == R.id.shareItinerary) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter email ids below(comma separated)");

// Set up the input
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String emailAddrs = input.getText().toString();

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, emailAddrs.split(","));
                    i.putExtra(Intent.EXTRA_SUBJECT, tripTitle + " Itinerary");
                    //i.putExtra(Intent.EXTRA_TEXT   , mfullTripDetails+ mCompleteEventDetails);
                    i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
                            .append("<h1>"+tripInfo.title+"</h1>")
                            .append("<b>"+tripInfo.city+", "+tripInfo.state+"</b>")
                            .append("<p><b>"+st_mon_day_year[0] +" "+ st_mon_day_year[1] +" " +st_mon_day_year[2] +" - "+ end_mon_day_year[0] +" "+ end_mon_day_year[1] +", "+end_mon_day_year[2]+"</b></p>")
                            .append(mCompleteEventDetails)
                            .toString()));
                    //("<font color='#0023FF'>mfullTripDetails</font>"+mfullTripDetails + mCompleteEventDetails + mAllHotelDetails));
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(),
                                "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        } else if (id == R.id.locationBasedNotification) {
            Intent locationIntent = new Intent(ViewTripItineraryActivity.this,
                    LocationBasedNotifierActivity.class);
            String place = tripInfo.city + " " + tripInfo.state + " " + tripInfo.country;
            locationIntent.putExtra("tripPlace", place);
            locationIntent.putExtra("tripId", tripId);
            startActivity(locationIntent);
        } else if(id == R.id.deleteTrip){
            cursor = tripDb.fetchAllEventsForTrip(tripId);
            List<EventInfo> eventInfos = tripDb.getEventInfo(tripId);
            if (eventInfos != null) {
                for(EventInfo ev: eventInfos){
                    //get hotel details
                    Cursor hotelCursor = tripDb.fetchAllHotelsForEvent(ev.id);
                    if (hotelCursor.getCount() > 0) {
                        //delete all entries
                        List<HotelInfo> hotelInfos = tripDb.getHotelsInfo(ev.id);
                        for(HotelInfo hotel: hotelInfos){
                            tripDb.deleteEntry("hotelInfo", hotel.getId());
                        }
                    }
//                    //get transport details + delete all entries
//                    Cursor transportCursor = tripDb.fetchAllTransportResForEvent(ev.id);
//                    if (transportCursor.getCount() > 0) {
//                        //delete all entries
//                    }
//                    //get other res details + delete all entries
//                    Cursor otherResCursor = tripDb.fetchAllOtherResForEvent(ev.id);
//                    if (otherResCursor.getCount() > 0) {
//                        //delete all entries
//                    }
                    tripDb.deleteEntry("eventInfo", ev.getId());
                }
            }
            tripDb.deleteEntry("tripInfo", tripId);
            if(tripKind!=null) {

                Intent intent = new Intent(ViewTripItineraryActivity.this, TripListActivity.class);
                intent.putExtra("tripKind", tripKind);
                //TODO go to triplistActivity MUST:-pass tripkind.
                startActivity(intent); // when back pressed/save pressed go to TripListActivity
            }else {
                Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
                //TODO go to triplistActivity MUST:-pass tripkind.
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);
    }


    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(tripKind!=null) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, TripListActivity.class);
            intent.putExtra("tripKind", tripKind);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
        }else {
            Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent);
        }

    }

//    public String writeItineraryToFile() {
//        String file = Environment.getExternalStorageDirectory().getAbsolutePath();
//        OutputStream out = null;
//        try {
//            out = new BufferedOutputStream(new FileOutputStream(file + "/" + tripInfo.title));
//
//            finally{
//                if (out != null) {
//                    out.close();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static String createHTML() {//static not needed.
//        String format =
//                "<html>" +
//                        "<body>" +
//                        "<h2 style=\"background-color:cyan\">\n" +
//                        "Background-color set by using cyan\n" +
//                        "</h2>" +
//                        "</body>" +
//                "</html>";
//        return String.format(format, study.title, study.name);
//    }
}