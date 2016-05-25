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
 * Created by vijay on 5/24/2016.
 */
public class EventListAdapter extends CursorAdapter {
    public EventListAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.event_list_custom_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //get title
        Log.i("bV ", cursor.getCount() + "");
        //        String eventTitle = cursor.getString(cursor.getColumnIndex("city"));
//        Log.i("new trip name displayed", eventTitle);
//        ((TextView)view.findViewById(R.id.textViewVTAEventListPlace)).setText(eventTitle);
        String eventPlace = cursor.getString(cursor.getColumnIndex("city"));
        Log.i("new trip name displayed", eventPlace);
        ((TextView)view.findViewById(R.id.textViewVTAEventListPlace)).setText(eventPlace);

        String eventFrom = cursor.getString(cursor.getColumnIndex("startDate"));
        Log.i("new trip name displayed", eventFrom);
        ((TextView)view.findViewById(R.id.textViewVTAEventListFromDate)).setText(eventFrom);

        String eventTo = cursor.getString(cursor.getColumnIndex("endDate"));
        Log.i("new trip name displayed", eventTo);
        ((TextView)view.findViewById(R.id.textViewVTAEventListToDate)).setText(eventTo);


    }
}
