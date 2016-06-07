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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    String mCompleteEventDetails = "";// = "Trip includes the below Events" + "\n";
    List<String> mAllEventsDetailsInOnePlace;//not used
    TripInfo tripInfo;
    String endTime, stTime;
    String[] st_mon_day_year, end_mon_day_year;
    String tripKind = null;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_trip_activity);
        mDrawerList = (ListView) findViewById(R.id.navList);
        addDrawerItems();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ViewTripItineraryActivity.this, "Time for an upgrade!" + position, Toast.LENGTH_SHORT).show();
                navigationBarOptionsHandling(position);
            }
        });

        tripDb = new TripDbHelper(this);
        //eventId //id
        Intent tripIntent = getIntent();
        tripId = tripIntent.getIntExtra("id", -1);
        if (tripId == -1) {
            Log.i("invalid trip id", "View Trip activity");
            return;
        }
        Log.i("tripId: VTA ", tripId + "");
        tripKind = tripIntent.getStringExtra("tripKind");

        tripInfo = tripDb.getTripInfo(tripId);
        TextView titleDisplay = (TextView) findViewById(R.id.textViewVTATitle);
        tripTitle = tripInfo.getTitle();
        titleDisplay.setText(tripTitle);
        if (tripTitle == null) {
            Log.i("empty s ", "");
        }

        st_mon_day_year = tripDb.getDateFromMilli(tripInfo.stTimeMillis, "MMM/dd/yyyy");
        end_mon_day_year = tripDb.getDateFromMilli(tripInfo.endTimeMillis, "MMM/dd/yyyy");


        TextView placeDisplay = (TextView) findViewById(R.id.textViewVTAPlace);
        tripPlace = tripInfo.getCity() + ", " + tripInfo.getState() + ", " + tripInfo.getCountry();
        placeDisplay.setText(tripPlace);
        placeDisplay.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        placeDisplay.setSingleLine(true);
        placeDisplay.setMarqueeRepeatLimit(10);
        placeDisplay.setSelected(true);

        TextView durationDisplay = (TextView) findViewById(R.id.textViewVTADuration);
        tripTimings = st_mon_day_year[0] + " " + st_mon_day_year[1] + ", " + st_mon_day_year[2] +
                " to " + end_mon_day_year[0] + " " + end_mon_day_year[1] + ", " + end_mon_day_year[2];
                //tripInfo.getStartDate() + " " + tripInfo.getStartTime() + " to " + tripInfo.getEndDate() + " " + tripInfo.getEndTime();
        durationDisplay.setText(tripTimings);


        stTime = tripDb.getTimeFromMilli(tripInfo.stTimeMillis, "hh:mm aaa");
        endTime = tripDb.getTimeFromMilli(tripInfo.endTimeMillis, "hh:mm aaa");
        Log.i("start timings: ", st_mon_day_year[0] + st_mon_day_year[1] + st_mon_day_year[2] + stTime);
        Log.i("end timings: ", end_mon_day_year[0] + end_mon_day_year[1] + end_mon_day_year[2] + endTime);

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
                String[] ev_st_mon_day_year = tripDb.getDateFromMilli(ev.stTimeMillis, "MMM/dd/yy");
                Log.i("abracada: ", ev.getStTimeMillis() + "");//ev_st_mon_day_year[0]+ev_st_mon_day_year[1]+ev_st_mon_day_year[2]+
                String[] ev_end_mon_day_year = tripDb.getDateFromMilli(ev.endTimeMillis, "MMM/dd/yy");
                Log.i("dabba", ev_end_mon_day_year[0] + ev_end_mon_day_year[1] + ev_end_mon_day_year[2]);

                String ev_stTime = tripDb.getTimeFromMilli(ev.getStTimeMillis(), "hh:mm aaa");
                String ev_endTime = tripDb.getTimeFromMilli(ev.getEndTimeMillis(), "hh:mm aaa");
                eventPlace = ev.city + ", " + ev.state + ", " + ev.country;
                eventDate =
                        "<p>" + ev_st_mon_day_year[0] + " " + ev_st_mon_day_year[1] + ", " + ev_st_mon_day_year[2] +
                        " - " + ev_end_mon_day_year[0] + " " + ev_end_mon_day_year[1] + ", " + ev_end_mon_day_year[2] +
                                "<br>" + ev_stTime + "</p>";
                // "<h2 style=\"color:blue\"><i>Timings:</i> " + ev.startDate + " @ " + ev.startTime + " to " + ev.endDate + "<i><b>@</b></i>" + ev.endTime + "</h2>";
                eventTitle = ev.title;
                Log.i("event title: ", ev.title);
                Log.i("event id ", ev.id + "");
                mCompleteEventDetails = mCompleteEventDetails +
                        "<br><h4>" + eventTitle + "</h4>" +
                        "" + eventDate +
                        "" + "    " + eventPlace +
                        "<br>" + "    " + ev_stTime + " to " + ev_endTime +
                        "<br>" + "     Note: " + "Get some snacks! :) ";

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
                        hotelDate = hotelInfo.checkin_date + " - " + hotelInfo.checkout_date;
                        hotelCheckinTime = "Check-in: " + hotelInfo.checkin_time;
                        hotelConfirmationNo = "Confirmation No: " + hotelInfo.confirmationNo;


                        allHotelDetails = allHotelDetails +
                                "<br>" +
                                "<br>" + "    " + hotelName +
                                "<br>" + "    " + hotelDate +
                                "<br>" + "        " + hotelPlace +
                                "<br>" + "        " + hotelConfirmationNo +
                                "<br>" + "        " + hotelTimings;
                    }
                    mCompleteEventDetails = mCompleteEventDetails + allHotelDetails;
                }



                Cursor transportCursor = tripDb.fetchAllTransportForEvent(ev.id);
                int transportCount = transportCursor.getCount();

                if(transportCount >0){
                    transportCursor.moveToFirst();
                    String typeOfTransport = transportCursor.getString(transportCursor.getColumnIndex("typeofTransport"));
                    String fromPlace = transportCursor.getString(transportCursor.getColumnIndex("fromPlace"));
                    String timings = transportCursor.getString(transportCursor.getColumnIndex("fromDate")) + " at "+
                            transportCursor.getString(transportCursor.getColumnIndex("fromTime"));
                    String confNo = transportCursor.getString(transportCursor.getColumnIndex("confNo"));


                    String transportDetails = "<br>"+"<br>"+
                            typeOfTransport + " Reservation Details: <br>"
                            + fromPlace+"<br>"
                            + timings+ "<br>"
                            + "Conf No: "+confNo+"<br>";

                    mCompleteEventDetails = mCompleteEventDetails + "\n" + transportDetails;
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


    private void navigationBarOptionsHandling(int id) {

        if (id == 0) {
            addEventOption();
        } else if (id == 1) {
            editTripOption();
        } else if (id == 2) {
            //calling set planning reminder activity
            setPlanningReminderOption();
        } else if (id == 3) {
            shareItineraryOption();

        } else if (id == 4) {
            locationBasedNotificationOption();
        } else if (id == 5) {
            deleteTripOption();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_view_trip_itinerary, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.action_home) {
            goHome();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (tripKind != null) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, TripListActivity.class);
            intent.putExtra("tripKind", tripKind);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
        } else {
            Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent);
        }

    }

    private void addDrawerItems() {
        String[] osArray = {"Add Event", "Edit Trip", "Set Planning Reminders", "Share Itinerary", "Turn ON Location-Based Notification", "Delete Trip"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void editTripOption() {
        Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditTripActivity.class);
        intent.putExtra("purpose", "edit");
        intent.putExtra("id", tripId);
        startActivity(intent); // when back pressed/save pressed go to TripListActivity
    }

    private void goHome() {
        Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
        startActivity(intent); //startActivityForResult
    }

    private void addEventOption() {
        Intent intent = new Intent(ViewTripItineraryActivity.this, AddEditEventActivity.class);
        intent.putExtra("id", tripId);
        intent.putExtra("tripKind", tripKind);
        startActivity(intent); //startActivityForResult
    }

    private void setPlanningReminderOption() {
        Intent newIntent = new Intent(ViewTripItineraryActivity.this, SetTripPlanningReminderActivity.class);
        newIntent.putExtra("StartDate", tripStartDate);
        newIntent.putExtra("StartTime", tripStartTime);
        newIntent.putExtra("Id", tripId);
        newIntent.putExtra("Title", tripTitle);
        startActivity(newIntent);
    }

    private void shareItineraryOption() {
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
                        .append("<h1>" + tripInfo.title + "</h1>")
                        .append("<b>" + tripInfo.city + ", " + tripInfo.state + "</b>")
                        .append("<p><b>" + st_mon_day_year[0] + " " + st_mon_day_year[1] + ", " + st_mon_day_year[2] + " - " + end_mon_day_year[0] + " " + end_mon_day_year[1] + ", " + end_mon_day_year[2] + "</b></p>")
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
    }

    private void locationBasedNotificationOption() {
        Intent locationIntent = new Intent(ViewTripItineraryActivity.this,
                LocationBasedNotifierActivity.class);
        String place = tripInfo.city + " " + tripInfo.state + " " + tripInfo.country;
        locationIntent.putExtra("tripPlace", place);
        locationIntent.putExtra("tripId", tripId);
        startActivity(locationIntent);
    }

    private void deleteTripOption() {
        cursor = tripDb.fetchAllEventsForTrip(tripId);
        List<EventInfo> eventInfos = tripDb.getEventInfo(tripId);
        if (eventInfos != null) {
            for (EventInfo ev : eventInfos) {
                //get hotel details
                Cursor hotelCursor = tripDb.fetchAllHotelsForEvent(ev.id);
                if (hotelCursor.getCount() > 0) {
                    //delete all entries
                    List<HotelInfo> hotelInfos = tripDb.getHotelsInfo(ev.id);
                    for (HotelInfo hotel : hotelInfos) {
                        tripDb.deleteEntry("hotelInfo", hotel.getId());
                    }
                }
//                    //get transport details + delete all entries
//                    Cursor transportCursor = tripDb.fetchAllTransportResForEvent(ev.id);
//                    if (transportCursor.getCount() > 0) {
//                        //delete all entries
//                        List<TransportInfo> transportInfos = tripDb.getTransportInfo(ev.id);
//                        for (TransportInfo transport : transportInfos) {
//                            tripDb.deleteEntry("transportInfo", transport.getId());
//                        }
//                    }
//                    //get other res details + delete all entries
//                    Cursor otherResCursor = tripDb.fetchAllOtherResForEvent(ev.id);
//                    if (otherResCursor.getCount() > 0) {
//                        //delete all entries
//                        List<OtherResInfo> otherResInfos = tripDb.getOtherResInfo(ev.id);
//                        for (OtherResInfo otherRes : otherResInfos) {
//                            tripDb.deleteEntry("otherResInfo", otherRes.getId());
//                        }
//                    }
                tripDb.deleteEntry("eventInfo", ev.getId());
            }
        }
        tripDb.deleteEntry("tripInfo", tripId);
        if (tripKind != null) {

            Intent intent = new Intent(ViewTripItineraryActivity.this, TripListActivity.class);
            intent.putExtra("tripKind", tripKind);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent); // when back pressed/save pressed go to TripListActivity
        } else {
            Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
            //TODO go to triplistActivity MUST:-pass tripkind.
            startActivity(intent);
        }
    }
}