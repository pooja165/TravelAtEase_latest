package lshankarrao.travelatease1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class AlarmReceiver extends BroadcastReceiver{

    private static int notificationId = 1111;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Creates an explicit intent for an Activity in your app
        int requestCode=1253;

        Toast.makeText(context, "Alarm Recieved.", Toast.LENGTH_LONG).show();
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

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.jinggling_on)
                        .setContentTitle("A simple notification!")
                        .setContentText(intent.getStringExtra("tripTitle"))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

        //mBuilder.setStyle(createBigContent());

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }
}
