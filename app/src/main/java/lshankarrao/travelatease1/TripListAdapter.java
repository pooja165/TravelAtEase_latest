package lshankarrao.travelatease1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by lakshmi on 5/23/2016.
 */
public class TripListAdapter extends CursorAdapter {

    String[] st_mon_day_year, end_mon_day_year;
    TripInfo tripInfo;
    TripDbHelper tripDb;
    public TripListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.trip_list_custom_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //get title
        String tripTitle = cursor.getString(cursor.getColumnIndex("title"));

        Log.i("new trip name displayed", tripTitle);
        ((TextView)view.findViewById(R.id.textViewTLcustomRowTitle)).setText(tripTitle);

        int tripId = cursor.getInt(cursor.getColumnIndex("_id"));

        tripDb = new TripDbHelper(context);
        tripInfo = tripDb.getTripInfo(tripId);

        st_mon_day_year = tripDb.getDateFromMilli(tripInfo.stTimeMillis, "MMM/dd/yyyy");
        end_mon_day_year = tripDb.getDateFromMilli(tripInfo.endTimeMillis, "MMM/dd/yyyy");


                //cursor.getString(cursor.getColumnIndex("startDate")) +" to " + cursor.getString(cursor.getColumnIndex("endDate"));

        String startDateAndNumEvents ="starts "+st_mon_day_year[2] + ", "+ st_mon_day_year[0] + " " + st_mon_day_year[1];;

        Cursor c = tripDb.fetchAllEventsForTrip(tripId);
        int count = c.getCount();
        if(count == 1){
            startDateAndNumEvents = startDateAndNumEvents+ "\n"+count+" Event";
        }
        else if(count > 1){
            startDateAndNumEvents = startDateAndNumEvents+ "\n"+count+" Events";
        }

        ((TextView)view.findViewById(R.id.textViewTLcustomRowDuration)).setText(startDateAndNumEvents);


    }
}
