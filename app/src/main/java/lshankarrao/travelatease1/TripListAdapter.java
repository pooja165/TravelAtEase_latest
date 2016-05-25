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

        String duration = cursor.getString(cursor.getColumnIndex("startDate")) +" to " + cursor.getString(cursor.getColumnIndex("endDate"));

        ((TextView)view.findViewById(R.id.textViewTLcustomRowDuration)).setText(duration);


    }
}
