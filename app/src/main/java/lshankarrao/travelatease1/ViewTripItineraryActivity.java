package lshankarrao.travelatease1;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class ViewTripItineraryActivity extends AppCompatActivity {

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
    String mEventDetails = "Trip includes the below Events" + "\n";
    String mAllHotelDetails = "\n Hotel Reservation details for the Event: " + "\n";
    List<String> mAllEventsDetailsInOnePlace;//not used
    TripInfo tripInfo;

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

        String eventTimings;
        String eventPlace;
        String eventTitle;

        mAllEventsDetailsInOnePlace = new ArrayList<String>();
        if (cursor.getCount() > 0) {
            List<EventInfo> eventInfos = tripDb.getEventInfo(tripId);
            for (EventInfo ev : eventInfos) {
                eventPlace = "<br><b>Place:</b> " + ev.city + ", " + ev.state + ", " + ev.country + "<br>";
                eventTimings = "<h2 style=\"color:blue\"><i>Timings:</i> " + ev.startDate + " @ " + ev.startTime + " to " + ev.endDate + "<i><b>@</b></i>" + ev.endTime + "</h2>";
                eventTitle = ev.title;
                Log.i("event title: ", ev.title);
                Log.i("event id ", ev.id + "");
                mEventDetails = mEventDetails + "<br>" +
                        "<br>" + eventTitle + "<br>" +
                        "<br>" + eventPlace + "<br>" +
                        "<br>" + eventTimings + "<br>";
                mAllEventsDetailsInOnePlace.add(mEventDetails);
                Cursor hotelCursor = tripDb.fetchAllHotelsForEvent(ev.id);
                if (hotelCursor.getCount() > 0) {
                    String hotelName, hotelPlace, hotelTimings, hotelConfirmationNo;
                    List<HotelInfo> hotelInfos = tripDb.getHotelsInfo(ev.id);
                    for (HotelInfo hotelInfo : hotelInfos) {
                        hotelName = "Hotel: " + hotelInfo.hotel;
                        hotelPlace = "Address: " + hotelInfo.address;
                        hotelTimings = "Check-in: " + hotelInfo.checkin_date + " at " + hotelInfo.checkin_time +
                                "\nCheck-out: " + hotelInfo.checkout_date + " at " + hotelInfo.checkout_time;
                        hotelConfirmationNo = "Confirmation No: " + hotelInfo.confirmationNo;
                        mAllHotelDetails = mAllHotelDetails + hotelName + "\n" + hotelPlace + "\n" + hotelTimings + "\n" + hotelConfirmationNo;
                    }
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
            Intent intent = new Intent(ViewTripItineraryActivity.this, EditTripActivity.class);
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
            // Html.TagHandler

            //
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter email ids below(comma separated)");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
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
                    //i.putExtra(Intent.EXTRA_TEXT   , mfullTripDetails+ mEventDetails);
                    i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder().append("<h1 style=\"background-color:yellow;color:blue;\">This is a heading</h1>")
                            .append("<p><b>Some Content</b></p>")
                            .append("<small><p>More content</p></small>")
                            .toString()));
                    //("<font color='#0023FF'>mfullTripDetails</font>"+mfullTripDetails + mEventDetails + mAllHotelDetails));
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
            //TODO
            Intent locationIntent = new Intent(ViewTripItineraryActivity.this,
                    LocationBasedNotifierActivity.class);
            String place = tripInfo.city + " " + tripInfo.state + " " + tripInfo.country;
            locationIntent.putExtra("tripPlace", place);
            locationIntent.putExtra("tripId", tripId);
            startActivity(locationIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewTripItineraryActivity.this, MainActivity.class);
        //TODO go to triplistActivity MUST:-pass tripkind.
        startActivity(intent); // when back pressed/save pressed go to TripListActivity

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