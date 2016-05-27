package lshankarrao.travelatease1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class AlarmReceiver extends BroadcastReceiver{
    Cursor cursor;
    TripDbHelper tripDb;
    public final int VISIBILITY_PRIVATE = 0;

    private static int notificationId = 1111;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Creates an explicit intent for an Activity in your app
        int requestCode=1253;

        Toast.makeText(context, "Recieved Req.Sending notification", Toast.LENGTH_LONG).show();
        Log.i("ARbande", "hi");


        int id = intent.getIntExtra("tripId", -1);
        Log.i("AR alli rx tripID: ", id+"");
        if(id == -1){
            Log.i("trip id invalid. val: ", id+"");
            return;
        }
        String title = intent.getStringExtra("tripTitle");
        Log.i("AR alli rx trip title: ", title+"");

        Intent resultIntent = new Intent(context, ViewTripItineraryActivity.class);
        resultIntent.putExtra("id",id);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, notificationId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        tripDb = new TripDbHelper(context);
        cursor = tripDb.fetchAllEventsForTrip(id);
        Log.i("no. of events: ", cursor.getCount() + "");
        int numEvents = cursor.getCount();
        List<String> eventsList = new ArrayList<String>();
        List<EventInfo> eventInfoList = tripDb.getEventInfo(id);
        String s;
        int i = 1;
        for(EventInfo ev: eventInfoList){
            s = "Event "+i+": "+ev.getTitle() + " @ " + ev.getCity() +" starts "+ ev.getStartDate();
            eventsList.add(s);
            Log.i("event totle: ", ev.title);
            Log.i("event id ", ev.id+"");
            i++;
        }
        Calendar calendar = Calendar.getInstance();
        long when = calendar.getTimeInMillis();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.jinggling_on)
                        .setContentTitle("Start Planning your trip...!")
                        .setContentText(intent.getStringExtra("tripTitle"))
                        .setContentIntent(resultPendingIntent)
                        .setWhen(when)
                        .setVisibility(VISIBILITY_PRIVATE)
                        .setAutoCancel(true);
        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

        mBuilder.setStyle(createBigContent(numEvents, eventsList));

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }
    NotificationCompat.InboxStyle createBigContent(int numEvents, List<String> eventsList) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        //String[] events = {"First event", "second event", "Third event", "Forth event"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Start Planning your trip...!");
        Log.i("events list size: ", eventsList.size()+"");
        // Moves events into the expanded layout
        for (int i=0; i < eventsList.size(); i++) {
            inboxStyle.addLine(eventsList.get(i));
        }
        inboxStyle.setSummaryText("There are total of " + numEvents + " events");
        return inboxStyle;
    }
}
